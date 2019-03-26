package com.zb.p2p.trade.common.domain;

import com.zb.p2p.trade.common.enums.RepaymentTypeEnum;
import lombok.Data;

/**
 * <p> 兑付计划主键 </p>
 *
 * @author Vinson
 * @version CashBillPlanKey.java v1.0 2018/4/21 16:15 Zhengwenquan Exp $
 */
@Data
public class CashBillPlanKey {

    // 资产(转让/原始)编号
    private String assetNo;
    // 期号
    private int stageNo;
    // 兑付类型
    private RepaymentTypeEnum repaymentType;

    public CashBillPlanKey(){}

    public CashBillPlanKey(String assetNo, int stageNo, RepaymentTypeEnum repaymentType) {
        this.assetNo = assetNo;
        this.stageNo = stageNo;
        this.repaymentType = repaymentType;
    }
}
