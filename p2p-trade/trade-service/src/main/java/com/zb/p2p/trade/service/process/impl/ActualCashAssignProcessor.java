package com.zb.p2p.trade.service.process.impl;

import com.zb.p2p.trade.common.domain.CashBillPlan;
import com.zb.p2p.trade.common.enums.CashAmountTypeEnum;
import com.zb.p2p.trade.common.enums.CashStatusEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import org.springframework.stereotype.Service;

/**
 * <p> 实际兑付金额计算处理 </p>
 *
 * @author Vinson
 * @version ActualCashAssignProcessor.java v1.0 2018/4/23 19:18 Zhengwenquan Exp $
 */
@Service
public class ActualCashAssignProcessor extends AbstractCashPlanProcessor {


    @Override
    protected CashStatusEnum process(CashBillPlan billPlan) throws BusinessException {
        super.assign(billPlan, CashAmountTypeEnum.ACTUAL);

        return CashStatusEnum.CASH_WAIT_PERFORM;
    }

    @Override
    protected CashStatusEnum getExpectStatus() {
        return CashStatusEnum.CASH_WAIT_ACTUAL;
    }


}
