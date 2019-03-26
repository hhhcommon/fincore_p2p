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
import com.zb.fincore.ams.facade.AssetPoolRelationServiceFacade;
import com.zb.fincore.ams.facade.dto.req.CreateAssetPoolRelationRequest;
import com.zb.fincore.ams.facade.dto.req.QueryPoolAssetListRequest;
import com.zb.fincore.ams.facade.dto.resp.CreateAssetPoolRelationResponse;
import com.zb.fincore.ams.facade.model.AssetModel;
import com.zillionfortune.boss.biz.ams.AssetPoolRelationBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
@Component
public class AssetPoolRelationBizImpl implements AssetPoolRelationBiz {

	private Logger logger = LoggerFactory.getLogger(AssetPoolRelationBizImpl.class);

	/** 资产池服务 */
	@Autowired
	private AssetPoolRelationServiceFacade AssetPoolRelationServiceFacade;

	@Override
	public BaseWebResponse createAssetPoolRelation(CreateAssetPoolRelationRequest req) {
		logger.info("AssetPoolRelationBizImpl.add.request:" + JSON.toJSONString(req));
		CreateAssetPoolRelationResponse response = null;
		BaseWebResponse resp = null;
		try {
			response =AssetPoolRelationServiceFacade.createAssetPoolRelation(req);
			logger.info("AssetPoolRelationBizImpl.add.response:"
					+ JSON.toJSONString(response));
		} catch (Exception e) {
			resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
		}
		
		if(ResultCode.SUCCESS.code().equals(response.getRespCode())){
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("assetPoolRelationModelList", response.getAssetPoolRelationModelList());
			respMap.put("addition", response.getAddition());
			
			resp.setData(respMap);
		}else{
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
		}
		
		return resp;

	}
	
	
	@Override
	public BaseWebResponse queryPoolAssetList(QueryPoolAssetListRequest req) {
		logger.info("AssetPoolRelationBizImpl.querylist.request:" + JSON.toJSONString(req));
		PageQueryResponse<AssetModel> response = null;
		BaseWebResponse resp = null;
		try {
			response =AssetPoolRelationServiceFacade.queryPoolAssetList(req);
			logger.info("AssetPoolRelationBizImpl.query.response:"
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
