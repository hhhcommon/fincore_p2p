package com.zillionfortune.boss.biz.ams.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.zb.fincore.ams.common.dto.BaseResponse;
import com.zb.fincore.ams.facade.dto.req.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zb.fincore.ams.common.dto.PageQueryResponse;
import com.zb.fincore.ams.common.dto.QueryResponse;
import com.zb.fincore.ams.facade.AssetServiceFacade;
import com.zb.fincore.ams.facade.dto.resp.BatchCreateAssetResponse;
import com.zb.fincore.ams.facade.dto.resp.CreateAssetResponse;
import com.zb.fincore.ams.facade.model.AssetModel;
import com.zillionfortune.boss.biz.ams.AssetBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
@Component
public class AssetBizImpl implements AssetBiz {

	private Logger logger = LoggerFactory.getLogger(AssetBizImpl.class);

	/** 资产服务 */
	@Autowired
	private AssetServiceFacade assetServiceFacade;

	@Override
	public BaseWebResponse createAsset(CreateAssetRequest req) {
		logger.info("AssetBizImpl.add.request:" + JSON.toJSONString(req));
		CreateAssetResponse response = null;
		BaseWebResponse resp = null;
		try {
			response =assetServiceFacade.createAsset(req);
			logger.info("AssetBizImpl.add.response:"
					+ JSON.toJSONString(response));
		} catch (Exception e) {
			resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
		}
		
		if(ResultCode.SUCCESS.code().equals(response.getRespCode())){
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("addition", response.getAddition());
			respMap.put("id", response.getId());
			respMap.put("assetCode", response.getAssetCode());
			
			resp.setData(respMap);
		}else{
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
		}
		
		return resp;

	}
	
	@Override
	public BaseWebResponse batchCreateAsset(BatchCreateAssetRequest req) {
		logger.info("AssetBizImpl.batchadd.request:" + JSON.toJSONString(req));
		BatchCreateAssetResponse response = null;
		BaseWebResponse resp = null;
		try {
			response =assetServiceFacade.batchCreateAsset(req);
			logger.info("AssetBizImpl.batchadd.response:"
					+ JSON.toJSONString(response));
		} catch (Exception e) {
			resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
		}
		
		if(ResultCode.SUCCESS.code().equals(response.getRespCode())){
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("addition", response.getAddition());
			respMap.put("totalCount", response.getTotalCount());
			respMap.put("errorCount", response.getErrorCount());
			respMap.put("errorMap", response.getErrorModelList());
			
			resp.setData(respMap);
		}else{
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("addition", response.getAddition());
			respMap.put("totalCount", response.getTotalCount());
			respMap.put("errorCount", response.getErrorCount());
			respMap.put("errorMap", response.getErrorModelList());
			resp.setData(respMap);
		}
		
		return resp;

	}
	
	@Override
	public BaseWebResponse queryAssetList(QueryAssetListForManageRequest req) {
		logger.info("AssetBizImpl.queryList.request:" + JSON.toJSONString(req));
		PageQueryResponse<AssetModel> response = null;
		BaseWebResponse resp = null;
		try {
			response =assetServiceFacade.queryAssetListForManage(req);
			logger.info("AssetBizImpl.queryList.response:"
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
	
	@Override
	public BaseWebResponse queryAssetListForApproval(QueryAssetListForManageRequest req) {
		logger.info("AssetBizImpl.queryAssetListForApproval.request:" + JSON.toJSONString(req));
		PageQueryResponse<AssetModel> response = null;
		BaseWebResponse resp = null;
		try {
			response =assetServiceFacade.queryAssetListForApproval(req);
			logger.info("AssetBizImpl.queryAssetListForApproval.response:"
					+ JSON.toJSONString(response));
		} catch (Exception e) {
			resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
		}
		
		if(ResultCode.SUCCESS.code().equals(response.getRespCode())||ResultCode.NO_DATA.code().equals(response.getRespCode())){
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
	
	@Override
	public BaseWebResponse queryAsset(QueryAssetRequest req) {
		logger.info("AssetBizImpl.query.request:" + JSON.toJSONString(req));
		QueryResponse<AssetModel> response = null;
		BaseWebResponse resp = null;
		try {
			response =assetServiceFacade.queryAsset(req);
			logger.info("AssetBizImpl.query.response:"
					+ JSON.toJSONString(response));
		} catch (Exception e) {
			resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
		}
		
		if(ResultCode.SUCCESS.code().equals(response.getRespCode())){
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("addition", response.getAddition());
			respMap.put("data", response.getData()!=null?response.getData():new Object());
			resp.setData(respMap);
		}else{
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
		}
		
		return resp;

	}

    @Override
    public BaseWebResponse updateAssetPublishInfo(String assetCode, String publishInfo) {
        BaseResponse response = null;
        BaseWebResponse resp = null;
        try {
            UpdateAssetPublishInfoRequest req = new UpdateAssetPublishInfoRequest();
            req.setAssetCode(assetCode);
            req.setPublishInfo(publishInfo);
            response = assetServiceFacade.updateAssetPublishInfo(req);
            logger.info("AssetBizImpl.updateAssetPublishInfo.response: {}", JSON.toJSONString(response));
        } catch (Exception e) {
            return new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
        }
        if (ResultCode.SUCCESS.code().equals(response.getRespCode())) {
            resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
        } else {
            resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response.getRespMsg());
        }
        return resp;
    }

}
