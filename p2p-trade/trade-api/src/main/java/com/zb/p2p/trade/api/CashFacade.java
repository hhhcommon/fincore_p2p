package com.zb.p2p.trade.api;


import com.zb.p2p.trade.api.req.NotifyTradeStatusReq;
import com.zb.p2p.trade.common.model.CommonResp;

/**
 * Function:兑付信息查询
 * Author: created by liguoliang
 * Date: 2017/9/1 0001 上午 10:13
 * Version: 1.0
 */
public interface CashFacade {

    /**
     * 更新兑付计划到余额的支付状态（兑付）
     *
     * @param notifyTradeStatusReq
     * @return
     */
    void updateCashRecord2Balance(NotifyTradeStatusReq notifyTradeStatusReq);

    void updateCashRecord2Invest(NotifyTradeStatusReq notifyTradeStatusReq);

    /**
     * 余额到卡（提现）
     *
     * @param notifyTradeStatusReq
     * @return
     */
    CommonResp<String> processRepaymentFeeNotifyResult(NotifyTradeStatusReq notifyTradeStatusReq);


}
