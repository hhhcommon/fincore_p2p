package com.zb.p2p.trade.persistence.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CashRecordEntity {
    private Long id;

    private Long billPlanId;

    private String reqNo;

    private String productCode;

    private String extOrderNo;

    private String assetNo;

    private String orgAssetNo;

    private BigDecimal expectPrinciple;

    private BigDecimal payingInterest;

    private int lockTag;

    private BigDecimal expectInterest;

    private String refNo;

    private String loanMemberId;

    private String memberId;

    private String saleChannel;

    private BigDecimal cashAmount;

    private BigDecimal cashIncome;

    private Integer stageSeq;

    private Date expectDate;

    private Date cashDate;

    private String repayType;

    private String cashStatus;

    private String status;

    private Integer version;

    private Date createTime;

    private String createBy;

    private Date modifyTime;

    private String modifyBy;

    private String payChannel;
}