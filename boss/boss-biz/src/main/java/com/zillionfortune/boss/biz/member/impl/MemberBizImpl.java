package com.zillionfortune.boss.biz.member.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zb.fincore.common.utils.AesHttpClientUtils;
import com.zb.fincore.common.utils.HttpClientUtil;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zillionfortune.boss.biz.member.MemberBiz;
import com.zillionfortune.boss.common.constants.Constants;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.dto.TradeRespObj;
import com.zillionfortune.boss.common.dto.TxsRespObj;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.utils.JsonUtils;

/**
 * ClassName: ProductIntegrationImpl <br/>
 * Function: 会员服务接口实现. <br/>
 * Date: 2017年5月8日 下午5:42:29 <br/>
 * 
 * @author wangzinan_tech@zillionfortune.com
 * @since JDK 1.7
 */
@Component
public class MemberBizImpl implements MemberBiz {

	private final Logger log = LoggerFactory.getLogger(this.getClass());


	@Value("${query_member_info_url}")
	private String queryMemberInfoUrl;

	@Value("${query_member_cards_by_memberid_url}")
	private String queryMemberCardsByMemberIdUrl;

	@Autowired
	protected AesHttpClientUtils aesHttpClientUtils;

	@Override
	public BaseWebResponse queryMemberInfo(String memberId, String mobile) {
		log.info("MemberBizImpl.queryMemberInfo.req: memberId=" + memberId + " mobile=" + memberId);
		BaseWebResponse resp = null;
		try {
			// 调用产品上线http服务接口
			BaseResponse baseResponse = queryMemberInfoUseeHttp(memberId, mobile);

			if (ResultCode.SUCCESS.code().equals(baseResponse.getRespCode())) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
				resp.setData(baseResponse.getAddition());
			} else {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), baseResponse.getRespMsg());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
		}

		log.info("MemberBizImpl.queryMemberInfo.resp:" + JSON.toJSONString(resp));
		return resp;
	}

	@Override
	public BaseWebResponse queryMemberCards(String memberId) {
		log.info("MemberBizImpl.queryMemberCards.req: memberId=" + memberId + " mobile=" + memberId);
		BaseWebResponse resp = null;
		try {
			// 调用产品上线http服务接口
			BaseResponse baseResponse = queryMemberCardsUseHttp( memberId);

			if (ResultCode.SUCCESS.code().equals(baseResponse.getRespCode())) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
				resp.setData(baseResponse.getAddition());
			} else {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), baseResponse.getRespMsg());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
		}

		log.info("MemberBizImpl.queryMemberCards.resp:" + JSON.toJSONString(resp));
		return resp;
	}

	/**	
	 * 调用会员系统查询会员信息
	 * 
	 * @param product
	 * @param currentStock
	 * @param addStock
	 * @throws Exception
	 */
	private BaseResponse queryMemberInfoUseeHttp( String memberId, String mobile) throws Exception {
		BaseResponse resp = new BaseResponse();
		Map map = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(memberId)) {
			map.put("memberId", memberId);
		} else if (StringUtils.isNotEmpty(mobile)) {
			map.put("mobile", mobile);
		}
		String requestJson = JsonUtils.object2Json(map);
		log.info("调用会员系统查询会员信息请求报文：" + requestJson);
		String responseStr = HttpClientUtil.sendPostRequest(queryMemberInfoUrl, requestJson);
		log.info("调用会员系统查询会员信息响应报文：" + responseStr);
		if (StringUtils.isNotEmpty(responseStr)) {
			TxsRespObj respObj = JsonUtils.json2Object(responseStr, TxsRespObj.class);
			if (TradeRespObj.SUCCESS.equals(respObj.getCode())) {
				resp.setRespCode(Constants.SUCCESS_RESP_CODE);
				resp.setAddition(JsonUtils.object2Json(respObj.getData()));
				return resp;
			} else {
				resp.setRespCode(Constants.FAIL_RESP_CODE);
				resp.setRespMsg(respObj.getMessage());
			}
		} else {
			resp.setRespCode(Constants.FAIL_RESP_CODE);
			resp.setRespMsg("调用会员系统查询会员信息系统异常");
		}

		return resp;
	}

	/**
	 * 调用会员系统接口通知更新产品库存
	 * 
	 * @param product
	 * @param currentStock
	 * @param addStock
	 * @throws Exception
	 */
	private BaseResponse queryMemberCardsUseHttp(String memberId) throws Exception {
		BaseResponse resp = new BaseResponse();
		Map map = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(memberId)) {
			map.put("memberId", memberId);
		}
		String requestJson = JsonUtils.object2Json(map);
		log.info("调用会员系统查询会员绑卡信息请求报文：" + requestJson);
		String responseStr = HttpClientUtil.sendPostRequest(queryMemberCardsByMemberIdUrl, requestJson);
		log.info("调用会员系统查询会员绑卡信息响应报文：" + responseStr);
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
			resp.setRespMsg("调用会员系统查询会员绑卡信息系统异常");
		}

		return resp;
	}
}
