package com.zb.p2p.trade.service.cash.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zb.p2p.order.api.OrderAPI;
import com.zb.p2p.order.api.order.request.NotifyOrder;
import com.zb.p2p.paychannel.api.common.BusinessTypeEnum;
import com.zb.p2p.paychannel.api.req.FeeTransferReq;
import com.zb.p2p.paychannel.api.req.HonourRequest;
import com.zb.p2p.paychannel.api.resp.TradeRespDTO;
import com.zb.p2p.trade.client.dto.AssetBillPlanDto;
import com.zb.p2p.trade.client.member.TxsMemberClient;
import com.zb.p2p.trade.client.payment.PaymentClientService;
import com.zb.p2p.trade.client.request.GetMemberCardReq;
import com.zb.p2p.trade.client.response.MemberCardResp;
import com.zb.p2p.trade.common.constant.GlobalVar;
import com.zb.p2p.trade.common.domain.CashBillPlanKey;
import com.zb.p2p.trade.common.domain.CashPlan;
import com.zb.p2p.trade.common.domain.CreditorInfo;
import com.zb.p2p.trade.common.enums.*;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.model.CashAmountSuite;
import com.zb.p2p.trade.common.model.CommonResp;
import com.zb.p2p.trade.common.util.DateUtil;
import com.zb.p2p.trade.persistence.converter.CashPlanConverter;
import com.zb.p2p.trade.persistence.dao.CashRecordMapper;
import com.zb.p2p.trade.persistence.dao.LoanRequestMapper;
import com.zb.p2p.trade.persistence.dao.RepayBillPlanMapper;
import com.zb.p2p.trade.persistence.dto.CashKeyState;
import com.zb.p2p.trade.persistence.dto.CashSumAmountEntity;
import com.zb.p2p.trade.persistence.entity.*;
import com.zb.p2p.trade.service.cash.CashRecordService;
import com.zb.p2p.trade.service.common.DistributedSerialNoService;
import com.zb.p2p.trade.service.common.PaymentRecordService;
import com.zb.p2p.trade.service.config.ReadOnlyConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Function:兑付服务
 * Author: created by liguoliang
 * Date: 2017/8/31 0031 上午 11:16
 * Version: 1.0
 */
@Service
@Slf4j
public class CashRecordServiceImpl implements CashRecordService {

    @Autowired
    private CashRecordMapper cashRecordMapper;
    @Autowired
    private LoanRequestMapper loanRequestMapper;
    @Autowired
    private RepayBillPlanMapper repayBillPlanMapper;
    @Autowired
    private DistributedSerialNoService distributedSerialNoService;
    @Autowired
    private TxsMemberClient txsMemberClient;
    @Autowired
    private PaymentRecordService paymentRecordService;
    @Autowired
    private PaymentClientService paymentClientService;
    @Autowired
    private OrderAPI orderAPI;

    @Value("${ext.gateway.enable:true}")
    boolean enableExtGateWay;
    @Value("${guarantee.accountNo:1234567890}")
    private String guaranteeAccountNo;
    @Value("${guarantee.sinaAccountNo:1234567890}")
    private String guaranteeSinaAccountNo;
    @Value("${fee.accountNo:1234567890}")
    private String feeAccountNo;
    @Value("${fee.sinaAccountNo:1234567890}")
    private String feeSinaAccountNo;


