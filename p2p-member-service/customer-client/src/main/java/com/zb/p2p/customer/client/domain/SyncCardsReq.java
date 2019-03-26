package com.zb.p2p.customer.client.domain;

import lombok.Data;

@Data
public class SyncCardsReq { 
	private String lastCardId; //上次同步的最后一个Id
	private Integer pageSize; //同步多少个账户
	  
}
