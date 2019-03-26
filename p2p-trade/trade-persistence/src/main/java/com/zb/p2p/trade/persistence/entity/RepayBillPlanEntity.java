package com.zb.p2p.trade.persistence.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RepayBillPlanEntity {

    private Long id;

    private String loanNo;

    private String productCode;

    private String orgAssetCode;

    private String assetCode;

    private String memberId;

    private Integer stageCount;

    private Date billStartDate;

    private Date billEndDate;

    private Integer stageSeq;

    private BigDecimal expectPrinciple;

    private BigDecimal expectInterest;

    private Date repaymentTime;

    private BigDecimal actualPrinciple;

    private BigDecimal actualInterest;

    private String status;

    private BigDecimal repaymentFee;
    private BigDecimal actualRepaymentFee;
    private String repaymentFeeStatus;

    private BigDecimal remainAmount;

    private String platformId;

    private Integer version;

    private Date createTime;

    private Date modifyTime;

    // 渠道
    private String saleChannel;
    private String payChannel;

}