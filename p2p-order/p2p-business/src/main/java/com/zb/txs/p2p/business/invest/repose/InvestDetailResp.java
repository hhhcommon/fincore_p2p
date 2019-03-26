package com.zb.txs.p2p.business.invest.repose;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@ToString
@Builder
public class InvestDetailResp implements Serializable {
    /**
     * 产品名称
     */
    private String productTitle;
    /**
     * 期望年化收益率
     */
    private String rate;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 起息时间
     */
    private Date interestStartTime;
    /**
     * 到期回款时间
     */
    private Date paymentTime;
    /**
     * 已匹配金额
     */
    private String matchAmount;
    /**
     * 待匹配金额
     */
    private String unMatchAmount;
    /**
     * 投资金额
     */
    private String investAmount;
    /**
     * 到期收益
     */
    private String expireProfit;
    /**
     * 回款总金额
     */
    private String allPaymentAmount;
    /**
     * 回款方式
     */
    private String payType;
    /**
     * 订单编号
     */
    private String orderId;
    /**
     * 投资时间
     */
    private Date investTime;
    /**
     * 持有时间
     */
    private String holdTime;
    /**
     * 在投金额
     */
    private String amountInvested;
    /**
     * 注
     */
    private String notes;
    /**
     * 回款时间
     */
    private String paidTime;
    /**
     * 回款至
     */
    private String payTo;

}