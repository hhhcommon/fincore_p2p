package com.zb.p2p.facade.service;

import com.zb.p2p.facade.api.req.CashRecordReq;
import com.zb.p2p.facade.api.req.NotifyTradeStatusReq;
import com.zb.p2p.facade.api.resp.CashRecordDTO;
import com.zb.p2p.facade.api.resp.CommonResp;

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

    /**
     * 更新兑付计划到卡的的支付状态（提现）
     *
     * @param notifyTradeStatusReq
     * @return
     */
    void updateCashRecord2Card(NotifyTradeStatusReq notifyTradeStatusReq);

}
