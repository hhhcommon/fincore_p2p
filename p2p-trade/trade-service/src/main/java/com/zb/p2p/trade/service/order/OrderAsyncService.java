package com.zb.p2p.trade.service.order;

import com.alibaba.fastjson.JSON;
import com.zb.fincore.common.enums.ResultCodeEnum;
import com.zb.fincore.common.exception.CommonException;
import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.p2p.paychannel.api.common.BusinessTypeEnum;
import com.zb.p2p.paychannel.api.common.ChannelTypeEnum;
import com.zb.p2p.paychannel.api.req.LoanRequest;
import com.zb.p2p.paychannel.api.resp.TradeRespDTO;
import com.zb.p2p.trade.api.req.NotifyTradeStatusReq;
import com.zb.p2p.trade.client.ams.AssetManagerClient;
import com.zb.p2p.trade.client.order.TxsOrderClient;
import com.zb.p2p.trade.client.payment.PaymentClientService;
import com.zb.p2p.trade.client.request.CashedNotifyTxsReq;
import com.zb.p2p.trade.client.request.NotifyLoanStatusReq;
import com.zb.p2p.trade.common.constant.GlobalVar;
import com.zb.p2p.trade.common.enums.*;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.model.CommonResp;
import com.zb.p2p.trade.common.util.DateUtil;
import com.zb.p2p.trade.common.util.JsonUtil;
import com.zb.p2p.trade.common.util.StringUtils;
import com.zb.p2p.trade.persistence.dao.CreditorInfoMapper;
import com.zb.p2p.trade.persistence.dao.LoanRequestMapper;
import com.zb.p2p.trade.persistence.dao.PaymentRecordMapper;
import com.zb.p2p.trade.persistence.entity.*;
import com.zb.p2p.trade.service.common.DistributedLockService;
import com.zb.p2p.trade.service.common.DistributedSerialNoService;
import com.zb.p2p.trade.service.common.InterfaceRetryService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 预匹配处理业务操作类
 *
 * @author zhangxin
 * @create 2017-08-31 11:24
 */
@Service
@Slf4j
public class OrderAsyncService {


    @Autowired
    private DistributedSerialNoService distributedSerialNoService;
    @Autowired
    private InterfaceRetryService interfaceRetryService;

    @Autowired
    private LoanRequestMapper loanRequestMapper;
    @Autowired
    private PaymentRecordMapper paymentRecordMapper;
    @Autowired
    private CreditorInfoMapper creditorInfoMapper;

//    @Autowired
//    private MessageService messageService;

    @Autowired
    private PaymentClientService paymentClientService;
    @Autowired
    private AssetManagerClient assetManagerClient;
    @Autowired
    private TxsOrderClient txsOrderClient;
    @Autowired
    private DistributedLockService distributedLockService;



    public void sendCashResultToTxsOrder(CashRecordEntity cashRecordEntity) {
        // 通知订单兑付完成
        CashedNotifyTxsReq notifyTxsReq = new CashedNotifyTxsReq();
        try {
            notifyTxsReq.setAccountid(cashRecordEntity.getMemberId());
            notifyTxsReq.setCapitalamount(cashRecordEntity.getCashAmount());
            notifyTxsReq.setInterestamount(cashRecordEntity.getCashIncome());
            notifyTxsReq.setExtorderno(cashRecordEntity.getExtOrderNo());
            notifyTxsReq.setRepayno(cashRecordEntity.getReqNo());
            notifyTxsReq.setRepaytime(DateUtil.formatDateTime(cashRecordEntity.getCashDate()));
            txsOrderClient.cashedResultNotify(notifyTxsReq);
        } catch (Exception e) {
            InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
            interfaceRetryEntity.setRequestParam(JSON.toJSONString(notifyTxsReq));
            interfaceRetryEntity.setBusinessType(InterfaceRetryBusinessTypeEnum.CASH_RESULT_NOTIFY_TXS.getCode());
            interfaceRetryService.saveInterfaceRetryRecord(interfaceRetryEntity);
        }
    }

