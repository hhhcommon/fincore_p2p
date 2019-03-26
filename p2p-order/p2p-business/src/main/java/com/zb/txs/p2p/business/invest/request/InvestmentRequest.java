package com.zb.txs.p2p.business.invest.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class InvestmentRequest implements Serializable {
    /**
     * 产品ID
     */
    private String productId;
    private String productCode;

    /**
     * 账户ID
     */
    private String memberId;
    /**
     * 订单记录ID
     */
    private String orderId;
    /**
     * 收益日期
     */
    private String interestDate;
}