    @Override
    public int update(CashPlan plan, CashStatusEnum preStatus) {
        return cashRecordMapper.updateByPreStatus(CashPlanConverter.convert(plan), preStatus.getCode());
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public int batchUpdate(List<CashPlan> planList, CashStatusEnum preStatus) {
        int updateRows = 0;
        for (CashPlan plan : planList) {
            updateRows = updateRows + cashRecordMapper.updateByKey(CashPlanConverter.convert(plan), preStatus.getCode());
        }
        return updateRows;
    }

    @Override
    public boolean isAllFinished(Long billPlanId, CashStatusEnum nextStatus) {

        List<CashKeyState> keyStateList = cashRecordMapper.selectStatByBillPlanId(billPlanId);
        if (CollectionUtils.isEmpty(keyStateList)) {
            return false;
        }

        for (CashKeyState keyState : keyStateList) {
            CashStatusEnum tempStatus = CashStatusEnum.getByCode(keyState.getStrKey());
            if (tempStatus == null) {
                return false;
            }

            if (tempStatus != nextStatus && tempStatus != CashStatusEnum.CASH_TRANSFERRED) {
                return false;
            }
        }

        return true;
    }

    public List<CashRecordEntity> selectByWithdrawlStatus(CashStatusEnum status, PayChannelEnum payChannel,
                                                          Date beginTime, Date endTime, int limit){
        return cashRecordMapper.selectByWithdrawlStatus(status.getCode(), payChannel.getCode(), beginTime, endTime, limit);
    }

    @Override
    @ReadOnlyConnection
    public List<CashPlan> loadByKey(CashBillPlanKey key) {
        List<CashRecordEntity> planList = cashRecordMapper.selectByKey(key.getAssetNo(), key.getStageNo(), key.getRepaymentType().getCode());
        return CashPlanConverter.convertToList(planList);
    }

    @Override
    @ReadOnlyConnection
    public Map<String, CashAmountSuite> loadMemberTotalAmount(CashBillPlanKey key, List<String> memberIds, CashAmountTypeEnum cashAmountType) {
        List<CashSumAmountEntity> assignedList = cashRecordMapper.selectMemberTotalAmount(key.getAssetNo(), memberIds, key.getRepaymentType().name());
        return buildTotalAmount(assignedList, cashAmountType);
    }

    @Override
    @ReadOnlyConnection
    public Map<String, CashAmountSuite> loadOrderTotalAmount(CashBillPlanKey key, List<String> orderIds, CashAmountTypeEnum cashAmountType) {
        List<CashSumAmountEntity> assignedList = cashRecordMapper.selectOrderTotalAmount(key.getAssetNo(), orderIds, key.getRepaymentType().name());
        return buildTotalAmount(assignedList, cashAmountType);
    }

    private Map<String, CashAmountSuite> buildTotalAmount(List<CashSumAmountEntity> assignedList, CashAmountTypeEnum cashAmountType){
        Map<String, CashAmountSuite> assignedMap = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(assignedList)) {
            for (CashSumAmountEntity assigned : assignedList) {
                switch (cashAmountType) {
                    case EXPECT:
                        assignedMap.put(assigned.getMemberId(), new CashAmountSuite(assigned.getExpectPrincipal(),
                                assigned.getExpectInterest()));
                        break;
                    case ACTUAL:
                        assignedMap.put(assigned.getMemberId(), new CashAmountSuite(assigned.getActualPrincipal(),
                                assigned.getActualInterest()));
                        break;
                }
            }
        }
        return assignedMap;
    }

    @Override
    @ReadOnlyConnection
    public List<CashPlan> selectByTransferCreditor(CreditorInfo creditorInfo) throws BusinessException {
        // 查询兑付计划表
        List<CashRecordEntity> cashRecordEntityList = cashRecordMapper.selectByAttornCreditor(creditorInfo.getMemberId(),
                String.valueOf(creditorInfo.getId()));

        return CashPlanConverter.convertToList(cashRecordEntityList);
    }

    @Override
    public boolean cashPlanPayment(CashBillPlanEntity billPlanEntity, PaymentRecordEntity retryRecord) {
        // 支付处理结果
        boolean payReqFlag = false;
        // 查询待兑付明细
        List<CashRecordEntity> cashRecordList = cashRecordMapper.selectByBillPlanId(billPlanEntity.getId(),
                CashStatusEnum.CASH_WAIT_PERFORM.getCode());

        if (CollectionUtils.isEmpty(cashRecordList)) {
            log.warn("根据应收账款Id=[{}]未查到可用的兑付记录，不再执行兑付支付", billPlanEntity.getId());
            return payReqFlag;
        }

        // 支付流水
        List<PaymentRecordEntity> paymentRecordEntityList = new ArrayList<>();
        // 获取会员类型
        LoanRequestEntity loanRequestEntity = loanRequestMapper.selectByAssetCode(billPlanEntity.getOrgAssetNo());
        String memberType = CashPlanConverter.convert2MemberType(loanRequestEntity.getLoanType());
        // 逐笔兑付
        for (CashRecordEntity cashRecordEntity : cashRecordList) {
            PaymentRecordEntity paymentRecord = executeCashPay(cashRecordEntity, memberType, retryRecord);
            // 组装兑付明细的支付流水
            if (paymentRecord != null) {
                paymentRecordEntityList.add(paymentRecord);
                payReqFlag = true;
            }
        }
        // 支付流水等待支付结果
        if (!CollectionUtils.isEmpty(paymentRecordEntityList)) {
            paymentRecordService.batchInsert(paymentRecordEntityList);
        }

        return payReqFlag;
    }

