package com.zb.p2p.trade.service.message.service.impl;

import com.zb.p2p.trade.service.common.DistributedSerialNoService;
import com.zb.p2p.trade.service.message.MessageSender;
import com.zb.p2p.trade.service.message.service.MessageService;
import com.zb.p2p.trade.client.request.MessageInfo;
import com.zb.p2p.trade.client.request.MessageReq;
import com.zb.p2p.trade.client.request.MessageResp;
import com.zb.p2p.trade.common.constant.GlobalVar;
import com.zb.p2p.trade.common.enums.SequenceEnum;
import com.zb.p2p.trade.common.util.MD5Util;
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
	 
	@Autowired
	private DistributedSerialNoService distributedSerialNoService;
	
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
     	messageInfo.setMessageId(distributedSerialNoService.generatorSerialNoByIncrement(SequenceEnum.MESSAGE));
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
    	messageReq.setSign(MD5Util.encript( messageSignKey + currentTimeMillis )  );
    	messageReq.setSourceCode(GlobalVar.MESSAGE_SOURCECODE);
    	messageReq.setTimestamp( currentTimeMillis);
    	
    	MessageResp messageResp = messageSender.sendMessage(messageReq);
    	log.info("发送短信结果     {} ",messageResp);
    	
    	if(messageResp != null && messageResp.getSuccess().equals("true") ){
    		return true;
    	} 
    	
    	return false;
    }
     
}
