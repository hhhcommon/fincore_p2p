package com.zb.p2p.customer.client.message.api;

import lombok.Data;

@Data
public class ValicodeMessageReq {
     
    String mobile;
    
    String businessType; 
    
    String sourceCode; 
    
    String timestamp;
    
    String sign; 
}
