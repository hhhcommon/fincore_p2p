package com.zb.p2p.facade.service;

import com.zb.p2p.facade.api.req.BatchLoanReq;
import com.zb.p2p.facade.api.req.LoanReq;
import com.zb.p2p.facade.api.req.NotifyTradeStatusReq;
import com.zb.p2p.facade.api.req.QueryLoanOrderInfoReq;
import com.zb.p2p.facade.api.req.RepayAmountReq;
import com.zb.p2p.facade.api.req.StockQueryReq;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.zb.p2p.facade.api.req.*;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.api.resp.ProductStockDTO;
import com.zb.p2p.facade.api.resp.RepayAmountDTO;
import com.zb.p2p.facade.api.resp.order.LoanOrderRespDTO;
import com.zb.p2p.facade.api.resp.order.OrderMatchRespDTO;

/**
 * Created by limingxin on 2017/8/31.
 */
public interface OrderFacade {

    /**
     * 批量借款申请接口
     *
     * @param batchLoanReq
     * @return
     */
    CommonResp<String> batchLoan(BatchLoanReq batchLoanReq);

    /**
     * 借款申请接口
     *
     * @param loanReq
     * @return
     */
    CommonResp<String> loan(LoanReq loanReq);

    /**
     * 查询借款订单信息
     * @param req
     * @return
     */
    CommonResp<LoanOrderRespDTO> queryLoanOrderInfo(QueryLoanOrderInfoReq req);

    /**
     * 查询投资订单匹配信息
     * @param req
     * @return
     */
    CommonResp<OrderMatchRespDTO> queryOrderMatchInfo(QueryOrderMatchInfoReq req);

    /**
     * 库存查询接口
     *
     * @param req
     * @return
     */
    CommonResp<ProductStockDTO> queryStock(StockQueryReq req);
    
    public void loanWithdrawCallBack(NotifyTradeStatusReq resp) throws Exception;
    
    /**
     * 查询还款总金额
     * @param tepayAmountReq
     * @return
     */
    public CommonResp<List<RepayAmountDTO>> getRepayAmountList(RepayAmountReq tepayAmountReq);
    
    
    
    
}
