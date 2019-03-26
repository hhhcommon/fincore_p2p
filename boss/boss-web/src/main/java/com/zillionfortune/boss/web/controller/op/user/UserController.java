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
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.service.history.OperationHistoryService;
import com.zillionfortune.boss.service.redis.UserRedisService;
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;
import com.zillionfortune.boss.web.controller.op.user.check.UserParameterChecker;
import com.zillionfortune.boss.web.controller.op.user.vo.UserAddRequestVo;
import com.zillionfortune.boss.web.controller.op.user.vo.UserDeleteRequestVo;
import com.zillionfortune.boss.web.controller.op.user.vo.UserModifyPasswordRequestVo;
import com.zillionfortune.boss.web.controller.op.user.vo.UserModifyRequestVo;
import com.zillionfortune.boss.web.controller.op.user.vo.UserQueryListByPageRequestVo;
import com.zillionfortune.boss.web.controller.op.user.vo.UserQueryListRequestVo;
import com.zillionfortune.boss.web.controller.op.user.vo.UserResetPasswordRequestVo;
import com.zillionfortune.boss.biz.user.UserBiz;
import com.zillionfortune.boss.biz.user.dto.UserAddRequest;
import com.zillionfortune.boss.biz.user.dto.UserDeleteRequest;
import com.zillionfortune.boss.biz.user.dto.UserModifyPasswordRequest;
import com.zillionfortune.boss.biz.user.dto.UserModifyRequest;
import com.zillionfortune.boss.biz.user.dto.UserQueryListByPageRequest;
import com.zillionfortune.boss.biz.user.dto.UserQueryListRequest;
import com.zillionfortune.boss.biz.user.dto.UserResetPasswordRequest;

/**
 * ClassName: UserController <br/>
 * Function: 运营后台_用户管理_Controller. <br/>
 * Date: 2017年2月21日 下午4:43:29 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/userservice")
public class UserController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private UserParameterChecker parameterChecker;
	
	@Resource
	private UserBiz userBiz; 
	
	@Autowired
	private OperationHistoryService operationHistoryService;
	
	@Autowired
	private UserRedisService userRedisService;
	
	@Autowired
	private HttpSessionUtils httpSessionUtils;
	
	
	/**
	 * 用户管理_新增
	 *
	 * @param vo
	 * 		UserAddRequestVo
	 * @return
	 * 		BaseWebResponse
	 */
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public BaseWebResponse add(@RequestBody UserAddRequestVo vo){
		log.info("UserController.add.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		UserAddRequest req = null;
		
		try {
			
			//参数校验
			parameterChecker.checkUserAddRequest(vo);
		
			//参数对象封装
			req = new UserAddRequest();
			PropertyUtils.copyProperties(req, vo);
			req.setCreateBy(httpSessionUtils.getCuruser().getName());
			req.setUserId(httpSessionUtils.getCuruser().getId());
			
			//调用新增用户方法
			resp = userBiz.insert(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("UserController.add.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	/**
	 * modify:用户管理_修改. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public BaseWebResponse modify(@RequestBody UserModifyRequestVo vo){
		log.info("UserController.modify.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		UserModifyRequest req = null;
		
		try {
			
			//参数校验
			parameterChecker.checkUserModifyRequest(vo);
		
			//参数对象封装
			req = new UserModifyRequest();
			PropertyUtils.copyProperties(req, vo);
			req.setModifyBy(httpSessionUtils.getCuruser().getName());
			
			//用更新用户方法
			resp = userBiz.update(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("UserController.modify.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	
	/**
	 * resetPassword:重置登录密码. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/resetpassword",method=RequestMethod.POST)
	public BaseWebResponse resetPassword(@RequestBody UserResetPasswordRequestVo vo){
		log.info("UserController.resetPassword.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		UserResetPasswordRequest req = null;
		
		try {
			
			//参数校验
			parameterChecker.checkUserResetPasswordRequest(vo);
		
			//参数对象封装
			req = new UserResetPasswordRequest();
			PropertyUtils.copyProperties(req, vo);
			req.setModifyBy(httpSessionUtils.getCuruser().getName());
			
			//调用重置登录密码方法
			resp = userBiz.resetPassword(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("UserController.resetPassword.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	/**
	 * modifypassword:修改登录密码. <br/>
	 *
	 * @param vo
	 * 		UserModifyPasswordRequestVo
	 * @return
	 * 		BaseWebResponse
	 */
	@ResponseBody
	@RequestMapping(value="/modifypassword",method=RequestMethod.POST)
	public BaseWebResponse modifyPassword(@RequestBody UserModifyPasswordRequestVo vo){
		log.info("UserController.modifyPassword.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		UserModifyPasswordRequest req = null;
		
		try {
			//参数校验
			parameterChecker.checkUserModifyPasswordRequest(vo);
			
			//参数对象封装
			req = new UserModifyPasswordRequest();
			PropertyUtils.copyProperties(req, vo);
			req.setModifyBy(httpSessionUtils.getCuruser().getName());
			
			//调用重置登录密码方法
			resp = userBiz.modifyPassword(req);
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("UserController.modifyPassword.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	/**
	 * delete:用户管理_删除_逻辑. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public BaseWebResponse delete(@RequestBody UserDeleteRequestVo vo){
		log.info("UserController.delete.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		UserDeleteRequest req = null;
		
		try {
			
			//参数校验
			parameterChecker.checkUserDeleteRequest(vo);
			
			//参数对象封装
			req = new UserDeleteRequest();
			PropertyUtils.copyProperties(req, vo);
			req.setModifyBy(httpSessionUtils.getCuruser().getName());
			
			//调用删除用户方法
			resp = userBiz.deleteByPrimaryKey(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("UserController.delete.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	/**
	 * querylist:用户列表（非分页）. <br/>
	 *
	 * @param vo
	 * 		UserQueryListRequestVo
	 * @return
	 * 		BaseWebResponse
	 */
	@ResponseBody
	@RequestMapping(value="/query",method=RequestMethod.POST)
	public BaseWebResponse querylist(@RequestBody UserQueryListRequestVo vo){
		log.info("UserController.querylist.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		UserQueryListRequest req = null;
		
		try {
			
			//参数校验
			parameterChecker.checkUserQueryListRequest(vo);
			
			//参数对象封装
			req = new UserQueryListRequest();
			PropertyUtils.copyProperties(req, vo);
			
			//调用用户列表方法
			resp = userBiz.queryList(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("UserController.querylist.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	/**
	 * querylistByPage:用户列表（有分页）. <br/>
	 *
	 * @param vo
	 * 		UserQueryListRequestVo
	 * @return
	 * 		BaseWebResponse
	 */
	@ResponseBody
	@RequestMapping(value="/querybypage",method=RequestMethod.POST)
	public BaseWebResponse querylistByPage(@RequestBody UserQueryListByPageRequestVo vo){
		log.info("UserController.querybypage.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null; 
		
		try {
			//赋值前台参数
			UserQueryListByPageRequest req = new UserQueryListByPageRequest();
			PropertyUtils.copyProperties(req, vo);
			
			//参数校验
			parameterChecker.checkUserQueryListByPageRequest(vo);
			
			//调用用户列表方法
			if(req.getPageSize() != null && req.getPageNo() != null){
		    	req.setPageStart((req.getPageNo()-1)*req.getPageSize());
		    }
			resp = userBiz.queryListByPage(req);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("UserController.querybypage.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	}
	
	
}
