/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.op.user;

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
import com.zillionfortune.boss.service.history.OperationHistoryService;
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;
import com.zillionfortune.boss.web.controller.op.user.check.UserRoleParameterChecker;
import com.zillionfortune.boss.web.controller.op.user.vo.UserRoleAddRequestVo;
import com.zillionfortune.boss.biz.user.UserRoleBiz;
import com.zillionfortune.boss.biz.user.dto.UserRoleAddRequest;

/**
 * ClassName: UserRoleController <br/>
 * Function: 运营平台用户分配角色管理Controller. <br/>
 * Date: 2017年2月27日 上午9:53:54 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/userroleservice")
public class UserRoleController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private UserRoleParameterChecker parameterChecker;
	
	@Resource
	private UserRoleBiz userRoleBiz; 
	
	@Autowired
	private HttpSessionUtils httpSessionUtils;
	
	/**
	 * add:分配角色. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public BaseWebResponse add(@RequestBody UserRoleAddRequestVo vo){
		log.info("UserRoleController.add.req:" + JSON.toJSONString(vo));
		
		UserRoleAddRequest req = null;
		BaseWebResponse resp = null; 
		
		try {
			
			//step1: 请求参数校验
			parameterChecker.checkUserRoleAddRequest(vo);
			
			//step2: 参数对象封装
			req = new UserRoleAddRequest();
			PropertyUtils.copyProperties(req, vo);
			req.setCreateBy(httpSessionUtils.getCuruser().getName());
			
			//3.===调用新增方法
			resp = userRoleBiz.add(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("UserRoleController.add.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	/**
	 * queryRoleByUserId:根据用户Id查用户角色. <br/>
	 *
	 * @param jsonObject
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/query",method=RequestMethod.POST)
	public BaseWebResponse queryRoleByUserId(@RequestBody JSONObject jsonObject){
		log.info("UserRoleController.queryRoleByUserId.req:" + jsonObject);
		
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
			
			//2.===调用查询方法
			resp = userRoleBiz.selectRoleByUserId(userId);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("UserRoleController.queryRoleByUserId.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
}