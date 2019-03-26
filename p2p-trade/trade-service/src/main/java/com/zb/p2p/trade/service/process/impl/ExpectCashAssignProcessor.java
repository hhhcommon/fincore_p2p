package com.zb.p2p.trade.service.process.impl;

import com.zb.p2p.trade.common.domain.CashBillPlan;
import com.zb.p2p.trade.common.enums.CashAmountTypeEnum;
import com.zb.p2p.trade.common.enums.CashStatusEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import org.springframework.stereotype.Service;

/**
 * <p> 应收兑付金额计算处理 </p>
 *
 * @author Vinson
 * @version ExpectCashAssignProcessor.java v1.0 2018/4/23 19:15 Zhengwenquan Exp $
 */
@Service
public class ExpectCashAssignProcessor extends AbstractCashPlanProcessor {

    @Override
    public CashStatusEnum process(CashBillPlan billPlan) throws BusinessException {

        super.assign(billPlan, CashAmountTypeEnum.EXPECT);
        return CashStatusEnum.CASH_WAIT_ACTUAL;
    }

    @Override
    protected CashStatusEnum getExpectStatus() {
        return CashStatusEnum.INIT;
    }


}
