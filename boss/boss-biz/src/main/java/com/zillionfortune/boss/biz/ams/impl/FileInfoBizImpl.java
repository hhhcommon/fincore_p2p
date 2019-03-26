package com.zillionfortune.boss.biz.ams.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zb.fincore.ams.common.dto.BaseResponse;
import com.zb.fincore.ams.common.dto.PageQueryResponse;
import com.zb.fincore.ams.facade.FileInfoFacade;
import com.zb.fincore.ams.facade.dto.req.FileInfoRequest;
import com.zb.fincore.ams.facade.model.FileInfoModel;
import com.zb.fincore.ams.facade.model.SignInfoObj;
import com.zillionfortune.boss.biz.ams.FileInfoBiz;
import com.zillionfortune.boss.biz.file.FileBiz;
import com.zillionfortune.boss.biz.template.model.SignInfo;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.dal.entity.Template;
import com.zillionfortune.boss.service.file.FileService;
import com.zillionfortune.boss.service.template.TemplateService;

@Component
public class FileInfoBizImpl implements FileInfoBiz {

	private Logger logger = LoggerFactory.getLogger(FileInfoBizImpl.class);

	/** 创建及修改准入申请服务 */
	@Autowired
	private FileInfoFacade fileInfoFacade;
	@Autowired
	private FileService fileService;

	@Autowired
	private TemplateService templateService;
	@Autowired
	private FileBiz fileBiz;

	private final static String TEMPLATE_NAME = "订单融资合同模板";
	
	private final static String POSITION_CODE_1="p0001";
	
	private final static String POSITION_CODE_2="p0002";
	
	private final static String POSITION_CODE_3="p0003";
	
	private final static String POSITION_CODE_4="p0004";

