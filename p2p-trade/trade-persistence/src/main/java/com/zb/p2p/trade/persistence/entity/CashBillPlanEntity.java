package com.zb.p2p.trade.persistence.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CashBillPlanEntity {
    private Long id;

    private String productCode;

    private String assetNo;

    private String orgAssetNo;

    private String loanMemberId;

    private String saleChannel;

    private BigDecimal expectPrinciple;

    private BigDecimal expectInterest;

    private BigDecimal cashAmount;

    private BigDecimal cashIncome;

    private Integer stageSeq;

    private Date expectDate;

    private Date cashDate;

    private String repayType;

    private String cashStatus;

    private String status;

    private Integer version;

    private Integer lockTag;

    private Integer loanFeeStatus;

    private Date createTime;

    private Date modifyTime;
    // 支付通道
    private String payChannel;
}