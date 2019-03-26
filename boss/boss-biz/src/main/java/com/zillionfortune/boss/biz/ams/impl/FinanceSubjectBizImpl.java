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
import com.zb.fincore.ams.facade.FinanceSubjectServiceFacade;
import com.zb.fincore.ams.facade.dto.req.CreateFinanceSubjectRequest;
import com.zb.fincore.ams.facade.dto.req.QueryFinanceSubjectListRequest;
import com.zb.fincore.ams.facade.dto.req.QueryFinanceSubjectRequest;
import com.zb.fincore.ams.facade.dto.resp.CreateFinanceSubjectResponse;
import com.zb.fincore.ams.facade.model.FinanceSubjectModel;
import com.zillionfortune.boss.biz.ams.FinanceSubjectBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
@Component
public class FinanceSubjectBizImpl implements FinanceSubjectBiz {
	
	private Logger logger = LoggerFactory.getLogger(FinanceSubjectBizImpl.class);

	/** 发行方服务 */
	@Autowired
	private FinanceSubjectServiceFacade financeSubjectServiceFacade;
	
	@Override
	public BaseWebResponse createFinanceSubject(CreateFinanceSubjectRequest req) {
		logger.info("FinanceSubjectBizImpl.add.request:" + JSON.toJSONString(req));
		CreateFinanceSubjectResponse response = null;
		BaseWebResponse resp = null;
		try {
			response =financeSubjectServiceFacade.createFinanceSubject(req);
			logger.info("FinanceSubjectBizImpl.add.response:"
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
	public BaseWebResponse queryFinanceSubject(QueryFinanceSubjectRequest req) {
		logger.info("FinanceSubjectBizImpl.query.request:" + JSON.toJSONString(req));
		QueryResponse<FinanceSubjectModel> response = null;
		BaseWebResponse resp = null;
		try {
			response =financeSubjectServiceFacade.queryFinanceSubject(req);
			logger.info("FinanceSubjectBizImpl.query.response:"
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
	public BaseWebResponse queryFinanceSubjectList(QueryFinanceSubjectListRequest req) {
		logger.info("FinanceSubjectBizImpl.queryList.request:" + JSON.toJSONString(req));
		PageQueryResponse<FinanceSubjectModel> response = null;
		BaseWebResponse resp = null;
		try {
			response =financeSubjectServiceFacade.queryFinanceSubjectList(req);
			logger.info("FinanceSubjectBizImpl.queryList.response:"
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
