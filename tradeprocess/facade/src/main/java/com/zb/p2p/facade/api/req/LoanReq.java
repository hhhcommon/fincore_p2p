package com.zb.p2p.facade.api.req;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by limingxin on 2017/8/31.
 */
@Data
public class LoanReq implements Serializable {
    /*会员id*/
    private String memberId;
    /*借款金额*/
    private BigDecimal loanAmount;
    /*外部编号（马上贷）*/
    private String loanNo;
    /*借款手续费*/
    private BigDecimal loanFee;
    /*借款利息*/
    private BigDecimal loanInterests;
    /*产品编号*/
    private String productCode;
    /*产品名*/
    private String productName;
    /*资产起息日期*/
    private Date valueStartTime;
    /*资产止息日期*/
    private Date valueEndTime;
    /*资产到期日*/
    private Date expireDate;
    /*借款期限*/
    private Integer lockDate;
    /*借款时间*/
    private Date loanTime;
    /*交易渠道*/
    private String saleChannel;
    
    private String branchBankProvince; //开户行省名
    
    private String branchBankCity;//开户行市名
    
    private String branchBankInst;//开户行机构名
    
    private String bankName;//收款人银行名称
    
    private String bankAccountNo;//收款人银行卡号
    
    private String memberName;//收款人姓名
    
    private BigDecimal loanRate; //借款利率
    
    private String financeSubjectName; //融资方名称

    private String finSubDesensitizationName; //融资方脱敏名称
    
    private String financeSubjectAddress; //融资方地址
      
    private Integer  repaymentType; //还款方式          1 到期还本付息，2 每月付息到期还本，3 等额本息，4 等额本金，5 利息自动拨付本金复投
    
    private String  companyCertificateNo; //借款方统一社会信用代码
    
    private String loanPurpose; //借款用途
    
    private String financeSubjectTel;//融资方联系方式
}
