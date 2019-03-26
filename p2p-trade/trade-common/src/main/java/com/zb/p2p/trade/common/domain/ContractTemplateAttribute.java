package com.zb.p2p.trade.common.domain;

import lombok.Data;

/**
 * @author
 * @create 2018-06-05 14:53
 */
@Data
public class ContractTemplateAttribute {

    /**协议编号*/
    private String agreementNo;

    /**出借人（甲方）姓名*/
    private String investMemberName;

    /**身份证号*/
    private String investMemberIdCard;

    /**联系方式*/
    private String investMemberTel;

    /**借款人（乙方）姓名*/
    private String loanMemberName;

    /**统一社会信用代码*/
    private String companyCertificateNo;

    /**联系地址*/
    private String contactAddress;


    /**借款本金*/
    private String loanAmount;
    /**借款年利率*/
    private String loanYearYield;
    /**借款用途*/
    private String loanPurpose;
    /**借款放款日*/
    private String loanWithdrawTimeStr;
    /**起息日*/
    private String valueTimeStr;
    /**到期日*/
    private String expireTimeStr;
    /**还款日*/
    private String repayTimeStr;
    /**每期还款本息金额*/
    private String needPayBackAmount;



    /**签署日期*/
    private String contractTimeStr;

}
