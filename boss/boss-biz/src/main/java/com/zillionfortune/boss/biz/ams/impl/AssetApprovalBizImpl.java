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
import com.zb.fincore.ams.facade.AssetApprovalServiceFacade;
import com.zb.fincore.ams.facade.dto.req.ApproveAssetRequest;
import com.zb.fincore.ams.facade.dto.req.QueryAssetApprovalListRequest;
import com.zb.fincore.ams.facade.model.AssetApprovalModel;
import com.zillionfortune.boss.biz.ams.AssetApprovalBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
@Component
public class AssetApprovalBizImpl implements AssetApprovalBiz {

	private Logger logger = LoggerFactory.getLogger(AssetApprovalBizImpl.class);

	/** 资产审核服务 */
	@Autowired
	private AssetApprovalServiceFacade approvalServiceFacade;

	@Override
	public BaseWebResponse approveAsset(ApproveAssetRequest req) {
		logger.info("AssetApprovalImpl.approveAsset.request:" + JSON.toJSONString(req));
		BaseResponse response = null;
		BaseWebResponse resp = null;
		try {
			response =approvalServiceFacade.approveAsset(req);
			logger.info("AssetApprovalImpl.approveAsset.response:"
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
	public BaseWebResponse queryAssetApprovalList(QueryAssetApprovalListRequest req) {
		logger.info("AssetApprovalImpl.queryAssetApprovalList.request:" + JSON.toJSONString(req));
		PageQueryResponse<AssetApprovalModel> response = null;
		BaseWebResponse resp = null;
		try {
			response =approvalServiceFacade.queryAssetApprovalList(req);
			logger.info("AssetApprovalImpl.queryAssetApprovalList.response:"
					+ JSON.toJSONString(response));
		} catch (Exception e) {
			resp =new BaseWebResponse(RespCode.FAIL.code(),ResultCode.FAIL.code(),ResultCode.FAIL.desc());
		}
		
		if(ResultCode.SUCCESS.code().equals(response.getRespCode())){
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("addition", response.getAddition());
			respMap.put("dataList", response.getDataList()!=null?response.getDataList():new ArrayList()!=null?response.getDataList()!=null?response.getDataList():new ArrayList():new ArrayList());
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
