package com.zb.p2p.trade.persistence.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class LoanRequestEntity {
    private Integer id;

    private String memberId;

    private BigDecimal loanAmount;

    private BigDecimal matchedAmount;

    private String loanNo;

    private BigDecimal loanFeeRate;

    private BigDecimal loanInterests;
    private BigDecimal actualLoanInterests;

    private String productCode;

    private String productName;

    private Date establishTime;

    private Date valueStartTime;

    private Date valueEndTime;

    private Date expireDate;

    private Integer lockDate;
    private String payChannel;

    private Integer loanStage;

    private Date loanTime;

    private String loanStatus;

    private String saleChannel;

    private Integer version;

    private String loanPaymentStatus;

    private Date loanPaymentTime;

    private String contractStatus;

    private Date createTime;

    private Date modifyTime;

    private String createBy;

    private String modifyBy;

    private String memberName;

    private String bankAccountNo;

    private String bankName;

    private String branchBankInst;

    private String branchBankCity;

    private String branchBankProvince;

    private BigDecimal loanRate;

    private String financeSubjectName;

    private String finsubDesensitizationName;

    private String financeSubjectAddress;

    private Integer repaymentType;

    private String companyCertificateNo;

    private String financeSubjectTel;

    private String loanPurpose;

    private BigDecimal totalLoanCharge;

    private String originalAssetCode;

    private String transferCode;

    private String assetPoolCode;

    // 资产类型（1:原始资产,2:债转资产）
    private Integer assetType;

    private BigDecimal transferAmount;

    private BigDecimal transferInterests;

    private String transferRequestNo;

    private Date transferTime;

    // 借款类型（个人/企业）
    private String loanType;

}