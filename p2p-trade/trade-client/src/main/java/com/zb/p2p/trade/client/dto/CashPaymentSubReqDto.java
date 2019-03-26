package com.zb.p2p.trade.client.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p> 支付兑付明细请求body </p>
 *
 * @author Vinson
 * @version CashPaymentSubReqDto.java v1.0 2018/5/14 12:47 Zhengwenquan Exp $
 */
@Data
public class CashPaymentSubReqDto {

    /**
     * 子请求订单号
     */
    private String subRequestOrderNo;
    /**
     * 投资订单号
     */
    private String investOrderNo;
    /**
     * 还款人会员id
     */
    private String loanMemberId;
    /**
     * 还款人会员类型：10-个人,20-机构
     */
    private String memberType;
    /**
     * 账户用途：101-借款账户；207-担保人账户（逾期情况传入207-担保人账户）
     */
    private String accountPurpose;
    /**
     * 投资人会员id 交易类型为CASH_FEE 时传平台手续费帐号
     */
    private String memberId;

    /**
     * CASH-兑付，REINVEST_CASH -复投,CASH_FEE -手续费
     */
    private String tradeType;
    /**
     * 交易总金额(单位元, 精确到分)
     */
    private BigDecimal tradeAmount;

}
