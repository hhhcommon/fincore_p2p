package com.zb.p2p.trade.service.message;

import com.zb.p2p.trade.client.request.MessageReq;
import com.zb.p2p.trade.client.request.MessageResp;
import feign.Headers;
import feign.RequestLine;

/**
 * 短信服务
 * Created by limingxin on 2018/1/15.
 */
public interface MessageSender {

    @RequestLine("POST /message/message/sendMessage")
    @Headers("Content-Type: application/json")
    MessageResp sendMessage(MessageReq messageReq);

}
