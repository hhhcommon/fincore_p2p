package com.zb.p2p.trade.service.common.impl;

import com.google.common.collect.Lists;
import com.zb.p2p.trade.api.req.NotifyTradeStatusReq;
import com.zb.p2p.trade.api.resp.FailCashResp;
import com.zb.p2p.trade.common.enums.TradeStatusEnum;
import com.zb.p2p.trade.persistence.dao.PaymentRecordMapper;
import com.zb.p2p.trade.persistence.entity.PaymentRecordEntity;
import com.zb.p2p.trade.service.common.PaymentRecordService;
import com.zb.p2p.trade.service.config.ReadOnlyConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by limingxin on 2018/1/11.
 */
@Service
public class PaymentRecordServiceImpl implements PaymentRecordService {
    @Autowired
    private PaymentRecordMapper paymentRecordMapper;

    @Override
    public PaymentRecordEntity queryBySerialNo(String serialNo) {
        return paymentRecordMapper.selectBySerialNo(serialNo);
    }

    @Override
    public void savePaymentRecord(PaymentRecordEntity paymentRecordEntity) {
        paymentRecordMapper.insertSelective(paymentRecordEntity);
    }

    @Override
    public int batchInsert(List<PaymentRecordEntity> paymentRecordEntityList) {
        return paymentRecordMapper.batchInsert(paymentRecordEntityList);
    }

    @Override
    public void updatePaymentRecord(PaymentRecordEntity paymentRecordEntity) {
        paymentRecordMapper.updatePaymentRecord(paymentRecordEntity);
    }

    @Override
    @ReadOnlyConnection
    public List<FailCashResp> queryFailAll(String productCode) {
        List<PaymentRecordEntity> paymentRecordEntityList = paymentRecordMapper.queryFailAll(productCode);
        List<FailCashResp> FailCashRespList = Lists.newArrayList();
        for (PaymentRecordEntity paymentRecordEntity : paymentRecordEntityList) {
            FailCashResp FailCashResp = new FailCashResp();
            FailCashResp.setErrorOrderNo(paymentRecordEntity.getTradeSerialNo());
            FailCashResp.setErrorCode(paymentRecordEntity.getTradeStatus());
            FailCashResp.setErrorMsg(paymentRecordEntity.getTradeMsg());
            FailCashRespList.add(FailCashResp);
        }
        return FailCashRespList;
    }

    @Override
    public int updateByPaymentNotifyResult(NotifyTradeStatusReq notifyTradeReq){
        PaymentRecordEntity paymentRecordEntity = new PaymentRecordEntity();
        paymentRecordEntity.setTradeStatus(notifyTradeReq.getStatus());
        paymentRecordEntity.setTradeMsg(notifyTradeReq.getErrMsg());
        paymentRecordEntity.setEndTime(new Date());
        paymentRecordEntity.setTradeSerialNo(notifyTradeReq.getOrderNo());
        paymentRecordEntity.setPayNo(notifyTradeReq.getNotifyNo());

        return paymentRecordMapper.updatePaymentRecord(paymentRecordEntity);
    }

    @Override
    @ReadOnlyConnection
    public PaymentRecordEntity queryByRefAndStatus(String refId, List<String> statusList) {
        List<PaymentRecordEntity> paymentRecordList = paymentRecordMapper.selectByRefAndType(refId, statusList);
        return paymentRecordList != null ? paymentRecordList.get(0) : null;
    }

    @Override
    @ReadOnlyConnection
    public List<PaymentRecordEntity> queryWaitingClose(TradeStatusEnum status, Date beginTime, Date endTime, int limitRows) {
        return paymentRecordMapper.selectWaitingClose(status.getCode(), beginTime, endTime, limitRows);
    }

    @Override
    @ReadOnlyConnection
    public List<PaymentRecordEntity> queryFinalFailed(TradeStatusEnum status, Date beginTime, Date endTime, int limitRows){
        return paymentRecordMapper.selectFinalFailed(status.getCode(), beginTime, endTime, limitRows);
    }
}
