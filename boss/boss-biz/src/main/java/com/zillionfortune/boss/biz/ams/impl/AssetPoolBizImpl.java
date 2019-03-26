package com.zillionfortune.boss.biz.ams.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.zb.fincore.ams.facade.dto.req.QueryPoolLeftAssetRequest;
import com.zb.fincore.ams.facade.model.PoolLeftAssetModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zb.fincore.ams.common.dto.PageQueryResponse;
import com.zb.fincore.ams.common.dto.QueryResponse;
import com.zb.fincore.ams.facade.PoolServiceFacade;
import com.zb.fincore.ams.facade.dto.req.CreatePoolRequest;
import com.zb.fincore.ams.facade.dto.req.QueryPoolListRequest;
import com.zb.fincore.ams.facade.dto.req.QueryPoolRequest;
import com.zb.fincore.ams.facade.dto.resp.CreatePoolResponse;
import com.zb.fincore.ams.facade.model.PoolModel;
import com.zillionfortune.boss.biz.ams.AssetPoolBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
@Component
public class AssetPoolBizImpl implements AssetPoolBiz {

	private Logger logger = LoggerFactory.getLogger(AssetPoolBizImpl.class);

	/** 资产池服务 */
	@Autowired
	private PoolServiceFacade poolServiceFacade;

	@Override
	public BaseWebResponse createAssetPool(CreatePoolRequest req) {
		logger.info("AssetPoolBizImpl.add.request:" + JSON.toJSONString(req));
		CreatePoolResponse response = null;
		BaseWebResponse resp = null;
		try {
			response =poolServiceFacade.createPool(req);
			logger.info("AssetPoolBizImpl.add.response:"
					+ JSON.toJSONString(response));
		} catch (Exception e) {
			resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
		}
		
		if(ResultCode.SUCCESS.code().equals(response.getRespCode())){
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("assetPoolId", response.getId());
			respMap.put("poolCode", response.getPoolCode());
			respMap.put("addition", response.getAddition());
			
			resp.setData(respMap);
		}else{
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),response.getRespMsg());
		}
		
		return resp;

	}
	
	@Override
	public BaseWebResponse queryAssetPoolList(QueryPoolListRequest req) {
		logger.info("AssetPoolBizImpl.queryList.request:" + JSON.toJSONString(req));
		PageQueryResponse<PoolModel> response = null;
		BaseWebResponse resp = null;
		try {
			response =poolServiceFacade.queryPoolList(req);
			logger.info("AssetPoolBizImpl.queryList.response:"
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
	public BaseWebResponse queryAssetPool(QueryPoolRequest req) {
		logger.info("AssetPoolBizImpl.query.request:" + JSON.toJSONString(req));
		QueryResponse<PoolModel> response = null;
		BaseWebResponse resp = null;
		try {
			response =poolServiceFacade.queryPool(req);
			logger.info("AssetPoolBizImpl.query.response:"
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
    public BaseWebResponse queryLeftAssetAmountList(QueryPoolLeftAssetRequest req) {
        logger.info("AssetPoolBizImpl.queryLeftAssetAmountList.request:" + JSON.toJSONString(req));
        PageQueryResponse<PoolLeftAssetModel> response = null;
        BaseWebResponse resp = null;
        try {
            response =poolServiceFacade.queryLeftAssetAmountList(req);
            logger.info("AssetPoolBizImpl.queryLeftAssetAmountList.response:"
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