    /**
     * 调用支付  放款
     * @param loanRequestEntity
     * @param
     */
    @Transactional
    public void loanWithdrawal(LoanRequestEntity loanRequestEntity) throws Exception {

        // 校验
        List<CreditorInfoEntity> creditorInfoList = creditorInfoMapper.selectByAssetNo(loanRequestEntity.getTransferCode());
        if (CollectionUtils.isNullOrEmpty(creditorInfoList) ) {
            return ;
        }
        BigDecimal loanRequestMatchedAmount = loanRequestEntity.getMatchedAmount().setScale(2, BigDecimal.ROUND_DOWN);// 删除多余的小数位
        BigDecimal matchedAmountTotal = new BigDecimal(0);
        for (CreditorInfoEntity creditorInfoEntity : creditorInfoList) {
            matchedAmountTotal = matchedAmountTotal.add(creditorInfoEntity.getInvestAmount() );
        }
        if(loanRequestMatchedAmount.compareTo(matchedAmountTotal) != 0){
            String msStr = "【放款资金校验不通过】借款表和匹配表的匹配金额不相等   loanRequest.getTransferCode():" + loanRequestEntity.getTransferCode();
            throw new CommonException(msStr);
        }

        //更新放款处理中
        Date loanPaymentTime = new Date();
        loanRequestMapper.updateLoanInfoStatus(loanRequestEntity.getId(), loanRequestEntity.getLoanStatus(),
                LoanPaymentStatusEnum.LOAN_PAYMENT_PROCESSING.getCode(),  loanPaymentTime, null);

        //处理每笔放款（调用放款代付接口）
        for (CreditorInfoEntity creditorInfoEntity : creditorInfoList) {
            try {
                this.doLoanHandler(loanRequestEntity, creditorInfoEntity, false, null);
            } catch (Exception e) {
                log.error("============实际处理放款操作失败:[creditorNo={}]", creditorInfoEntity.getCreditorNo(), e);
            }
        }
    }

