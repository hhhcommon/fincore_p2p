package com.zillionfortune.boss.biz.trade.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zb.fincore.ams.common.dto.BaseResponse;
import com.zb.fincore.common.utils.HttpClientUtil;
import com.zillionfortune.boss.biz.trade.P2PTradeBiz;
import com.zillionfortune.boss.biz.trade.model.QueryTradeRecodeRequest;
import com.zillionfortune.boss.common.constants.Constants;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.dto.TradeRespObj;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.utils.JsonUtils;

@Component
public class P2PTradeBizImpl implements P2PTradeBiz {

	private Logger logger = LoggerFactory.getLogger(P2PTradeBizImpl.class);

    @Value("${query_trade_record_url}")
	private String queryTradeRecordUrl;
    
    @Value("${query_trade_cash_error_list_url}")
	private String queryTradeCashErrorListUrl;
    
    @Value("${trade_cash_url}")
    private String tradeCashUrl;
    
    @Value("${query_loan_order_info_url}")
    private String queryLoanOrderInfoUrl;

	@Override
	public BaseWebResponse queryTradeRecord	(QueryTradeRecodeRequest req) {
		logger.info("P2PTradeBizImpl.queryTradeRecord.request:" +JSON.toJSONString(req));
		BaseResponse response = null;
		BaseWebResponse resp = null;
		try {
			response = callTradeQueryTradeRecord(req);
			logger.info("P2PTradeBizImpl.queryTradeRecord.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			logger.error("调用P2PTradeBizImpl.queryTradeRecord系统异常",e);
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			resp.setData(response.getAddition());
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response.getRespMsg());
		}
		return resp;

	}
	
	@Override
	public BaseWebResponse queryTradeCashErrorList	(String productCode) {
		logger.info("P2PTradeBizImpl.queryTradeCashErrorList.request:productCode=" +productCode);
		BaseResponse response = null;
		BaseWebResponse resp = null;
		try {
			response = callTradeQueryCashErrorList(productCode);
			logger.info("P2PTradeBizImpl.queryTradeCashErrorList.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			logger.error("调用P2PTradeBizImpl.queryTradeCashErrorList系统异常",e);
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("addition", response.getAddition());
			resp.setData(respMap);
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response.getRespMsg());
		}
		return resp;

	}
	
	@Override
	public BaseWebResponse cash	(String productCode) {
		logger.info("P2PTradeBizImpl.cash.request:productCode=" +productCode);
		BaseResponse response = null;
		BaseWebResponse resp = null;
		try {
			response = callTradeCash(productCode);
			logger.info("P2PTradeBizImpl.cash.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			logger.error("调用P2PTradeBizImpl.cash系统异常",e);
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("addition", response.getAddition());
			resp.setData(respMap);
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response.getRespMsg());
		}
		return resp;

	}
	
	@Override
	public BaseWebResponse queryLoanOrderInfo	(String loanNo) {
		logger.info("P2PTradeBizImpl.queryLoanOrderInfo.request:loanNo=" +loanNo);
		BaseResponse response = null;
		BaseWebResponse resp = null;
		try {
			response = callTradeQueryLoanOrderInfo(loanNo);
			logger.info("P2PTradeBizImpl.queryLoanOrderInfo.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			logger.error("调用P2PTradeBizImpl.queryLoanOrderInfo系统异常",e);
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("addition", response.getAddition());
			resp.setData(respMap);
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response.getRespMsg());
		}
		return resp;

	}


