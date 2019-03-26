package com.zillionfortune.boss.web.controller.cashier;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zillionfortune.boss.biz.cashier.P2PCashierBiz;
import com.zillionfortune.boss.biz.cashier.model.LoanWithdrawalRetryRequest;
import com.zillionfortune.boss.biz.cashier.model.QueryTradeDatasForManualRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.cashier.vo.LoanWithdrawalRetryRequestVO;
import com.zillionfortune.boss.web.controller.cashier.vo.QueryTradeDatasForManualRequestVO;
import com.zillionfortune.boss.web.controller.cashier.vo.QueryTradeDatasRequestVO;
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;

/**
 * 收单相关Controller
 * 
 * @author litaiping
 * 
 */
@Controller
@RequestMapping(value = "/p2pcashierservice")
public class P2PCashierController {

	private final Logger log = LoggerFactory.getLogger(P2PCashierController.class);

	@Autowired
	private P2PCashierBiz p2pCashierBiz;

	@Autowired
	private HttpSessionUtils httpSessionUtils;

	/**
	 * 查询投资交易记录
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/querytraderecord", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse queryTradeRecord(@RequestBody QueryTradeDatasForManualRequestVO vo) {
		log.info("P2PCashierController.queryTradeRecord.req:" + JSON.toJSONString(vo));
		QueryTradeDatasForManualRequest req = null;
		BaseWebResponse resp = null;
		try {
			req = new QueryTradeDatasForManualRequest();
			PropertyUtils.copyProperties(req, vo);
			resp = p2pCashierBiz.queryTradeDatasForManual(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PCashierController.queryTradeRecord.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}
	
	/**
	 * 查询投资交易记录
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/querytradedatas", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse queryTradeDatas(@RequestBody QueryTradeDatasRequestVO vo) {
		log.info("P2PCashierController.queryTradeDatas.req:" + JSON.toJSONString(vo));
		QueryTradeDatasForManualRequest req = null;
		BaseWebResponse resp = null;
		try {
			req = new QueryTradeDatasForManualRequest();
			PropertyUtils.copyProperties(req, vo);
			resp = p2pCashierBiz.queryTradeDatasForManual(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PCashierController.queryTradeDatas.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}
	

	/**
	 * 放款失败重试
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/loanwithdrawalretry", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse loanWithdrawalRetry(@RequestBody LoanWithdrawalRetryRequestVO vo) {
		log.info("P2PCashierController.loanWithdrawalRetry.req:" + JSON.toJSONString(vo));
		LoanWithdrawalRetryRequest req = null;
		BaseWebResponse resp = null;
		try {
			req = new LoanWithdrawalRetryRequest();
			PropertyUtils.copyProperties(req, vo);
			resp = p2pCashierBiz.loanWithdrawalRetry(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PCashierController.loanWithdrawalRetry.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}
	
}
