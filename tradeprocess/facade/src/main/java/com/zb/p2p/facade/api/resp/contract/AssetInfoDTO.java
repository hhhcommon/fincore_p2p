package com.zb.p2p.facade.api.resp.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by mengkai on 2017/8/31.
 */
@Data
public class AssetInfoDTO implements Serializable {
    /**
     * 借款单编号
     */
    private String loanOrderNo;
    /**
     * 借款人id
     */
    private String loanMemberId;
    /**
     * 借款人姓名
     */
    private String loanMemberName;
    /**
     * 借款金额
     */
    private BigDecimal loanAmount;

    /**
     * 借款用途 联系方式 身份证
     */
    private String loanPurpose, loanTelNo, loanIdentityCard;

}
