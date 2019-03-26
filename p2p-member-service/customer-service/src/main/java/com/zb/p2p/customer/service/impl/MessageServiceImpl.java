package com.zb.p2p.customer.service.impl;

import com.zb.p2p.customer.client.message.MessageSender;
import com.zb.p2p.customer.client.message.api.*;
import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.common.exception.AppException;
import com.zb.p2p.customer.common.util.MD5Util;
import com.zb.p2p.customer.common.util.UuidUtils;
import com.zb.p2p.customer.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 短息服务
 * @author tangqingqing
 *
 */
@Service
@Slf4j
public class MessageServiceImpl implements MessageService {
	
	 @Autowired
	 private MessageSender messageSender;
	 
//	@Autowired
//	private DistributedSerialNoService distributedSerialNoService;
	
	@Value("${message.sign.key}")
	private String  messageSignKey ;
	
	@Value("${message.switch:close}")
	private String  messageSwitch ;
	
    /**
     * 发送
     * 
     * 模版编号,手机号，json参数
     */
    public boolean send(String messageTemplateCode,String mobile,String jsonParameter){
    	
    	if(!"open".equals(messageSwitch )){
    		log.info("短信开关为关闭状态");
    		return true;
    	}
    	
    	log.info("发送短信     {}, {}, {}",messageTemplateCode,mobile,jsonParameter);
    	
    	MessageInfo messageInfo = new MessageInfo();
     	messageInfo.setMessageId(UuidUtils.getUUID());
    	messageInfo.setMessageTemplateCode(messageTemplateCode);
    	messageInfo.setMobile(mobile);
    	messageInfo.setParameter(jsonParameter);
    	
    	ArrayList<MessageInfo> messageInfoList= new ArrayList<>();
    	messageInfoList.add(messageInfo);
    	 
    	String currentTimeMillis = String.valueOf(System.currentTimeMillis() );
    	MessageReq messageReq = new MessageReq();
    	messageReq.setCount(String.valueOf(messageInfoList.size()));
    	messageReq.setMessageInfos(messageInfoList);
    	messageReq.setRequestId(currentTimeMillis);
    	messageReq.setSign(MD5Util.encrypt( messageSignKey + currentTimeMillis )  );
    	messageReq.setSourceCode("p2pTradeprocess");
    	messageReq.setTimestamp( currentTimeMillis);
    	
    	MessageResp messageResp = messageSender.sendMessage(messageReq);
    	log.info("发送短信结果     {} ",messageResp);
    	
    	if(messageResp != null && messageResp.getSuccess().equals("true") ){
    		return true;
    	} 
    	
    	return false;
    }
    
    /**
     *  发送验证码
     */
    public String sendValidateCode(ValicodeMessageReq validateMessageReq){
    	
    	if(!"open".equals(messageSwitch )){
    		log.info("短信开关为关闭状态");
    		return "";
    	} 
    	
    	String currentTimeMillis = String.valueOf(System.currentTimeMillis() );
    	validateMessageReq.setSign(MD5Util.encrypt( messageSignKey + currentTimeMillis )  );
    	validateMessageReq.setSourceCode("p2pTradeprocess");
    	validateMessageReq.setTimestamp(currentTimeMillis);
    	
    	ValicodeMessageResp valicodeMessageResp = messageSender.sendValidateCode(validateMessageReq);
    	log.info("发送短信结果     {} ",valicodeMessageResp);
    	
    	if(valicodeMessageResp != null && valicodeMessageResp.getSuccess().equals("true") ){
    		return valicodeMessageResp.getVerificationCode();
    	} else{
    		log.error("短信发送失败");
            throw AppException.getInstance(AppCodeEnum._9999_ERROR);
    	}
    	 
    }
     
}
