package com.zb.p2p.service.incomecalc;

import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.p2p.entity.OrderRequestEntity;
import com.zb.p2p.facade.api.req.BatchDailyIncomeReq;
import com.zb.p2p.facade.api.req.DailyIncomeReq;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.api.resp.DailyIncomeDTO;
import com.zb.p2p.facade.api.resp.product.ProductDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by wangwanbin on 2017/8/31.
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
     * @param account
     * @param productCode
     * @param productLimit
     * @param startDate
     * @param yield
     */
    void incomeCalc(ProductDTO productDTO);

    /**
     * 查询单个产品每日收益
     *
     * @param req
     * @return
     */
    CommonResp<DailyIncomeDTO> queryIncome(DailyIncomeReq req);

    /**
     * 查询多个产品每日收益
     *
     * @param req
     * @return
     */
    CommonResp<List<DailyIncomeDTO>> queryBatchIncome(BatchDailyIncomeReq req);
}