    private PaymentRecordEntity executeCashPay(CashRecordEntity cashRecordEntity, String memberType,
                                               PaymentRecordEntity retryRecord) {
        PaymentRecordEntity paymentRecord = null;
        // 调用支付
        try {
            HonourRequest paymentDto = buildCashPaymentRequest(cashRecordEntity, memberType);
            TradeRespDTO cashierRspDTO = paymentClientService.cashPay(paymentDto);

            // 返回结果处理
            if (cashierRspDTO != null && cashierRspDTO.getTradeStatus().equals(GlobalVar.PAYMENT_TRADE_STATUS_PROCESSING)) {
                // 标记兑付类型 转账处理中
                CashRecordEntity updateCashRecordEntity = new CashRecordEntity();
                updateCashRecordEntity.setId(cashRecordEntity.getId());
                updateCashRecordEntity.setReqNo(cashRecordEntity.getReqNo());
                updateCashRecordEntity.setStatus(CashStatusEnum.CASHING.getCode());
                updateCashRecordEntity.setCashStatus(cashRecordEntity.getCashStatus());
                int rows = cashRecordMapper.updateByPrimaryKeySelective(updateCashRecordEntity);

                Assert.isTrue(rows == 1, "兑付转账中更新兑付计划信息失败");

                // 组装兑付明细的支付流水
                paymentRecord = buildCashPaymentRecord(cashRecordEntity, cashierRspDTO, retryRecord);
            }
        } catch (Exception e) {
            log.error("调用支付请求失败", e);
        }
        return paymentRecord;
    }

