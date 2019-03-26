package com.zb.p2p.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by mengkai on 2017/8/30.
 */
@Data
public class LoanContractBO implements Serializable {
    /**
     *借款订单号
     */
    private String loanOrderNo,loanTelNo;
    /**
     *合同编号
     */
    private String loanContractNo;
    /**
     *渠道
     */
    private String saleChannel;
    /**
     *资产编码
     */
    private String assetCode;
    /**
     *资产名称
     */
    private String assetName;
    /**
     *借款人id
     */
    private String loanMemberId;
    /**
     *借款人姓名
     */
    private String loanMemberName;
    /**
     *借款人身份证号码
     */
    private String loanIdentityCard;
    /**
     *借款金额
     */
    private BigDecimal loanAmount;
    /**
     *借款年利率
     */
    private BigDecimal loanYearYield;
    /**
     *委托借款日(用户借款成功日期，精确到年月日)
     */
    private Date loanStartDate;
    /**
     *委托借款到期日(产品到期日)
     */
    private Date loanEndDate;
    /**
     *借款期限(天)
     */
    private int loanLockData;
    /**
     *手续费
     */
    private BigDecimal loanFee;

    /**
     *借款用途
     */
    private String loanPurpose;

}
