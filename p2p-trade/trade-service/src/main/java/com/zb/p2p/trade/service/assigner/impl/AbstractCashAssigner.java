package com.zb.p2p.trade.service.assigner.impl;

import com.zb.p2p.trade.common.domain.CashPlan;
import com.zb.p2p.trade.common.enums.CashAmountTypeEnum;
import com.zb.p2p.trade.common.enums.CashStatusEnum;
import com.zb.p2p.trade.common.model.CashAmountSuite;
import com.zb.p2p.trade.common.model.CashPlanHolder;
import com.zb.p2p.trade.service.assigner.CashAssigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p> 兑付计算抽象类 </p>
 * 非最后一期：非最后一个人占比计算，最后一个人(最大投资金额)使用减法（本期总本金(总利息)-前边已分配的本金(利息)）
 * 最后一期：单个人总本金(总利息,总占比)-单人已分配本金(利息)
 *
 * @author Vinson
 * @version AbstractCashAssigner.java v1.0 2018/4/25 19:20 Zhengwenquan Exp $
 */
public abstract class AbstractCashAssigner implements CashAssigner{

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected CashStatusEnum currentStatus(){
        return CashPlanHolder.get().getAmountType() == CashAmountTypeEnum.EXPECT ? CashStatusEnum.INIT
                : CashStatusEnum.CASH_WAIT_ACTUAL;
    }

    protected CashStatusEnum nextStatus(){
        return CashPlanHolder.get().getAmountType() == CashAmountTypeEnum.EXPECT ? CashStatusEnum.CASH_WAIT_ACTUAL
                : CashStatusEnum.CASH_WAIT_PERFORM;
    }

    /**
     * 根据套件进行计算
     * @param plan
     * @param assigned
     * @param needAssignAmount
     * @param isBillLast
     */
    protected void calc(CashPlan plan, CashAmountSuite assigned,
                        CashAmountSuite needAssignAmount, boolean isBillLast) {
        // 投资金额
        BigDecimal investAmount = CashPlanHolder.get().getInvestedMap().get(getAssignedKey(plan));
        // 1. 本金
        BigDecimal principal;
        BigDecimal assignedPrincipal = getAssignedPrincipal(getAssignedKey(plan));
        if (CashPlanHolder.get().isLastPrinciple()) {
            logger.info("最后一期{}本金使用减法：投资金额[{}]-已分配本金[{}]",
                    plan.getStageSeq(), investAmount, assignedPrincipal);
            principal = investAmount.subtract(assignedPrincipal);
        }else {
            // 增加已分配金额
            if (isBillLast) {
                principal = needAssignAmount.getPrinciple().subtract(assigned.getPrinciple());
            } else {
                principal = calcAddDown(investAmount, needAssignAmount.getPrinciple());
            }
        }

        // 2.利息
        BigDecimal interest;
        if (CashPlanHolder.get().isLastPrinciple()) {
            if (isBillLast) {
                logger.info("最后一期{}最后一个{}利息使用减法：本次待分配总利息[{}]-已分配利息[{}]",
                        plan.getStageSeq(), plan.getId(), needAssignAmount.getInterest(), assigned.getInterest());
                interest = needAssignAmount.getInterest().subtract(assigned.getInterest());
            } else {
                // 当前投资人总利息
                BigDecimal totalInterest = calcAddDown(investAmount, CashPlanHolder.get().getTotalInterest());
                // 已分配利息
                BigDecimal assignedInterest = getAssignedInterest(getAssignedKey(plan));
                logger.info("最后一期{}利息使用减法：个人总利息[{}]-已分配利息[{}]",
                        plan.getStageSeq(), totalInterest, assignedInterest);
                interest = totalInterest.subtract(assignedInterest);
            }
            if (interest.compareTo(BigDecimal.ZERO) < 0) {
                interest = BigDecimal.ZERO;
            }
        }else {
            // 增加已分配金额
            if (isBillLast) {
                interest = needAssignAmount.getInterest().subtract(assigned.getInterest());
            } else {
                interest = calcAddDown(investAmount, needAssignAmount.getInterest());
            }
        }

        Assert.isTrue(BigDecimal.ZERO.compareTo(principal) < 0, String.format("计算本金[[%f]]不能小于等于零", principal));
        Assert.isTrue(BigDecimal.ZERO.compareTo(interest) <= 0, String.format("计算利息[[%f]]不能小于零", interest));

        // 增加已分配金额
        assigned.add(new CashAmountSuite(principal, interest));

        // Set
        if (CashAmountTypeEnum.EXPECT == CashPlanHolder.get().getAmountType()) {
            plan.setExpectPrinciple(principal);
            plan.setExpectInterest(interest);
        }else if (CashAmountTypeEnum.ACTUAL == CashPlanHolder.get().getAmountType()) {
            plan.setCashAmount(principal);
            plan.setCashIncome(interest);
            plan.setCashDate(new Date());
            // 清除应收本金利息（不更新）
            plan.setExpectPrinciple(null);
            plan.setExpectInterest(null);
        }
        // 更新状态
        if (currentStatus() == plan.getStatus()) {
            plan.setStatus(nextStatus());
        }
    }

    /**
     * 投资金额/资产总金额 * 待分配本金或者利息
     * @param investAmount
     * @param waitingAssignAmount
     * @return
     */
    protected BigDecimal calcAddDown(BigDecimal investAmount, BigDecimal waitingAssignAmount) {
        if (waitingAssignAmount.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        // 计算
        BigDecimal amount = waitingAssignAmount.multiply(investAmount)
                .divide(CashPlanHolder.get().getTotalInvest(), 2, BigDecimal.ROUND_DOWN);

        return amount;
    }

    /**
     * 获得个人/投资单已分配本金
     * @param holdKey
     * @return
     */
    protected BigDecimal getAssignedPrincipal(String holdKey) {
        if (CashPlanHolder.get().getAssignedPrincipleMap() == null) {
            return BigDecimal.ZERO;
        }
        return getAmount(CashPlanHolder.get().getAssignedPrincipleMap().get(holdKey));
    }

    protected abstract String getAssignedKey(CashPlan plan);

    /**
     * 获得个人/投资单已分配利息
     * @param holdKey
     * @return
     */
    protected BigDecimal getAssignedInterest(String holdKey) {
        if (CashPlanHolder.get().getAssignedInterestMap() == null) {
            return BigDecimal.ZERO;
        }
        return getAmount(CashPlanHolder.get().getAssignedInterestMap().get(holdKey));
    }

    protected BigDecimal getAmount(BigDecimal amount) {
        if (amount == null) {
            return BigDecimal.ZERO;
        }
        return amount;
    }
}
