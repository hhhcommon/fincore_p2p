package com.zb.p2p.trade.service.assigner.impl;

import com.zb.p2p.trade.common.domain.CashBillPlanKey;
import com.zb.p2p.trade.common.domain.CashPlan;
import com.zb.p2p.trade.common.model.CashAmountSuite;
import com.zb.p2p.trade.common.model.CashPlanHolder;
import com.zb.p2p.trade.persistence.converter.CashPlanConverter;
import com.zb.p2p.trade.service.cash.CashRecordService;
import com.zb.p2p.trade.service.match.CreditorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> 2.0一次性到期还本付息计算器 </p>
 * 非最后一个人占比计算，最后一个人(最大投资金额)使用减法（本期总本金(总利息)-前边已分配的本金(利息)）
 * @author Vinson
 * @version OneOffCreditorAssigner.java v1.0 2018/4/24 12:49 Zhengwenquan Exp $
 */
@Service
public class OneOffCreditorAssigner extends AbstractCashAssigner {

    @Autowired
    private CreditorInfoService creditorInfoService;
    @Autowired
    private CashRecordService cashRecordService;

    @Override
    public void assign(CashBillPlanKey key, CashAmountSuite waitingAssignAmount) {
        logger.info("按一次性还本付息计算分配，key=[{}], waitingAssignAmount=[{}]", key, waitingAssignAmount);
        // 1.数据准备
        prepareSummary(waitingAssignAmount);

        final CashAmountSuite assigned = new CashAmountSuite();
        final List<CashPlan> assignedPlanList = new ArrayList<>();

        // 2.执行分配
        // 2.1 待处理信息加载
        List<CashPlan> cashPlanList = cashRecordService.loadByKey(key);
        List<String> orderList = CashPlanConverter.convert2InvestOrder(cashPlanList);
        Assert.notNull(orderList, "未找到待计算的兑付计划信息");

        // 每个人的投资金额
        CashPlanHolder.get().setInvestedMap(creditorInfoService.loadOrderTotalSuccessAmount(key.getAssetNo(), orderList));

        // 总本金（资产额度）及总利息，组装套件
        // 2.2.逐个进行分配
        for (int i = 0; i < cashPlanList.size(); i++) {
            CashPlan plan = cashPlanList.get(i);
            // 实际状态不为预期状态或者分配实收时不等于已转让则直接结束
            if (plan.getStatus() != currentStatus()) {
                return;
            }
            // 当期是否最后一个分配
            boolean isBillLast = i == (cashPlanList.size()-1);
            // 计算
            calc(plan, assigned, waitingAssignAmount, isBillLast);

            assignedPlanList.add(plan);

        }
        // 分配結果批量更新
        cashRecordService.batchUpdate(assignedPlanList, currentStatus());

    }

    /**
     * 获取数据摘要
     * @param waitingAssignAmount
     */
    private void prepareSummary(CashAmountSuite waitingAssignAmount) {
        // 总投资金额
        CashPlanHolder.get().setTotalInvest(waitingAssignAmount.getPrinciple());
        // 总营收利息
        CashPlanHolder.get().setTotalInterest(waitingAssignAmount.getInterest());

        // 是否最后一期本金
        CashPlanHolder.get().setLastPrinciple(true);
        // 是否最后一期利息
        CashPlanHolder.get().setLastInterest(true);
    }

    @Override
    protected String getAssignedKey(CashPlan plan) {
        return plan.getExtOrderNo();
    }
}
