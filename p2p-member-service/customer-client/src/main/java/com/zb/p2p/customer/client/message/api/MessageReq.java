package com.zb.p2p.customer.client.message.api;

import lombok.Data;

import java.util.List;

/**
 * Created by limingxin on 2018/1/15.
 */
@Data
public class MessageReq {
    /**
     * 请求编号
     * 必填参数。消息来源，建议取系统名称
     * messageInfo数量
     * 时间戳
     * 签名（key+timestamp）
     */
    private String requestId,
            sourceCode,
            count,
            timestamp,
            sign;

    /**
     * 消息列表
     */
    private List<MessageInfo> messageInfos;

   
}
