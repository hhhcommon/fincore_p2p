package com.zb.p2p.trade.api;


import com.zb.p2p.trade.api.req.NotifyTradeStatusReq;
import com.zb.p2p.trade.common.model.CommonResp;

/**
 * Created by limingxin on 2018/1/9.
 */
public interface PaymentCallBackFacade {

    CommonResp callback(NotifyTradeStatusReq notifyTradeStatusReq);
}
