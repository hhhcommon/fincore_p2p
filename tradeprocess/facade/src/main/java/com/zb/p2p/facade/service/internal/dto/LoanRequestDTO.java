package com.zb.p2p.facade.service.internal.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class LoanRequestDTO implements Serializable {
    private Integer id;

    private String memberId;

    private BigDecimal loanAmount;

    private BigDecimal matchedAmount;

    private String loanNo;

    private BigDecimal loanFee;

    private BigDecimal loanInterests;

    private String assetCode;

    private String productCode;

    private String productName;

    private String assetPoolCode;

    private BigDecimal assetAmount;

    private String assetName;

    private Date establishTime;

    private Date valueStartTime;

    private Date valueEndTime;

    private Date expireDate;

    private Integer lockDate;

    private Date loanTime;

    private String loanStatus;

    private BigDecimal investedAmount;

    private String repayStatus;

    private Boolean isOverdue;

    private BigDecimal repaidAmount;

    private String saleChannel;

    private Integer version;

    private Date createTime;

    private Date modifyTime;

    private String createBy;

    private String modifyBy;

    private String contractStatus;

}