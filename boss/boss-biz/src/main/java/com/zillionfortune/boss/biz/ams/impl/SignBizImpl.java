package com.zillionfortune.boss.biz.ams.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zb.fincore.ams.common.dto.BaseResponse;
import com.zb.fincore.ams.common.dto.PageQueryResponse;
import com.zb.fincore.ams.common.dto.QueryResponse;
import com.zb.fincore.ams.facade.ContractSignFacade;
import com.zb.fincore.ams.facade.dto.req.QueryContractSignListRequest;
import com.zb.fincore.ams.facade.dto.req.QueryContractSignRequest;
import com.zb.fincore.ams.facade.dto.req.UpdateContractSignStatusRequest;
import com.zillionfortune.boss.biz.ams.SignBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
@Component
public class SignBizImpl implements SignBiz {

	private Logger logger = LoggerFactory.getLogger(SignBizImpl.class);
	
	@Autowired
	private ContractSignFacade contractSignFacade;

	@Override
	public BaseWebResponse queryContractSignList(QueryContractSignListRequest req) {
		logger.info("SignBizImpl.queryContractSignList.request:" + JSON.toJSONString(req));
		 PageQueryResponse response = null;
		BaseWebResponse resp = null;
		try {
			response = contractSignFacade.queryContractSignList(req);
			logger.info("SignBizImpl.queryContractSignList.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			logger.error("SignBizImpl.queryContractSignList.exception",e);
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (response != null && ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("addition", response.getAddition());
			respMap.put("dataList", response.getDataList() != null ? response.getDataList() : new ArrayList());
			respMap.put("pageNo", response.getPageNo());
			respMap.put("pageSize", response.getPageSize());
			respMap.put("totalCount", response.getTotalCount());
			respMap.put("totalPage", response.getTotalPage());
			resp.setData(respMap);
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response==null?ResultCode.FAIL.desc():response.getRespMsg());
		}
		logger.info("SignBizImpl.queryContractSignList.response:" + JSON.toJSONString(resp));
		return resp;
	}
	
	@Override
	public BaseWebResponse queryContractSignDetail(QueryContractSignRequest req) {
		logger.info("SignBizImpl.queryContractSignDetail.request:" + JSON.toJSONString(req));
		 QueryResponse response = null;
		BaseWebResponse resp = null;
		try {
			response = contractSignFacade.queryContractSignDetail(req);
			logger.info("SignBizImpl.queryContractSignDetail.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			logger.error("SignBizImpl.queryContractSignDetail.exception",e);
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (response != null && ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response==null?ResultCode.FAIL.desc():response.getRespMsg());
		}
		logger.info("SignBizImpl.queryContractSignDetail.response:" + JSON.toJSONString(resp));
		return resp;
	}
	
	@Override
	public BaseWebResponse signContract(UpdateContractSignStatusRequest req) {
		logger.info("SignBizImpl.signContract.request:" + JSON.toJSONString(req));
		  BaseResponse response = null;
		BaseWebResponse resp = null;
		try {
			response = contractSignFacade.signContract(req);
			logger.info("SignBizImpl.signContract.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			logger.error("SignBizImpl.signContract.exception",e);
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}
		if (response != null && ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("addition", response.getAddition());
			resp.setData(response.getAddition());
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response==null?ResultCode.FAIL.desc():response.getRespMsg());
		}
		logger.info("SignBizImpl.signContract.response:" + JSON.toJSONString(resp));
		return resp;
	}
	
	@Override
	public BaseWebResponse cancelSignContract(UpdateContractSignStatusRequest req) {
		logger.info("SignBizImpl.cancelSignContract.request:" + JSON.toJSONString(req));
		  BaseResponse response = null;
		BaseWebResponse resp = null;
		try {
			response = contractSignFacade.cancelSignContract(req);
			logger.info("SignBizImpl.cancelSignContract.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			logger.error("SignBizImpl.cancelSignContract.exception",e);
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}
		if (response != null && ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("addition", response.getAddition());
			resp.setData(response.getAddition());
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response==null?ResultCode.FAIL.desc():response.getRespMsg());
		}
		logger.info("SignBizImpl.cancelSignContract.response:" + JSON.toJSONString(resp));
		return resp;
	}


}
