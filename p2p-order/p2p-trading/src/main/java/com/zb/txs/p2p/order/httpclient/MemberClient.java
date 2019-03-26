package com.zb.txs.p2p.order.httpclient;

import com.zb.p2p.customer.api.entity.CustomerCardBin;
import com.zb.p2p.customer.api.entity.CustomerDetail;
import com.zb.p2p.customer.api.entity.CustomerReq;
import com.zb.p2p.customer.common.model.BaseRes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Function:  MemberClient  <br/>
 * Date:  2018/2/10 17:19 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */
public interface MemberClient {
    /**
     * 获取会员绑卡信息
     *
     * @param req
     * @return
     */
    @POST("info/bankCardBindInfo")
    Call<BaseRes<CustomerCardBin>> bankCardBindInfo(@Body CustomerReq req, String customerId);

    /**
     * 会员信息查询接口
     *
     * @param req
     * @return
     */
    @POST("info/perDetailInfo")
    Call<BaseRes<CustomerDetail>> perDetailInfo(@Body CustomerReq req, String customerId);

}
