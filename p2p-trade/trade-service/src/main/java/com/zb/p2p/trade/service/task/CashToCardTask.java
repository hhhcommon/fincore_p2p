package com.zb.p2p.trade.service.task;

import com.zb.p2p.trade.common.enums.CashStatusEnum;
import com.zb.p2p.trade.common.enums.PayChannelEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.util.DateUtil;
import com.zb.p2p.trade.persistence.entity.CashRecordEntity;
import com.zb.p2p.trade.service.cash.impl.CashRecordServiceImpl;
import com.zb.p2p.trade.service.common.AbstractScheduleTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 兑付到卡处理
 * Created by limingxin on 2018/1/10.
 */
@Component("cashToCardTask")
public class CashToCardTask extends AbstractScheduleTask {
    @Autowired
    private CashRecordServiceImpl cashRecordService;

    /**
     * 转账（兑付到余额）完成后执行提现
     */
    @Transactional
    public boolean cashWithDrawl() {
//        List<CashNotify> orders = cashRecordMapper.selectByWithdrawlStatus();
//        for (CashNotify cashNotify : orders) {
//            if (cashNotify.getCashCommand().equals(RepaymentFeeStatusEnum.AVAIL_CASH.getCode())) {
//                CashRecordEntity cashRecordEntity = new CashRecordEntity();
//                cashRecordEntity.setNotifyTime(cashNotify.getNotifyTime());
//                cashRecordEntity.setProductCode(cashNotify.getProductCode());
//                //生成提现指令编号
//                PayFundRecordReq withdrawalsReqDTO = new PayFundRecordReq();
//                String reqNo = distributedSerialNoService.generatorSerialNoByIncrement(SequenceEnum.WITHDRAW, SourceIdEnum.TXS.getCode(), 1);
//                withdrawalsReqDTO.setSignId(cashRecordService.getCardSignId(cashRecordEntity));
//                withdrawalsReqDTO.setNotifyTime(cashNotify.getNotifyTime());
//                withdrawalsReqDTO.setOrderNo(reqNo);
//                withdrawalsReqDTO.setOrderTime(DateUtil.format(new Date(),DateUtil.LONG_FORMAT));
//                BigDecimal tradeAmount = cashNotify.getCashIncome().add(cashNotify.getCashAmount());
//                withdrawalsReqDTO.setTradeAmount(tradeAmount);
//                withdrawalsReqDTO.setAccountPurpose(AccountPurposeEnum._102.getCode());
//                withdrawalsReqDTO.setChannelType(SourceIdEnum.TXS.getCode());
                //基于account表（产品+用户纬度）的减去本次兑付的本金 收益
//                AccountEntity accountEntity = accountMapper.selectByMemberIdAndProductCode(cashRecordEntity.getNotifyTime(), cashRecordEntity.getProductCode());
//                //虚户钱足够才可以执行提现
//                if (accountEntity.getAmount().add(accountEntity.getInterest()).compareTo(tradeAmount) >= 0) {
//                    CashierRspDTO<TradeBaseRspDTO> cashierRspDTO = null;
//                    try {
//                        logger.info("payment withdrawals start req={}", JSON.toJSONString(withdrawalsReqDTO));
//                        cashierRspDTO = paymentClientManager.withdrawalsPrivate(withdrawalsReqDTO);
//                        logger.info("payment withdrawals end resp={}", JSON.toJSONString(cashierRspDTO));
//                    } catch (Exception e) {
//                        logger.error("调用支付提现失败", e);
//                        //这里如果失败，job会自动重试，不需要通知资管兑付失败
//                    }
//                    if (cashierRspDTO != null && cashierRspDTO.getCode().equals(GlobalVar.PAYMENT_SUCCESS_CODE)) {
////                        //兑付计划改为提现处理中
////                        CashRecordEntity cashRecordEntity_ = new CashRecordEntity();
////                        cashRecordEntity_.setStatus(CashStatusEnum.CASHING_CARD.getCode());
////                        cashRecordEntity_.setProductCode(cashNotify.getProductCode());
////                        cashRecordEntity_.setNotifyTime(cashNotify.getNotifyTime());
////                        cashRecordMapper.updateStatusByPCodeAndMemId(cashRecordEntity_);
////                        //虚户扣掉提现金额 标记提现进行中
////                        AccountEntity accountEntity_ = new AccountEntity();
////                        accountEntity_.setId(accountEntity.getId());
////                        accountEntity_.setAmount(accountEntity.getAmount().subtract(cashNotify.getCashAmount()));
////                        accountEntity_.setInterest(accountEntity.getInterest().subtract(cashNotify.getCashIncome()));
////                        accountEntity_.setWithdrawNo(reqNo);
////                        accountEntity_.setWithdrawFlag(WithdrawFlagEnum.WITHDRAWING.getCode());
////                        accountMapper.updateByPrimaryKeySelective(accountEntity_);
//                        //记录支付提现流水
//                        PaymentRecordEntity paymentRecordEntity = new PaymentRecordEntity();
//                        paymentRecordEntity.setNotifyTime(cashRecordEntity.getNotifyTime());
//                        paymentRecordEntity.setAmount(tradeAmount);
//                        paymentRecordEntity.setTradeType(SequenceEnum.WITHDRAW.getCode());
//                        paymentRecordEntity.setRefId(String.valueOf(accountEntity.getId()));//虚户主键
//                        paymentRecordEntity.setTradeSerialNo(reqNo);//提现单号
//                        paymentRecordEntity.setStartTime(new Date());
//                        paymentRecordEntity.setPayCode(cashierRspDTO.getCode());
//                        paymentRecordEntity.setPayMsg(cashierRspDTO.getMsg());
//                        paymentRecordService.savePaymentRecord(paymentRecordEntity);
//                    }
//                }
//            }
//            //等待回调更新兑付状态为到卡完成
//        }
        return true;
    }


    @Override
    public List<CashRecordEntity> selectProcessItems(String taskParameter, List taskItemList, int eachFetchDataNum) throws BusinessException {
        cashWithDrawl();
        // 执行开始和结束时间，偏移量
        final Date now = new Date();
        final Date beginTime = DateUtil.addDays(now, -1 * 3);
        final Date endTime = DateUtil.addHours(now, -1 * 1);

        List<CashRecordEntity> cashRecordList = cashRecordService.selectByWithdrawlStatus(
                CashStatusEnum.CASH_SUCCESS, PayChannelEnum.BAOFU, beginTime, endTime, eachFetchDataNum);

        if (CollectionUtils.isEmpty(cashRecordList)) {
            return Collections.emptyList();
        }

        return cashRecordList;
    }

    @Override
    protected boolean process(Object o, String s) throws BusinessException {


        return false;
    }

    @Override
    protected String getTaskName() {
        return "提现到卡任务";
    }
}
