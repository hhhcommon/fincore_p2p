package com.zb.txs.p2p.order.httpclient;

import com.zb.txs.foundation.response.Result;
import com.zb.txs.p2p.business.asset.request.OrderInvestRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Function:   针对订单服务的http client 调用 金核<br/>
 * Date:   2017年09月21日 上午11:02 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */


public interface FinancialorderClient {

    /**
     * 产品投资申请
     */
    @POST("order/invest")
    Call<Result<Void>> invest(@Body OrderInvestRequest orderInvestRequest);
}