	/**
	 * 调用交易系统查询交易记录
	 * 
	 * @param product
	 * @param currentStock
	 * @param addStock
	 * @throws Exception
	 */
	private BaseResponse callTradeQueryTradeRecord(QueryTradeRecodeRequest req) throws Exception {
		BaseResponse resp = new BaseResponse();
		String requestUrl = queryTradeRecordUrl;
		Map map = new HashMap<String, String>();
		map.put("memberId", req.getMemberId());
		map.put("pageNo", req.getPageNo());
		map.put("pageSize", req.getPageSize());
		map.put("startDate", req.getStartDate());
		map.put("endDate", req.getEndDate());
		map.put("transType", req.getTransType());
		String requestJson = JsonUtils.object2Json(map);
		logger.info("调用交易系统查询交易记录请求报文：" + requestJson);
		String responseStr = HttpClientUtil.sendPostRequest(requestUrl, requestJson);
		logger.info("调用交易系统查询交易记录响应报文：" + responseStr);
		if (StringUtils.isNotEmpty(responseStr)) {
			TradeRespObj respObj = JsonUtils.json2Object(responseStr, TradeRespObj.class);
			if (TradeRespObj.SUCCESS.equals(respObj.getCode())) {
				resp.setRespCode(Constants.SUCCESS_RESP_CODE);
				resp.setAddition(JsonUtils.object2Json(respObj.getData()));
				return resp;
			} else {
				resp.setRespCode(Constants.FAIL_RESP_CODE);
				resp.setRespMsg(respObj.getMsg());
			}
		} else {
			resp.setRespCode(Constants.FAIL_RESP_CODE);
			resp.setRespMsg("调用交易系统查询交易记录系统异常");
		}

		return resp;
	}
	/**
	 * 调用交易系统查询交易记录
	 * 
	 * @param product
	 * @param currentStock
	 * @param addStock
	 * @throws Exception
	 */
	private BaseResponse callTradeCash(String productCode) throws Exception {
		BaseResponse resp = new BaseResponse();
		String requestUrl = tradeCashUrl;
		Map map = new HashMap<String, String>();
		map.put("productNo", productCode);
		String requestJson = JsonUtils.object2Json(map);
		logger.info("调用交易系统兑付请求报文：" + requestJson);
		String responseStr = HttpClientUtil.sendPostRequest(requestUrl, requestJson);
		logger.info("调用交易系统兑付响应报文：" + responseStr);
		if (StringUtils.isNotEmpty(responseStr)) {
			TradeRespObj respObj = JsonUtils.json2Object(responseStr, TradeRespObj.class);
			if (TradeRespObj.SUCCESS.equals(respObj.getCode())) {
				resp.setRespCode(Constants.SUCCESS_RESP_CODE);
				resp.setAddition(JsonUtils.object2Json(respObj.getData()));
				return resp;
			} else {
				resp.setRespCode(Constants.FAIL_RESP_CODE);
				resp.setRespMsg(respObj.getMsg());
			}
		} else {
			resp.setRespCode(Constants.FAIL_RESP_CODE);
			resp.setRespMsg("调用交易系统兑付系统异常");
		}

		return resp;
	}
	
	/**
	 * 调用交易系统查询兑付失败记录
	 * 
	 * @param product
	 * @param currentStock
	 * @param addStock
	 * @throws Exception
	 */
	private BaseResponse callTradeQueryCashErrorList(String productCode) throws Exception {
		BaseResponse resp = new BaseResponse();
		String requestUrl = queryTradeCashErrorListUrl;
		Map map = new HashMap<String, String>();
		map.put("productNo", productCode);
		String requestJson = JsonUtils.object2Json(map);
		logger.info("调用交易系统查询兑付失败请求报文：" + requestJson);
		String responseStr = HttpClientUtil.sendPostRequest(requestUrl, requestJson);
		logger.info("调用交易系统查询兑付失败响应报文：" + responseStr);
		if (StringUtils.isNotEmpty(responseStr)) {
			TradeRespObj respObj = JsonUtils.json2Object(responseStr, TradeRespObj.class);
			if (TradeRespObj.SUCCESS.equals(respObj.getCode())) {
				resp.setRespCode(Constants.SUCCESS_RESP_CODE);
				resp.setAddition(JsonUtils.object2Json(respObj.getData()));
				return resp;
			} else {
				resp.setRespCode(Constants.FAIL_RESP_CODE);
				resp.setRespMsg(respObj.getMsg());
			}
		} else {
			resp.setRespCode(Constants.FAIL_RESP_CODE);
			resp.setRespMsg("调用交易系统查询兑付失败系统异常");
		}

		return resp;
	}
	
	/**
	 * 调用交易系统查询借款详情记录
	 * 
	 * @param product
	 * @param currentStock
	 * @param addStock
	 * @throws Exception
	 */
	private BaseResponse callTradeQueryLoanOrderInfo(String loanNo) throws Exception {
		BaseResponse resp = new BaseResponse();
		String requestUrl = queryLoanOrderInfoUrl;
		Map map = new HashMap<String, String>();
		map.put("loanNo", loanNo);
		String requestJson = JsonUtils.object2Json(map);
		logger.info("调用交易系统查借款详情请求报文：" + requestJson);
		String responseStr = HttpClientUtil.sendPostRequest(requestUrl, requestJson);
		logger.info("调用交易系统查询借款详情响应报文：" + responseStr);
		if (StringUtils.isNotEmpty(responseStr)) {
			TradeRespObj respObj = JsonUtils.json2Object(responseStr, TradeRespObj.class);
			if (TradeRespObj.SUCCESS.equals(respObj.getCode())) {
				resp.setRespCode(Constants.SUCCESS_RESP_CODE);
				resp.setAddition(JsonUtils.object2Json(respObj.getData()));
				return resp;
			} else {
				resp.setRespCode(Constants.FAIL_RESP_CODE);
				resp.setRespMsg(respObj.getMsg());
			}
		} else {
			resp.setRespCode(Constants.FAIL_RESP_CODE);
			resp.setRespMsg("调用交易系统查询借款详情系统异常");
		}

		return resp;
	}
}