    /**
     * 实际处理放款逻辑
     * @param loanRequestEntity 借款记录
     * @param creditorInfoEntity 债权记录
     * @param isRetryLoan 是否是重新发起放款
     * @param OrgTradeSerialNo 原始放款交易流水号,只有在isRetryLoan=true时，才使用
     */
    public void doLoanHandler(LoanRequestEntity loanRequestEntity, CreditorInfoEntity creditorInfoEntity, boolean isRetryLoan, String OrgTradeSerialNo) throws Exception {
        com.zb.p2p.paychannel.api.common.CommonResp<TradeRespDTO> payResp = null;
        String tradeStatus = TradeStatusEnum.PROCESSING.getCode();
        String loanTradeSerialNumber = null;
        String errorMsg = null;

        // 去重
        if(LoanPaymentStatusEnum.LOAN_PAYMENT_SUCCESS.getCode().equals(loanRequestEntity.getLoanPaymentStatus() )){
            return;
        }
        try {
            // 生成放款交易流水号
            loanTradeSerialNumber = distributedSerialNoService.generatorSerialNoByIncrement(SequenceEnum.LOAD, loanRequestEntity.getSaleChannel(), 1);
            //放款
            payResp = paymentClientService.loanToBalance(creditorInfoEntity, loanRequestEntity, loanTradeSerialNumber);
            if (payResp == null || payResp.getData() == null
                    || !(GlobalVar.PAYMENT_TRADE_CODE_SUCCESS.equals(payResp.getCode()) && "P".equals(payResp.getData().getTradeStatus()) ) ) {
                tradeStatus = TradeStatusEnum.FAIL.getCode();
            }
        } catch (Exception e) {
            log.error("============该笔债权[creditorNo={}]放款失败",creditorInfoEntity.getCreditorNo(), e);
            errorMsg = e.getMessage();
            throw e;
        } finally {
            //插入一条放款记录
            PaymentRecordEntity paymentRecordEntity = new PaymentRecordEntity();
            paymentRecordEntity.setRefId(creditorInfoEntity.getCreditorNo() );// 放款编号=债权编号
            paymentRecordEntity.setTradeSerialNo(loanTradeSerialNumber);// 交易流水号
            paymentRecordEntity.setOrgTradeSerialNo(false == isRetryLoan ? loanTradeSerialNumber : OrgTradeSerialNo);// 原始交易流水号，考虑是否是重试放款的情况
            paymentRecordEntity.setTradeType(TradeTypeEnum.LOAN.getCode());
            paymentRecordEntity.setMemberId(creditorInfoEntity.getLoanMemberId());// 投资平台用户ID
            paymentRecordEntity.setAmount(creditorInfoEntity.getInvestAmount());
            paymentRecordEntity.setPayCode(null == payResp ? errorMsg : payResp.getCode());
            paymentRecordEntity.setPayMsg(null == payResp ? errorMsg : payResp.getMsg());
            paymentRecordEntity.setStartTime(new Date() );
            paymentRecordEntity.setEndTime(new Date() );
            paymentRecordEntity.setTradeStatus(tradeStatus);
            paymentRecordEntity.setTradeMsg(null==payResp?errorMsg:payResp.getData().getTradeMsg() );
            paymentRecordEntity.setPayNo(null==payResp?errorMsg:payResp.getData().getPayNo() );
            paymentRecordEntity.setPayChannel(loanRequestEntity.getPayChannel() );
            paymentRecordEntity.setMemo(TradeStatusEnum.PROCESSING.getCode().equals(tradeStatus) ? "放款处理中" : "放款失败" );
            paymentRecordMapper.insertSelective(paymentRecordEntity);
        }
    }
    /**
     * 放款异步回调
     * @param resp
     * @throws Exception
     */
    @Transactional
    public CommonResp<String> loanWithdrawCallBack(NotifyTradeStatusReq resp) throws Exception{
    	log.info( "放款回调开始:[serialNumber={}]", resp.getOrderNo() );

        String loanTradeSerialNumber = resp.getOrderNo();// 交易流水号
        String tradeStatus = resp.getStatus();

        // 校验
        PaymentRecordEntity paymentRecordEntity = paymentRecordMapper.selectByTradeSerialNo(loanTradeSerialNumber);
        if (null==paymentRecordEntity) {
            return new CommonResp(ResultCodeEnum.NOT_FOUND_INFO.code(), "交易流水号不存在: loanTradeSerialNumber=" + loanTradeSerialNumber );
        }
        CreditorInfoEntity creditorInfoEntity = creditorInfoMapper.selectByCreditorNo(paymentRecordEntity.getRefId() );
        if (null==creditorInfoEntity) {
            return new CommonResp(ResultCodeEnum.NOT_FOUND_INFO.code(), "债权记录不存在: loanTradeSerialNumber=" + loanTradeSerialNumber );
        }
    	LoanRequestEntity loanRequest = loanRequestMapper.selectByLoanNo(creditorInfoEntity.getLoanNo() );
    	if (null==loanRequest){
            return new CommonResp(ResultCodeEnum.NOT_FOUND_INFO.code(), "借款记录不存在: loanTradeSerialNumber=" + loanTradeSerialNumber );
        }

    	//幂等
    	if(LoanPaymentStatusEnum.LOAN_PAYMENT_SUCCESS.getCode().equals(loanRequest.getLoanPaymentStatus() )){
    		return new CommonResp(ResultCodeEnum.FAIL.code(), "放款回调重复: loanTradeSerialNumber=" + loanTradeSerialNumber );
    	}

        //计算已放款成功金额
        BigDecimal alreadyLoanAmtTotal = paymentRecordMapper.countAlreadyLoanAmtTotal(TradeTypeEnum.LOAN.getCode(),
                TradeStatusEnum.SUCCESS.getCode());
        alreadyLoanAmtTotal = alreadyLoanAmtTotal.add(paymentRecordEntity.getAmount()).setScale(2);

        //判断是否是最后一笔放款
        if (0 == alreadyLoanAmtTotal.compareTo(loanRequest.getMatchedAmount())) {
            log.info("monitor tradeStatus:{}", tradeStatus);
            String loanPaymentStatus = LoanPaymentStatusEnum.LOAN_PAYMENT_SUCCESS.getCode();
            if (GlobalVar.PAYMENT_TRADE_STATUS_SUCCESS.equals(tradeStatus)) {
                // 通知资管放款状态（资管要求只接受成功）
                NotifyLoanStatusReq notifyLoanStatusReq = new NotifyLoanStatusReq();
                notifyLoanStatusReq.setLoanOrderNo(loanRequest.getTransferCode());//资产编号
                notifyLoanStatusReq.setLoanStatus(5);
                notifyLoanStatusReq.setRepayTime(new Date() );
                this.notifyAssertLoanPaymentStatus(notifyLoanStatusReq );
            } else {
                loanPaymentStatus = LoanPaymentStatusEnum.LOAN_PAYMENT_FAILED.getCode();
            }
            //更新放款状态
            loanRequestMapper.updateLoanInfoStatus(loanRequest.getId(), loanRequest.getLoanStatus(),
                    loanPaymentStatus, new Date(), null);
        }

        //更新放款支付流水
        PaymentRecordEntity updatePaymentRecord = new PaymentRecordEntity();
        updatePaymentRecord.setTradeSerialNo(paymentRecordEntity.getTradeSerialNo() );
        updatePaymentRecord.setTradeStatus(tradeStatus );
        paymentRecordMapper.updatePaymentRecord(updatePaymentRecord);

        log.info( "放款回调结束:loanTradeSerialNumber={}", loanTradeSerialNumber );

        return CommonResp.success(null);

//        	// 发送放款短信
//            sendLoanMessage(loanRequest);

//        //通知MSD放款状态（MSD独立理财端）
//        notifyMSDLoanPaymentStatus(loanRequest, loanPaymentStatus, loanPaymentTime);
    }

