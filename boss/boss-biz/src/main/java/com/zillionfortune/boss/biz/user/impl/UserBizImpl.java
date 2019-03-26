/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.user.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zillionfortune.boss.biz.user.UserBiz;
import com.zillionfortune.boss.biz.user.dto.UserAddRequest;
import com.zillionfortune.boss.biz.user.dto.UserDeleteRequest;
import com.zillionfortune.boss.biz.user.dto.UserModifyPasswordRequest;
import com.zillionfortune.boss.biz.user.dto.UserModifyRequest;
import com.zillionfortune.boss.biz.user.dto.UserQueryListByPageRequest;
import com.zillionfortune.boss.biz.user.dto.UserQueryListRequest;
import com.zillionfortune.boss.biz.user.dto.UserResetPasswordRequest;
import com.zillionfortune.boss.common.dto.BaseWebPageResponse;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.DeleteFlag;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.common.utils.DateUtil;
import com.zillionfortune.boss.common.utils.PageBean;
import com.zillionfortune.boss.common.utils.PassUtil;
import com.zillionfortune.boss.dal.entity.OperationHistory;
import com.zillionfortune.boss.dal.entity.Role;
import com.zillionfortune.boss.dal.entity.User;
import com.zillionfortune.boss.dal.entity.UserConvert;
import com.zillionfortune.boss.service.history.OperationHistoryService;
import com.zillionfortune.boss.service.user.UserRoleService;
import com.zillionfortune.boss.service.user.UserService;

