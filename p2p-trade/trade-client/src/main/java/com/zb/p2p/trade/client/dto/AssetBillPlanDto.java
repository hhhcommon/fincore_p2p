package com.zb.p2p.trade.client.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p> 还款计划列表 </p>
 *
 * @author Vinson
 * @version AssetBillPlanDto.java v1.0 2018/5/9 10:02 Zhengwenquan Exp $
 */
@Data
public class AssetBillPlanDto {

    private Long assetId;
    private String assetCode;
    private Integer repayCount;
    private Integer currentRepayCount;
    private BigDecimal yieldRate;
    private BigDecimal repayAmount;
    private BigDecimal repayPrincipal;
    private BigDecimal repayInterest;
    private String loanName;
    // 计划还款日
    private String repayDate;
    // 实际还款日
    private String repayTime;
    private String loanCertNo;
    private Integer loanCertType;
    private BigDecimal loanFee;
    // 还款状态:0:待还款,1:部分还款,2:全部还款；3：逾期待还款；4：逾期已还款
    private Integer repayStatus;
    private boolean isOverdue;
    private BigDecimal repaidAmount;
    private BigDecimal waitingRepayAmount;
    private Long version;
    private String memberId;
    private String tel;
}
