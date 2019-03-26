package com.zillionfortune.boss.biz.ams.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zb.fincore.ams.common.dto.PageQueryResponse;
import com.zb.fincore.ams.common.dto.QueryResponse;
import com.zb.fincore.ams.facade.TrusteeServiceFacade;
import com.zb.fincore.ams.facade.dto.req.CreateTrusteeRequest;
import com.zb.fincore.ams.facade.dto.req.QueryTrusteeListRequest;
import com.zb.fincore.ams.facade.dto.req.QueryTrusteeRequest;
import com.zb.fincore.ams.facade.dto.resp.CreateTrusteeResponse;
import com.zb.fincore.ams.facade.model.TrusteeModel;
import com.zillionfortune.boss.biz.ams.TrusteeBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
@Component
public class TrusteeBizImpl implements TrusteeBiz {
	
	private Logger logger = LoggerFactory.getLogger(TrusteeBizImpl.class);

	/** 受托方服务 */
	@Autowired
	private TrusteeServiceFacade trusteeServiceFacade;
	
	@Override
	public BaseWebResponse createTrustee(CreateTrusteeRequest req) {
		logger.info("TrusteeBizImpl.add.request:" + JSON.toJSONString(req));
		CreateTrusteeResponse response = null;
		BaseWebResponse resp = null;
		try {
			response =trusteeServiceFacade.createTrustee(req);
			logger.info("TrusteeBizImpl.add.response:"
					+ JSON.toJSONString(response));
		} catch (Exception e) {
			resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
		}
		
		if(ResultCode.SUCCESS.code().equals(response.getRespCode())){
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("addition", response.getAddition());
			
			resp.setData(respMap);
		}else{
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
		}
		
		return resp;

	}

	@Override
	public BaseWebResponse queryTrustee(QueryTrusteeRequest req) {
		logger.info("TrusteeBizImpl.query.request:" + JSON.toJSONString(req));
		QueryResponse<TrusteeModel> response = null;
		BaseWebResponse resp = null;
		try {
			response =trusteeServiceFacade.queryTrustee(req);
			logger.info("TrusteeBizImpl.query.response:"
					+ JSON.toJSONString(response));
		} catch (Exception e) {
			resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
		}
		
		if(ResultCode.SUCCESS.code().equals(response.getRespCode())){
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("addition", response.getAddition());
			respMap.put("data", response.getData());
			resp.setData(respMap);
		}else{
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
		}
		
		return resp;

	}

	@Override
	public BaseWebResponse queryTrusteeList(QueryTrusteeListRequest req) {
		logger.info("TrusteeBizImpl.queryList.request:" + JSON.toJSONString(req));
		PageQueryResponse<TrusteeModel> response = null;
		BaseWebResponse resp = null;
		try {
			response =trusteeServiceFacade.queryTrusteeList(req);
			logger.info("TrusteeBizImpl.queryList.response:"
					+ JSON.toJSONString(response));
		} catch (Exception e) {
			resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
		}
		
		if(ResultCode.SUCCESS.code().equals(response.getRespCode())){
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("addition", response.getAddition());
			respMap.put("dataList", response.getDataList()!=null?response.getDataList():new ArrayList());
			respMap.put("pageNo", response.getPageNo());
			respMap.put("pageSize", response.getPageSize());
			respMap.put("totalCount", response.getTotalCount());
			respMap.put("totalPage", response.getTotalPage());
			resp.setData(respMap);
		}else{
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
		}
		
		return resp;
	}

}
