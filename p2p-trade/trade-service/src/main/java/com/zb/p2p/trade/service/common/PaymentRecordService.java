package com.zb.p2p.trade.service.common;


import com.zb.p2p.trade.api.req.NotifyTradeStatusReq;
import com.zb.p2p.trade.api.resp.FailCashResp;
import com.zb.p2p.trade.common.enums.TradeStatusEnum;
import com.zb.p2p.trade.persistence.entity.PaymentRecordEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by limingxin on 2018/1/11.
 */
public interface PaymentRecordService {

    PaymentRecordEntity queryBySerialNo(String serialNo);

    void savePaymentRecord(PaymentRecordEntity paymentRecordEntity);

    int batchInsert(List<PaymentRecordEntity> paymentRecordEntityList);

    void updatePaymentRecord(PaymentRecordEntity paymentRecordEntity);

    List<FailCashResp> queryFailAll(String productCode);

    int updateByPaymentNotifyResult(NotifyTradeStatusReq notifyTradeReq);

    /**
     * 根据引用ID、类型及状态查询
     * @param refId
     * @param status
     * @return
     */
    PaymentRecordEntity queryByRefAndStatus(String refId, List<String> status);

    /**
     * 根据状态和发起支付时间查询
     * @param status
     * @param beginTime
     * @param endTime
     * @param limitRows
     * @return
     */
    List<PaymentRecordEntity> queryWaitingClose(TradeStatusEnum status, Date beginTime, Date endTime, int limitRows);

    /**
     * 根据状态和发起支付时间查询
     * @param status
     * @param beginTime
     * @param endTime
     * @param limitRows
     * @return
     */
    List<PaymentRecordEntity> queryFinalFailed(TradeStatusEnum status, Date beginTime, Date endTime, int limitRows);
}
