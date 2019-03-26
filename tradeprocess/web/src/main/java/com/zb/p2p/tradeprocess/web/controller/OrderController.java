package com.zb.p2p.tradeprocess.web.controller;

import com.zb.p2p.enums.ResponseCodeEnum;
import com.zb.p2p.facade.api.req.*;
import com.zb.p2p.facade.api.resp.ProductStockDTO;
import com.zb.p2p.facade.api.resp.order.OrderRespDTO;
import com.zb.p2p.facade.service.MatchRecordFacade;
import com.zb.p2p.facade.service.OrderFacade;
import com.zb.p2p.service.order.OrderAsyncService;
import com.zb.p2p.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by limingxin on 2017/8/25.
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderFacade orderFacade;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderAsyncService orderAsyncService;

    @Autowired
    private MatchRecordFacade matchRecordFacade;

    @RequestMapping(value = "batchLoan", method = RequestMethod.POST)
    public GenericResp batchLoan(@RequestBody BatchLoanReq batchLoanReq) {
        return GenericResp.convert(orderFacade.batchLoan(batchLoanReq));
    }

    @RequestMapping(value = "loan", method = RequestMethod.POST)
    public GenericResp loan(@RequestBody LoanReq loanReq) {
        return GenericResp.convert(orderFacade.loan(loanReq));
    }

    @RequestMapping(value = "invest", method = RequestMethod.POST)
    public GenericResp<OrderRespDTO> invest(@RequestBody OrderReq orderReq) {
        try {
            return GenericResp.convert(orderService.order(orderReq));
        } catch (Exception e) {
            return defaultFailed();
        }
    }

    @RequestMapping(value = "assetMatch", method = RequestMethod.POST)
    public GenericResp loan(@RequestBody AssetMatchReq assetMatchReq) {
        return GenericResp.convert(matchRecordFacade.assetMatch(assetMatchReq));
    }

    @RequestMapping(value = "queryLoanOrderInfo", method = RequestMethod.POST)
    public GenericResp queryLoanOrderInfo(@RequestBody QueryLoanOrderInfoReq queryLoanOrderInfoReq) {
        return GenericResp.convert(orderFacade.queryLoanOrderInfo(queryLoanOrderInfoReq));
    }

    @RequestMapping(value = "queryOrderMatchInfo", method = RequestMethod.POST)
    public GenericResp queryOrderMatchInfo(@RequestBody QueryOrderMatchInfoReq queryOrderMatchInfoReq) {
        return GenericResp.convert(orderFacade.queryOrderMatchInfo(queryOrderMatchInfoReq));
    }


    @RequestMapping(value = "queryInvestStock", method = RequestMethod.POST)
    public GenericResp<ProductStockDTO> queryInvestStock(@RequestBody StockQueryReq stockQueryReq) {
        try {
            return GenericResp.convert(orderService.queryStock(stockQueryReq));
        } catch (Exception e) {
            return defaultFailed();
        }
    }

    @RequestMapping(value = "fixLoan", method = RequestMethod.POST)
    public GenericResp fixLoan(@RequestBody OrderMatchReq orderMatchReq) {
        try {
//            orderAsyncService.loanExec(orderMatchReq);
            return GenericResp.builder().code(ResponseCodeEnum.RESPONSE_SUCCESS.getCode()).build();
        } catch (Exception e) {
            return defaultFailed();
        }
    }
    
   /* @RequestMapping(value = "getRepayAmountList")
    public GenericResp getRepayAmountList() {
        try {
        	List<String> list = new ArrayList<>();
        	list.add( "AAA0001");
        	list.add( "AAA0002");
        	RepayAmountReq tepayAmountReq = new RepayAmountReq();
        	tepayAmountReq.setLoanNoList(list);
        	orderFacade.getRepayAmountList(tepayAmountReq);
            return GenericResp.builder().code(ResponseCodeEnum.RESPONSE_SUCCESS.getCode()).build();
        } catch (Exception e) {
            return defaultFailed();
        }
    }*/

    static GenericResp defaultFailed() {
        GenericResp commonResp = new GenericResp();
        commonResp.setCode(ResponseCodeEnum.RESPONSE_FAIL.getCode());
        commonResp.setMsg(ResponseCodeEnum.RESPONSE_FAIL.getDesc());
        return commonResp;
    }
}