    /**
     * 通知资管更新资产放款状态为“放款成功或放款失败”
     *
     * @param notifyLoanStatusReq
     * @throws Exception
     */
    public void notifyAssertLoanPaymentStatus(NotifyLoanStatusReq notifyLoanStatusReq) {
        String assetNo = notifyLoanStatusReq.getLoanOrderNo();
        try {
            //通知资管
            log.debug("通知资管放款状态请求参数：" + notifyLoanStatusReq);
            CommonResp resp = assetManagerClient.notifyAssertLoanStatus(notifyLoanStatusReq);
            log.debug("通知资管放款状态响应参数：" + resp);
            // 判断远程URl调用是否成功
            if (null != resp && !"0000".equals(resp.getCode())) {
                throw new BusinessException(ResultCodeEnum.FAIL.code(), "通知资管放款状态失败:" + resp.getMsg());
            }
        } catch (Exception e) {
            log.error("============通知资管更新资产放款状态失败:[assetNo={}]", assetNo, e);
            //进入重试队列
            InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
            interfaceRetryEntity.setBusinessType(InterfaceRetryBusinessTypeEnum.LOAN_SUCCESS_NOTIFY_ASSERT.getCode());
            interfaceRetryEntity.setRequestParam(JSON.toJSONString(notifyLoanStatusReq));
            interfaceRetryEntity.setStatus(InterfaceRetryStatusEnum.INIT.getCode());
            try {
                interfaceRetryService.saveInterfaceRetryRecord(interfaceRetryEntity);
            } catch (Exception e1) {
                log.error("============通知资管更新资产放款状态插入重试记录失败:[assetNo={}]", assetNo, e1);
            }
        }
    }

