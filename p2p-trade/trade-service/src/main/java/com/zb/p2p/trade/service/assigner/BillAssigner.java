package com.zb.p2p.trade.service.assigner;

import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import com.zb.p2p.trade.persistence.entity.RepayBillPlanEntity;

import java.util.List;

/**
 * <p> 还款计划分配器 </p>
 *
 * @author Vinson
 * @version CashAssigner.java v1.0 2018/5/31 11:25 Zhengwenquan Exp $
 */
public interface BillAssigner {

    /**
     * 分配计算还款计划
     * @param loanRequest
     */
    List<RepayBillPlanEntity> createBill(LoanRequestEntity loanRequest);

}
