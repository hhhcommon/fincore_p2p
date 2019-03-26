package com.zb.p2p.trade.service.assigner.impl;

import com.zb.p2p.trade.common.domain.CashBillPlan;
import com.zb.p2p.trade.common.enums.CashAmountTypeEnum;
import com.zb.p2p.trade.common.enums.RepaymentTypeEnum;
import com.zb.p2p.trade.service.assigner.BillAssigner;
import com.zb.p2p.trade.service.assigner.CashAssigner;
import com.zb.p2p.trade.service.assigner.CashAssignerTypeEnum;
import com.zb.p2p.trade.service.assigner.CashPlanAssignerFactory;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * <p> 兑付本息分配计算器工厂 </p>
 *
 * @author Vinson
 * @version CashPlanAssignerFactoryImpl.java v1.0 2018/4/24 12:30 Zhengwenquan Exp $
 */
public class CashPlanAssignerFactoryImpl implements CashPlanAssignerFactory {

    private Map<CashAssignerTypeEnum, CashAssigner> cashAssignerMap;
    private Map<RepaymentTypeEnum, BillAssigner> billAssignerMap;

    public void setCashAssignerMap(Map<CashAssignerTypeEnum, CashAssigner> cashAssignerMap) {
        this.cashAssignerMap = cashAssignerMap;
    }

    public void setBillAssignerMap(Map<RepaymentTypeEnum, BillAssigner> billAssignerMap) {
        this.billAssignerMap = billAssignerMap;
    }

    @Override
    public CashAssigner loadCashAssigner(CashBillPlan billPlan, CashAmountTypeEnum amountType) {

        CashAssignerTypeEnum assignerType = CashAssignerTypeEnum.RATIO_SHARE;

        if (billPlan.getRepayType() == RepaymentTypeEnum.CREDITOR) {
            assignerType = CashAssignerTypeEnum.ONE_OFF_CREDITOR;
        }

        return cashAssignerMap.get(assignerType);
    }

    @Override
    public BillAssigner loadBillAssigner(RepaymentTypeEnum repaymentType) {
        BillAssigner assigner = null;
        if (repaymentType != null) {
            assigner = billAssignerMap.get(repaymentType);
        }

        Assert.notNull(assigner, "未找到还款计划计算分配器");

        return assigner;
    }
}