    /**
     * 交易结果确认处理
     * @param payStatus 支付状态
     * @param paymentRecordEntity 支付流水（放款）
     */
    public CommonResp<String> doTradeResultHandle(String payStatus, PaymentRecordEntity paymentRecordEntity) {
        try {
            TradeStatusEnum tradeStatusEnum = TradeStatusEnum.getByCode(payStatus);
            switch (tradeStatusEnum) {
                case PROCESSING:
                    //不做处理
                    break;
                case SUCCESS:
                    //更新交易流水
                    PaymentRecordEntity updatePaymentRecord = new PaymentRecordEntity();
                    updatePaymentRecord.setId(paymentRecordEntity.getId() );
                    updatePaymentRecord.setTradeStatus(payStatus );
                    paymentRecordMapper.updatePaymentRecord(updatePaymentRecord);
                    break;
                case FAIL:
                    //重新发起放款
                    this.doRetryLoanHandle(paymentRecordEntity);
                    break;
                default:
                    return new CommonResp(ResultCodeEnum.PARAMS_VALIDATE_FAIL.code(), "tradeStatus参数类型暂不支持");
            }
            // 返回
            return new CommonResp(ResultCodeEnum.SUCCESS.code(), ResultCodeEnum.SUCCESS.desc());
        } catch (Exception e) {
            log.error("doTradeResultHandle error", e);
            return new CommonResp(ResultCodeEnum.FAIL.code(), ResultCodeEnum.FAIL.desc());
        }
    }

