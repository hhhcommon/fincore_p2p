package com.zb.p2p.trade.persistence.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CreditorInfoEntity {
    private Long id;

    private String creditorNo;

    private Long matchId;

    private String memberId;

    private String loanMemberId;
    private String loanNo;

    private String orderNo;

    private String fromOrderNo;

    private String orgOrderNo;

    private String assetNo;

    private String orgAssetNo;

    private String transferTradeNo;

    private String transferAssetCode;

    private BigDecimal investAmount;

    private BigDecimal payingPrinciple;

    private BigDecimal payingInterest;

    private Date latestCashDate;

    private BigDecimal latestPrinciple;

    private BigDecimal latestInterest;

    private BigDecimal paidPrinciple;

    private BigDecimal paidInterest;

    private BigDecimal serviceFee;

    private String status;

    private String memo;

    private Integer version;

    private Date createTime;

    private Date modifyTime;

}