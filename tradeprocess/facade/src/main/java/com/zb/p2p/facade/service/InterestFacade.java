package com.zb.p2p.facade.service;

import java.util.List;

import com.zb.p2p.facade.api.req.BatchDailyIncomeReq;
import com.zb.p2p.facade.api.req.DailyIncomeReq;
import com.zb.p2p.facade.api.req.OrderIncomeReq;
import com.zb.p2p.facade.api.req.YesterdayIncomeReq;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.api.resp.DailyIncomeDTO;
import com.zb.p2p.facade.api.resp.IncomeDTO;

/**
 * Created by wangwanbin on 2017/9/8.
 */
public interface InterestFacade {

    /**
     * 查询收益
     *
     * @param req
     * @return
     */
   /* CommonResp<DailyIncomeDTO> queryIncome(DailyIncomeReq req);

    *//**
     * 批量查询收益
     *
     * @param req
     * @return
     *//*
    CommonResp<List<DailyIncomeDTO>> queryBatchIncome(BatchDailyIncomeReq req);*/
    
    public CommonResp<IncomeDTO> queryYesterdayIncome(YesterdayIncomeReq req);
    
    public CommonResp<IncomeDTO> queryOrderIncome(OrderIncomeReq req) ;
    
    public CommonResp queryAccountAndHistoryIncome(DailyIncomeReq req);
    
    public void invalidateCache();
    
    
}