    /**
     * 放款重试
     * @param paymentRecordEntity 放款支付流水
     * @throws Exception
     */
    public void doRetryLoanHandle (PaymentRecordEntity paymentRecordEntity ) throws Exception {
        String creditorNo = paymentRecordEntity.getRefId();
        CreditorInfoEntity creditorInfoEntity = creditorInfoMapper.selectByCreditorNo(creditorNo );
        if (null==creditorInfoEntity) {
            throw new BusinessException("债权记录不存在: creditorNo=" + creditorNo);
        }
        String loanNo = creditorInfoEntity.getLoanNo();
        LoanRequestEntity loanRequest = loanRequestMapper.selectByLoanNo(loanNo );
        if (null==loanRequest){
            throw new BusinessException("借款记录不存在:loanNo=" + loanNo);
        }
        //防止重复放款操作
        List<PaymentRecordEntity> recordEntityList = paymentRecordMapper.selectByOrgTradeSerialNoAndTradeType(paymentRecordEntity.getOrgTradeSerialNo(),
                paymentRecordEntity.getTradeType() );//DESC降序
        if (null!=recordEntityList && recordEntityList.size()>0 ) {
            if (paymentRecordEntity.getTradeSerialNo().equals(recordEntityList.get(0).getTradeSerialNo())) {//去最新一条比较
                this.doLoanHandler(loanRequest, creditorInfoEntity, true, paymentRecordEntity.getOrgTradeSerialNo() );
            }
        }
    }

//    private void sendLoanMessage(LoanRequestEntity loanRequest){
//        try {
//            //发送短信
//            String[] timeArr = StringUtils.split( DateUtils.format(new Date(), "MM-dd-HH-mm") , "-");
//            String[] loanTimeArr = StringUtils.split( DateUtils.format(loanRequest.getLoanTime(), "yyyy-MM-dd") , "-");
//            String bankNo = loanRequest.getBankAccountNo();
//
//            LoanWithdrawMessageInfo loanWithdrawMessageInfo = new LoanWithdrawMessageInfo();
//
//            DecimalFormat decimalFormat = new DecimalFormat("###.00");
//
//            loanWithdrawMessageInfo.setBankNo(bankNo.substring( bankNo.length() - 4 , bankNo.length()) );
//            loanWithdrawMessageInfo.setTotalAmount(decimalFormat.format( loanRequest.getMatchedAmount())   );
//            //loanWithdrawMessageInfo.setTotalAmount(NumberUtils.numberFormat(loanRequest.getMatchedAmount(), NumberUtils.DECIMAL_FORMAT_N2)  );
//            //loanWithdrawMessageInfo.setUserName(loanRequest.getMemberName());
//            loanWithdrawMessageInfo.setMonth(timeArr[0]);
//            loanWithdrawMessageInfo.setDay(timeArr[1]);
//            loanWithdrawMessageInfo.setHour(timeArr[2]);
//            loanWithdrawMessageInfo.setMins(timeArr[3]);
//            loanWithdrawMessageInfo.setLoanDay(loanTimeArr[2]);
//            loanWithdrawMessageInfo.setLoanMonth(loanTimeArr[1]);
//            loanWithdrawMessageInfo.setLoanYear(loanTimeArr[0]);
//
//            String mobile = loanRequest.getFinanceSubjectTel();
//            //String mobile = "15102106229";
//
//            boolean isSendSuccess = messageService.send(GlobalVar.LOAN_WITHDRAW_MESSAGE_TEMPLATE_CODE, mobile, JSON.toJSONString(loanWithdrawMessageInfo ));
//
//            if(isSendSuccess){
//                log.info("放款短信发送成功");
//            }else{
//                log.info("放款短信发送失败");
//            }
//        } catch (Exception e) {
//            log.error("放款短信发送异常",e);
//        }
//    }
//
//    /**
//     * 通知马上贷更新资产放款状态为“放款成功或放款失败”
//     * @param loanRequestEntity
//     */
//   public void notifyMSDLoanPaymentStatus(LoanRequestEntity loanRequestEntity, String loanPaymentStatus, Date loanPaymentTime) {
//       NotifyLoanStatusReq notifyLoanStatusReq = null;
//        try {
//            //生成流水号
//            String loanPaymentNotifyStatusSerialNo = distributedSerialNoService.generatorSerialNoByIncrement(
//                    SequenceEnum.PAYMENT_NOTIFY, loanRequestEntity.getSaleChannel(), 1);
//
//            List<MatchRecordEntity> list = matchRecordMapper.selectListByLoanNo(loanRequestEntity.getLoanNo());
//            List<Object> dataList = new ArrayList<>();
//            for (MatchRecordEntity entity : list){
//                dataList.add(entity.getCreditorNo());
//            }
//
//            //通知马上贷
//            notifyLoanStatusReq = new NotifyLoanStatusReq();
//            notifyLoanStatusReq.setTransactionNo(loanPaymentNotifyStatusSerialNo);
//            notifyLoanStatusReq.setOrderNo(loanRequestEntity.getLoanNo());
//            notifyLoanStatusReq.setNotifyType("1");//放款通知
//            if(LoanPaymentStatusEnum.LOAN_PAYMENT_FAILED.getCode().equals(loanPaymentStatus)){
//                notifyLoanStatusReq.setStatus("2");
//            }else if(LoanPaymentStatusEnum.LOAN_PAYMENT_SUCCESS.getCode().equals(loanPaymentStatus)){
//                notifyLoanStatusReq.setStatus("1");
//            }else{
//                notifyLoanStatusReq.setStatus(loanPaymentStatus);
//            }
//            notifyLoanStatusReq.setTradeAmount(String.valueOf(loanRequestEntity.getMatchedAmount()));
//            notifyLoanStatusReq.setRequestTime(DateUtils.format(loanPaymentTime, DateUtils.DEFAULT_DATETIME_FORMAT));
//            notifyLoanStatusReq.setData(dataList);
//            log.debug("通知MSD放款状态请求参数：" + notifyLoanStatusReq);
//            CommonResp resp = msdCallBackClient.notifyLoanStatus(notifyLoanStatusReq);
//            log.debug("通知MSD放款状态响应参数：" + resp);
//            // 判断远程URl调用是否成功
//            if (null != resp && !"0000".equals(resp.getCode())) {
//                throw new BusinessException(ResultCodeEnum.FAIL.code(), "通知MSD放款状态失败:" + resp.getMsg());
//            }
//        } catch (Exception e) {
//            log.error("", e);
//            //进入重试队列
//            InterfaceRetryEntity interfaceRetryEntity = new InterfaceRetryEntity();
//            interfaceRetryEntity.setBusinessType(InterfaceRetryBusinessTypeEnum.LOAN_SUCCESS_NOTIFY_MSD.getCode());
//            interfaceRetryEntity.setRequestParam(JSON.toJSONString(notifyLoanStatusReq));
//            interfaceRetryEntity.setStatus(InterfaceRetryStatusEnum.INIT.getCode());
//            try {
//                interfaceRetryService.saveInterfaceRetryRecord(interfaceRetryEntity);
//            } catch (Exception e1) {
//                log.error("", e1);
//            }
//        }
//    }

}