/**
 * ClassName: UserBizImpl <br/>
 * Function: 运营后台_用户管理_biz层接口实现. <br/>
 * Date: 2017年2月21日 下午5:03:48 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Component
public class UserBizImpl implements UserBiz {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${qylc_reset_pwd_value}")
	private String resetPwd;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private OperationHistoryService operationHistoryService;
	
	
	@Override
	public BaseWebResponse insert(UserAddRequest req) {

		log.info("UserBizImpl.add.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp = null;
		
		try {
			
			//检查用户是否存在
			if(userService.selectByUserName(req.getUserName())!=null){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.USER_IS_EXIST.code(),ResultCode.USER_IS_EXIST.desc());
				return resp;
			}
			
			//执行新增
			User user = new User();
			PropertyUtils.copyProperties(user, req);
			user.setName(req.getUserName());
			user.setPassword(PassUtil.getPassword(req.getPassword()));//MD5加密
			if(!userService.insert(user)){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.ADD_USER_FAIL.code(),ResultCode.ADD_USER_FAIL.desc());
				return resp;
			}
			
			user = userService.selectByUserName(req.getUserName());
			
			//日志插入
			OperationHistory history=new OperationHistory();
			history.setUserId(req.getUserId());
			history.setOperationType("addUser");
			history.setContent("用户管理->新增用户");
			history.setCreateBy(req.getCreateBy());
			operationHistoryService.insertOperationHistory(history);
			
			//处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,String> respMap = new HashMap<String,String>();
			respMap.put("userId", user.getId()+"");
			respMap.put("userName", user.getName());
			resp.setData(respMap);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
		}finally {
			log.info("UserBizImpl.add.resp:" + JSON.toJSONString(resp));
		}
		
		return resp;
	
	}


	@Override
	public BaseWebResponse update(UserModifyRequest req) {

		log.info("UserBizImpl.update.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp = null;
		
		try {
			int userId = Integer.parseInt(req.getUserId());
			
			User user = userService.selectByPrimaryKey(userId);
			
			//检查用户是否存在
			if(userService.selectByPrimaryKey(userId)==null){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.USER_ISNOT_EXIST.code(),ResultCode.USER_ISNOT_EXIST.desc());
				return resp;
			}
			
			//执行更新
			PropertyUtils.copyProperties(user, req);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
			user.setId(Integer.parseInt(req.getUserId()));
			user.setName(req.getUserName());
			if(StringUtils.isNotBlank(req.getPassword())){
				user.setPassword(PassUtil.getPassword(req.getPassword()));
			}
			if(!userService.update(user)){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),"用户更新失败，请重新操作");
				return resp;
			}
			
			//日志插入
			OperationHistory history=new OperationHistory();
			history.setUserId(Integer.parseInt(req.getUserId()));
			history.setOperationType("updateUserInfo");
			history.setContent("用户管理->修改用户");
			history.setCreateBy(req.getModifyBy());
			operationHistoryService.insertOperationHistory(history);
			
			//处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,String> respMap = new HashMap<String,String>();
			respMap.put("userId", user.getId()+"");
			respMap.put("userName", user.getName());
			resp.setData(respMap);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
		}finally {
			log.info("UserBizImpl.update.resp:" + JSON.toJSONString(resp));
		}
		
		return resp;
	}
	
	
	@Override
	public BaseWebResponse resetPassword(UserResetPasswordRequest req) {

		log.info("UserBizImpl.resetPassword.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp = null;
		
		try {
			
			int userId = Integer.parseInt(req.getUserId());
			
			//检查用户是否存在
			User user = userService.selectByPrimaryKey(Integer.parseInt(req.getUserId()) );
			if(user==null){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.USER_ISNOT_EXIST.code(),ResultCode.USER_ISNOT_EXIST.desc());
				return resp;
			}
			
			//执行重置登录密码
			user.setId(userId);
			user.setPassword(PassUtil.getPassword(resetPwd));//配置文件里的默认值
			if(!userService.resetPassword(user)){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),"重置登录密码失败，请重新操作");
				return resp;
			}
			
			//日志插入
			userId = Integer.parseInt(req.getUserId());
			OperationHistory history=new OperationHistory();
			history.setUserId(userId);
			history.setOperationType("resetPassword");
			history.setContent("用户管理->重置密码");
			history.setCreateBy(req.getModifyBy());
			operationHistoryService.insertOperationHistory(history);
			
			//处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			Map<String,String> respMap = new HashMap<String,String>();
			respMap.put("userId", user.getId()+"");
			respMap.put("userName", user.getName());
			resp.setData(respMap);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
		}finally {
			log.info("UserBizImpl.resetPassword.resp:" + JSON.toJSONString(resp));
		}
		
		return resp;
	}
	
	@Override
	public BaseWebResponse modifyPassword(UserModifyPasswordRequest req) {

		log.info("UserBizImpl.modifyPassword.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp = null;
		
		try {
			
			int userId = Integer.parseInt(req.getUserId());
			
			//校验原始密码是否正确
			User user = new User();
			user.setId(userId);
			user.setPassword(PassUtil.getPassword(req.getOriginalPwd()));
			user = userService.selectOpUser(user);
			if(user==null){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.USER_ORIGINAL_PWD_ERROR.code(),ResultCode.USER_ORIGINAL_PWD_ERROR.desc());
				return resp;
			}
			
			//执行修改登录密码
			user.setPassword(PassUtil.getPassword(req.getNewPwd()));
			if(!userService.resetPassword(user)){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),"修改登录密码失败，请重新操作");
				return resp;
			}
			
			//日志插入
			userId = Integer.parseInt(req.getUserId());
			OperationHistory history=new OperationHistory();
			history.setUserId(userId);
			history.setOperationType("modifyPassword");
			history.setContent("基本信息->修改密码");
			history.setCreateBy(req.getModifyBy());
			operationHistoryService.insertOperationHistory(history);
			
			//处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,String> respMap = new HashMap<String,String>();
			respMap.put("userId", user.getId()+"");
			respMap.put("userName", user.getName());
			resp.setData(respMap);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
		}finally {
			log.info("UserBizImpl.modifyPassword.resp:" + JSON.toJSONString(resp));
		}
		
		return resp;
	}
	

	@Override
	public BaseWebResponse deleteByPrimaryKey(UserDeleteRequest req) {
		log.info("UserBizImpl.deleteByPrimaryKey.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp = null;
		
		try {
			int userId = Integer.parseInt(req.getUserId());
			
			//检查用户是否存在
			if(userService.selectByPrimaryKey(userId)==null){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.USER_ISNOT_EXIST.code(),ResultCode.USER_ISNOT_EXIST.desc());
				return resp;
			}
			
			//判断用户是否拥有角色
			Map<String,Object> paraMap = new HashMap<String,Object>();
			paraMap.put("userId", Integer.parseInt(req.getUserId()));
			paraMap.put("deleteFlag", DeleteFlag.EXISTS.code());
			List<Role> roleList = userRoleService.selectRoleByUserId(paraMap);
			if(roleList!=null && !roleList.isEmpty() ){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.USER_HAVE_ROLE.code(),ResultCode.USER_HAVE_ROLE.desc());
				return resp;
			}
			
			//执行更新用户信息方法
			User user = new User();
			user.setId(userId);
			user.setDeleteFlag(DeleteFlag.DELETED.code());
			user.setModifyBy(req.getModifyBy());
			if(!userService.update(user)){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),"删除用户（逻辑）失败，请重新操作");
				return resp;
			}
			
			//日志插入
			OperationHistory history=new OperationHistory();
			history.setUserId(userId);
			history.setOperationType("deleteUser");
			history.setContent("用户管理->删除用户（逻辑）");
			history.setCreateBy(req.getModifyBy());
			operationHistoryService.insertOperationHistory(history);
			
			//处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,String> respMap = new HashMap<String,String>();
			respMap.put("userId", userId+"");
			resp.setData(respMap);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
		}finally {
			log.info("UserBizImpl.deleteByPrimaryKey.resp:" + JSON.toJSONString(resp));
		}
		
		return resp;
	}
	
	@Override
	public BaseWebResponse deleteByPrimaryKey(Integer id) {
		log.info("UserBizImpl.deleteByPrimaryKey.req:" + JSON.toJSONString(id));
		
		BaseWebResponse resp = null;
		
		try {
			
			//检查用户是否存在
			if(userService.selectByPrimaryKey(id)==null){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.USER_ISNOT_EXIST.code(),ResultCode.USER_ISNOT_EXIST.desc());
				return resp;
			}
			
			//判断用户是否拥有角色
			Map<String,Object> paraMap = new HashMap<String,Object>();
			paraMap.put("userId", id);
			paraMap.put("deleteFlag", DeleteFlag.EXISTS.code());
			List<Role> roleList = userRoleService.selectRoleByUserId(paraMap);
			if(roleList!=null && !roleList.isEmpty() ){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.USER_HAVE_ROLE.code(),ResultCode.USER_HAVE_ROLE.desc());
				return resp;
			}
			
			//执行删除用户方法
			if(!userService.deleteByPrimaryKey(id)){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),"删除用户（物理）失败，请重新操作");
				return resp;
			}
			
			//处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,String> respMap = new HashMap<String,String>();
			respMap.put("userId", id+"");
			resp.setData(respMap);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
		}finally {
			log.info("UserBizImpl.deleteByPrimaryKey.resp:" + JSON.toJSONString(resp));
		}
		
		return resp;
	}


	@Override
	public BaseWebResponse queryList(UserQueryListRequest req) {
		log.info("UserBizImpl.queryList.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp = null;
		List<User> userList = null;
		
		try {
			
			int userId = Integer.parseInt(req.getUserId());
			
			//执行查询用户列表方法
			User user = new User();
			if(StringUtils.isNotBlank(req.getUserId())){
				user.setId(userId);
			}
			user.setDeleteFlag(0);//未删除
			userList = userService.queryList(user);
			
			//处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			List<UserConvert> proConvertList = new ArrayList<UserConvert>();
			if(userList!=null){
				for(User opUser : userList){
					UserConvert opUserConvert = new UserConvert();
					opUserConvert.setUserId(opUser.getId()+"");
					opUserConvert.setUserName(opUser.getName());
					opUserConvert.setDeleteFlag(opUser.getDeleteFlag()+"");
					opUserConvert.setRealName(opUser.getRealName());
					opUserConvert.setEmail(opUser.getEmail());
					opUserConvert.setMobile(opUser.getMobile());
					opUserConvert.setCreateTime(DateUtil.dateToDateString(opUser.getCreateTime()));
					opUserConvert.setModifyTime(DateUtil.dateToDateString(opUser.getModifyTime()));
					proConvertList.add(opUserConvert);
				}
			}
			
			resp.setData(proConvertList);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			if (e instanceof BusinessException) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
            } else {
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
		}finally {
			log.info("UserBizImpl.queryList.resp:" + JSON.toJSONString(resp));
		}
		
		return resp;
	}


	@Override
	public BaseWebResponse queryListByPage(UserQueryListByPageRequest req) {
		log.info("UserBizImpl.queryListByPage.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp = null;
		List<User> userList = null;
		int totalCount = 0;
		
		try {
			//1、对象封装
			User user = new User();
			PropertyUtils.copyProperties(user, req);
			user.setName(req.getUserName());
			user.setDeleteFlag(0);//未删除
			
			//2、执行查询用户列表方法
			totalCount = userService.queryListByPageCount(user);
			List<UserConvert> proConvertList = new ArrayList<UserConvert>();
			if(totalCount>0){
				userList = userService.queryListByPage(user);
				// 转换结果集
				if(userList!=null){
					for(User opUser : userList){
						UserConvert opUserConvert = new UserConvert();
						opUserConvert.setUserId(opUser.getId()+"");
						opUserConvert.setUserName(opUser.getName());
						opUserConvert.setDeleteFlag(opUser.getDeleteFlag()+"");
						opUserConvert.setRealName(opUser.getRealName());
						opUserConvert.setMobile(opUser.getMobile());
						opUserConvert.setEmail(opUser.getEmail());
						opUserConvert.setCreateTime(DateUtil.dateToDateString(opUser.getCreateTime()));
						opUserConvert.setModifyTime(DateUtil.dateToDateString(opUser.getModifyTime()));
						proConvertList.add(opUserConvert);
					}
				}
			}
			
			//3、处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			Map<String,Object> respMap = new HashMap<String, Object>();
			respMap.put("list", proConvertList);
			respMap.put("totalCount", totalCount);
			respMap.put("pageSize", req.getPageSize());
			respMap.put("pageNo", req.getPageNo());
			if(totalCount > 0 && req.getPageSize() !=null ){
				respMap.put("totalPage", new PageBean().countPageCount(totalCount, req.getPageSize()));
			}
			resp.setData(respMap);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			if (e instanceof BusinessException) {
                resp = new BaseWebPageResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
            } else {
                resp = new BaseWebPageResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
		}finally {
			log.info("UserBizImpl.queryListByPage.resp:" + JSON.toJSONString(resp));
		}
		
		return resp;
	}
	
}
