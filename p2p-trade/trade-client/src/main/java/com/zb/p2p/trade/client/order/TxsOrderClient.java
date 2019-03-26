package com.zb.p2p.trade.client.order;

import com.zb.p2p.trade.client.request.CashedNotifyTxsReq;
import com.zb.p2p.trade.client.response.TxsResponse;
import feign.Headers;
import feign.RequestLine;

/**
 * <p> 唐小僧订单回调接口 </p>
 *
 * @author Vinson
 * @version PaymentClientService.java v1.0 2018/4/25 15:19 Zhengwenquan Exp $
 */
public interface TxsOrderClient {
    /**
     * 查询用户绑卡接口
     *
     * @param req
     * @return
     */
    @RequestLine("POST /txs/callback/repay")
    @Headers("Content-Type: application/json")
    TxsResponse cashedResultNotify(CashedNotifyTxsReq req);

}
