/*package com.zb.p2p.customer.service.impl;

import java.io.IOException;

import com.zb.p2p.customer.common.model.BaseRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zb.p2p.customer.api.entity.SmsReq;
import com.zb.p2p.customer.common.util.HttpClientUtils;
import com.zb.p2p.customer.common.util.JsonUtil;
import com.zb.p2p.customer.common.util.UuidUtils;
import com.zb.p2p.customer.service.MessageCenterService;
import com.zb.p2p.customer.service.bo.MessageCenterReq;
import com.zb.p2p.customer.service.bo.MessageCenterResp;
import com.zb.p2p.customer.service.bo.MsgBodyInfo;

//消息中心
//@Service
//
public class MessageCenterServiceImpl implements MessageCenterService {
    private static final Logger logger = LoggerFactory.getLogger(MessageCenterServiceImpl.class);

    @Value("${smsCenter.appKey}")
    private String appKey;//
    @Value("${smsCenter.host}")
    private String smsHost;//

    public BaseRes<MessageCenterResp> sendSms(SmsReq req) {
        BaseRes<MessageCenterResp> baseResp = new BaseRes<MessageCenterResp>();
        String url = smsHost + "/send";
        String reqId = UuidUtils.getUUID();
        MessageCenterReq json = new MessageCenterReq();
        json.setAppKey(appKey);
        json.setReqId(reqId);
        json.setBizCode(reqId);
        json.setMsgMode(req.getMsgMode());

        MsgBodyInfo msgBodyInfo = new MsgBodyInfo();
        msgBodyInfo.setContent(req.getContent());
        msgBodyInfo.setPhone(req.getPhone());
        MsgBodyInfo[] array = {msgBodyInfo};
        json.setBody(array);
        String jsonStr = JsonUtil.printStrFromObj(json);
        String result = "";
        try {
            logger.info("http request MessageCenter:" + jsonStr);
            result = HttpClientUtils.doPost(url, jsonStr);
            logger.info("http response MessageCenter:" + result);
            if (result != null) {
                MessageCenterResp resp = (MessageCenterResp) JsonUtil.getObjectByJsonStr(result, MessageCenterResp.class);
                if ("200".equals(resp.getCode())) {
                    baseResp.setData(resp);
                } else {
                    baseResp.setCode(resp.getCode());
                    baseResp.setMessage(resp.getMessage());
                }


            } else {
                return new BaseRes(false);
            }


        } catch (IOException e) {
            logger.error("请求" + url + "报错!参数为" + json, e);
            return new BaseRes(false);

        }

        return baseResp;

    }

}
*/