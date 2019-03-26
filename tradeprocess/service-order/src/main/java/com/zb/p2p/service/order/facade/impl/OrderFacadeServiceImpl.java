package com.zb.p2p.service.order.facade.impl;

import com.zb.p2p.common.exception.ExceptionHandler;
import com.zb.p2p.dao.master.LoanRequestDAO;
import com.zb.p2p.enums.OperationTypeEnum;
import com.zb.p2p.enums.ResponseCodeEnum;
import com.zb.p2p.facade.api.req.BatchLoanReq;
import com.zb.p2p.facade.api.req.LoanReq;
import com.zb.p2p.facade.api.req.NotifyTradeStatusReq;
import com.zb.p2p.facade.api.req.QueryLoanOrderInfoReq;
import com.zb.p2p.facade.api.req.RepayAmountReq;
import com.zb.p2p.facade.api.req.StockQueryReq;
import com.zb.p2p.facade.api.req.*;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.api.resp.ProductStockDTO;
import com.zb.p2p.facade.api.resp.RepayAmountDTO;
import com.zb.p2p.facade.api.resp.order.LoanOrderRespDTO;
import com.zb.p2p.facade.api.resp.order.OrderMatchRespDTO;
import com.zb.p2p.facade.service.OrderFacade;
import com.zb.p2p.service.order.OrderAsyncService;
import com.zb.p2p.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by limingxin on 2017/8/31.
 */
@Service
@Slf4j
public class OrderFacadeServiceImpl implements OrderFacade {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ExceptionHandler exceptionHandler;
     
    @Autowired 
    private OrderAsyncService orderAsyncService;
    
    @Autowired 
    private LoanRequestDAO loanRequestDAO;

    @Override
    public CommonResp<String> batchLoan(BatchLoanReq batchLoanReq) {
        try {
            CommonResp resp = orderService.batchLoan(batchLoanReq);
            return resp;
        } catch (Exception e) {
            log.error(OperationTypeEnum.LOAN_APPLY_EXCEPTION.getDesc(), e);
            return exceptionHandler.handleException(e);
            /*CommonResp<String> commonResp = new CommonResp<>();
            commonResp.setCode(ResponseCodeEnum.RESPONSE_FAIL.getCode());
            commonResp.setMessage(ResponseCodeEnum.RESPONSE_FAIL.getDesc());
            return commonResp;*/
        }
    }

    @Override
    public CommonResp<String> loan(LoanReq loanReq) {
        try {
            CommonResp resp = orderService.loan(loanReq);
            return resp;
        } catch (Exception e) {
            log.error(OperationTypeEnum.LOAN_APPLY_EXCEPTION.getDesc(), e);
            CommonResp<String> commonResp = new CommonResp<>();
            commonResp.setCode(ResponseCodeEnum.RESPONSE_FAIL.getCode());
            commonResp.setMessage(ResponseCodeEnum.RESPONSE_FAIL.getDesc());
            return commonResp;
        }
    }
    
    public CommonResp<List<RepayAmountDTO>> getRepayAmountList(RepayAmountReq tepayAmountReq) {
    	CommonResp resp  = null;
        try {
        	resp = new CommonResp<>(ResponseCodeEnum.RESPONSE_SUCCESS.getCode(), ResponseCodeEnum.RESPONSE_SUCCESS.getDesc());
        	List<RepayAmountDTO> repayAmountList =  loanRequestDAO.selectRepayAmountList(tepayAmountReq.getLoanNoList());
        	
        	if(repayAmountList != null && repayAmountList.size() > 0 ){
        		for (RepayAmountDTO repayAmountDTO : repayAmountList) {
        			repayAmountDTO.setAmountAndIncome( repayAmountDTO.getAmountAndIncome().setScale(2, BigDecimal.ROUND_FLOOR));
    			}
        	}
        	
            resp.setData(repayAmountList);
            return resp;
        } catch (Exception e) {
        	resp = new CommonResp<>();
        	resp.setCode(ResponseCodeEnum.RESPONSE_FAIL.getCode());
        	resp.setMessage(ResponseCodeEnum.RESPONSE_FAIL.getDesc());
            return resp;
        }
    }

    @Override
    public CommonResp<LoanOrderRespDTO> queryLoanOrderInfo(QueryLoanOrderInfoReq req) {
        return orderService.queryLoanOrderInfo(req);
    }

    @Override
    public CommonResp<OrderMatchRespDTO> queryOrderMatchInfo(QueryOrderMatchInfoReq req) {
        return orderService.queryOrderMatchInfo(req);
    }

    @Override
    public CommonResp<ProductStockDTO> queryStock(StockQueryReq req) {
        return orderService.queryStock(req);
    }
    
    public void loanWithdrawCallBack(NotifyTradeStatusReq resp) throws Exception {
          orderAsyncService.loanWithdrawCallBack(resp);
    }
    
    
}
