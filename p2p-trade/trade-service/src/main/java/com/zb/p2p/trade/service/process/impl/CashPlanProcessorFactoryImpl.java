package com.zb.p2p.trade.service.process.impl;

import com.zb.p2p.trade.common.constant.GlobalVar;
import com.zb.p2p.trade.common.enums.CashStatusEnum;
import com.zb.p2p.trade.service.process.CashPlanProcessor;
import com.zb.p2p.trade.service.process.CashPlanProcessorFactory;

import java.util.Map;

/**
 * <p> 描述 </p>
 *
 * @author Vinson
 * @version CashPlanProcessorFactoryImpl.java v1.0 2018/4/23 19:08 Zhengwenquan Exp $
 */
public class CashPlanProcessorFactoryImpl implements CashPlanProcessorFactory {

    private Map<String, CashPlanProcessor> processorMap;

    public void setProcessorMap(Map<String, CashPlanProcessor> processorMap) {
        this.processorMap = processorMap;
    }

    @Override
    public CashPlanProcessor load(CashStatusEnum status) {
        if (status == null) {
            return null;
        }
        return processorMap.get(status.getCode());
    }

    public String getCashAmountLockKey(String assetNo, Integer stageNo) {
        return GlobalVar.GLOBAL_CASH_AMOUNT_CALC_LOCK_KEY + "_" + assetNo + stageNo;
    }
}
