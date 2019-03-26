/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.op.power;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.common.utils.PageBean;
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;
import com.zillionfortune.boss.web.controller.op.power.check.PowerParameterChecker;
import com.zillionfortune.boss.web.controller.op.power.vo.PowerAddRequestVo;
import com.zillionfortune.boss.web.controller.op.power.vo.PowerModifyRequestVo;
import com.zillionfortune.boss.web.controller.op.power.vo.PowerQueryByPageRequestVo;
import com.zillionfortune.boss.biz.power.PowerBiz;
import com.zillionfortune.boss.biz.power.dto.PowerAddRequest;
import com.zillionfortune.boss.biz.power.dto.PowerModifyRequest;
import com.zillionfortune.boss.biz.power.dto.PowerQueryByPageRequest;

/**
 * ClassName: PowerController <br/>
 * Function: 运营台权限管理Controller. <br/>
 * Date: 2017年2月20日 下午2:14:35 <br/>
 *
 * @author zhengrunlong@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/powerservice")
public class PowerController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private PowerParameterChecker parameterChecker;
	
	@Resource
	private PowerBiz powerBiz; 
	
	@Autowired
	private HttpSessionUtils httpSessionUtils;
	
	/**
	 * add:新增权限. <br/>
	 *
	 * @param 
	 * @return 
	 * @throws 
	 */
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public BaseWebResponse add(@RequestBody PowerAddRequestVo vo){
		log.info("PowerController.add.req:" + JSON.toJSONString(vo));
		
		PowerAddRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			parameterChecker.checkPowerAddRequest(vo);
		
			//2.===参数对象封装
			req = new PowerAddRequest();
			PropertyUtils.copyProperties(req, vo);
			req.setCreateBy(httpSessionUtils.getCuruser().getName());
			req.setUserId(httpSessionUtils.getCuruser().getId());
			
			//3.===调用新增方法
			resp = powerBiz.add(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("PowerController.add.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	/**
	 * modify:修改权限. <br/>
	 *
	 * @param 
	 * @return 
	 * @throws 
	 */
	@ResponseBody
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public BaseWebResponse modify(@RequestBody PowerModifyRequestVo vo){
		log.info("PowerController.modify.req:" + JSON.toJSONString(vo));
		
		PowerModifyRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			parameterChecker.checkPowerModifyRequest(vo);
		
			//2.===参数对象封装
			req = new PowerModifyRequest();
			PropertyUtils.copyProperties(req, vo);
			req.setModifyBy(httpSessionUtils.getCuruser().getName());
			req.setUserId(httpSessionUtils.getCuruser().getId());
			
			//3.===调用修改方法
			resp = powerBiz.modify(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("PowerController.modify.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	/**
	 * delete:删除权限. <br/>
	 *
	 * @param 
	 * @return 
	 * @throws 
	 */
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public BaseWebResponse delete(@RequestBody JSONObject jsonObject){
		log.info("PowerController.delete.req:" + jsonObject);
		
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			if(jsonObject == null) {
				throw new BusinessException("请求参数对象不能为空");
			}
			
			Integer powerId = jsonObject.getInteger("powerId");
			if(powerId == null){
				throw new BusinessException("权限Id不能为空");
			}
			
			String deleteBy = "";
			Integer userId = null;
			userId = httpSessionUtils.getCuruser().getId();
			deleteBy = httpSessionUtils.getCuruser().getName();
			//2.===调用删除方法
			resp = powerBiz.delete(powerId, deleteBy, userId);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("PowerController.delete.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	/**
	 * query:根据主键查询权限. <br/>
	 *
	 * @param 
	 * @return 
	 * @throws 
	 */
	@ResponseBody
	@RequestMapping(value="/query",method=RequestMethod.POST)
	public BaseWebResponse query(@RequestBody JSONObject jsonObject){
		log.info("PowerController.query.req:" + jsonObject);
		
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===参数校验
			if(jsonObject == null) {
				throw new BusinessException("请求参数对象不能为空");
			}
			
			Integer powerId = jsonObject.getInteger("powerId");
			if(powerId == null){
				throw new BusinessException("权限Id不能为空");
			}

			//2.===调用查询方法
			resp = powerBiz.query(powerId);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("PowerController.query.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	/**
	 * queryByPage:查询权限（分页）. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/querybypage",method=RequestMethod.POST)
	public BaseWebResponse queryByPage(@RequestBody PowerQueryByPageRequestVo vo){
		
		log.info("PowerController.queryByPage.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		PowerQueryByPageRequest req = null;
		
		try {
			
			//1.===参数校验
			parameterChecker.checkPowerQueryByPageRequest(vo);
			
			//2.===参数对象封装
			req = new PowerQueryByPageRequest();
			PropertyUtils.copyProperties(req, vo);
		
			//3.===调用查询方法
		    if(req.getPageSize() != null && req.getPageNo() != null){
		    	PageBean pageBean = new PageBean(vo.getPageNo(), vo.getPageSize());
		    	req.setPageStart(pageBean.getPageStart());
		    }
		  
			resp = powerBiz.queryByPage(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} 
		
		log.info("PowerController.queryByPage.resp:" + JSON.toJSONString(resp));
		
		return resp;
	}
	
}
