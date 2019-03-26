package com.zb.p2p.trade.service.assigner;

import com.zb.p2p.trade.common.domain.CashBillPlan;
import com.zb.p2p.trade.common.enums.CashAmountTypeEnum;
import com.zb.p2p.trade.common.enums.RepaymentTypeEnum;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;

/**
 * <p> 兑付计划执行工厂 </p>
 *
 * @author Vinson
 * @version CashPlanAssignerFactory.java v1.0 2018/4/24 19:07 Zhengwenquan Exp $
 */
public interface CashPlanAssignerFactory {

    /**
     * 根据兑付状态获取执行者
     * @param billPlan
     * @param amountType
     * @return
     */
    CashAssigner loadCashAssigner(CashBillPlan billPlan, CashAmountTypeEnum amountType);

    /**
     * 根据还款类型获取账单计算分配器
     * @param repaymentType
     * @return
     */
    BillAssigner loadBillAssigner(RepaymentTypeEnum repaymentType);
}
