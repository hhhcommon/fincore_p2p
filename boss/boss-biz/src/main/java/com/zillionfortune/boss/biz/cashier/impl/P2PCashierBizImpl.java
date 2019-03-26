package com.zillionfortune.boss.biz.cashier.impl;

import java.text.SimpleDateFormat;
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
import com.zillionfortune.boss.biz.cashier.P2PCashierBiz;
import com.zillionfortune.boss.biz.cashier.model.LoanWithdrawalRetryRequest;
import com.zillionfortune.boss.biz.cashier.model.QueryTradeDatasForManualRequest;
import com.zillionfortune.boss.biz.cashier.model.QueryTradeDatasRequest;
import com.zillionfortune.boss.common.constants.Constants;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.dto.CashierRespObj;
import com.zillionfortune.boss.common.dto.TradeRespObj;
import com.zillionfortune.boss.common.dto.TxsRespObj;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.utils.DateUtil;
import com.zillionfortune.boss.common.utils.JsonUtils;

@Component
public class P2PCashierBizImpl implements P2PCashierBiz {

	private Logger logger = LoggerFactory.getLogger(P2PCashierBizImpl.class);

	@Value("${query_cashier_trade_manual_url}")
	private String queryTradeRecordUrl;
	
	@Value("${query_cashier_trade_datas_url}")
	private String queryTradeDatasUrl;

	@Value("${cashier_loanwithdrawal_retry_url}")
	private String cashierLoanWithdrawalRetryUrl;

	@Override
	public BaseWebResponse queryTradeDatasForManual(QueryTradeDatasForManualRequest req) {
		logger.info("P2PCashierBizImpl.queryTradeDatasForManual.request:" + JSON.toJSONString(req));
		BaseResponse response = null;
		BaseWebResponse resp = null;
		try {
			response = callCashiereQueryTradeRecord(req);
			logger.info("P2PCashierBizImpl.queryTradeDatasForManual.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			logger.error("调用P2PCashierBizImpl.queryTradeDatasForManual系统异常",e);
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map map = new HashMap<String, Object>();
			map.put("data", response.getAddition());
			map.put("totalCount", Integer.valueOf(response.getRespMsg()));
			resp.setData(map);
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response.getRespMsg());
		}
		return resp;

	}
	
	@Override
	public BaseWebResponse queryTradeDatas(QueryTradeDatasRequest req) {
		logger.info("P2PCashierBizImpl.queryTradeDatas.request:" + JSON.toJSONString(req));
		BaseResponse response = null;
		BaseWebResponse resp = null;
		try {
			response = callCashiereQueryTradeDatas(req);
			logger.info("P2PCashierBizImpl.queryTradeDatas.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			logger.error("调用P2PCashierBizImpl.queryTradeDatas系统异常",e);
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map map = new HashMap<String, Object>();
			map.put("data", response.getAddition());
			map.put("totalCount", Integer.valueOf(response.getRespMsg()));
			resp.setData(map);
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response.getRespMsg());
		}
		return resp;

	}

