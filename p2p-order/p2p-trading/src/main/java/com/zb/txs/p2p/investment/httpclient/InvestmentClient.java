package com.zb.txs.p2p.investment.httpclient;

import com.zb.txs.foundation.response.Result;
import com.zb.txs.p2p.business.invest.repose.InvestIncomeResp;
import com.zb.txs.p2p.business.invest.repose.OrderMatchResp;
import com.zb.txs.p2p.business.invest.repose.TradeQueryResp;
import com.zb.txs.p2p.business.invest.request.AssetDetailRequest;
import com.zb.txs.p2p.business.invest.request.InvestIncomeRequest;
import com.zb.txs.p2p.business.invest.request.TradeQueryRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InvestmentClient {
    @POST("income/queryIncome")
    Call<Result<InvestIncomeResp>> getIncome(@Body InvestIncomeRequest investIncomeRequest);

    /**
     * 查询昨日收益接口
     */
    @POST("income/queryYesterdayIncome")
    Call<Result<TradeQueryResp>> queryYesterdayIncome(@Body TradeQueryRequest tradeQueryRequest);

    /**
     * 查询总本金和已兑付收益接口
     */
    @POST("income/queryAccountAndHistoryIncome")
    Call<Result<TradeQueryResp>> queryAccountAndHistoryIncome(@Body TradeQueryRequest tradeQueryRequest);

    /**
     * 根据订单查预期收益接口
     */
    @POST("income/queryOrderIncome")
    Call<Result<TradeQueryResp>> queryOrderIncome(@Body TradeQueryRequest tradeQueryRequest);

    /**
     * 根据订单编号查资产详情接口
     */
    @POST("order/queryOrderMatchInfo")
    Call<Result<OrderMatchResp>> queryOrderMatchInfo(@Body AssetDetailRequest assetDetailRequest);

}
