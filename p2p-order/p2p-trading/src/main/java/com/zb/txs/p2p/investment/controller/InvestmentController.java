package com.zb.txs.p2p.investment.controller;

import com.google.common.base.Preconditions;
import com.zb.txs.p2p.business.enums.order.ResponseCodeEnum;
import com.zb.txs.p2p.business.invest.repose.InvestProfitResp;
import com.zb.txs.p2p.business.invest.repose.InvestmentRecordResp;
import com.zb.txs.p2p.business.invest.request.InvestPageReq;
import com.zb.txs.p2p.business.invest.request.InvestmentRecordRequest;
import com.zb.txs.p2p.business.invest.request.InvestmentRequest;
import com.zb.txs.p2p.business.order.response.CommonResponse;
import com.zb.txs.p2p.business.order.response.PageQueryResp;
import com.zb.txs.p2p.investment.service.InvestmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/p2p/trading/investment")
@Slf4j
public class InvestmentController {
    @Autowired
    InvestmentService investmentService;

    /**
     * 昨日收益和网贷总额接口
     *
     * @param investmentRequest
     * @return 投资详情
     */
    @RequestMapping(value = "/holdTotalAssets", method = RequestMethod.POST)
    public CommonResponse<InvestProfitResp> getInvestProfit(@RequestBody InvestmentRequest investmentRequest) {
        try {
            Preconditions.checkNotNull(investmentRequest, "请求实体investmentRequest为空");
            Preconditions.checkNotNull(investmentRequest.getMemberId(), "用户ID为空");
            Preconditions.checkNotNull(investmentRequest.getInterestDate(), "收益日期为空");
        } catch (NullPointerException e) {
            log.warn("请求参数错误: {}", e.getMessage());
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), e.getMessage(), null);
        }
        log.info("getInvestProfit请求参数,investmentRequest:{}", investmentRequest);

        return investmentService.getInvestProfit(investmentRequest);
    }

    /**
     * 前端查询交易记录接口(TXS后端)
     * @param investmentRecordRequest
     * @return 投资详情
     */
    @RequestMapping(value = "/tradeRecord", method = RequestMethod.POST)
    public CommonResponse<InvestmentRecordResp> getInvestmentRecord(@RequestBody InvestmentRecordRequest investmentRecordRequest) {
        try{
            Preconditions.checkNotNull(investmentRecordRequest,"请求实体investmentRecordRequest为空");
            Preconditions.checkNotNull(investmentRecordRequest.getMemberId(), "用户ID为空");
            Preconditions.checkNotNull(investmentRecordRequest.getTransType(), "交易类型为空");
        }catch(NullPointerException e){
            log.warn("请求参数错误: {}", e.getMessage());
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), e.getMessage(), null);
        }

        try {
            log.info("getInvestmentRecord请求参数,investmentRecordRequest:{}", investmentRecordRequest);

            return investmentService.getInvestmentRecord(investmentRecordRequest);
        } catch (Exception e) {
            log.error("系统异常:", e);
            return CommonResponse.failure();
        }
    }

    /**
     * 运营台查询资金交易记录接口
     *
     * @param investPageReq
     */
    @RequestMapping(value = "/listPageRecord", method = RequestMethod.POST)
    public CommonResponse<PageQueryResp> listPageRecord(@RequestBody InvestPageReq investPageReq) {
        try {
            Preconditions.checkNotNull(investPageReq, "请求实体investPageReq为空");
            Preconditions.checkNotNull(investPageReq.getStartDate(), "开始时间为空");
            Preconditions.checkNotNull(investPageReq.getEndDate(), "结束时间为空");
            Preconditions.checkNotNull(investPageReq.getTransType(), "交易类型为空");
            Preconditions.checkNotNull(investPageReq.getMemberId(), "用户ID为空");
            Preconditions.checkNotNull(investPageReq.getPageNo(), "页码为空");
            Preconditions.checkNotNull(investPageReq.getPageSize(), "每页显示条数为空");
        } catch (NullPointerException e) {
            log.warn("请求参数错误: {}", e.getMessage());
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), e.getMessage(), null);
        }

        if (investPageReq.getStartDate().getTime() > investPageReq.getEndDate().getTime()) {
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "开始时间不能大于结束时间", null);
        }

        if (investPageReq.getPageNo() <= 0 || investPageReq.getPageSize() <= 0) {
            return CommonResponse.build(ResponseCodeEnum.RESPONSE_PARAM_FAIL.getCode(), "pageNo或pageSize必须大于0", null);
        }
        log.info("listPageRecord请求参数,investPageReq:{}", investPageReq);

        return investmentService.listPageRecord(investPageReq);

    }


}