	@Override
	public BaseWebResponse loanWithdrawalRetry(LoanWithdrawalRetryRequest req) {
		logger.info("P2PCashierBizImpl.loanWithdrawalRetry.request:" + JSON.toJSONString(req));
		BaseResponse response = null;
		BaseWebResponse resp = null;
		try {
			response = callCashiereloanWithdrawalRetry(req);
			logger.info("P2PCashierBizImpl.loanWithdrawalRetry.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			logger.error("调用P2PCashierBizImpl.loanWithdrawalRetry系统异常",e);
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

	/**
	 * 调用收单系统查询收单记录
	 * 
	 * @param product
	 * @param currentStock
	 * @param addStock
	 * @throws Exception
	 */
	private BaseResponse callCashiereQueryTradeDatas(QueryTradeDatasRequest req) throws Exception {
		BaseResponse resp = new BaseResponse();
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATAFORMAT_STR);
		String requestUrl = queryTradeDatasUrl;
		Map map = new HashMap<String, Object>();
		map.put("pageType", req.getPageType());
		map.put("memberId", req.getMemberId());
		map.put("busiType", req.getBusiType());
		map.put("traderType", req.getTraderType());
		map.put("startDate", req.getStartDate());
		map.put("endDate", req.getEndDate());
		map.put("sourceId", req.getSourceId());
		map.put("pageSize", req.getPageSize());
		map.put("pageNo", req.getPageNo());
		String requestJson = JsonUtils.object2Json(map);
		logger.info("调用收单系统查询交易记录请求报文：" + requestJson);
		String responseStr = HttpClientUtil.sendPostRequest(requestUrl, requestJson);
		logger.info("调用收单系统查询交易记录响应报文：" + responseStr);
		if (StringUtils.isNotEmpty(responseStr)) {
			CashierRespObj respObj = JsonUtils.json2Object(responseStr, CashierRespObj.class);
			if (CashierRespObj.SUCCESS.equals(respObj.getCode())) {
				resp.setRespCode(Constants.SUCCESS_RESP_CODE);
				resp.setRespMsg(String.valueOf(respObj.getTotalCount()));
				resp.setAddition(JsonUtils.object2Json(respObj.getData()));
				return resp;
			} else {
				resp.setRespCode(Constants.FAIL_RESP_CODE);
				resp.setRespMsg(respObj.getMsg());
			}
		} else {
			resp.setRespCode(Constants.FAIL_RESP_CODE);
			resp.setRespMsg("调用收单系统查询交易记录系统异常");
		}

		return resp;
	}
	
	/**
	 * 调用收单系统查询收单记录
	 * 
	 * @param product
	 * @param currentStock
	 * @param addStock
	 * @throws Exception
	 */
	private BaseResponse callCashiereQueryTradeRecord(QueryTradeDatasForManualRequest req) throws Exception {
		BaseResponse resp = new BaseResponse();
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATAFORMAT_STR);
		String requestUrl = queryTradeRecordUrl;
		Map map = new HashMap<String, Object>();
		map.put("originalOrderNo", req.getOriginalOrderNo());
		map.put("memberId", req.getMemberId());
		map.put("busiType", req.getBusiType());
		map.put("tradeType", req.getTradeType());
		map.put("startDate", req.getStartDate());
		map.put("endDate", req.getEndDate());
		map.put("sourceId", req.getSourceId());
		map.put("pageSize", req.getPageSize());
		map.put("pageNo", req.getPageNo());
		String requestJson = JsonUtils.object2Json(map);
		logger.info("调用收单系统查询交易记录请求报文：" + requestJson);
		String responseStr = HttpClientUtil.sendPostRequest(requestUrl, requestJson);
		logger.info("调用收单系统查询交易记录响应报文：" + responseStr);
		if (StringUtils.isNotEmpty(responseStr)) {
			CashierRespObj respObj = JsonUtils.json2Object(responseStr, CashierRespObj.class);
			if (CashierRespObj.SUCCESS.equals(respObj.getCode())) {
				resp.setRespCode(Constants.SUCCESS_RESP_CODE);
				resp.setRespMsg(String.valueOf(respObj.getTotalCount()));
				resp.setAddition(JsonUtils.object2Json(respObj.getData()));
				return resp;
			} else {
				resp.setRespCode(Constants.FAIL_RESP_CODE);
				resp.setRespMsg(respObj.getMsg());
			}
		} else {
			resp.setRespCode(Constants.FAIL_RESP_CODE);
			resp.setRespMsg("调用收单系统查询交易记录系统异常");
		}

		return resp;
	}

	/**
	 * 调用收单系统代付失败重试
	 * 
	 * @param product
	 * @param currentStock
	 * @param addStock
	 * @throws Exception
	 */
	private BaseResponse callCashiereloanWithdrawalRetry(LoanWithdrawalRetryRequest req) throws Exception {
		BaseResponse resp = new BaseResponse();
		String requestUrl = cashierLoanWithdrawalRetryUrl;
		Map map = new HashMap<String, Object>();
		map.put("originalOrderNo", req.getOriginalOrderNo());
		map.put("orderNo", req.getOrderNo());
		map.put("orderTime", req.getOrderTime());
		map.put("signId", req.getSignId());
		map.put("memberId", req.getMemberId());
		map.put("tradeType", req.getTradeType());
		map.put("sourceId", req.getSourceId());
		String requestJson = JsonUtils.object2Json(map);
		logger.info("调用收单系统代付失败重试请求报文：" + requestJson);
		String responseStr = HttpClientUtil.sendPostRequest(requestUrl, requestJson);
		logger.info("调用收单系统代付失败重试响应报文：" + responseStr);
		if (StringUtils.isNotEmpty(responseStr)) {
			CashierRespObj respObj = JsonUtils.json2Object(responseStr, CashierRespObj.class);
			if (CashierRespObj.PROCESSING.equals(respObj.getCode())) {
				resp.setRespCode(Constants.SUCCESS_RESP_CODE);
				resp.setAddition(JsonUtils.object2Json(respObj.getData()));
				return resp;
			} else {
				resp.setRespCode(Constants.FAIL_RESP_CODE);
				resp.setRespMsg(respObj.getMsg());
			}
		} else {
			resp.setRespCode(Constants.FAIL_RESP_CODE);
			resp.setRespMsg("调用收单系统代付失败重试系统异常");
		}

		return resp;
	}
}
