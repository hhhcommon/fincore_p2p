/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.op.role;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;
import com.zillionfortune.boss.web.controller.op.power.check.PowerParameterChecker;
import com.zillionfortune.boss.web.controller.op.role.vo.RolePowerAddRequestVo;
import com.zillionfortune.boss.biz.role.RolePowerBiz;
import com.zillionfortune.boss.biz.role.dto.RolePowerAddRequest;

/**
 * ClassName: RolePowerController <br/>
 * Function: 运营台角色分配权限管理Controller. <br/>
 * Date: 2017年2月20日 下午2:14:35 <br/>
 *
 * @author zhengrunlong@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/rolepowerservice")
public class RolePowerController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private PowerParameterChecker parameterChecker;
	
	@Resource
	private RolePowerBiz rolePowerBiz; 
	
	@Resource
	private HttpSessionUtils httpSessionUtils;
	
	/**
	 * add:分配权限. <br/>
	 *
	 * @param 
	 * @return 
	 * @throws 
	 */
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public BaseWebResponse add(@RequestBody RolePowerAddRequestVo vo){
		log.info("RolePowerController.add.req:" + JSON.toJSONString(vo));
		
		RolePowerAddRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===请求参数校验
			if (vo == null) {
	           throw new BusinessException("请求对象不能为空");
			}
		 
			if (vo.getRoleId() == null) {
				throw new BusinessException("角色Id不能为空");
			}
		
			if (CollectionUtils.isEmpty(vo.getPowerIds())) {
				throw new BusinessException("权限Id列表不能为空");
			}
			
			//2.===参数对象封装
			req = new RolePowerAddRequest();
			PropertyUtils.copyProperties(req, vo);
			req.setCreateBy(httpSessionUtils.getCuruser().getName());
			req.setUserId(httpSessionUtils.getCuruser().getId());
			
			//3.===调用新增方法
			resp = rolePowerBiz.add(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("RolePowerController.add.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	/**
	 * query:查询角色对应的权限接. <br/>
	 *
	 * @param 
	 * @return 
	 * @throws 
	 */
	@ResponseBody
	@RequestMapping(value="/query",method=RequestMethod.POST)
	public BaseWebResponse query(@RequestBody JSONObject jsonObject){
		log.info("RolePowerController.query.req:" + jsonObject);
		
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===请求参数校验
			if (jsonObject == null) {
	           throw new BusinessException("请求对象不能为空");
			}
		 
			Integer roleId = jsonObject.getInteger("roleId");
			if (roleId == null) {
				throw new BusinessException("角色Id不能为空");
			}
	
			//2.===调用查询方法
			resp = rolePowerBiz.query(roleId);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("RolePowerController.query.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	/**
	 * queryPower:查询用户对于的权限. <br/>
	 *
	 * @param 
	 * @return 
	 * @throws 
	 */
	@ResponseBody
	@RequestMapping(value="/querypower",method=RequestMethod.POST)
	public BaseWebResponse queryPower(@RequestBody JSONObject jsonObject){
		log.info("RolePowerController.queryPower.req:" + jsonObject);
		
		BaseWebResponse resp = null; 
		
		try {
			
			//1.===请求参数校验
			if (jsonObject == null) {
	           throw new BusinessException("请求对象不能为空");
			}
		 
			Integer userId = jsonObject.getInteger("userId");
			if (userId == null) {
				throw new BusinessException("用户Id不能为空");
			}
			
			Integer menuId = jsonObject.getInteger("menuId");
			if (menuId == null) {
				throw new BusinessException("菜单Id不能为空");
			}
	
			//2.===调用查询方法
			resp = rolePowerBiz.queryPower(userId, menuId);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("RolePowerController.queryPower.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
}