    /**
     * 转手续费（隔离当前事物 开启新事物提交，跟之后的逻辑互不影响，避免异常情况多次转手续费导致实际要兑付给投资人的钱不足）
     *
     * @param billPlanEntity
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void paymentTransferFee(RepayBillPlanEntity billPlanEntity, CashOverdueEnum cashOverdueEnum,
                                    PaymentRecordEntity retryRecord) {
        // 校验手续费金额
        if (billPlanEntity.getActualRepaymentFee() == null ||
                billPlanEntity.getActualRepaymentFee().compareTo(BigDecimal.ZERO) <= 0) {
            log.info("借款资产[{}]没有放款手续费[{}]，不再发起转账交易",
                    billPlanEntity.getAssetCode(), billPlanEntity.getActualRepaymentFee());
            return;
        }
        // 校验是否已支付
        PaymentRecordEntity paymentRecordEntity = paymentRecordService.queryByRefAndStatus(String.valueOf(billPlanEntity.getId()),
                Arrays.asList(new String[]{GlobalVar.PAYMENT_TRADE_STATUS_SUCCESS, GlobalVar.PAYMENT_TRADE_STATUS_PROCESSING}));
        if (paymentRecordEntity != null) {
            log.warn("借款资产[{}]放款手续费已支付完成或者在处理中，支付记录信息：{}",
                    billPlanEntity.getAssetCode(), paymentRecordEntity);
            return;
        }
        // 组装参数
        FeeTransferReq cashPaymentReq = buildFeePaymentRequest(billPlanEntity, cashOverdueEnum);

        // 支付
        TradeRespDTO cashierRspDTO = paymentClientService.feeTransfer(cashPaymentReq);
        // 结果处理
        if (cashierRspDTO != null && cashierRspDTO.getTradeStatus().equals(GlobalVar.PAYMENT_TRADE_STATUS_PROCESSING)) {
            //记录支付转账流水
            PaymentRecordEntity newRecordEntity = new PaymentRecordEntity();
            newRecordEntity.setAmount(billPlanEntity.getActualRepaymentFee());
            newRecordEntity.setTradeType(SequenceEnum.TRANSFER_FEE.getCode());
            newRecordEntity.setRefId(String.valueOf(billPlanEntity.getId()));
            // 手续费的支付流水号
            newRecordEntity.setTradeSerialNo(cashPaymentReq.getOrderNo());
            if (retryRecord != null) {
                newRecordEntity.setOrgTradeSerialNo(retryRecord.getTradeSerialNo());
            }else {
                newRecordEntity.setOrgTradeSerialNo(cashPaymentReq.getOrderNo());
            }
            newRecordEntity.setPayCode(GlobalVar.PAYMENT_TRADE_CODE_SUCCESS);
            newRecordEntity.setPayMsg(cashierRspDTO.getTradeMsg());
            newRecordEntity.setStartTime(new Date());
            newRecordEntity.setTradeStatus(cashierRspDTO.getTradeStatus());
            paymentRecordService.savePaymentRecord(newRecordEntity);
        }
    }

    /**
     * 逐笔組裝兑付支付请求参数
     * @param cashRecordEntity
     * @return
     */
    private HonourRequest buildCashPaymentRequest(CashRecordEntity cashRecordEntity, String memberType) {

        HonourRequest paymentReq = new HonourRequest();
        // 投资人信息
        paymentReq.setOrderTime(DateUtil.getDateString(new Date(), DateUtil.NEW_FORMAT));
        paymentReq.setAssetNo(cashRecordEntity.getAssetNo());
        paymentReq.setInvestOrderNo(cashRecordEntity.getExtOrderNo());
        paymentReq.setBusinessType(BusinessTypeEnum.HONOUR.getCode());
        paymentReq.setInvestorMemberId(cashRecordEntity.getMemberId());

        // 支付渠道
        PayChannelEnum payChannel = PayChannelEnum.getByCode(cashRecordEntity.getPayChannel());
        paymentReq.setChannelType(payChannel.getArgus());
        // 借款人信息（默认正常）
        paymentReq.setLoanerMemberId(cashRecordEntity.getLoanMemberId());
        paymentReq.setAccountPurpose(AccountPurposeEnum._101.getCode());
        CashOverdueEnum cashOverdueEnum = CashOverdueEnum.getByCode(cashRecordEntity.getCashStatus());
        setLoanMemberAccountRequest(paymentReq, cashOverdueEnum, payChannel);

        //生成兑付明细请求流水号
        String cashReqNo = distributedSerialNoService.generatorSerialNoByIncrement(SequenceEnum.CASH,
                SourceIdEnum.TXS.getCode(), 1);
        paymentReq.setOrderNo(cashReqNo);
        BigDecimal cashAmount = cashRecordEntity.getCashAmount().add(cashRecordEntity.getCashIncome());
        paymentReq.setAmount(cashAmount.toString());
        if (payChannel == PayChannelEnum.BAOFU) {
            paymentReq.setSingId(getCardSignId(cashRecordEntity));
        }
        paymentReq.setNotifyUrl(GlobalVar.PAY_CALL_BACK_URL);

        paymentReq.setMemberType(memberType);

        // 回填数据
        cashRecordEntity.setReqNo(cashReqNo);
        cashRecordEntity.setStatus(CashStatusEnum.CASHING.getCode());
        cashRecordEntity.setCashStatus(cashOverdueEnum.getCode());

        return paymentReq;
    }

    private PaymentRecordEntity buildCashPaymentRecord(CashRecordEntity cashRecordEntity, TradeRespDTO tradeRespDTO,
                                                       PaymentRecordEntity retryRecord) {
        // 支付流水等待支付结果
        PaymentRecordEntity paymentRecordEntity = new PaymentRecordEntity();
        paymentRecordEntity.setMemberId(cashRecordEntity.getMemberId());
        paymentRecordEntity.setAmount(cashRecordEntity.getCashAmount().add(cashRecordEntity.getCashIncome()));
        paymentRecordEntity.setTradeType(SequenceEnum.CASH.getCode());
        paymentRecordEntity.setRefId(String.valueOf(cashRecordEntity.getId()));
        paymentRecordEntity.setTradeSerialNo(cashRecordEntity.getReqNo());
        if (retryRecord != null) {
            paymentRecordEntity.setOrgTradeSerialNo(retryRecord.getTradeSerialNo());
        } else {
            paymentRecordEntity.setOrgTradeSerialNo(cashRecordEntity.getReqNo());
        }
        paymentRecordEntity.setCreateTime(new Date());
        // PayCode,PayMsg 统一根据主体结果码回填
        paymentRecordEntity.setStartTime(new Date());
        paymentRecordEntity.setPayCode(GlobalVar.PAYMENT_TRADE_CODE_SUCCESS);
        paymentRecordEntity.setPayMsg(tradeRespDTO.getTradeMsg());
        paymentRecordEntity.setTradeStatus(tradeRespDTO.getTradeStatus());
        paymentRecordEntity.setPayNo(tradeRespDTO.getPayNo());

        return paymentRecordEntity;
    }

