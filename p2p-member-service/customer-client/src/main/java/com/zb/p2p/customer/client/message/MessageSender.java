package com.zb.p2p.customer.client.message;

import com.zb.p2p.customer.client.message.api.MessageReq;
import com.zb.p2p.customer.client.message.api.MessageResp;
import com.zb.p2p.customer.client.message.api.ValicodeMessageReq;
import com.zb.p2p.customer.client.message.api.ValicodeMessageResp;
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
    
    @RequestLine("POST /message/message/sendValidateCode")
    @Headers("Content-Type: application/json")
    ValicodeMessageResp sendValidateCode(ValicodeMessageReq valicodeMessageReq);

}
