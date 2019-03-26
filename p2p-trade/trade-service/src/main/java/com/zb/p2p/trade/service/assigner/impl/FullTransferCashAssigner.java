package com.zb.p2p.trade.service.assigner.impl;

import com.zb.p2p.trade.common.domain.CashBillPlanKey;
import com.zb.p2p.trade.common.domain.CashPlan;
import com.zb.p2p.trade.common.enums.CashAmountTypeEnum;
import com.zb.p2p.trade.common.enums.CashStatusEnum;
import com.zb.p2p.trade.common.model.CashAmountSuite;
import com.zb.p2p.trade.common.model.CashPlanHolder;
import com.zb.p2p.trade.service.cash.CashRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * <p> 1对1转让兑付本息计算器 </p>
 *
 * @author Vinson
 * @version FullTransferCashAssigner.java v1.0 2018/5/24 10:44 Zhengwenquan Exp $
 */
@Service
public class FullTransferCashAssigner extends AbstractCashAssigner{

    @Autowired
    private CashRecordService cashRecordService;

    @Override
    public void assign(CashBillPlanKey key, CashAmountSuite waitingAssignAmount) {

        logger.info("执行1:1转让计算本息分配处理[{}]", key);
        CashPlan cashRecord = loadByKey(key);

        assignCalculate(cashRecord, key, waitingAssignAmount);
    }

    private CashPlan loadByKey(CashBillPlanKey key) {
        List<CashPlan> cashRecordEntityList = cashRecordService.loadByKey(key);
        Assert.isTrue(cashRecordEntityList.size() == 1, "该兑付信息不适用1:1转让计算分配");
        return cashRecordEntityList.get(0);
    }

    private void assignCalculate(CashPlan plan, CashBillPlanKey key, CashAmountSuite waitingAssignAmount) {
        Assert.isTrue(plan.getStatus() == currentStatus()
                || plan.getStatus() == CashStatusEnum.CASH_TRANSFERRED, "兑付计划的状态不合法");
        // 分配
        if (CashAmountTypeEnum.EXPECT == CashPlanHolder.get().getAmountType()) {
            plan.setExpectPrinciple(waitingAssignAmount.getPrinciple());
            plan.setExpectInterest(waitingAssignAmount.getInterest());
        }else if (CashAmountTypeEnum.ACTUAL == CashPlanHolder.get().getAmountType()) {
            plan.setCashAmount(waitingAssignAmount.getPrinciple());
            plan.setCashIncome(waitingAssignAmount.getInterest());
            plan.setCashDate(new Date());
            // 清除应收本金利息（不更新）
            plan.setExpectPrinciple(null);
            plan.setExpectInterest(null);
        }
        // 更新状态
        if (currentStatus() == plan.getStatus()) {
            plan.setStatus(nextStatus());
        }
        // 计算截止当日应结利息

        // 更新
        cashRecordService.update(plan, currentStatus());
    }

    @Override
    protected String getAssignedKey(CashPlan plan) {
        return plan.getExtOrderNo();
    }
}
