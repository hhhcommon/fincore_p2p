package com.zb.p2p.trade.web.controller;

import com.alibaba.fastjson.JSON;
import com.zb.fincore.common.enums.ResultCodeEnum;
import com.zb.p2p.trade.api.req.*;
import com.zb.p2p.trade.api.resp.IncomeDto;
import com.zb.p2p.trade.common.model.CommonResp;
import com.zb.p2p.trade.common.queue.model.MatchMqResult;
import com.zb.p2p.trade.persistence.dto.CashRecordDto;
import com.zb.p2p.trade.persistence.dto.RepayAmountDTO;
import com.zb.p2p.trade.service.cash.CashBillPlanService;
import com.zb.p2p.trade.service.cash.IncomeCalcService;
import com.zb.p2p.trade.service.cash.RepayBillPlanService;
import com.zb.p2p.trade.service.topic.AssetMatchCompleteNotifyListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p> 借款、还款、兑付暴露服务 </p>
 *
 * @author Vinson
 * @version CashManagerController.java v1.0 2018/4/26 9:43 Zhengwenquan Exp $
 */
@RestController
@RequestMapping("/cash")
public class CashManagerController {

    @Autowired
    private CashBillPlanService cashBillPlanService;
    @Autowired
    private IncomeCalcService incomeCalcService;
    @Autowired
    private RepayBillPlanService repayBillPlanService;
    @Autowired
    private AssetMatchCompleteNotifyListener assetMatchCompleteNotifyListener;

    @RequestMapping(value = "/queryCashPlan", method = RequestMethod.POST)
    public CommonResp<List<CashRecordDto>> queryCashPlan(@RequestBody CashRecordReq cashRecordReq) {
        return new CommonResp(ResultCodeEnum.SUCCESS.code(), ResultCodeEnum.SUCCESS.desc(),
                cashBillPlanService.queryCashPlanInfoByCondition(cashRecordReq));
    }

    /**
     * 查询还款的本金和利息
     * @param req
     * @return
     */
    @RequestMapping(value = "/getRepayAmountList", method = RequestMethod.POST, produces = "application/json")
    public CommonResp<List<RepayAmountDTO>> getRepayAmountList(@RequestBody @Valid RepayAmountReq req) {
        CommonResp resp = new CommonResp<>();
        try {
            resp = new CommonResp<>(ResultCodeEnum.SUCCESS.code(), ResultCodeEnum.SUCCESS.desc());
            List<RepayAmountDTO> repayAmountList =  repayBillPlanService.selectRepayAmountList(req);

            resp.setData(repayAmountList);
        } catch (Exception e) {
            resp.resetCode(ResultCodeEnum.FAIL.code(), ResultCodeEnum.FAIL.desc());
        }
        return resp;
    }

    /**
     * 还款交易请求(3.0)
     * @param req
     * @return
     */
    @RequestMapping(value = "/repayment", method = RequestMethod.POST, produces = "application/json")
    public CommonResp<String> repayment(@RequestBody @Valid RepaymentReq req) {
        // 请求还款
        return cashBillPlanService.repaymentTradeResultHandler(req);
    }

    @RequestMapping(value = "/matchCompleted", method = RequestMethod.POST, produces = "application/json")
    public CommonResp<String> matchCompleted(@RequestBody MatchMqResult req){
        try {
            assetMatchCompleteNotifyListener.consume(JSON.toJSONString(req));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommonResp<>();
    }

    /**
     * 查询每日收益
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryDailyIncome" , method = RequestMethod.POST)
    public CommonResp queryDailyInCome(@RequestBody @Valid DailyIncomeReq req){

        IncomeDto resultDto = incomeCalcService.queryYesterdayIncome(req);

        CommonResp commonResp = new CommonResp();

        if(resultDto == null){
            commonResp.resetCode(ResultCodeEnum.NOT_FOUND_INFO.code(), ResultCodeEnum.NOT_FOUND_INFO.desc());
        }else{
            commonResp.setData(resultDto );
        }
        return commonResp;
    }

    /**
     * 投资订单预期总收益查询
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryInvestOrderIncome" , method = RequestMethod.POST)
    public CommonResp queryInvestOrderIncome(@RequestBody @Valid InvestOrderIncomeReq req){

        IncomeDto resultDto = incomeCalcService.queryInvestOrderIncome(req);

        CommonResp commonResp = new CommonResp();

        if(resultDto == null){
            commonResp.resetCode(ResultCodeEnum.NOT_FOUND_INFO.code(), ResultCodeEnum.NOT_FOUND_INFO.desc());
        }else{
            commonResp.setData(resultDto );
        }
        return commonResp;
    }
}
