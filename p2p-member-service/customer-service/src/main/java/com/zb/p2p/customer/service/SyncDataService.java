package com.zb.p2p.customer.service;

import com.zb.p2p.customer.client.domain.SyncCorpInfoReq;

public interface SyncDataService {
	
	public void syncAccountInfo(String lastId,String pageSize) ;
	
	public void syncCards(String lastId,String pageSize);
	
	public void updateHistoryIsBindCard();

	/**
	 * 查询唐小僧1.0企业会员信息，同步到会员企业会员库
	 * @param req
	 */
	void syncCorpMemberInfo(SyncCorpInfoReq req);
}
