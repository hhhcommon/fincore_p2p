package com.zb.p2p.trade.persistence.dto;

import com.zb.p2p.trade.common.domain.CashBillPlanKey;
import com.zb.p2p.trade.common.enums.RepaymentTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p> 描述 </p>
 *
 * @author Vinson
 * @version CreateCashBilPlanRequest.java v1.0 2018/4/21 16:14 Zhengwenquan Exp $
 */
@Data
public class CreateCashBilPlanRequest implements Comparable<CreateCashBilPlanRequest> {

    // 兑付主键
    private CashBillPlanKey key;
    //产品编号
    private String productCode;
    private String orgAssetNo;
    // 预计兑付日期
    private Date expectDate;
    // 借款人
    private String loanMemberId;
    //渠道
    private String saleChannel;

    // 预期本金利息
    private BigDecimal expectPrinciple;
    private BigDecimal expectInterest;
    private BigDecimal loanChargeFee;
    // 支付通道
    private String payChannel;


    @Override
    public int compareTo(CreateCashBilPlanRequest other) {
        if (this.key.getStageNo() > other.getKey().getStageNo()) {
            return 1;
        } else if (this.key.getStageNo() < other.getKey().getStageNo()) {
            return -1;
        }else {
            return 0;
        }
    }
}
