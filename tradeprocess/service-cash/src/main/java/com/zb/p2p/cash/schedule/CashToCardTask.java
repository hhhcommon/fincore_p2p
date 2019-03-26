package com.zb.p2p.cash.schedule;

import com.alibaba.fastjson.JSON;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.zb.fincore.common.utils.BeanUtils;
import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.p2p.CashNotify;
import com.zb.p2p.GlobalVar;
import com.zb.p2p.cash.service.impl.CashRecordServiceImpl;
import com.zb.p2p.dao.master.AccountDao;
import com.zb.p2p.dao.master.CashRecordDAO;
import com.zb.p2p.dao.master.PaymentRecordDao;
import com.zb.p2p.entity.AccountEntity;
import com.zb.p2p.entity.CashRecordEntity;
import com.zb.p2p.entity.PaymentRecordEntity;
import com.zb.p2p.enums.*;
import com.zb.p2p.service.common.DistributedSerialNoService;
import com.zb.p2p.service.common.PaymentRecordService;
import com.zb.payment.comm.constant.MsdTradeType;
import com.zb.payment.msd.cashier.facade.TradeFacade;
import com.zb.payment.msd.cashier.facade.dto.req.WithdrawalsReqDTO;
import com.zb.payment.msd.cashier.facade.dto.rsp.CashierRspDTO;
import com.zb.payment.msd.cashier.facade.dto.rsp.TradeBaseRspDTO;
import com.zb.payment.msd.cashier.facade.enums.AccountPurposeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 兑付到卡处理
 * Created by limingxin on 2018/1/10.
 */
@Component("cashToCardTask")
@Slf4j
public class CashToCardTask implements IScheduleTaskDealSingle {
    @Autowired
    private TradeFacade tradeFacade;
    @Autowired
    private CashRecordDAO cashRecordDAO;
    @Autowired
    private CashRecordServiceImpl cashRecordService;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private DistributedSerialNoService distributedSerialNoService;
    @Autowired
    private PaymentRecordService paymentRecordService;

    /**
     * 转账（兑付到余额）完成后执行提现
     */
    public boolean cashWithDrawl() {
        List<CashNotify> orders = cashRecordDAO.withdrawlList();
        for (CashNotify cashNotify : orders) {
            if (cashNotify.getCashCommand().equals(CashCommandEnum.AVAIL_CASH.getCode())) {
                CashRecordEntity cashRecordEntity = new CashRecordEntity();
                cashRecordEntity.setMemberId(cashNotify.getMemberId());
                cashRecordEntity.setProductCode(cashNotify.getProductCode());
                //生成提现指令编号
                WithdrawalsReqDTO withdrawalsReqDTO = new WithdrawalsReqDTO();
                String reqNo = distributedSerialNoService.generatorSerialNoByIncrement(SequenceEnum.WITHDRAW, SourceIdEnum.MSD.getCode(), 1);
                withdrawalsReqDTO.setSignId(cashRecordService.getCardSignId(cashRecordEntity));
                withdrawalsReqDTO.setMemberId(cashNotify.getMemberId());
                withdrawalsReqDTO.setOrderNo(reqNo);
                withdrawalsReqDTO.setOrderTime(new Date());
                BigDecimal tradeAmount = cashNotify.getCashIncome().add(cashNotify.getCashAmount());
                withdrawalsReqDTO.setTradeAmount(tradeAmount);
                withdrawalsReqDTO.setAccountPurpose(AccountPurposeEnum._102.getCode());
                withdrawalsReqDTO.setTradeType(MsdTradeType.WITHHOLDING_PUBLIC.getCode());
                withdrawalsReqDTO.setSourceId(SourceIdEnum.MSD.getCode());
                withdrawalsReqDTO.setOrderFrom(GlobalVar.SYS_IDENTIFY_CODE);
                //基于account表（产品+用户纬度）的减去本次兑付的本金 收益
                AccountEntity accountEntity = accountDao.selectByMemberIdAndProductCode(cashRecordEntity.getMemberId(), cashRecordEntity.getProductCode());
                //虚户钱足够才可以执行提现
                if (accountEntity.getAmount().add(accountEntity.getInterest()).compareTo(tradeAmount) >= 0) {
                    CashierRspDTO<TradeBaseRspDTO> cashierRspDTO = null;
                    try {
                        log.info("payment withdrawals start req={}", JSON.toJSONString(withdrawalsReqDTO));
                        cashierRspDTO = tradeFacade.withdrawals(withdrawalsReqDTO);
                        log.info("payment withdrawals end resp={}", JSON.toJSONString(cashierRspDTO));
                    } catch (Exception e) {
                        log.error("调用支付提现失败", e);
                        //这里如果失败，job会自动重试，不需要通知资管兑付失败
                    }
                    if (cashierRspDTO != null && cashierRspDTO.getCode().equals(GlobalVar.PAYMENT_SUCCESS_CODE)) {
                        //兑付计划改为提现处理中
                        CashRecordEntity cashRecordEntity_ = new CashRecordEntity();
                        cashRecordEntity_.setStatus(CashStatusEnum.CASHING_CARD.getCode());
                        cashRecordEntity_.setProductCode(cashNotify.getProductCode());
                        cashRecordEntity_.setMemberId(cashNotify.getMemberId());
                        cashRecordDAO.updateStatusByPCodeAndMemId(cashRecordEntity_);
                        //虚户扣掉提现金额 标记提现进行中
                        AccountEntity accountEntity_ = new AccountEntity();
                        accountEntity_.setId(accountEntity.getId());
                        accountEntity_.setAmount(accountEntity.getAmount().subtract(cashNotify.getCashAmount()));
                        accountEntity_.setInterest(accountEntity.getInterest().subtract(cashNotify.getCashIncome()));
                        accountEntity_.setWithdrawNo(reqNo);
                        accountEntity_.setWithdrawFlag(WithdrawFlagEnum.WITHDRAWING.getCode());
                        accountDao.updateByPrimaryKeySelective(accountEntity_);
                        //记录支付提现流水
                        PaymentRecordEntity paymentRecordEntity = new PaymentRecordEntity();
                        paymentRecordEntity.setAmount(tradeAmount);
                        paymentRecordEntity.setType(SequenceEnum.WITHDRAW.getCode());
                        paymentRecordEntity.setRefId(String.valueOf(accountEntity.getId()));//虚户主键
                        paymentRecordEntity.setPaymentSerialNo(reqNo);//提现单号
                        paymentRecordEntity.setStartTime(new Date());
                        paymentRecordEntity.setPayCode(cashierRspDTO.getCode());
                        paymentRecordEntity.setPayMsg(cashierRspDTO.getMsg());
                        paymentRecordService.savePaymentRecord(paymentRecordEntity);
                    }
                }
            }
            //等待回调更新兑付状态为到卡完成
        }
        return true;
    }

    @Override
    public boolean execute(Object o, String s) throws Exception {
        return false;
    }

    @Override
    @Transactional
    public List selectTasks(String s, String s1, int i, List list, int i1) throws Exception {
        log.info("提现到卡任务处理中..");
        //注意这个任务不能分片执行
        cashWithDrawl();
        return null;
    }

    @Override
    public Comparator getComparator() {
        return null;
    }
}
