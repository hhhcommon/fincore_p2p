package com.zb.p2p.trade.service.assigner;

import com.zb.p2p.trade.common.domain.CashBillPlanKey;
import com.zb.p2p.trade.common.model.CashAmountSuite;

/**
 * <p> 兑付收益分配器 </p>
 *
 * @author Vinson
 * @version CashAssigner.java v1.0 2018/4/24 11:25 Zhengwenquan Exp $
 */
public interface CashAssigner {

    /**
     * 分配计算兑付计划
     * @param key
     * @param waitingAssignAmount
     */
    void assign(CashBillPlanKey key, CashAmountSuite waitingAssignAmount);

}
