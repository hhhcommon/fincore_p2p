package com.zb.p2p.service.match.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zb.fincore.common.enums.ResultCodeEnum;
import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.fincore.common.utils.DateUtils;
import com.zb.fincore.common.utils.NumberUtils;
import com.zb.p2p.common.exception.BusinessException;
import com.zb.p2p.dao.master.MatchRecordDAO;
import com.zb.p2p.entity.InterfaceRetryEntity;
import com.zb.p2p.entity.MatchRecordEntity;
import com.zb.p2p.enums.*;
import com.zb.p2p.facade.service.internal.OrderInternalService;
import com.zb.p2p.facade.service.internal.dto.LoanRequestDTO;
import com.zb.p2p.facade.service.internal.dto.OrderDTO;
import com.zb.p2p.service.callback.MSDCallBackService;
import com.zb.p2p.service.callback.api.req.NotifyMsdAssetMatchResultReq;
import com.zb.p2p.service.callback.api.req.NotifyMsdMatchDTO;
import com.zb.p2p.service.callback.api.resp.NotifyResp;
import com.zb.p2p.service.common.DistributedSerialNoService;
import com.zb.p2p.service.common.InterfaceRetryService;
import com.zb.p2p.service.match.AssetMatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 具体的资产匹配业务操作
 *
 * @author zhangxin
 * @create 2017-08-31 11:24
 */
@Service
public class AssetMatchServiceImpl implements AssetMatchService {

    private static Logger logger = LoggerFactory.getLogger(AssetMatchServiceImpl.class);

    @Autowired
    private OrderInternalService orderInternalService;

    @Autowired
    private InterfaceRetryService interfaceRetryService;

    @Autowired
    private MatchRecordDAO matchRecordDAO;

    @Value("${ext.gateway.enable:true}")
    private boolean enableExtGateWay;

    @Autowired
    private MSDCallBackService msdCallBackService;

