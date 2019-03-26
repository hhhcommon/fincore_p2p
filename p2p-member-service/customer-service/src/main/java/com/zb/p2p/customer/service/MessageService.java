package com.zb.p2p.customer.service;

import com.zb.p2p.customer.client.message.api.ValicodeMessageReq;

public interface MessageService {

    public boolean send(String messageTemplateCode, String mobile, String jsonParameter);

    public String sendValidateCode(ValicodeMessageReq validateMessageReq);


}
