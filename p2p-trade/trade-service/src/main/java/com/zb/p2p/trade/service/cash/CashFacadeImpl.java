package com.zb.p2p.trade.service.cash;

import com.zb.fincore.common.enums.ResultCodeEnum;
import com.zb.p2p.trade.api.CashFacade;
import com.zb.p2p.trade.api.req.NotifyTradeStatusReq;
import com.zb.p2p.trade.common.constant.GlobalVar;
import com.zb.p2p.trade.common.enums.CashStatusEnum;
import com.zb.p2p.trade.common.enums.RepaymentFeeStatusEnum;
import com.zb.p2p.trade.common.model.CommonResp;
import com.zb.p2p.trade.persistence.dao.CashRecordMapper;
import com.zb.p2p.trade.persistence.dao.RepayBillPlanMapper;
import com.zb.p2p.trade.persistence.entity.CashRecordEntity;
import com.zb.p2p.trade.persistence.entity.PaymentRecordEntity;
import com.zb.p2p.trade.persistence.entity.RepayBillPlanEntity;
import com.zb.p2p.trade.service.common.PaymentRecordService;
import com.zb.p2p.trade.service.match.CreditorInfoService;
import com.zb.p2p.trade.service.order.OrderAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


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
    private CashRecordMapper cashRecordMapper;
    @Autowired
    private RepayBillPlanMapper repayBillPlanMapper;
    @Autowired
    private PaymentRecordService paymentRecordService;
    @Autowired
    private OrderAsyncService orderAsyncService;
    @Autowired
    private CreditorInfoService creditorInfoService;

    @Override
    @Transactional
    public void updateCashRecord2Balance(NotifyTradeStatusReq notifyResult) {
        //这里是明确收到回调指令的，如果出现F的情况需要运维报警
        CashRecordEntity cashRecordEntity = cashRecordMapper.selectByReqNo(notifyResult.getOrderNo());
        if (cashRecordEntity != null && GlobalVar.PAYMENT_TRADE_STATUS_SUCCESS.equals(notifyResult.getStatus())) {
            //更新转账状态
            CashRecordEntity updateCashRecord = new CashRecordEntity();
            updateCashRecord.setStatus(CashStatusEnum.CASH_SUCCESS.getCode());
            updateCashRecord.setId(cashRecordEntity.getId());
            cashRecordMapper.updateByPrimaryKeySelective(updateCashRecord);

            // 更新债权
            creditorInfoService.updateCreditorAmountAndNotifyOrder(cashRecordEntity);

            // 通知订单兑付完成
            orderAsyncService.sendCashResultToTxsOrder(cashRecordEntity);

        } else {
          log.info("兑付通知结果处理失败，未执行兑付计划及债权的更新操作，ReqNo={}", notifyResult.getOrderNo());
        }

        //更新支付流水
        paymentRecordService.updateByPaymentNotifyResult(notifyResult);
    }

    @Override
    @Transactional
    public void updateCashRecord2Invest(NotifyTradeStatusReq notifyTradeReq) {
        CashRecordEntity cashRecordEntity = cashRecordMapper.selectByReqNo(notifyTradeReq.getOrderNo());
        Assert.notNull(cashRecordEntity, "未找到复投兑付计划信息");
        // 支付结果状态
        boolean isPaySuccess = notifyTradeReq.getStatus().equals(GlobalVar.PAYMENT_TRADE_STATUS_SUCCESS);
        //更新兑付状态
        CashRecordEntity updateCashRecord = new CashRecordEntity();
        updateCashRecord.setStatus(isPaySuccess ?
                CashStatusEnum.CASH_SUCCESS.getCode() : CashStatusEnum.CASH_FAIL.getCode());
        updateCashRecord.setId(cashRecordEntity.getId());
        cashRecordMapper.updateByPrimaryKeySelective(updateCashRecord);
        // 更新债权
        creditorInfoService.updateCreditorAmountAndNotifyOrder(cashRecordEntity);

        //更新支付流水
        paymentRecordService.updateByPaymentNotifyResult(notifyTradeReq);

    }

    @Override
    @Transactional
    public CommonResp<String> processRepaymentFeeNotifyResult(NotifyTradeStatusReq notifyTradeReq) {
        // 获取支付记录
        PaymentRecordEntity paymentRecord = paymentRecordService.queryBySerialNo(notifyTradeReq.getOrderNo());
        Assert.notNull(paymentRecord, "支付记录不存在");

        // 获取还款计划
        RepayBillPlanEntity p2pFundRecord = repayBillPlanMapper.selectByPrimaryKey(Long.valueOf(paymentRecord.getRefId()));
        if (p2pFundRecord != null && GlobalVar.PAYMENT_TRADE_STATUS_SUCCESS.equals(notifyTradeReq.getStatus())) {
            // 更新状态
            RepayBillPlanEntity record = new RepayBillPlanEntity();
            record.setRepaymentFeeStatus(RepaymentFeeStatusEnum.OVERDUE_REPAID.getCode());
            record.setId(p2pFundRecord.getId());
            repayBillPlanMapper.updateByPrimaryKeySelective(record);
        } else {
            log.info("手续费通知结果处理失败，未还款计划及支付记录的更新操作，ReqNo={}", notifyTradeReq.getOrderNo());
            return new CommonResp(ResultCodeEnum.FAIL.code(), "记录不存在");
        }
        //更新支付流水
        paymentRecordService.updateByPaymentNotifyResult(notifyTradeReq);
        return  new CommonResp();
    }
}
