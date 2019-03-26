package com.zb.p2p.customer.client.domain;

import lombok.Data;

@Data
public class SyncAccountInfoReq { 
	private String lastAccountId; //上次同步的最后一个用户Id
	private Integer pageSize; //同步多少个账户
	  
}
