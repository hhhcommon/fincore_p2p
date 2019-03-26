package com.zb.p2p.trade.service.process;

import com.zb.p2p.trade.common.exception.BusinessException;

/**
 * <p> 兑付计划执行器 </p>
 *
 * @author Vinson
 * @version CashPlanProcessor.java v1.0 2018/4/23 19:09 Zhengwenquan Exp $
 */
public interface CashPlanProcessor {

    /**
     * 处理
     * @throws BusinessException
     */
    void process() throws BusinessException;
}
