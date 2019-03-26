package com.zillionfortune.boss.web.controller.trade;

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
import com.zillionfortune.boss.biz.trade.P2PTradeBiz;
import com.zillionfortune.boss.biz.trade.model.QueryTradeRecodeRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;
import com.zillionfortune.boss.web.controller.trade.vo.CashRequestVO;
import com.zillionfortune.boss.web.controller.trade.vo.QueryLoanOrderInfoVO;
import com.zillionfortune.boss.web.controller.trade.vo.QueryTradeRecodeRequestVO;

/**
 * 交易相关Controller
 * 
 * @author litaiping
 * 
 */
@Controller
@RequestMapping(value = "/p2ptradeservice")
public class P2PTradeController {

	private final Logger log = LoggerFactory.getLogger(P2PTradeController.class);

	@Autowired
	private P2PTradeBiz p2pTradeBiz;

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
	public BaseWebResponse queryTradeRecord(@RequestBody QueryTradeRecodeRequestVO vo) {
		log.info("P2PAssetController.queryTradeRecord.req:" + JSON.toJSONString(vo));
		QueryTradeRecodeRequest req = null;
		BaseWebResponse resp = null;
		try {
			req = new QueryTradeRecodeRequest();
			PropertyUtils.copyProperties(req, vo);
			resp = p2pTradeBiz.queryTradeRecord(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PAssetController.queryTradeRecord.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}
	/**
	 * 兑付
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/cash", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse cash(@RequestBody CashRequestVO vo) {
		log.info("P2PAssetController.cash.req:" + JSON.toJSONString(vo));
		BaseWebResponse resp = null;
		try {
			resp = p2pTradeBiz.cash(vo.getProductCode());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PAssetController.cash.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}
	
	/**
	 * 查询交易兑付失败错误列表记录
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/querytradcasherrorlist", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse queryTradeCashErrorList(@RequestBody CashRequestVO vo) {
		log.info("P2PAssetController.queryTradeCashErrorList.req:" + JSON.toJSONString(vo));
		BaseWebResponse resp = null;
		try {
			resp = p2pTradeBiz.queryTradeCashErrorList(vo.getProductCode());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PAssetController.queryTradeCashErrorList.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}
	
	/**
	 * 查询借款详情
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/queryloanorderinfo", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse queryLoanOrderInfo(@RequestBody QueryLoanOrderInfoVO vo) {
		log.info("P2PAssetController.queryLoanOrderInfo.req:" + JSON.toJSONString(vo));
		BaseWebResponse resp = null;
		try {
			resp = p2pTradeBiz.queryLoanOrderInfo(vo.getLoanNo());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("P2PAssetController.queryLoanOrderInfo.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}

}
