package com.zb.p2p.facade.service;

import com.zb.p2p.facade.api.req.NotifyTradeStatusReq;
import com.zb.p2p.facade.api.resp.CommonResp;

/**
 * Created by limingxin on 2018/1/9.
 */
public interface PaymentCallBackFacade {

    CommonResp callback(NotifyTradeStatusReq notifyTradeStatusReq);
}
