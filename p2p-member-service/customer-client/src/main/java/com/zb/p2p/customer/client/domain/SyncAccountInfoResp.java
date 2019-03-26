package com.zb.p2p.customer.client.domain;

import java.util.Date;

import lombok.Data;

@Data
public class SyncAccountInfoResp { 
	private String accountId; //
	private String mobile; // 
	private String identityName; // 
	private String identityNo; // 
	private String identityType; //  IDCARD-身份证
	private Date created; // 
	private String payUserId; // 资金账号
//	private String thirdPartId; //第三方会员账号Id
	
	  
}
