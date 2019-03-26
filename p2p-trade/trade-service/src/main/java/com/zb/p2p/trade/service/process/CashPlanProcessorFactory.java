package com.zb.p2p.trade.service.process;

import com.zb.p2p.trade.common.enums.CashStatusEnum;

/**
 * <p> 兑付计划执行工厂 </p>
 *
 * @author Vinson
 * @version CashPlanProcessorFactory.java v1.0 2018/4/23 19:07 Zhengwenquan Exp $
 */
public interface CashPlanProcessorFactory {

    /**
     * 根据兑付状态获取执行者
     * @param status
     * @return
     */
    CashPlanProcessor load(CashStatusEnum status);

    /**
     * 得到兑付金额计算LockKey
     * @param assetNo
     * @param stageNo
     * @return
     */
    String getCashAmountLockKey(String assetNo, Integer stageNo);
}
