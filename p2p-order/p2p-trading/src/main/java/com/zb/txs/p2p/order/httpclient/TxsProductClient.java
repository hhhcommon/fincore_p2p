package com.zb.txs.p2p.order.httpclient;

import com.zb.txs.foundation.response.Result;
import com.zb.txs.p2p.business.product.request.ProductCutDayRecord;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Function:  TxsProductClient  <br/>
 * Date:  2018/3/10 17:19 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */
public interface TxsProductClient {
    /**
     * 库存售罄，通知接口TXS产品
     *
     * @param req
     * @return
     */
    @POST("p2p/product/soldoutproduct/")
    Call<Result<Void>> soldOutProduct(@Body ProductCutDayRecord req);

}
