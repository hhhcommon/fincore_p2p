package com.zb.p2p.trade.client.request;

import lombok.Data;

@Data
public class MessageInfo {
    /**
     * 消息编号
     * 消息模板code
     * (消息系统后台配置)
     * 手机号码
     * (支持对个手机号，用英文逗号隔开，最多支持100个手机号)
     * 参数JSON字符串
     * (参数字段和消息模板一致)
     */
    String messageId,
            messageTemplateCode,
            mobile,
            parameter;
}
