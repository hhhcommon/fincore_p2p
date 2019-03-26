/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.user.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zillionfortune.boss.biz.user.UserRoleBiz;
import com.zillionfortune.boss.biz.user.dto.UserRoleAddRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.DeleteFlag;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.dal.entity.OperationHistory;
import com.zillionfortune.boss.dal.entity.Role;
import com.zillionfortune.boss.dal.entity.UserRole;
import com.zillionfortune.boss.service.history.OperationHistoryService;
import com.zillionfortune.boss.service.user.UserRoleService;

/**
 * ClassName: UserRoleBizImpl <br/>
 * Function: 运营平台用户分配角色管理biz层接口实现. <br/>
 * Date: 2017年2月27日 上午10:18:14 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Component
public class UserRoleBizImpl implements UserRoleBiz {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private OperationHistoryService operationHistoryService;
	
	/**
	 * 分配角色.
	 * @see com.zillionfortune.boss.biz.user.UserRoleBiz#add(com.zillionfortune.boss.biz.user.dto.UserRoleAddRequest)
	 */
	@Override
	public BaseWebResponse add(UserRoleAddRequest req) {
		log.info("UserRoleBizImpl.add.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp;
		
		try {
			//step1: 创建用户角色对象
			UserRole opUserRole = new UserRole();
			opUserRole.setUserId(req.getUserId());
			opUserRole.setCreateTime(new Date());
			opUserRole.setCreateBy(req.getCreateBy());
			
			//step2: 分配角色
			userRoleService.distributeRoles(opUserRole, req.getRoleIds());
			
			//step3: 日志插入
			OperationHistory history=new OperationHistory();
			history.setUserId(req.getUserId());
			history.setOperationType("userRoleAdd");
			history.setCreateBy(req.getCreateBy());
			history.setContent("用户管理->用户分配角色");
			
			operationHistoryService.insertOperationHistory(history);
			
			//step4: 处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("userId", req.getUserId());
			respMap.put("roleIds", req.getRoleIds());
			
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
			
		}
		
		log.info("UserRoleBizImpl.add.resp:" + JSON.toJSONString(resp));
		
		return resp;
	}

	@Override
	public BaseWebResponse selectRoleByUserId(Integer userId) {
		log.info("UserRoleBizImpl.selectRoleByUserId.req:" + JSON.toJSONString(userId));
		
		BaseWebResponse resp = null;
		
		try {
			Map<String,Object> paraMap = new HashMap<String,Object>();
			paraMap.put("userId", userId);
			paraMap.put("deleteFlag", DeleteFlag.EXISTS.code());
			
			List<Role> roleList = userRoleService.selectRoleByUserId(paraMap);
			
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("userId", userId);
			respMap.put("list", roleList);
			
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
			
		}
		
		log.info("UserRoleBizImpl.selectRoleByUserId.resp:" + JSON.toJSONString(resp));
		
		return resp;
	}

}
