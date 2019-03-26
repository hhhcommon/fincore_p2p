package com.zb.p2p.facade.impl;

import com.google.common.collect.Lists;
import com.zb.p2p.cash.service.CashRecordService;
import com.zb.p2p.cash.service.impl.CashRecordServiceImpl;
import com.zb.p2p.dao.master.AccountDao;
import com.zb.p2p.dao.master.CashRecordDAO;
import com.zb.p2p.entity.AccountEntity;
import com.zb.p2p.entity.CashRecordEntity;
import com.zb.p2p.entity.PaymentRecordEntity;
import com.zb.p2p.enums.*;
import com.zb.p2p.facade.api.req.NotifyTradeStatusReq;
import com.zb.p2p.facade.service.CashFacade;
import com.zb.p2p.service.common.PaymentRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * Function:兑付信息查询
 * Author: created by liguoliang
 * Date: 2017/8/31 0031 下午 5:08
 * Version: 1.0
 */
@Service
@Slf4j
public class CashFacadeImpl implements CashFacade {
    @Autowired
    private CashRecordDAO cashRecordDAO;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private PaymentRecordService paymentRecordService;
    private static final String SUCCESS = "S";//成功
    private static final String FAIL = "F";//失败
    @Autowired
    CashRecordServiceImpl cashRecordService;

    @Override
    @Transactional
    public void updateCashRecord2Balance(NotifyTradeStatusReq notifyTradeStatusReq) {
        //这里是明确收到回调指令的，如果出现F的情况需要运维报警，只能人工处理
        if (!notifyTradeStatusReq.getOrderNo().contains(SequenceEnum.TRANSFER_FEE.getCode())) {//不是转手续费
            CashRecordEntity cashRecordEntity = cashRecordDAO.selectByReqNo(notifyTradeStatusReq.getOrderNo());
            if (cashRecordEntity != null) {
                //更新转账状态
                CashRecordEntity cashRecordEntity_ = new CashRecordEntity();
                cashRecordEntity_.setStatus(notifyTradeStatusReq.getTradeStatus().equals(SUCCESS) ?
                        CashStatusEnum.CASH_SUCCESS.getCode() : CashStatusEnum.CASH_FAIL.getCode());
                cashRecordEntity_.setId(cashRecordEntity.getId());
                cashRecordDAO.updateByPrimaryKeySelective(cashRecordEntity_);
            }
        } else {
            if (!notifyTradeStatusReq.getTradeCode().equals(ResponseCodeEnum.RESPONSE_SUCCESS.getCode())) {
                //转手续费失败需要变更状态为手续费未转
                String loanNo = paymentRecordService.queryBySerialNo(notifyTradeStatusReq.getOrderNo());
                cashRecordDAO.updateFeeStatusByLoanNo(loanNo, FeeTransferStatusEnum.MATCH_FAIL.getCode());
            }
        }
        //更新支付流水
        PaymentRecordEntity paymentRecordEntity = new PaymentRecordEntity();
        paymentRecordEntity.setCallBackCode(notifyTradeStatusReq.getTradeCode());
        paymentRecordEntity.setCallBackMsg(notifyTradeStatusReq.getTradeMsg());
        paymentRecordEntity.setEndTime(new Date());
        paymentRecordEntity.setPaymentSerialNo(notifyTradeStatusReq.getOrderNo());
        paymentRecordService.updatePaymentRecord(paymentRecordEntity);

        //回调结果出现业务失败 通知资管为业务失败
        if (!paymentRecordEntity.getCallBackCode().equals(ResponseCodeEnum.RESPONSE_SUCCESS)) {
            String loanNo = paymentRecordService.queryBySerialNo(notifyTradeStatusReq.getOrderNo());
            cashRecordService.notifyAssetParty(Lists.newArrayList(loanNo), CashStatusEnum.CASH_FAIL);
        }

    }

    @Override
    @Transactional
    public void updateCashRecord2Card(NotifyTradeStatusReq notifyTradeStatusReq) {
        //这里是明确收到回调指令的，如果出现F的情况需要运维报警，只能人工处理
        //更新提现状态
        AccountEntity accountEntity = accountDao.selectByWithdrawNo(notifyTradeStatusReq.getOrderNo());
        AccountEntity accountEntity_ = new AccountEntity();
        accountEntity_.setId(accountEntity.getId());
        accountEntity_.setWithdrawFlag(notifyTradeStatusReq.getTradeStatus().equals(SUCCESS) ?
                WithdrawFlagEnum.WITHDRAW_SUCCESS.getCode() : WithdrawFlagEnum.WITHDRAW_FAIL.getCode());
        accountDao.updateByPrimaryKeySelective(accountEntity_);
        //更新支付流水
        PaymentRecordEntity paymentRecordEntity = new PaymentRecordEntity();
        paymentRecordEntity.setCallBackCode(notifyTradeStatusReq.getTradeCode());
        paymentRecordEntity.setCallBackMsg(notifyTradeStatusReq.getTradeMsg());
        paymentRecordEntity.setEndTime(new Date());
        paymentRecordEntity.setPaymentSerialNo(notifyTradeStatusReq.getOrderNo());
        paymentRecordService.updatePaymentRecord(paymentRecordEntity);
        //以产品和用户纬度提现完成后 需要更新兑付计划状态为到卡完成
        //查询兑付计划
        CashRecordEntity query = new CashRecordEntity();
        query.setProductCode(accountEntity.getProductCode());
        query.setMemberId(accountEntity.getMemberId());
        query.setSaleChannel(SourceIdEnum.MSD.getCode());
        List<CashRecordEntity> cashRecordEntitys = cashRecordDAO.queryCashRecord(query);
        for (CashRecordEntity cashRecordEntity : cashRecordEntitys) {
            CashRecordEntity cashRecordEntity_ = new CashRecordEntity();
            cashRecordEntity_.setId(cashRecordEntity.getId());
            cashRecordEntity_.setStatus(notifyTradeStatusReq.getTradeStatus().equals(SUCCESS) ?
                    CashStatusEnum.CASHED_CARD_SUCCESS.getCode() : CashStatusEnum.CASHED_CARD_FAIL.getCode());
            cashRecordDAO.updateByPrimaryKeySelective(cashRecordEntity_);
        }
    }
}
