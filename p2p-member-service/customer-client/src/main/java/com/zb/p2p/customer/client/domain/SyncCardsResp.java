package com.zb.p2p.customer.client.domain;

import lombok.Data;

@Data
public class SyncCardsResp { 
	
	private String cardId; //
	private String accountId; //
	private String code; // 卡号
	private String bankCode; // 
	private String bankName; //
	private String type; //卡类型
	private String identityType; //
	private String identityNo; //
	private String identityName; //
	private String phoneNo; //
	private String extId; //sign_id
	
	
	 
	 
	  
}
