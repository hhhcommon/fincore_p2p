package com.zb.p2p.trade.web.controller;


import com.zb.p2p.trade.api.PaymentCallBackFacade;
import com.zb.p2p.trade.api.req.NotifyTradeStatusReq;
import com.zb.p2p.trade.common.model.CommonResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/callBack")
public class CallbackController {

    private final PaymentCallBackFacade paymentCallBack;

    @Autowired
    public CallbackController(PaymentCallBackFacade paymentCallBack) {
        this.paymentCallBack = paymentCallBack;
    }

    /**
     * 支付回调
     *
     * @param notifyTradeStatusReq 支付回调
     * @return
     */
    @PostMapping("/payCallBack")
    public CommonResp callback(@RequestBody @Valid NotifyTradeStatusReq notifyTradeStatusReq) {
        return paymentCallBack.callback(notifyTradeStatusReq);
    }

}