    /**
     * 組裝还款手续费支付请求参数
     * @param billPlanEntity
     * @return
     */
    private FeeTransferReq buildFeePaymentRequest(RepayBillPlanEntity billPlanEntity, CashOverdueEnum cashOverdueEnum) {

        FeeTransferReq paymentReqDTO = new FeeTransferReq();

        paymentReqDTO.setBusinessType(BusinessTypeEnum.FEE.getCode());
        //生成兑付主体请求流水号
        String transferReqNo = distributedSerialNoService.generatorSerialNoByIncrement(SequenceEnum.TRANSFER_FEE,
                SourceIdEnum.TXS.getCode(), 1);
        paymentReqDTO.setOrderNo(transferReqNo);
        paymentReqDTO.setOrderTime(DateUtil.format(new Date(), DateUtil.NEW_FORMAT));
        // 实际还款手续费
        paymentReqDTO.setTradeAmount(billPlanEntity.getActualRepaymentFee().toString());

        // 根据支付渠道组装
        PayChannelEnum payChannel = PayChannelEnum.getByCode(billPlanEntity.getPayChannel());
        paymentReqDTO.setChannelType(payChannel.getArgus());

        // 借款人信息-付款方ID
        paymentReqDTO.setLoanOrderNo(billPlanEntity.getLoanNo());
        switch (payChannel){
            case SINA:
                // 投资人信息-收款方ID
                paymentReqDTO.setPayeeAccountId(feeSinaAccountNo);
                // 担保人信息-付款方ID
                paymentReqDTO.setPayerAccountId(guaranteeSinaAccountNo);
                break;
            case BAOFU:
                // 投资人信息-收款方ID
                paymentReqDTO.setPayeeAccountId(feeAccountNo);
                // 担保人信息-付款方ID
                paymentReqDTO.setPayerAccountId(guaranteeAccountNo);
                break;
            default:
                throw new BusinessException("不支持的支付渠道类型");
        }

        return paymentReqDTO;
    }

    private void setLoanMemberAccountRequest(HonourRequest cashSubReq, CashOverdueEnum cashOverdueEnum,
                                             PayChannelEnum payChannel) {
        // 借款人信息(默认企业)
        if (cashOverdueEnum == CashOverdueEnum.EXPIRE) {
            cashSubReq.setAccountPurpose(AccountPurposeEnum._207.getCode());
            switch (payChannel) {
                case SINA:
                    cashSubReq.setLoanerMemberId(guaranteeSinaAccountNo);
                    break;
                case BAOFU:
                    cashSubReq.setLoanerMemberId(guaranteeAccountNo);
                    break;
                default:
                    throw new BusinessException("不支持的支付渠道类型");
            }
        }
    }

    //通知订单兑付中
    private void notifyOrderParty() {
        //投资端的兑付中数据
        List<NotifyOrder> cashNotifyList = Lists.newArrayList();
//        List<CashNotify> cashNotifies = cashRecordMapper.queryOrdersByCashing();
//        for (CashNotify cashNotify_ : cashNotifies) {
//            NotifyOrder notifyTxsAssetMatchResultReq = new NotifyOrder();
//            notifyTxsAssetMatchResultReq.setExtOrderNo(cashNotify_.getExtOrderNo());
//            notifyTxsAssetMatchResultReq.setStatus(CashStatusEnum.CASHING.getCode());//订单根据code传
//            notifyTxsAssetMatchResultReq.setType(NotifyTxsAssetMatchResultReq.Status.CASH.name());
//            notifyTxsAssetMatchResultReq.setAmount(cashNotify_.getCashAmount().add(cashNotify_.getCashIncome()));
//            cashNotifyList.add(notifyTxsAssetMatchResultReq);
//        }
//        try {
//            if (!CollectionUtils.isEmpty(cashNotifyList)) {
//                orderAPI.tradeNotifyOrder(cashNotifyList);
//            }
//        } catch (Exception e) {
//            log.error("通知订单兑付中失败", e);
//            InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
//            interfaceRetryEntity.setRequestParam(JSON.toJSONString(cashNotifyList));
//            interfaceRetryEntity.setBusinessType(InterfaceRetryBusinessTypeEnum.CASH_RESULT_NOTIFY_TXS.getCode());
//            interfaceRetryService.saveInterfaceRetryRecord(interfaceRetryEntity);
//        }
    }