    @Autowired
    private DistributedSerialNoService distributedSerialNoService;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Map<String, Object> dealAssetMatchProcess(LoanRequestDTO loanRequestDTO, int i) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        String key = "mark";
        resultMap.put(key, "0");
        logger.debug("dealAssetMatchProcess 单个资产匹配start assetCode={}, index:{}", loanRequestDTO.getAssetCode(), i);
        //根据借款中关联的产品编号 查询订单记录 （查询借款状态为 "待匹配" 或 "匹配失败" 或 "部分匹配成功"的记录）
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setProductCode(loanRequestDTO.getProductCode());
        try {
            List<OrderDTO> orderDTOList = orderInternalService.queryOrderListByProductCodeForMatch(orderDTO);
            if (CollectionUtils.isNullOrEmpty(orderDTOList)) {
                resultMap.put(key, "1");
                return resultMap;
            }
            logger.debug("dealAssetMatchProcess index:{},单个资产匹配start assetCode={}, 查询到的预约单列表记录数：{}",
                    i, loanRequestDTO.getAssetCode(), orderDTOList.size());
            try {
                //具体的资产资金匹配逻辑
                List<MatchRecordEntity> matchRecordEntityList = doAssetMatchProcess(loanRequestDTO, orderDTOList);
                resultMap.put("matchRecordList", matchRecordEntityList);
            } catch (Exception e) {
                logger.error("资产匹配失败 assetCode：{}, error msg: {}", loanRequestDTO.getAssetCode(), ((BusinessException) e).getResultMsg());
                throw e;
            }
            return resultMap;
        } catch (Exception e) {
            logger.error("", e);
            throw e;
        }
    }


    /**
     * 单个资产匹配处理
     *
     * @param loanRequestDTO
     * @param orderDTOList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<MatchRecordEntity> doAssetMatchProcess(LoanRequestDTO loanRequestDTO, List<OrderDTO> orderDTOList) throws Exception {
        //资产金额
        BigDecimal loanAmount = loanRequestDTO.getLoanAmount();
        //资产的剩余待匹配金额
        BigDecimal restWaitMatchLoanAmount = NumberUtils.subtract(loanAmount, loanRequestDTO.getMatchedAmount());
        List<MatchRecordEntity> matchRecordList = new ArrayList<MatchRecordEntity>();
        List<OrderDTO> matchOrderList = new ArrayList<OrderDTO>();
        logger.info("in reservation ....");
        for (OrderDTO orderDTO : orderDTOList) {
            if (NumberUtils.isEqual(restWaitMatchLoanAmount, BigDecimal.ZERO)) {
                break;
            }
            //该笔预约单可用金额
            BigDecimal usefulAmount = NumberUtils.subtract(orderDTO.getInvestAmount(), orderDTO.getMatchedAmount());
            if(NumberUtils.isSmallerOrEqual(usefulAmount, BigDecimal.ZERO)){
                continue;
            }
            // 该笔资金匹配该资产的金额
            BigDecimal orderMatchAmount = BigDecimal.ZERO;
            if (NumberUtils.isGreaterOrEqual(usefulAmount, restWaitMatchLoanAmount)) {
                orderMatchAmount = restWaitMatchLoanAmount;
            } else {
                orderMatchAmount = usefulAmount;
            }
            restWaitMatchLoanAmount = NumberUtils.subtract(restWaitMatchLoanAmount, orderMatchAmount);

            MatchRecordEntity matchRecord = new MatchRecordEntity();

            String creditorNo = distributedSerialNoService.generatorSerialNoByIncrement(SequenceEnum.CREDITOR, loanRequestDTO.getSaleChannel(), 1);
            matchRecord.setCreditorNo(creditorNo);
            matchRecord.setMemberId(orderDTO.getMemberId());
            matchRecord.setLoanMemberId(loanRequestDTO.getMemberId());
            matchRecord.setLoanNo(loanRequestDTO.getLoanNo());
            matchRecord.setLoanAmount(loanRequestDTO.getLoanAmount());
            matchRecord.setProductCode(orderDTO.getProductCode());
            matchRecord.setExtOrderNo(orderDTO.getExtOrderNo());
            matchRecord.setOrderNo(orderDTO.getOrderNo());
            matchRecord.setMatchStatus(MatchRecordStatusEnum.MATCH_SUCCESS.getCode());
            matchRecord.setMatchedAmount(orderMatchAmount);
            matchRecord.setCreateTime(new Date());

            orderDTO.setMatchedAmount(NumberUtils.sum(orderDTO.getMatchedAmount(), orderMatchAmount));
            BigDecimal restAmount = NumberUtils.subtract(orderDTO.getInvestAmount(), orderDTO.getMatchedAmount());
            if (NumberUtils.isEqual(restAmount, BigDecimal.ZERO)) {
                orderDTO.setStatus(OrderStatusEnum.MATCH_SUCCESS.getCode());
            }else if (NumberUtils.isGreater(orderMatchAmount, BigDecimal.ZERO)){
                orderDTO.setStatus(OrderStatusEnum.PARTLY_MATCH_SUCCESS.getCode());
            }

            loanRequestDTO.setMatchedAmount(NumberUtils.sum(loanRequestDTO.getMatchedAmount(), orderMatchAmount));

            matchOrderList.add(orderDTO);
            matchRecordList.add(matchRecord);
        }
        logger.info("close reservation ....");

        //批量插入匹配记录 200条一批
        List<List<MatchRecordEntity>> batchMatchRecordList = (List<List<MatchRecordEntity>>)Lists.partition(matchRecordList, 200);
        for (List<MatchRecordEntity> batchMatchRecords : batchMatchRecordList) {
            matchRecordDAO.batchInsert(batchMatchRecords);
        }

        //批量更新预约单记录 200一批
        List<List<OrderDTO>> batchOrderList = (List<List<OrderDTO>>)Lists.partition(matchOrderList, 200);
        for (List<OrderDTO> batchOrders : batchOrderList) {
            orderInternalService.updateOrderForBatchById(batchOrders);
        }

        //更新借款申请 即 资产表的借款状态;
        if (NumberUtils.isGreater(restWaitMatchLoanAmount, BigDecimal.ZERO)) {
            loanRequestDTO.setLoanStatus(LoanStatusEnum.PARTLY_LOAN_SUCCESS.getCode());
        }else{
            loanRequestDTO.setLoanStatus(LoanStatusEnum.LOAN_SUCCESS.getCode());
        }
        orderInternalService.updateMatchStatusAndAmountById(loanRequestDTO);
        logger.info("end reservation ....");
        return matchRecordList;
    }

    @Override
    public void notifyMsdAssetMatchResult(LoanRequestDTO loanRequestDTO, List<MatchRecordEntity> matchRecordList, String status) throws Exception {
        NotifyResp resp = null;
        NotifyMsdAssetMatchResultReq req = new NotifyMsdAssetMatchResultReq();
        try {
            req.setTransactionNo(loanRequestDTO.getLoanNo());
            req.setOrderNo(loanRequestDTO.getLoanNo());
            req.setNotifyType("3");
            req.setStatus(status);
            req.setTradeAmount(String.valueOf(loanRequestDTO.getMatchedAmount()));
            req.setRequestTime(DateUtils.format(new Date(), DateUtils.DEFAULT_DATETIME_FORMAT));

            List<NotifyMsdMatchDTO> data = new ArrayList<>();
            if(!CollectionUtils.isNullOrEmpty(matchRecordList)) {
                //请求参数封装
                for (MatchRecordEntity matchRecord : matchRecordList) {
                    NotifyMsdMatchDTO notifyMsdMatchDTO = new NotifyMsdMatchDTO();
                    notifyMsdMatchDTO.setFinanceNo(matchRecord.getExtOrderNo());
                    notifyMsdMatchDTO.setFinanceUserNo(matchRecord.getMemberId());
                    notifyMsdMatchDTO.setMatchAmount(String.valueOf(matchRecord.getMatchedAmount()));
                    notifyMsdMatchDTO.setMatchTime(DateUtils.format(matchRecord.getCreateTime(), DateUtils.DEFAULT_DATETIME_FORMAT));
                    data.add(notifyMsdMatchDTO);
                }
            }
            req.setData(data);

            resp = doNotifyMsdAssetMatchResult(req);
            logger.debug("通知马上贷投资匹配结果响应参数：" + resp);
            // 判断远程URl调用是否成功
            if (null != resp && !"0000".equals(resp.getCode())) {
                throw new BusinessException(ResultCodeEnum.FAIL.code(), "通知马上贷投资匹配结果调用失败:" + resp.getMsg());
            }
        } catch (Exception e) {
            logger.info("资产编号={},匹配通知马上贷投资匹配结果调用失败", loanRequestDTO.getLoanNo(), e);
            InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
            interfaceRetryEntity.setBusinessType(InterfaceRetryBusinessTypeEnum.ASSET_MATCH_NOTIFY_MSD.getCode());
            interfaceRetryEntity.setBusinessNo(loanRequestDTO.getLoanNo());
            interfaceRetryEntity.setRequestParam(null != req ? JSONObject.toJSONString(req) : null);
            interfaceRetryEntity.setResponseParam(null != resp ? JSONObject.toJSONString(resp) : null);
            interfaceRetryEntity.setStatus(InterfaceRetryStatusEnum.INIT.getCode());
            interfaceRetryEntity.setRetryTimes(0);
            interfaceRetryEntity.setMemo(loanRequestDTO.getLoanNo());
            interfaceRetryEntity.setProductCode(matchRecordList.get(0).getProductCode());
            interfaceRetryService.saveInterfaceRetryRecord(interfaceRetryEntity);
        }
    }

    /**
     * 具体通知马上贷投资匹配结果业务处理
     *
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public NotifyResp doNotifyMsdAssetMatchResult(NotifyMsdAssetMatchResultReq req) throws Exception {
        // 调用远程服务
        logger.debug("通知马上贷投资匹配结果请求参数：" + req);
        return msdCallBackService.notifyAssetMatchResult(req);
    }

}
