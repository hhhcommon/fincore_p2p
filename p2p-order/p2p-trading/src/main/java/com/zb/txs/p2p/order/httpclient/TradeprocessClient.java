package com.zb.txs.p2p.order.httpclient;

import com.zb.txs.foundation.response.Result;
import com.zb.txs.p2p.business.order.request.AppointReservation;
import com.zb.txs.p2p.business.order.request.BatchIncomeRecord;
import com.zb.txs.p2p.business.order.request.IncomeRecord;
import com.zb.txs.p2p.business.order.response.AppointReservationResp;
import com.zb.txs.p2p.business.order.response.IncomeResp;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.List;

/**
 * Function:  TradeprocessClient  <br/>
 * Date:  2017/9/28 17:19 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */
public interface TradeprocessClient {
    /**
     * 批量查询每日收益
     *
     * @param ratchIncome
     * @return
     */
    @POST("income/queryBatchIncome")
    Call<Result<List<IncomeResp>>> queryBatchIncome(@Body BatchIncomeRecord ratchIncome);

    /**
     * 查询每日收益(单个查询)
     *
     * @param income
     * @return
     */
    @POST("income/queryIncome")
    Call<Result<IncomeResp>> queryIncome(@Body IncomeRecord income);

    /**
     * 预约申请
     */
    @POST("order/invest")
    Call<Result<AppointReservationResp>> orderReservation(@Body AppointReservation appointReservation);
}
