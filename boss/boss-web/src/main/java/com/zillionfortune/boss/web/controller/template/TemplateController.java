/*
 * Copyright (c) 2016, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.web.controller.template;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zillionfortune.boss.biz.template.TemplateBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.dal.entity.Template;
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;
import com.zillionfortune.boss.web.controller.template.vo.TemplateVo;

/**
 * ClassName: FileController <br/>
 * Function: 模板. <br/>
 * Date: 2017年5月16日 下午4:30:27 <br/>
 * 
 * @author wangzinan_tech@zillionfortune.com
 * @version
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/templateservice")
public class TemplateController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TemplateBiz templateBiz;

	@Autowired
	private HttpSessionUtils httpSessionUtils;
	
	/**
	 * 查询模板内容. <br/>
	 * 
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveorupdate", method = RequestMethod.POST)
	public BaseWebResponse saveOrUpdate(@RequestBody TemplateVo vo) {
		log.info("TemplateController.saveOrUpdate.req:" + JSON.toJSONString(vo));
		BaseWebResponse resp = null;
		try {
			if (vo == null) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), "模板不能为空");
				return resp;
			}
			Template template=new Template();
			
			if(vo.getTemplateId()==null){
				template.setCreateTime(new Date());
			}else{
				template.setId(vo.getTemplateId());
				template.setModifyTime(new Date());
			}
			template.setTemplateContent(vo.getTemplateContent());
			template.setTemplateType(vo.getTemplateType());
			template.setTemplateName(vo.getTemplateName());
			resp = templateBiz.saveOrUpdate(template);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("TemplateController.saveOrUpdate.resp:" + JSON.toJSONString(resp));
		}

		return resp;
	}

	/**
	 * 查询模板内容. <br/>
	 * 
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/querydetail", method = RequestMethod.POST)
	public BaseWebResponse queryDetail(@RequestBody TemplateVo vo) {
		log.info("TemplateController.querydetail.req:" + JSON.toJSONString(vo));
		BaseWebResponse resp = null;
		try {
			if (vo == null ||vo.getTemplateId()==null) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), "模板编号或者是模板Id不能为空");
				return resp;
			}
			resp = templateBiz.queryTemplateDetail(vo.getTemplateNum(),vo.getTemplateId());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("TemplateController.querydetail.resp:" + JSON.toJSONString(resp));
		}

		return resp;
	}
	
	/**
	 * 查询模板内容. <br/>
	 * 
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/querylist", method = RequestMethod.POST)
	public BaseWebResponse queryList(@RequestBody TemplateVo vo) {
		log.info("TemplateController.queryList.req:" + JSON.toJSONString(vo));
		BaseWebResponse resp = null;
		try {
			if(vo==null||vo.getTemplateType()==null){
				vo=new TemplateVo();
				vo.setTemplateType(1);
			}
			resp = templateBiz.queryTemplateList(vo.getTemplateType());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("TemplateController.queryList.resp:" + JSON.toJSONString(resp));
		}

		return resp;
	}

}
