package com.zb.p2p.trade.common.model;

import com.zb.p2p.trade.common.domain.CashBillPlanKey;
import com.zb.p2p.trade.common.enums.CashAmountTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p> 兑付计划持有器 </p>
 *
 * @author Vinson
 * @version CashPlanHolder.java v1.0 2018/4/23 18:26 Zhengwenquan Exp $
 */
public class CashPlanHolder {

    public static ThreadLocal<CashPlanCarrier> threadVar = new ThreadLocal<>();

    /**
     * 获取
     * @return
     */
    public static CashPlanCarrier get() {
        return threadVar.get();
    }

    public static void set(CashPlanCarrier carrier) {
        threadVar.set(carrier);
    }

    public static void clear() {
        threadVar.remove();
    }

    /**
     * 兑付计划载体
     */
    @Data
    public static class CashPlanCarrier {

        private CashBillPlanKey key;
        // 兑付金额类型
        private CashAmountTypeEnum amountType;
        //是否最后一期
        private boolean lastPrinciple;
        private boolean lastInterest;

        // 总本金利息
        private BigDecimal totalInvest;
        private BigDecimal totalInterest;

        // 已分配总利息
        private BigDecimal totalAssignedInterest = BigDecimal.ZERO;

        // 会员已分配本金利息总额
        private Map<String, BigDecimal> assignedPrincipleMap;
        private Map<String, BigDecimal> assignedInterestMap;

        // 会员投资总额
        private Map<String, BigDecimal> investedMap;


        public CashPlanCarrier(CashBillPlanKey key) {
            this.key = key;
        }
    }
}
