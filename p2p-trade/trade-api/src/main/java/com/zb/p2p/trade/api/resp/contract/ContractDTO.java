package com.zb.p2p.trade.api.resp.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ContractDTO implements Serializable {
	private Long id;

    private String contractNo;

    private String creditorNo;

    private String investOrderNo;

    private String extInvestOrderNo;

    private String loanOrderNo;

    private String productCode;

    private String productName;

    private String assetCode;

    private String assetName;

    private BigDecimal loanAmount;

    private BigDecimal investYearYield;

    private BigDecimal loanYearYield;

    private BigDecimal loanFee;

    private BigDecimal actualLoanInterests;

    private Integer lockDate;

    private String investMemberId;

    private String loanMemberId;

    private String investMemberName;

    private String financeSubjectName;

    private String investIdentityCard;

    private String status;

    private Date createTime;

    private Date modifyTime;

    private String createBy;

    private String modifyBy;

    private String memo;

    private String loanPurpose;

    private String loanTelNo;

    private String investTelNo;

    private String financeSubjectAddress;

    private Date loanWithdrawTime;

    private Date valueTime;

    private Date expireTime;

    private Integer repaymentType;

    private Date repayTime;

    private String companyCertificateNo;


}
