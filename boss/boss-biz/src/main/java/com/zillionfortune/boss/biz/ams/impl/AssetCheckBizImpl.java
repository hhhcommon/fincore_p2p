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
import com.zb.fincore.ams.facade.AssetCheckServiceFacade;
import com.zb.fincore.ams.facade.dto.req.QueryAssetTransactionRequest;
import com.zb.fincore.ams.facade.dto.req.QueryNotInProcessAssetRequest;
import com.zb.fincore.ams.facade.model.AssetTransactionModel;
import com.zb.fincore.ams.facade.model.InProcessAssetModel;
import com.zb.fincore.ams.facade.model.NotInProcessAssetModel;
import com.zillionfortune.boss.biz.ams.AssetCheckBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;

@Component
public class AssetCheckBizImpl implements AssetCheckBiz {
	
	private Logger logger = LoggerFactory.getLogger(AssetApprovalBizImpl.class);
	
	@Autowired
	private AssetCheckServiceFacade assetCheckServiceFacade;
	
	@Override
	public BaseWebResponse queryInProcessAsset() {
		PageQueryResponse<InProcessAssetModel> response = null;
		BaseWebResponse resp = null;
		try {
			response=assetCheckServiceFacade.queryInProcessAsset();
			logger.info("AssetApprovalImpl.approveAsset.response:"
					+ JSON.toJSONString(response));
		} catch (Exception e) {
			resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
		}
		
		if(ResultCode.SUCCESS.code().equals(response.getRespCode())){
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("data", response.getDataList());
			
			resp.setData(respMap);
		}else{
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
		}
		
		return resp;

	}
	
	@Override
	public BaseWebResponse queryNotInProcessAsset(String date) {
		PageQueryResponse<NotInProcessAssetModel> response = null;
		BaseWebResponse resp = null;
		try {
			QueryNotInProcessAssetRequest req = new QueryNotInProcessAssetRequest();
			req.setDate(date);
			response=assetCheckServiceFacade.queryNotInProcessAsset(req);
			logger.info("AssetApprovalImpl.approveAsset.response:"
					+ JSON.toJSONString(response));
		} catch (Exception e) {
			resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
		}
		
		if(ResultCode.SUCCESS.code().equals(response.getRespCode())){
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("assetAmount", response.getAddition());
			respMap.put("data", response.getDataList());
			
			resp.setData(respMap);
		}else{
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
		}
		
		return resp;

	}

	@Override
	public BaseWebResponse queryAssetTransaction(QueryAssetTransactionRequest req) {
		PageQueryResponse<AssetTransactionModel> response = null;
		BaseWebResponse resp = null;
		try {
			response=assetCheckServiceFacade.queryAssetTransaction(req);
			logger.info("AssetApprovalImpl.approveAsset.response:"
					+ JSON.toJSONString(response));
		} catch (Exception e) {
			resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
		}
		
		if(ResultCode.SUCCESS.code().equals(response.getRespCode())){
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("data", response.getDataList());
			respMap.put("pageNo", response.getPageNo());
			respMap.put("pageSize", response.getPageSize());
			respMap.put("totalCount", response.getTotalCount());
			
			resp.setData(respMap);
		}else{
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
		}
		
		return resp;

	}
}
