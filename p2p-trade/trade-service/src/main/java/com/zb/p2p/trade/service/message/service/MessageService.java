package com.zb.p2p.trade.service.message.service;

public interface MessageService {
    
	boolean send(String messageTemplateCode, String mobile, String jsonParameter);
    
}
