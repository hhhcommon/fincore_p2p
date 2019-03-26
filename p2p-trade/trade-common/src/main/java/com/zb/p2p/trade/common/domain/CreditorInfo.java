package com.zb.p2p.trade.common.domain;

import com.zb.p2p.trade.common.enums.CreditorStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CreditorInfo {
    private Long id;

    private Long parentId;

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

    // 发起转让时的资产编号
    private String transferAssetCode;

    private String transferTradeNo;

    // 投资（匹配）金额
    private BigDecimal investAmount;

    private BigDecimal payingPrinciple;

    private BigDecimal payingInterest;

    private Date latestCashDate;

    private BigDecimal latestPrinciple;

    private BigDecimal latestInterest;

    private BigDecimal paidPrinciple;

    private BigDecimal paidInterest;

    private BigDecimal serviceFee;

    private CreditorStatusEnum status;

    private String memo;

    private Integer version;

    private Date createTime;

    private Date modifyTime;

}