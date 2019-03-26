package com.zb.p2p.service.order;

import com.alibaba.fastjson.JSON;
import com.zb.fincore.common.utils.BeanUtils;
import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.fincore.common.utils.NumberUtils;
import com.zb.fincore.pms.common.enums.ChangeProductStockTypeEnum;
import com.zb.fincore.pms.facade.product.ProductCacheServiceForP2PFacade;
import com.zb.fincore.pms.facade.product.dto.req.ChangeProductStockForP2PRequest;
import com.zb.fincore.pms.facade.product.dto.req.FreezeProductStockRequest;
import com.zb.fincore.pms.facade.product.dto.resp.ChangeProductStockResponse;
import com.zb.fincore.pms.facade.product.dto.resp.FreezeProductStockResponse;
import com.zb.p2p.common.exception.BusinessException;
import com.zb.p2p.dao.master.LoanRequestDAO;
import com.zb.p2p.dao.master.MatchRecordDAO;
import com.zb.p2p.dao.master.OrderRequestDAO;
import com.zb.p2p.entity.InterfaceRetryEntity;
import com.zb.p2p.entity.LoanRequestEntity;
import com.zb.p2p.entity.MatchRecordEntity;
import com.zb.p2p.entity.OperationRecordEntity;
import com.zb.p2p.enums.*;
import com.zb.p2p.facade.api.req.*;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.api.resp.ProductStockDTO;
import com.zb.p2p.facade.api.resp.order.*;
import com.zb.p2p.facade.api.resp.product.ProductDTO;
import com.zb.p2p.facade.service.internal.ProductInternalService;
import com.zb.p2p.service.common.DistributedSerialNoService;
import com.zb.p2p.service.common.InterfaceRetryService;
import com.zb.p2p.service.common.OperationService;
import com.zb.p2p.utils.ExecutorsFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by limingxin on 2017/8/31.
 */
@Service
@Slf4j
public class OrderService {

    @Autowired
    private LoanRequestDAO loanRequestDao;
    @Autowired
    private OrderRequestDAO orderRequestDAO;

    @Autowired
    private MatchRecordDAO matchRecordDAO;
    @Autowired
    private OperationService operationService;
    @Autowired
    private CheckService checkService;
    @Autowired
    private DistributedSerialNoService distributedSerialNoService;
    @Value("${QUEUE-ZB-P2P-ORDER-COMPLETE}")
    private String orderQueue;
    private ExecutorService frozenStockExec = ExecutorsFactory.getExecutorService("frozenStockPool");
    @Autowired
    private BasicDataService basicDataService;
    @Autowired
    private ProductInternalService productInternalService;

    @Autowired
    private InterfaceRetryService interfaceRetryService;

    @Autowired
    private ProductCacheServiceForP2PFacade productCacheServiceForP2PFacade;

