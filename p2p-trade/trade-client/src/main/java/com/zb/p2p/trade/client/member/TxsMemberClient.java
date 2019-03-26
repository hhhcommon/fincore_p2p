package com.zb.p2p.trade.client.member;

import com.zb.p2p.trade.client.request.GetMemberCardReq;
import com.zb.p2p.trade.client.response.MemberCardResp;
import com.zb.p2p.trade.common.model.CommonResp;
import feign.Headers;
import feign.RequestLine;

/**
 * 唐小僧回调接口定义
 * Created by limingxin on 2017/8/29.
 */
public interface TxsMemberClient {
    /**
     * 查询用户绑卡接口
     *
     * @param req
     * @return
     */
    /*@RequestLine("POST /members/account/cards")*/
    @RequestLine("POST /info/bankCardBindInfo")
    @Headers("Content-Type: application/json")
    CommonResp<MemberCardResp> getMemberCard(GetMemberCardReq req);

}
