package com.zb.p2p.trade.service.assigner.impl;

import com.zb.p2p.trade.common.domain.CashBillPlanKey;
import com.zb.p2p.trade.common.domain.CashPlan;
import com.zb.p2p.trade.common.enums.CashAmountTypeEnum;
import com.zb.p2p.trade.common.enums.CashStatusEnum;
import com.zb.p2p.trade.common.model.CashAmountSuite;
import com.zb.p2p.trade.common.model.CashPlanHolder;
import com.zb.p2p.trade.persistence.converter.CashPlanConverter;
import com.zb.p2p.trade.persistence.dto.CashSumAmountEntity;
import com.zb.p2p.trade.service.cash.CashBillPlanService;
import com.zb.p2p.trade.service.cash.CashRecordService;
import com.zb.p2p.trade.service.match.CreditorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> 比率平摊计算器 </p>
 * 非最后一期：非最后一个人占比计算，最后一个人(最大投资金额)使用减法（本期总本金(总利息)-前边已分配的本金(利息)）
 * 最后一期：单个人总本金(总利息,总占比)-单人已分配本金(利息)
 * @author Vinson
 * @version RatioShareCashAssigner.java v1.0 2018/4/24 11:33 Zhengwenquan Exp $
 */
@Service
public class RatioShareCashAssigner extends AbstractCashAssigner {

    @Autowired
    private CreditorInfoService creditorInfoService;
    @Autowired
    private CashBillPlanService cashBillPlanService;
    @Autowired
    private CashRecordService cashRecordService;

    @Override
    public void assign(CashBillPlanKey key, CashAmountSuite waitingAssignAmount) {

        logger.info("按比率占比分配计算，key=[{}], waitingAssignAmount=[{}]", key, waitingAssignAmount);

        // 1.数据准备
        prepareSummary(key, waitingAssignAmount);

        final CashAmountSuite assigned = new CashAmountSuite();
//        BigDecimal assignedInvest = BigDecimal.ZERO;
        final List<CashPlan> assignedPlanList = new ArrayList<>();

        // 2.执行分配
        // 2.1. loadCashAssigner
        List<CashPlan> cashPlanList = cashRecordService.loadByKey(key);
        Assert.isTrue(!CollectionUtils.isEmpty(cashPlanList), "未找到待计算的兑付计划信息");
        prepareInvestInfo(key, cashPlanList);

        // 2.2.逐个进行分配
        for (int i = 0; i < cashPlanList.size(); i++) {
            CashPlan plan = cashPlanList.get(i);
            // 实际状态不为预期状态或者分配实收时不等于已转让则直接结束
            if (plan.getStatus() != currentStatus() && plan.getStatus() != CashStatusEnum.CASH_TRANSFERRED) {
                return;
            }

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
     * @param key
     * @param waitingAssignAmount
     */
    private void prepareSummary(CashBillPlanKey key, CashAmountSuite waitingAssignAmount) {
        // 总投资金额
        BigDecimal totalInvest = creditorInfoService.loadTotalInvestedAmount(key.getAssetNo());
        Assert.isTrue(totalInvest.compareTo(BigDecimal.ZERO) > 0, "不存在投资信息");
        CashPlanHolder.get().setTotalInvest(totalInvest);
        // 总营收利息
        CashSumAmountEntity totalCash = cashBillPlanService.loadCashAmountTotal(key.getAssetNo(), key.getRepaymentType());
        CashPlanHolder.get().setTotalInterest(getAssignInterest(totalCash));

        if (waitingAssignAmount.getPrinciple().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal assignPrincipal = getAssignPrincipal(totalCash);
            // 校验
            Assert.isTrue(assignPrincipal.compareTo(totalInvest) <= 0,
                    String.format("兑付本金[%f]不得超过投资本金[%f]", assignPrincipal, totalInvest));
            CashPlanHolder.get().setLastPrinciple(isLastStageProcess(key, true));
        }

        if (waitingAssignAmount.getInterest().compareTo(BigDecimal.ZERO) > 0) {
            CashPlanHolder.get().setLastInterest(isLastStageProcess(key, false));
        }
    }

    private void prepareInvestInfo(CashBillPlanKey key, List<CashPlan> cashPlanList) {
        List<String> orderList = CashPlanConverter.convert2InvestOrder(cashPlanList);

        // 每个人(投资订单)的投资金额
        CashPlanHolder.get().setInvestedMap(creditorInfoService.loadOrderTotalSuccessAmount(key.getAssetNo(), orderList));

        // 已分配本金及利息
        Map<String, CashAmountSuite> memberAssignedMap = cashRecordService.loadOrderTotalAmount(key, orderList,
                CashPlanHolder.get().getAmountType());

        Map<String, BigDecimal> assignedPrincipalMap = new HashMap<>();
        Map<String, BigDecimal> assignedInterestMap = new HashMap<>();

        // 会员已分配本金
        for (Map.Entry<String, CashAmountSuite> entry : memberAssignedMap.entrySet()) {
            assignedPrincipalMap.put(entry.getKey(), entry.getValue().getPrinciple());
            assignedInterestMap.put(entry.getKey(), entry.getValue().getInterest());
        }
        CashPlanHolder.get().setAssignedPrincipleMap(assignedPrincipalMap);
        CashPlanHolder.get().setAssignedInterestMap(assignedInterestMap);
    }

    private BigDecimal getAssignPrincipal(CashSumAmountEntity totalCash) {
        if (CashPlanHolder.get().getAmountType() == CashAmountTypeEnum.EXPECT){
            return totalCash.getExpectPrincipal();
        } else if (CashPlanHolder.get().getAmountType() == CashAmountTypeEnum.ACTUAL) {
            return totalCash.getActualPrincipal();
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal getAssignInterest(CashSumAmountEntity totalCash) {
        if (CashPlanHolder.get().getAmountType() == CashAmountTypeEnum.EXPECT){
            return totalCash.getExpectInterest();
        } else if (CashPlanHolder.get().getAmountType() == CashAmountTypeEnum.ACTUAL) {
            return totalCash.getActualInterest();
        }
        return BigDecimal.ZERO;
    }

    /**
     * 是否最后一期
     * @param key
     * @param isPrincipal
     * @return
     */
    protected boolean isLastStageProcess(CashBillPlanKey key, boolean isPrincipal) {
        return cashBillPlanService.isLastStage(key, isPrincipal);
    }

    @Override
    protected String getAssignedKey(CashPlan plan) {
        return plan.getExtOrderNo();
    }
}