    /**
     * 借款处理
     *
     * @param loanReq
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResp loan(LoanReq loanReq) throws Exception {
        try {
            CommonResp commonResp = checkService.checkLoanReq(loanReq);
            if (commonResp != null) {
                return commonResp;
            }
            boolean state = logOperation(OperationTypeEnum.LOAN_APPLY.getDesc(), loanReq.getLoanNo());
            if (state) {
                //记录借款单
                LoanRequestEntity loanRequest = BeanUtils.copyAs(loanReq, LoanRequestEntity.class);
                loanRequest.setCreateTime(new Date());
                loanRequest.setLoanTime(loanReq.getLoanTime());
                loanRequest.setLoanStatus(LoanStatusEnum.LOAN_UNMATCHED.getCode());
                loanRequestDao.insertSelective(loanRequest);
                return defaultSucced();
            } else {
                commonResp = new CommonResp();
                commonResp.setCode(ResponseCodeEnum.RESPONSE_SUCCESS.getCode());
                commonResp.setMessage(ResponseCodeEnum.RESPONSE_SERIAL_REPEAT.getDesc());
                return commonResp;
            }
        } catch (Exception e) {
            log.error(OperationTypeEnum.RESERVATION_INVEST_APPLY_EXCEPTION.getDesc(), e);
            throw e;
        }
    }

    /**
     * 借款处理
     *
     * @param batchLoanReq
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResp batchLoan(BatchLoanReq batchLoanReq) throws Exception {
        try {
            if(null !=batchLoanReq && !CollectionUtils.isNullOrEmpty(batchLoanReq.getLoanReqList())){
                for(LoanReq loanReq : batchLoanReq.getLoanReqList()){
                    CommonResp commonResp = checkService.checkLoanReq(loanReq);
                    if (commonResp != null) {
                        throw new BusinessException(commonResp.getCode(),"["+loanReq.getLoanNo()+"]"+
                                commonResp.getMessage());
                    }
                    boolean state = logOperation(OperationTypeEnum.LOAN_APPLY.getDesc(), loanReq.getLoanNo());
                    if (state) {
                        //记录借款单
                        LoanRequestEntity loanRequest = BeanUtils.copyAs(loanReq, LoanRequestEntity.class);
                        loanRequest.setCreateTime(new Date());
                        loanRequest.setLoanTime(loanReq.getLoanTime());
                        loanRequest.setLoanStatus(LoanStatusEnum.LOAN_UNMATCHED.getCode());
                        loanRequestDao.insertSelective(loanRequest);
                    } else {
                        throw new BusinessException(ResponseCodeEnum.RESPONSE_SUCCESS.getCode(),
                                ResponseCodeEnum.RESPONSE_SERIAL_REPEAT.getDesc()+":"+loanReq.getLoanNo());
//                        commonResp.setCode(ResponseCodeEnum.RESPONSE_SUCCESS.getCode());
//                        commonResp.setMessage(ResponseCodeEnum.RESPONSE_SERIAL_REPEAT.getDesc());
//                        return commonResp;
                    }
                }
            }
            return defaultSucced();
        } catch (Exception e) {
            log.error(OperationTypeEnum.RESERVATION_INVEST_APPLY_EXCEPTION.getDesc(), e);
            throw e;
        }
    }

    /**
     * 查询借款订单信息
     *
     * @param req
     * @return
     */
    public CommonResp<LoanOrderRespDTO> queryLoanOrderInfo(QueryLoanOrderInfoReq req) {
        CommonResp commonResp = new CommonResp();
        try {
            log.info("调用queryLoanOrderInfo，请求参数{}", JSON.toJSONString(req));
            if (StringUtils.isBlank(req.getLoanNo())) {
                return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "借款订单号不能为空。");
            }
            commonResp.setCode(ResponseCodeEnum.RESPONSE_SUCCESS.getCode());
            commonResp.setMessage(ResponseCodeEnum.RESPONSE_SUCCESS.getDesc());
            LoanOrderRespDTO loanOrderRespDTO = new LoanOrderRespDTO();

            LoanRequestEntity loanRequestEntity = loanRequestDao.selectByLoanNo(req.getLoanNo());

            if (null == loanRequestEntity) {
                return CommonResp.build(ResponseCodeEnum.RESPONSE_NOT_FUND.getCode(), "借款订单不存在。");
            }

            BeanUtils.copy(loanRequestEntity, loanOrderRespDTO);

            List<MatchRecordEntity> list = matchRecordDAO.selectListByLoanNo(req.getLoanNo());
            if (!CollectionUtils.isNullOrEmpty(list)) {
                List<MatchRecordDTO> dataList = BeanUtils.copyAs(list, MatchRecordDTO.class);
                loanOrderRespDTO.setMatchRecordDTOList(dataList);
            }

            commonResp.setData(loanOrderRespDTO);
            log.info("调用queryLoanOrderInfo，返回参数{}", JSON.toJSONString(commonResp));
            return commonResp;
        }catch (Exception e){
            log.error("调用queryLoanOrderInfo查询借款订单信息异常：", e);
        }
        return commonResp;
    }

    /**
     * 查询投资订单匹配信息
     *
     * @param req
     * @return
     */
    public CommonResp<OrderMatchRespDTO> queryOrderMatchInfo(QueryOrderMatchInfoReq req) {
        CommonResp commonResp = new CommonResp();
        try {
            log.info("调用queryOrderMatchInfo，请求参数{}", JSON.toJSONString(req));
            if (StringUtils.isBlank(req.getExtOrderNo())) {
                return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "订单号不能为空。");
            }
            commonResp.setCode(ResponseCodeEnum.RESPONSE_SUCCESS.getCode());
            commonResp.setMessage(ResponseCodeEnum.RESPONSE_SUCCESS.getDesc());
            OrderMatchRespDTO orderMatchRespDTO = new OrderMatchRespDTO();
            List<OrderMatchInfoDTO> list = matchRecordDAO.queryOrderMatchInfoByExtOrderNo(req.getExtOrderNo());
            if(CollectionUtils.isNullOrEmpty(list)){
                orderMatchRespDTO.setCount(0l);
                orderMatchRespDTO.setOrderMatchInfoDTOList(null);
            }else{
                orderMatchRespDTO.setCount(Long.valueOf(list.size()));
                orderMatchRespDTO.setOrderMatchInfoDTOList(list);
            }
            commonResp.setData(orderMatchRespDTO);
            log.info("调用queryLoanOrderInfo，返回参数{}", JSON.toJSONString(commonResp));
            return commonResp;
        }catch (Exception e){
            log.error("调用queryLoanOrderInfo查询借款订单信息异常：", e);
        }
        return commonResp;
    }

    /**
     * 投资下单处理
     * FIXME 去掉事物，保证return后订单已经落库,匹配才不会漏单
     * @param orderReq
     * @return
     * @throws Exception
     */
