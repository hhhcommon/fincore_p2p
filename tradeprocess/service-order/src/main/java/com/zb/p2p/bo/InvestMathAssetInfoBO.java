package com.zb.p2p.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by mengkai on 2017/8/31.
 */
@Data
public class InvestMathAssetInfoBO implements Serializable {
    /**
     * 借款单编号
     */
    private String loanOrderNo;
    /**
     * 借款人id
     */
    private String loanMemberId;
    /**
     * 借款人姓名 电话号码 身份证
     */
    private String loanMemberName, loanTelNo, loanIdentityCard;
    /**
     * 借款金额
     */
    private BigDecimal loanTotalAmount;
    /**
     * 借款用途
     */
    private String loanPurpose;

}
