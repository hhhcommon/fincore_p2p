package com.zb.p2p.tradeprocess.web.controller;

import com.zb.p2p.facade.api.req.BatchDailyIncomeReq;
import com.zb.p2p.facade.api.req.DailyIncomeReq;
import com.zb.p2p.facade.api.req.OrderIncomeReq;
import com.zb.p2p.facade.api.req.YesterdayIncomeReq;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.api.resp.DailyIncomeDTO;
import com.zb.p2p.facade.api.resp.IncomeDTO;
import com.zb.p2p.facade.service.InterestFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wangwanbin on 2017/9/8.
 */
@RestController
@RequestMapping("/income")
public class IncomeController {

    @Autowired
    private InterestFacade interestFacade;

   /* @RequestMapping("/queryIncome")
    public GenericResp<DailyIncomeDTO> queryIncome(@RequestBody DailyIncomeReq req) {
        return GenericResp.convert(interestFacade.queryIncome(req));
    }

    @RequestMapping("/queryBatchIncome")
    public GenericResp<List<DailyIncomeDTO>> queryBatchIncome(@RequestBody BatchDailyIncomeReq req) {
        return GenericResp.convert(interestFacade.queryBatchIncome(req));
    }*/
    
    /**
     * 昨日收益
     * @param req
     * @return
     */
    @RequestMapping("/queryYesterdayIncome")
    public GenericResp<IncomeDTO> queryYesterdayIncome(@RequestBody YesterdayIncomeReq req) {
        return GenericResp.convert(interestFacade.queryYesterdayIncome(req));
    }
    
    /**
     * 订单到期收益
     * @param req
     * @return
     */
    @RequestMapping("/queryOrderIncome")
    public GenericResp<IncomeDTO> queryOrderIncome(@RequestBody OrderIncomeReq req) {
        return GenericResp.convert(interestFacade.queryOrderIncome(req));
    }
    
    /**
     * 持仓本金  和  累计已兑付收益
     * @param req
     * @return
     */
    @RequestMapping("/queryAccountAndHistoryIncome")
    public GenericResp queryAccountAndHistoryIncome(@RequestBody DailyIncomeReq req) {
        return GenericResp.convert(interestFacade.queryAccountAndHistoryIncome(req));
    }
    
    /**
     * 清除缓存
     */
    @RequestMapping("/invalidateCache")
    public void queryAccountAndHistoryIncome() {
          interestFacade.invalidateCache();
    }
    
    
}
