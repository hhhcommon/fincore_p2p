package com.zb.txs.p2p.order.httpclient;

import com.zb.txs.foundation.response.Result;
import com.zb.txs.p2p.business.asset.request.LoanTransferRequest;
import com.zb.txs.p2p.business.asset.response.LoanTransferResp;
import com.zb.txs.p2p.business.order.request.AppointInvest;
import com.zb.txs.p2p.business.order.request.BankQuota;
import com.zb.txs.p2p.business.order.request.ConfirmInvest;
import com.zb.txs.p2p.business.order.request.QueryTradeStatus;
import com.zb.txs.p2p.business.order.response.TradeStatusResp;
import com.zb.txs.p2p.business.product.repose.BankQuotaResp;
import com.zb.txs.p2p.order.persistence.model.RefoundJinhe;
import com.zb.txs.p2p.order.persistence.model.RefoundResponse;
import com.zb.txs.p2p.order.persistence.model.RefoundTradeRequest;
import com.zb.txs.p2p.order.persistence.model.RefoundTradeResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.List;

/**
 * Function:   针对订单服务的支付http client <br/>
 * Date:   2017年09月21日 上午11:02 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */


public interface OrderClient {

    /**
     * 查询银行限额
     */
    @POST("queryBankQuota")
    Call<Result<List<BankQuotaResp>>> queryBankQuota(@Body BankQuota bankQuota);

    /**
     * 预投资
     */
    @POST("appointInvest")
    Call<Result<TradeStatusResp>> appointInvest(@Body AppointInvest appointInvest);

    /**
     * 1709收银收单-接口文档：YZT-2531 > 放款转账（唐小僧调用）
     *
     * @return
     */
    @POST("loanTransfer")
    Call<Result<LoanTransferResp>> confirmInvestRequest(@Body LoanTransferRequest loanTransferRequest);


    /**
     * 发起退款
     * @param refoundJinhe
     * @return
     */
    @POST("refund")
    Call<Result<RefoundResponse>> sendRefound(@Body RefoundJinhe refoundJinhe);


    /**
     * 查询退款结果
     * @param refoundTrade
     * @return
     */
    @POST("queryTradeStatus1")
    Call<Result<RefoundTradeResponse>> queryRefoundStatus(@Body RefoundTradeRequest refoundTrade);

    /**
     * 确认投资
     *
     * @param confirmInvest
     * @return
     */
    @POST("confirmInvest")
    Call<Result<TradeStatusResp>> confirmInvest(@Body ConfirmInvest confirmInvest);

    /**
     * 交易状态查询
     */
    @POST("queryTradeStatus_new")
    Call<Result<TradeStatusResp>> queryTradeStatus(@Body QueryTradeStatus queryTradeStatus);
}