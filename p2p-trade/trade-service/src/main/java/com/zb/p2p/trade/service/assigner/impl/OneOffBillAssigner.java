package com.zb.p2p.trade.service.assigner.impl;

import com.zb.p2p.trade.common.enums.RepaymentFeeStatusEnum;
import com.zb.p2p.trade.common.model.CashAmountSuite;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import com.zb.p2p.trade.persistence.entity.RepayBillPlanEntity;
import org.springframework.stereotype.Service;

/**
 * <p> 一次性还本付息还款计划计算分配 </p>
 *
 * @author Vinson
 * @version OneOffBillAssigner.java v1.0 2018/5/31 0031 下午 9:19 Zhengwenquan Exp $
 */
@Service
public class OneOffBillAssigner extends AbstractBillAssigner {


    @Override
    protected void reBuildRepayBillPlan(LoanRequestEntity loanRequest, RepayBillPlanEntity billPlan) {

        CashAmountSuite billAmountSuite = billPropertyHolder.get().getTotalDebtAmount();

        billPlan.setExpectPrinciple(billAmountSuite.getPrinciple());
        billPlan.setExpectInterest(billAmountSuite.getInterest());

        billPlan.setRepaymentFee(billAmountSuite.getFee());
        billPlan.setRepaymentFeeStatus(RepaymentFeeStatusEnum.INIT.getCode());

//        billPlan.setRemainAmount(BigDecimal.ZERO);
    }

}