//    @Transactional(rollbackFor = Exception.class)
    public CommonResp<OrderRespDTO> order(final OrderReq orderReq) throws Exception {
        try {
            CommonResp commonResp = checkService.checkOrder(orderReq);
            if (commonResp != null) {
                return commonResp;
            }
            boolean state = logOperation(OperationTypeEnum.INVEST_APPLY.getDesc(), orderReq.getExtOrderNo());
            if (state) {
                final String orderNo = distributedSerialNoService.generatorSerialNoByIncrement(SequenceEnum.ORDER, orderReq.getSaleChannel(), 1);
                orderReq.setOrderNo(orderNo);
                /*frozenStockExec.execute(new Runnable() {
                    @Override
                    public void run() {
                        //冻结库存
                        freezeProductStock(orderReq, orderNo);

                        //占用库存
                        changeProductStock(orderReq, ChangeProductStockTypeEnum.OCCUPY.getCode());
                    }
                });*/

                //生成订单
                basicDataService.generatorOrder(orderReq);

                return defaultSucced();
            } else {
                commonResp = new CommonResp();
                commonResp.setCode(ResponseCodeEnum.RESPONSE_SUCCESS.getCode());
                commonResp.setMessage(ResponseCodeEnum.RESPONSE_SERIAL_REPEAT.getDesc());
                return commonResp;
            }
        } catch (Exception e) {
            log.error(OperationTypeEnum.INVEST_APPLY_EXCEPTION.getDesc(), e);
            throw e;
        }
    }


    private boolean logOperation(String opType, String referId) {
        OperationRecordEntity operationRecord = new OperationRecordEntity();
        operationRecord.setOperationType(opType);
        operationRecord.setReferId(referId);
        operationRecord.setCreateTime(new Date());
        try {
            return operationService.opRecord(operationRecord);
        } catch (Exception e) {
            log.error(OperationTypeEnum.INVEST_APPLY_EXCEPTION.getDesc(), e);
        }
        return false;
    }

    private CommonResp defaultSucced() {
        CommonResp commonResp = new CommonResp();
        commonResp.setCode(ResponseCodeEnum.RESPONSE_SUCCESS.getCode());
        commonResp.setMessage(ResponseCodeEnum.RESPONSE_SUCCESS.getDesc());
        return commonResp;
    }

    /**
     * 调用产品冻结接口
     *
     * @param orderReq
     * @param orderNo
     * @return
     */
    private void freezeProductStock(OrderReq orderReq, String orderNo) {
        FreezeProductStockRequest freezeProductStockRequest = null;
        try {
            freezeProductStockRequest = new FreezeProductStockRequest();
            freezeProductStockRequest.setProductCode(orderReq.getProductCode());
            freezeProductStockRequest.setChangeAmount(orderReq.getInvestAmount());
            freezeProductStockRequest.setRefNo(orderNo);
            log.info("调用pms接口freezeProductStock，请求参数{}", JSON.toJSONString(freezeProductStockRequest));
            FreezeProductStockResponse freezeProductStockResponse = productCacheServiceForP2PFacade.freezeProductStock(freezeProductStockRequest);
            log.info("调用pms接口freezeProductStock,响应结果{}", JSON.toJSONString(freezeProductStockResponse));
        } catch (Exception e) {
            log.error("调用产品冻结库存接口失败", e);
            //进入重试队列
            InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
            interfaceRetryEntity.setBusinessType(InterfaceRetryBusinessTypeEnum.PRODUCT_FROZEN_STOCK_NOTIFY.getCode());
            interfaceRetryEntity.setRequestParam(JSON.toJSONString(freezeProductStockRequest));
            interfaceRetryEntity.setStatus(InterfaceRetryStatusEnum.INIT.getCode());
            try {
                interfaceRetryService.saveInterfaceRetryRecord(interfaceRetryEntity);
            } catch (Exception e1) {
            }
        }

    }

    /**
     * 调用产品库存接口
     *
     * @param orderReq
     * @param type
     * @return
     */

    private void changeProductStock(OrderReq orderReq, Integer type) {
        ChangeProductStockForP2PRequest changeProductStockRequest = null;
        try {
            changeProductStockRequest = new ChangeProductStockForP2PRequest();
            changeProductStockRequest.setRefNo(orderReq.getOrderNo());
            changeProductStockRequest.setChangeAmount(orderReq.getInvestAmount());
            changeProductStockRequest.setProductCode(orderReq.getProductCode());
            changeProductStockRequest.setChangeType(type);
            log.info("调用pms接口changeProductStockForP2P，请求参数{}", JSON.toJSONString(changeProductStockRequest));
            ChangeProductStockResponse changeProductStockResponse = productCacheServiceForP2PFacade.changeProductStock(changeProductStockRequest);
            log.info("调用pms接口changeProductStockForP2P，响应结果{}", JSON.toJSONString(changeProductStockResponse));
        } catch (Exception e) {
            log.error("调用产品占用库存接口失败", e);
            //进入重试队列
            InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
            interfaceRetryEntity.setBusinessType(InterfaceRetryBusinessTypeEnum.PRODUCT_STOCK_NOTIFY.getCode());
            interfaceRetryEntity.setRequestParam(JSON.toJSONString(changeProductStockRequest));
            interfaceRetryEntity.setStatus(InterfaceRetryStatusEnum.INIT.getCode());
            try {
                interfaceRetryService.saveInterfaceRetryRecord(interfaceRetryEntity);
            } catch (Exception e1) {
                log.error("", e1);
            }
        }
    }

    /**
     * 库存查询接口
     *
     * @param req
     * @return
     */
    public CommonResp<ProductStockDTO> queryStock(StockQueryReq req) {
        log.info("调用queryStock，请求参数{}", JSON.toJSONString(req));
        if (StringUtils.isBlank(req.getSource())) {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "查询渠道不能为空。");
        }
        String productCode = null;
        if ("BOSS".equals(req.getSource())) {
            if (StringUtils.isBlank(req.getProductCode())) {//Boss系统传productCode查询
                return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "产品编号不能为空。");
            }
            productCode = req.getProductCode();
        } else if ("MSD".equals(req.getSource())) {//MSD系统传起息日和期限查询
            if (null == req.getValueStartTime()) {
                return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "起息日不能为空。");
            }
            if (null == req.getLockDate()) {
                return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "期限不能为空。");
            }
            CommonResp<ProductDTO> productDTOCommonResp = productInternalService.queryProductInfoForAssetMatch(null, req.getValueStartTime(), req.getLockDate());
            if (null == productDTOCommonResp || null == productDTOCommonResp.getData()) {
                return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "根据起息日和期限查询产品为空。");
            }
            productCode = productDTOCommonResp.getData().getProductCode();
        } else {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "查询渠道不正确。");
        }
        CommonResp commonResp = new CommonResp();
        commonResp.setCode(ResponseCodeEnum.RESPONSE_SUCCESS.getCode());
        commonResp.setMessage(ResponseCodeEnum.RESPONSE_SUCCESS.getDesc());
        ProductStockDTO productStockDTO = new ProductStockDTO();
        BigDecimal actualTotalAmount = orderRequestDAO.selectInvestedAmountByProductCode(productCode);
        BigDecimal reservationTotalAmount = BigDecimal.ZERO;//reservationOrderDao.selectReservationAmountByProductCode(productCode);
        productStockDTO.setActualTotalAmount(actualTotalAmount);
        if ("MSD".equals(req.getSource())) {//MSD系统传 已预约未放款金额 即已预约未下正式交易单金额
            productStockDTO.setActualTotalAmount(NumberUtils.subtract(reservationTotalAmount, actualTotalAmount));
        }
        productStockDTO.setReservationTotalAmount(reservationTotalAmount);
        commonResp.setData(productStockDTO);
        log.info("调用queryStock，返回参数{}", JSON.toJSONString(commonResp));
        return commonResp;
    }

}
