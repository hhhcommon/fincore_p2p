package com.zb.p2p.trade.api.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RepaymentReq implements Serializable {

    /**
     * 借款人
     */
    @NotNull(message = "借款人不能为空")
    @Size(min = 1, message = "借款人不能为空")
    private String loanMemberId;

    /**
     * 借款资产编号
     */
    @NotNull(message = "借款资产编号不能为空")
    @Size(min = 1, message = "借款资产编号不能为空")
    private String assetCode;

    /**
     * 还款期号
     */
    @NotNull(message = "还款期号不能为空")
    private Integer repayNum;

    /**
     * 还款金额
     */
    private BigDecimal repayAmount;

    /**
     * 逾期担保机构垫付金额（当部分还款时不为空）
     */
    private BigDecimal cushionAmount;
 
}
