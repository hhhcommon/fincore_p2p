package com.zillionfortune.boss.biz.template.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zillionfortune.boss.biz.template.TemplateBiz;
import com.zillionfortune.boss.biz.template.model.SignInfo;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.dal.entity.Template;
import com.zillionfortune.boss.service.template.TemplateService;
@Component
public class TemplateBizImpl implements TemplateBiz {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TemplateService templateService;
	
	@Override
	public BaseWebResponse saveOrUpdate(Template template) {

		BaseWebResponse resp = null;
		try {
			if(template.getId()!=null){
				templateService.update(template);
			}else{
				templateService.add(template);
			}
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resp = new BaseWebResponse(RespCode.FAIL.code(),"新增或更新模板异常");
		} finally {
			log.info("TemplateBizImpl.saveOrUpdate.resp:" +JSONObject.toJSONString(resp) );
		}
		
		return resp;
	
	}
	
	
	@Override
	public BaseWebResponse queryTemplateDetail(Integer templateNum,Long templateId) {
		log.info("TemplateBizImpl.query.req: templateId=" +templateId );
		BaseWebResponse resp = null;
		try {
			
			Map<String, Object> paramMap=new HashMap<String, Object>();
			if(templateId!=null){
				paramMap.put("templateId", templateId);
			}
			Template template = templateService.selectByPrimaryKey(templateId);
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			if(template!=null&&template.getTemplateType().intValue()==2){
				String templateContent=template.getTemplateContent();
				List<SignInfo> signInfos = JSONObject.parseArray(templateContent, SignInfo.class);
				if(templateNum!=null){
					List<SignInfo> signInfoList = new ArrayList<SignInfo>();
					for(int i=0;i<signInfos.size();i++){
						SignInfo signInfo = signInfos.get(i);
						if(signInfo.getCurrentUserSignNo()==templateNum.intValue()){
							signInfoList.add(signInfo);
							respMap.put("dataList",signInfoList);
						}
					}
				}else{
					respMap.put("dataList",signInfos);
				}
			}
			else if(template!=null){
				String templateContent=template.getTemplateContent();
				respMap.put("templateContent",templateContent);
			}
			resp.setData(respMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			resp = new BaseWebResponse(RespCode.FAIL.code(),"查询模板异常");
		} finally {
			log.info("TemplateBizImpl.query.resp:" +JSONObject.toJSONString(resp) );
		}
		
		return resp;
	}
	@Override
	public BaseWebResponse queryTemplateList(Integer templateType) {

		BaseWebResponse resp = null;
		try {
			Map<String, Object> paramMap=new HashMap<String, Object>();
			if(templateType!=null){
				paramMap.put("templateType", templateType);
			}
			List<Template> list = templateService.selectBySelective(paramMap);
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			if(list!=null&&list.size()>0){
				resp.setData(list);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resp = new BaseWebResponse(RespCode.FAIL.code(),"查询模板异常");
		} finally {
			log.info("TemplateBizImpl.query.resp:" +JSONObject.toJSONString(resp) );
		}
		
		return resp;
	
	}
	
}
