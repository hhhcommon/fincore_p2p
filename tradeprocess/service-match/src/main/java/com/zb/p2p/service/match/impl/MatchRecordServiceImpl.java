package com.zb.p2p.service.match.impl;

import com.alibaba.fastjson.JSONObject;
import com.zb.fincore.ams.common.dto.BaseResponse;
import com.zb.fincore.ams.facade.DebtRightInfoFacade;
import com.zb.fincore.ams.facade.dto.req.CreateDebtRightInfoRequest;
import com.zb.fincore.ams.facade.model.InvestInfoModel;
import com.zb.fincore.common.enums.ResultCodeEnum;
import com.zb.fincore.common.utils.BeanUtils;
import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.fincore.common.utils.DateUtils;
import com.zb.fincore.common.utils.NumberUtils;
import com.zb.p2p.GlobalVar;
import com.zb.p2p.common.exception.BusinessException;
import com.zb.p2p.dao.master.MatchRecordDAO;
import com.zb.p2p.entity.InterfaceRetryEntity;
import com.zb.p2p.entity.MatchRecordEntity;
import com.zb.p2p.enums.InterfaceRetryBusinessTypeEnum;
import com.zb.p2p.enums.InterfaceRetryStatusEnum;
import com.zb.p2p.enums.LoanStatusEnum;
import com.zb.p2p.facade.api.req.AssetMatchReq;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.api.resp.order.MatchRecordDTO;
import com.zb.p2p.facade.service.internal.OrderInternalService;
import com.zb.p2p.facade.service.internal.dto.LoanRequestDTO;
import com.zb.p2p.facade.service.internal.dto.OrderDTO;
import com.zb.p2p.queue.MnsMsgProducer;
import com.zb.p2p.service.callback.TXSCallBackService;
import com.zb.p2p.service.callback.api.req.NotifyTxsAssetMatchResultReq;
import com.zb.p2p.service.callback.api.resp.NotifyResp;
import com.zb.p2p.service.common.DistributedLockService;
import com.zb.p2p.service.common.InterfaceRetryService;
import com.zb.p2p.service.match.AssetMatchService;
import com.zb.p2p.service.match.MatchRecordService;
import com.zb.p2p.utils.ExecutorsFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 预匹配处理业务操作类
 *
 * @author zhangxin
 * @create 2017-08-31 11:24
 */
@Service
public class MatchRecordServiceImpl implements MatchRecordService {

    private static Logger logger = LoggerFactory.getLogger(MatchRecordServiceImpl.class);

    @Autowired
    private OrderInternalService orderInternalService;

    @Autowired
    private AssetMatchService assetMatchService;

    @Autowired
    private DistributedLockService distributedLockService;
    @Value("${dubbo.registry.address:192.168.0.65:2181}")
    private String zookeeperConnectionString;

    @Value("${ext.gateway.enable:true}")
    private boolean enableExtGateWay;

    private ExecutorService executorService = ExecutorsFactory.getExecutorService("matchPool");

    @Autowired
    private InterfaceRetryService interfaceRetryService;

    @Autowired
    private TXSCallBackService txsCallBackService;

    @Autowired
    private DebtRightInfoFacade debtRightInfoFacade;

    @Autowired
    private MatchRecordDAO matchRecordDAO;

    @Autowired
    private MnsMsgProducer mnsMsgProducer;

    @Value("${QUEUE-ZB-P2P-ORDER-COMPLETE}")
    private String orderQueue;

