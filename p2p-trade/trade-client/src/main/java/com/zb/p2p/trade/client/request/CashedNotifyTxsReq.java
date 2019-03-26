package com.zb.p2p.trade.client.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p> 兑付结果通知唐小僧的请求 </p>
 *
 * @author Vinson
 * @version CashedNotifyTxsReq.java v1.0 2018/6/9 0009 下午 2:49 Zhengwenquan Exp $
 */
@Data
public class CashedNotifyTxsReq {

    // 唐小僧购买时的订单号
    private String extorderno;
    // 还款订单号
    private String repayno;
    // 出借人会员Id
    private String accountid;
    // 本金
    private BigDecimal capitalamount;
    private BigDecimal interestamount;
    // 兑付时间
    private String repaytime;
}