	@Override
	public BaseWebResponse saveFileInfo(FileInfoRequest req) {
		logger.info("FileInfoBizImpl.saveFileInfo.request:" + JSON.toJSONString(req));
		BaseResponse response = null;
		BaseWebResponse resp = null;
		try {
			response = fileInfoFacade.saveFileInfo(req);
			logger.info("FileInfoBizImpl.saveFileInfo.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			logger.error("FileInfoBizImpl.saveFileInfo.exception", e);
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (response != null && ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("addition", response.getAddition());
			respMap.put("fileName", req.getFileName());

			resp.setData(respMap);
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response == null ? ResultCode.FAIL.desc() : response.getRespMsg());
		}
		logger.info("FileInfoBizImpl.saveFileInfo.response:" + JSON.toJSONString(resp));
		return resp;
	}

	@Override
	public BaseWebResponse queryFileInfoList(FileInfoRequest req) {
		logger.info("FileInfoBizImpl.queryFileInfoList.request:" + JSON.toJSONString(req));
		PageQueryResponse<FileInfoModel> response = null;
		BaseWebResponse resp = null;
		try {
			response = fileInfoFacade.queryFileInfoList(req);
			logger.info("FileInfoBizImpl.queryFileInfoList.response:" + JSON.toJSONString(response));
			if (response == null) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
				return resp;
			}
		} catch (Exception e) {
			logger.error("FileInfoBizImpl.queryFileInfoList.exception", e);
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), response == null ? ResultCode.FAIL.desc() : response.getRespMsg());
			return resp;
		} finally {
			logger.info("FileInfoBizImpl.queryFileInfoList.response:" + JSON.toJSONString(response));
		}

		if (ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("addition", response.getAddition());
			respMap.put("dataList", response.getDataList() != null ? response.getDataList() : new ArrayList() != null ? response.getDataList() != null ? response.getDataList()
					: new ArrayList() : new ArrayList());
			respMap.put("pageNo", response.getPageNo());
			respMap.put("pageSize", response.getPageSize());
			respMap.put("totalCount", response.getTotalCount());
			respMap.put("totalPage", response.getTotalPage());
			resp.setData(respMap);
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response == null ? ResultCode.FAIL.desc() : response.getRespMsg());
		}
		logger.info("FileInfoBizImpl.queryFileInfoList.response:" + JSON.toJSONString(resp));
		return resp;
	}

	@Override
	public BaseWebResponse delFileInfoById(Long id) {
		logger.info("FileInfoBizImpl.delFileInfoById.request:id=" + id);
		BaseResponse response = null;
		BaseWebResponse resp = null;
		try {
			response = fileInfoFacade.delFileInfoById(id);
			logger.info("FileInfoBizImpl.delFileInfoById.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (response != null && ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			resp.setData(response.getAddition());
			try {
				fileBiz.delete(response.getAddition());
			} catch (Exception e) {
				logger.error("删除oss文件失败，文件名：" + response.getAddition(), e);
			}

		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response == null ? ResultCode.FAIL.desc() : response.getRespMsg());
		}
		logger.info("FileInfoBizImpl.delFileInfoById.response:" + JSON.toJSONString(resp));
		return resp;
	}

	@Override
	public BaseWebResponse startSigned(FileInfoRequest req) {
		BaseResponse response = null;
		BaseWebResponse resp = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("templateName", TEMPLATE_NAME);
			List<Template> templateList = templateService.selectBySelective(paramMap);
			List<SignInfoObj> partyAList = new ArrayList<SignInfoObj>();
			List<SignInfoObj> partyBList = new ArrayList<SignInfoObj>();
			if (templateList != null && templateList.size() > 0) {
				String templateContent = templateList.get(0).getTemplateContent();
				List<SignInfo> signInfos = JSONObject.parseArray(templateContent, SignInfo.class);
				for (int i = 0; i < signInfos.size(); i++) {
					SignInfo signInfo = signInfos.get(i);
					if (signInfo.getRoleId().intValue() == Long.valueOf(req.getPartyAList().get(0).getRoleId()).intValue()) {
						SignInfoObj partyA = new SignInfoObj();
						partyA.setPositionCode(POSITION_CODE_1);
						partyA.setStamperCode(signInfo.getTemplateCode1());
						partyA.setRoleId(String.valueOf(signInfo.getRoleId()));
						partyAList.add(partyA);
						SignInfoObj partyB = new SignInfoObj();
						partyB.setPositionCode(POSITION_CODE_2);
						partyB.setStamperCode(signInfo.getTemplateCode2());
						partyB.setRoleId(String.valueOf(signInfo.getRoleId()));
						partyAList.add(partyB);
					} else if (signInfo.getRoleId().intValue() == Long.valueOf(req.getPartyBList().get(0).getRoleId()).intValue()) {
						SignInfoObj partyA = new SignInfoObj();
						partyA.setPositionCode(POSITION_CODE_3);
						partyA.setStamperCode(signInfo.getTemplateCode1());
						partyA.setRoleId(String.valueOf(signInfo.getRoleId()));
						partyBList.add(partyA);
						SignInfoObj partyB = new SignInfoObj();
						partyB.setPositionCode(POSITION_CODE_4);
						partyB.setStamperCode(signInfo.getTemplateCode2());
						partyB.setRoleId(String.valueOf(signInfo.getRoleId()));
						partyBList.add(partyB);
						
					}
				}
			}

			req.setPartyAList(partyAList);
			req.setPartyBList(partyBList);
			logger.info("FileInfoBizImpl.startSigned.request:" + JSON.toJSONString(req));
			response = fileInfoFacade.startSigned(req);
			logger.info("FileInfoBizImpl.startSigned.response:" + JSON.toJSONString(response));
		} catch (Exception e) {
			logger.error("FileInfoBizImpl.startSigned.exception", e);
			resp = new BaseWebResponse(RespCode.FAIL.code(), ResultCode.FAIL.code(), ResultCode.FAIL.desc());
		}

		if (response != null && ResultCode.SUCCESS.code().equals(response.getRespCode())) {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			resp.setData(response.getAddition());
		} else {
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response == null ? ResultCode.FAIL.desc() : response.getRespMsg());
		}
		logger.info("FileInfoBizImpl.startSigned.response:" + JSON.toJSONString(resp));
		return resp;
	}

}