    /**
     * 资产预匹配入口
     *
     * @return
     */
    @Override
    public CommonResp<String> assetMatch(final AssetMatchReq assetMatchReq) {
        try {
            logger.debug("assetMatch start, try to get lock ....");
            try {
                distributedLockService.tryLock(GlobalVar.GLOBAL_MATCH_LOCK_KEY);
                logger.debug("assetMatch start , get lock, start process ....");
                if (null != assetMatchReq && !CollectionUtils.isNullOrEmpty(assetMatchReq.getProductCodes())) {
                    assetMatchProcessForMultiProduct(assetMatchReq);

                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //匹配完成通知TXS
                                notifyTxsAssetMatchResult(assetMatchReq);
                            } catch (Exception e) {
                                logger.error("after asset match ,notify txs catch exception :", e);
                            }
                        }
                    });
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //匹配完成通知AMS
                                notifyAmsAssetMatchResult(assetMatchReq);
                            } catch (Exception e) {
                                logger.error("after asset match ,notify txs catch exception :", e);
                            }
                        }
                    });
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //通知MSD未匹配借款
                                notifyMsdUnMatchLoanResult(assetMatchReq);
                            } catch (Exception e) {
                                logger.error("after asset match ,notify msd unmatched loan catch exception :", e);
                            }
                        }
                    });
                }
            } catch (Exception e) {
                logger.error("", e);
            } finally {
                distributedLockService.unLock(GlobalVar.GLOBAL_MATCH_LOCK_KEY);
            }
        } catch (Exception e) {
            logger.error("", e);
            return new CommonResp(ResultCodeEnum.FAIL.code(), e.getMessage());
        }
        return new CommonResp(ResultCodeEnum.SUCCESS.code(), ResultCodeEnum.SUCCESS.desc());
    }

    /**
     * 同一天多产品资产匹配调用处理
     */
    @Override
    public void assetMatchProcessForMultiProduct(AssetMatchReq assetMatchReq) {
        logger.debug("assetMatchProcessForMultiProduct 资产匹配开始....");
        Date establishDate = DateUtils.parse(DateUtils.format(new Date(), DateUtils.DEFAULT_DATA_FORMAT),
                DateUtils.DEFAULT_DATA_FORMAT);

        //查询借款状态为 "借款待匹配"的借款
        Map<String, Object> params = new HashMap<>();
        params.put("productCodeList", assetMatchReq.getProductCodes());
        List<LoanRequestDTO> loanRequestDTOList = orderInternalService.getWaitMatchLoanRequestList(params);
        if (CollectionUtils.isNullOrEmpty(loanRequestDTOList)) {
            return;
        }
        logger.debug("assetMatchProcessForMultiProduct 当日借款列表记录：{}", loanRequestDTOList.size());
        //按照资产池编号，气息日期，期限分组
        Map<String, List<LoanRequestDTO>> loanRequestDTOMap = new HashMap<String, List<LoanRequestDTO>>();
        for (LoanRequestDTO loanRequestDTO : loanRequestDTOList) {
            String productCode = loanRequestDTO.getProductCode();
            if (StringUtils.isBlank(productCode)) {
                continue;
            }
            if (null == loanRequestDTOMap.get(productCode)) {
                List<LoanRequestDTO> tempList = new ArrayList<LoanRequestDTO>();
                tempList.add(loanRequestDTO);
                loanRequestDTOMap.put(productCode, tempList);
            } else {
                loanRequestDTOMap.get(productCode).add(loanRequestDTO);
            }

        }

        //循环loanRequestDTOMap
        for (String productCode : loanRequestDTOMap.keySet()) {
            //单个产品借款记录处理;
            logger.debug("单个产品资产组匹配开始 productCode :{}", productCode);
            assetMatchProcessForSingleProduct(productCode, loanRequestDTOMap.get(productCode), establishDate);
        }
    }

    /**
     * 同一个产品的资产匹配
     *
     * @param productCode
     */
    public void assetMatchProcessForSingleProduct(String productCode, List<LoanRequestDTO> loanRequestDTOList, Date establishDate) {
        logger.debug("assetMatchProcessForSingleProduct 单个产品资产组匹配开始....");
        if (CollectionUtils.isNullOrEmpty(loanRequestDTOList)) {
            return;
        }
        logger.debug("assetMatchProcessForSingleProduct 单个产品资产组匹配 productCode:{}, loanRequestList.size={}", productCode, loanRequestDTOList.size());
        for (int i = 0; i < loanRequestDTOList.size(); i++) {
            LoanRequestDTO loanRequestDTO = loanRequestDTOList.get(i);

            loanRequestDTO.setEstablishTime(establishDate);
            loanRequestDTO.setValueStartTime(establishDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(loanRequestDTO.getValueStartTime());
            calendar.add(Calendar.DATE, loanRequestDTO.getLockDate()-1);
            loanRequestDTO.setValueEndTime(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
            loanRequestDTO.setExpireDate(calendar.getTime());

            try {
                //这里全局锁住预约单，跟交易互斥
                distributedLockService.tryLock(GlobalVar.GLOBAL_RESERVATION_LOCK_KEY);
                //单个资产匹配，开启单个事物，一个匹配失败不影响其他资产匹配
                Map<String, Object> resultMap = assetMatchService.dealAssetMatchProcess(loanRequestDTO, i);

                String mark = String.valueOf(resultMap.get("mark"));
                logger.debug("mark={},matchRecordList={}", mark, resultMap.get("matchRecordList"));
                //1代表需要结束当前匹配的线程
                if ("1".equals(mark)) {
                    return;
                } else if ("0".equals(mark) && null != resultMap.get("matchRecordList")) {
                    List<MatchRecordEntity> matchRecordEntityList = (List<MatchRecordEntity>) resultMap.get("matchRecordList");
                    if (enableExtGateWay) {
                        //通知马上贷
                        try {
                            if(!CollectionUtils.isNullOrEmpty(matchRecordEntityList)) {
                                assetMatchService.notifyMsdAssetMatchResult(loanRequestDTO, matchRecordEntityList, "1");
                            }
                        } catch (Exception e) {
                            logger.error("notify msd catch exception :", e);
                        }
                    }

                    //发布放款事件
                    logger.debug("当个借款匹配成功，发布放款消息 start, loanNO :{}", loanRequestDTO.getLoanNo());
                    mnsMsgProducer.putMessage(orderQueue, loanRequestDTO.getLoanNo());
                    logger.debug("当个借款匹配成功，发布放款消息 end, loanNO :{}", loanRequestDTO.getLoanNo());
                }
            } catch (Exception e) {
                logger.error("assetMatchProcessForSingleProduct 单个产品资产 asset code:{} 匹配异常：", loanRequestDTO.getAssetCode(), e);
            } finally {
                try {
                    distributedLockService.unLock(GlobalVar.GLOBAL_RESERVATION_LOCK_KEY);
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        }
        logger.debug("assetMatchProcessForSingleProduct 单个产品资产组匹配结束");
    }


    /**
     * 通知唐小僧资产匹配处理
     *
     * @param
     * @throws Exception
     */
    @Override
    public void notifyTxsAssetMatchResult(AssetMatchReq assetMatchReq) throws Exception {
        NotifyResp resp = null;
        try {
            resp =  doNotifyTxsAssetMatchResult(assetMatchReq);
            logger.debug("通知唐小僧投资匹配结果响应参数：" + resp);
            // 判断远程URl调用是否成功
            if (null != resp && !"0000".equals(resp.getCode())) {
                throw new BusinessException(ResultCodeEnum.FAIL.code(), "通知唐小僧投资匹配结果调用失败:" + resp.getMsg());
            }
        } catch (Exception e) {
            logger.info("匹配通知唐小僧投资匹配结果调用失败", e);
            InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
            interfaceRetryEntity.setBusinessType(InterfaceRetryBusinessTypeEnum.ASSET_MATCH_NOTIFY_TXS.getCode());
            interfaceRetryEntity.setBusinessNo(null != assetMatchReq ? JSONObject.toJSONString(assetMatchReq) : "");
            interfaceRetryEntity.setRequestParam(null != assetMatchReq ? JSONObject.toJSONString(assetMatchReq) : null);
            interfaceRetryEntity.setResponseParam(null != resp ? JSONObject.toJSONString(resp) : null);
            interfaceRetryEntity.setStatus(InterfaceRetryStatusEnum.INIT.getCode());
            interfaceRetryEntity.setRetryTimes(0);
            interfaceRetryEntity.setMemo("");
            interfaceRetryEntity.setProductCode("");
            interfaceRetryService.saveInterfaceRetryRecord(interfaceRetryEntity);
        }
    }

    /**
     * 通知唐小僧资产匹配处理
     *
     * @param
     * @throws Exception
     */
    @Override
    public NotifyResp doNotifyTxsAssetMatchResult(AssetMatchReq assetMatchReq) throws Exception {
        List<NotifyTxsAssetMatchResultReq> assetMatchRecordList = null;
        logger.debug("通知唐小僧投资匹配结果产品编号：" + assetMatchReq.getProductCodes());
        Map<String, Object> params = new HashMap<>();
        params.put("productCodeList", assetMatchReq.getProductCodes());
        List<OrderDTO> orderDTOList = orderInternalService.queryOrderListByParams(params);
        if (CollectionUtils.isNullOrEmpty(orderDTOList)) {
            logger.debug("通知唐小僧投资匹配结果产品编号：" + assetMatchReq.getProductCodes()+",未找到投资订单，直接返回。");
            return null;
        }

        //请求参数封装
        assetMatchRecordList = new ArrayList<>();
        for (OrderDTO orderDTO : orderDTOList) {
            NotifyTxsAssetMatchResultReq req = new NotifyTxsAssetMatchResultReq();
            req.setExtOrderNo(orderDTO.getExtOrderNo());
            req.setAmount(NumberUtils.roundN2(orderDTO.getMatchedAmount()));
            req.setTradeNo(orderDTO.getOrderNo());
            req.setType("MATCH");
            req.setStatus(orderDTO.getStatus());
            assetMatchRecordList.add(req);
        }
        logger.debug("通知唐小僧投资匹配结果请求参数：" + assetMatchRecordList);
        return txsCallBackService.tradeNotifyOrder(assetMatchRecordList);
    }

    /**
     * 通知Ams资产匹配处理
     *
     * @param
     * @throws Exception
     */
    @Override
    public void notifyAmsAssetMatchResult(AssetMatchReq assetMatchReq) throws Exception {
        BaseResponse resp = null;
        try {
            resp =  doNotifyAmsAssetMatchResult(assetMatchReq);
            logger.debug("通知AMS投资匹配结果响应参数：" + resp);
            // 判断远程URl调用是否成功
            if (null != resp && !"0000".equals(resp.getRespCode())) {
                throw new BusinessException(ResultCodeEnum.FAIL.code(), "通知AMS投资匹配结果调用失败:" + resp.getRespMsg());
            }
        } catch (Exception e) {
            logger.info("匹配通知AMS投资匹配结果调用失败", e);
            InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
            interfaceRetryEntity.setBusinessType(InterfaceRetryBusinessTypeEnum.ASSET_MATCH_NOTIFY_AMS.getCode());
            interfaceRetryEntity.setBusinessNo(null != assetMatchReq ? JSONObject.toJSONString(assetMatchReq) : "");
            interfaceRetryEntity.setRequestParam(null != assetMatchReq ? JSONObject.toJSONString(assetMatchReq) : null);
            interfaceRetryEntity.setResponseParam(null != resp ? JSONObject.toJSONString(resp) : null);
            interfaceRetryEntity.setStatus(InterfaceRetryStatusEnum.INIT.getCode());
            interfaceRetryEntity.setRetryTimes(0);
            interfaceRetryEntity.setMemo("");
            interfaceRetryEntity.setProductCode("");
            interfaceRetryService.saveInterfaceRetryRecord(interfaceRetryEntity);
        }
    }

    /**
     * 通知Ams资产匹配处理
     *
     * @param
     * @throws Exception
     */
    @Override
    public BaseResponse doNotifyAmsAssetMatchResult(AssetMatchReq assetMatchReq) throws Exception {
        List<CreateDebtRightInfoRequest> assetMatchRecordList = null;
        logger.debug("通知AMS投资匹配结果产品编号：" + assetMatchReq.getProductCodes());
        Map<String, Object> params = new HashMap<>();
        params.put("productCodeList", assetMatchReq.getProductCodes());
        List<LoanRequestDTO> loanRequestDTOList = orderInternalService.queryLoanListByParams(params);
        if (CollectionUtils.isNullOrEmpty(loanRequestDTOList)) {
            logger.debug("通知AMS投资匹配结果产品编号：" + assetMatchReq.getProductCodes()+",未找到借款订单，直接返回。");
            return null;
        }

        //请求参数封装
        assetMatchRecordList = new ArrayList<>();
        for (LoanRequestDTO loanRequestDTO : loanRequestDTOList) {
            if(LoanStatusEnum.LOAN_UNMATCHED.getCode().equals(loanRequestDTO.getLoanStatus())){
                continue;
            }

            List<MatchRecordEntity> list = matchRecordDAO.selectListByLoanNo(loanRequestDTO.getLoanNo());
            List<InvestInfoModel> investInfoModels = new ArrayList<>();
            for(MatchRecordEntity recordEntity : list){
                InvestInfoModel investInfoModel = new InvestInfoModel();
                investInfoModel.setInvestNo(recordEntity.getExtOrderNo());
                investInfoModels.add(investInfoModel);
            }

            CreateDebtRightInfoRequest req = new CreateDebtRightInfoRequest();
            req.setLoanOrderNo(loanRequestDTO.getLoanNo());
            req.setAssetType("1");
            req.setMatchSuccAmt(loanRequestDTO.getMatchedAmount());
            req.setMatchSuccDate(loanRequestDTO.getModifyTime());
            req.setMatchSuccMode("1");
            req.setProductCode(loanRequestDTO.getProductCode());
            req.setProductName(loanRequestDTO.getProductName());
            req.setProductType("1");
            req.setEstablishTime(loanRequestDTO.getEstablishTime());
            req.setValueStartTime(loanRequestDTO.getValueStartTime());
            req.setValueEndTime(loanRequestDTO.getValueEndTime());
            req.setExpireTime(loanRequestDTO.getExpireDate());
            req.setAssetStatus("1");
            req.setInvestInfoList(investInfoModels);
            assetMatchRecordList.add(req);
        }
        logger.debug("通知AMS投资匹配结果请求参数：" + assetMatchRecordList);
        if (CollectionUtils.isNullOrEmpty(assetMatchRecordList)) {
            logger.debug("本次没有匹配成功的借款不通知AMS,返回。");
            return null;
        }

        return debtRightInfoFacade.createDebtRight(assetMatchRecordList);
    }

    /**
     * 通知MSD未匹配资产
     *
     * @param
     * @throws Exception
     */
    public void notifyMsdUnMatchLoanResult(AssetMatchReq assetMatchReq) throws Exception {
        BaseResponse resp = null;
        try {
            logger.debug("通知MSD未匹配借款产品编号：" + assetMatchReq.getProductCodes());
            Map<String, Object> params = new HashMap<>();
            List<String> loanStatusList = new ArrayList<>();
            loanStatusList.add(LoanStatusEnum.LOAN_UNMATCHED.getCode());

            params.put("productCodeList", assetMatchReq.getProductCodes());
            params.put("loanStatusList", loanStatusList);
            List<LoanRequestDTO> loanRequestDTOList = orderInternalService.queryLoanListByParams(params);
            if (CollectionUtils.isNullOrEmpty(loanRequestDTOList)) {
                logger.debug("通知MSD未匹配借款产品编号：" + assetMatchReq.getProductCodes()+",未找到借款订单，直接返回。");
                return;
            }

            for (LoanRequestDTO loanRequestDTO : loanRequestDTOList) {
                assetMatchService.notifyMsdAssetMatchResult(loanRequestDTO, null, "2");
            }
        } catch (Exception e) {
            logger.info("通知MSD未匹配资产失败", e);
        }
    }
}
