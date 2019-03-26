package com.zb.p2p.customer.client.txs;

import com.zb.p2p.customer.client.domain.*;

import feign.Headers;
import feign.RequestLine;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * <p> 调用唐小僧服务Client </p>
 * 
 */
public interface TxsClient {

     
	/**
	 * 查询会员信息
	 * @param jsonReq
	 * @return
	 */
    @RequestLine("POST /members/user/info")
    @Headers("Content-Type: application/json")
    TxsResponse queryMemberInfo(JSONObject jsonReq);
    
    /**
     * 授权注册回调
     * @param registeredBackReq
     * @return
     */
    @RequestLine("POST /members/registered_callback")
    @Headers("Content-Type: application/json")
    TxsResponse onregistered(RegisteredBackReq registeredBackReq);
    
    /**
     * 同步会员信息接口
     * @param syncAccountInfoReq
     * @return
     */
    @RequestLine("POST /members/sync/sync_account_info")
    @Headers("Content-Type: application/json")
    TxsResponse syncAccountInfo(SyncAccountInfoReq syncAccountInfoReq);
    
    /**
     * 同步绑卡信息接口
     * @param syncCardsReq
     * @return
     */
    @RequestLine("POST /members/sync/cards")
    @Headers("Content-Type: application/json")
    TxsResponse syncCards(SyncCardsReq syncCardsReq);


    @RequestLine("POST /members/sync/sync_corp_info")
    @Headers("Content-Type: application/json")
    TxsSyncResponse<SyncCorpInfoRes> syncCorpInfo(SyncCorpInfoReq syncCorpInfoReq);
}
