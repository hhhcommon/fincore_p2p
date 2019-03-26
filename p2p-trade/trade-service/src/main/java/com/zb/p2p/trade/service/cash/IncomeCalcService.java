package com.zb.p2p.trade.service.cash;

import com.zb.p2p.trade.api.req.DailyIncomeReq;
import com.zb.p2p.trade.api.req.InvestOrderIncomeReq;
import com.zb.p2p.trade.api.resp.IncomeDto;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;

/**
 * <p> 每日收益计算 </p>
 *
 * @author Vinson
 * @version IncomeCalcService.java v1.0 2018/6/2 0029 下午 7:43 Zhengwenquan Exp $
 */

public interface IncomeCalcService {

    /**
     * 获取待执行任务列表
     *
     * @return
     */
   // List<OrderRequestEntity> listTask(List<TaskItemDefine> list, int limit);

    /**
     * 批量插入收益记录
     *
     */
    void incomeCalc(LoanRequestEntity productDTO);

    /**
     * 查询单个产品每日收益
     *
     * @param req
     * @return
     */
    IncomeDto queryIncome(DailyIncomeReq req);

    /**
     * 查询会员昨日收益
     * @param req
     * @return
     */
    IncomeDto queryYesterdayIncome(DailyIncomeReq req);

    /**
     * 投资预期总收益
     * @param req
     * @return
     */
    IncomeDto queryInvestOrderIncome(InvestOrderIncomeReq req);
}
