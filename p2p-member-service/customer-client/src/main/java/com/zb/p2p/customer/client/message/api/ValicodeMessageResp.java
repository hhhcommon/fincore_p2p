package com.zb.p2p.customer.client.message.api;

import lombok.Data;

/**
 * Created by limingxin on 2018/1/15.
 */
@Data
public class ValicodeMessageResp {
    /**
     * 消息编码
     * 消息内容
     * 是否成功
     */
    private String errorCode,
            errorMessage,
            success;
    
    private String verificationCode;//验证码
}