    /**
     * 查询绑卡标识
     *
     * @param cashRecordReq
     */
    public String getCardSignId(CashRecordEntity cashRecordReq) {
        String signId = "";
        GetMemberCardReq getMemberCardReq = new GetMemberCardReq();
        try {
            if (enableExtGateWay) {
                getMemberCardReq.setCustomerId(cashRecordReq.getMemberId());
                CommonResp<MemberCardResp> resp = txsMemberClient.getMemberCard(getMemberCardReq);
                signId = resp.getData().getSignId();
            }
        } catch (Exception e) {
            log.error("调用唐小僧接口查询绑卡标识失败:{}", e);
            // ToDo 宝付默认
            signId = "201806042019031000009903132";
        }
        return signId;
    }

//    /**
//     * 查询绑卡信息
//     *
//     * @param notifyTime
//     */
//    public CustomerCardBin getCardInfo(String notifyTime) {
//        CustomerCardBin customerCardBin = new CustomerCardBin();
//        CustomerReq req = new CustomerReq();
//        try {
//            if (enableExtGateWay) {
//                req.setCustomerId(notifyTime);
//                BaseRes<CustomerCardBin> resp = txsMemberClient.bankCardBindInfo(req);
//                customerCardBin = resp.getData();
//            }
//        } catch (Exception e) {
//            log.error("调用会员查询绑卡失败:{}", e);
//        }
//        return customerCardBin;
//    }

    @Override
    public RepayBillPlanEntity repaymentTransferFee(RepayBillPlanEntity billPlanEntity, AssetBillPlanDto repayPlan) throws BusinessException {
        RepayBillPlanEntity updateBill = new RepayBillPlanEntity();
        // 还款类型
        CashOverdueEnum overdueEnum = CashOverdueEnum.getByRepayStatus(repayPlan.getRepayStatus());
        final BigDecimal loanCharge = repayPlan.getLoanFee();
        // 如果手续费小于等于0 直接返回
        if (overdueEnum == CashOverdueEnum.EXPIRE && loanCharge.compareTo(BigDecimal.ZERO) > 0) {
            // 保存手续费并同步还款计划
            updateBill.setActualRepaymentFee(loanCharge);
            updateBill.setRepaymentFeeStatus(RepaymentFeeStatusEnum.PROCESSING.getCode());

            // 调用手续费支付
            paymentTransferFee(billPlanEntity, overdueEnum, null);

        }else {
            log.warn("放款手续费[{}]小于等于零，不再发起转账交易", loanCharge);
        }
        return updateBill;
    }

    @Override
    public void retryCash(PaymentRecordEntity paymentRecord) throws BusinessException {
        // 根据支付记录查询准备信息
        log.info("兑付支付发起重试：{}", paymentRecord);

        // 查询发放中的兑付计划模板
        CashRecordEntity cashRecord = cashRecordMapper.selectByPrimaryKey(Long.valueOf(paymentRecord.getRefId()));
        Assert.notNull(cashRecord, "未找到兑付计划信息");

        // 获取会员类型
        LoanRequestEntity loanRequestEntity = loanRequestMapper.selectByAssetCode(cashRecord.getAssetNo());
        Assert.notNull(cashRecord, "未找到借款申请信息");
        String memberType = CashPlanConverter.convert2MemberType(loanRequestEntity.getLoanType());

        // 调用支付兑付给投资人
        executeCashPay(cashRecord, memberType, paymentRecord);
        // 兑付结果等待异步通知
    }

    @Override
    public void retryLoanFee(PaymentRecordEntity paymentRecord) throws BusinessException {

        log.info("还款手续费支付重试：{}", paymentRecord);

        RepayBillPlanEntity billPlanEntity = repayBillPlanMapper.selectByPrimaryKey(Long.valueOf(paymentRecord.getRefId()));

        Assert.notNull(billPlanEntity, "未找到交易还款计划信息");

        // 发起重试
        paymentTransferFee(billPlanEntity, CashOverdueEnum.EXPIRE, paymentRecord);
    }
}
