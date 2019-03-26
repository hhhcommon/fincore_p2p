package com.zb.p2p.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by mengkai on 2017/8/31.
 */
@Data
public class LoanMathInvestInfoBO implements Serializable {
    /**
     * 投资人id
     */
    private String investMemberId;
    /**
     * 投资人姓名 电话号码 预约单号 身份证
     */
    private String investMemberName, investTelNo, reservationNo, investIdentityCard;
    /**
     * 投资金额
     */
    private BigDecimal investTotalAmount;

}
