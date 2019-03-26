/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.role.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zillionfortune.boss.biz.role.RolePowerBiz;
import com.zillionfortune.boss.biz.role.dto.RolePowerAddRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.DeleteFlag;
import com.zillionfortune.boss.common.enums.IsValid;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.dal.entity.OperationHistory;
import com.zillionfortune.boss.dal.entity.Power;
import com.zillionfortune.boss.dal.entity.RolePower;
import com.zillionfortune.boss.service.history.OperationHistoryService;
import com.zillionfortune.boss.service.role.RolePowerService;

/**
 * ClassName: RolePowerBizImpl <br/>
 * Function:运营后台权限管理biz层接口实现. <br/>
 * Date: 2017年2月20日 下午3:41:56 <br/>
 *
 * @author zhengrunlong@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Component
public class RolePowerBizImpl implements RolePowerBiz {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RolePowerService rolePowerService;
	
	@Autowired
	private OperationHistoryService operationHistoryService;
	
	@Override
	public BaseWebResponse add(RolePowerAddRequest req) {

		log.info("RolePowerBizImpl.add.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp;
		
		try {
			
			//1.===创建角色权限对象
			RolePower opRolePower = new RolePower();
			opRolePower.setRoleId(req.getRoleId());
			opRolePower.setCreateTime(new Date());
			opRolePower.setCreateBy(req.getCreateBy());
			
			//2.===执行角色分配权限操作
			rolePowerService.distributePower(opRolePower, req.getPowerIds());
			
			//3.===日志插入
			OperationHistory history=new OperationHistory();
			history.setUserId(req.getUserId());
			history.setOperationType("rolePowerAdd");
			history.setCreateBy(req.getCreateBy());
			history.setContent("角色管理->分配权限");
			
			operationHistoryService.insertOperationHistory(history);
			
			//4.===处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("roleId", req.getRoleId());
			respMap.put("powerIds", req.getPowerIds());
			
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
		
		log.info("RolePowerBizImpl.add.resp:" + JSON.toJSONString(resp));
		
		return resp;
	
	}

	/**
	 * 查询角色对应的权限方法.
	 * @see com.zillionfortune.boss.biz.role.RolePowerBiz#query(java.lang.Integer)
	 */
	@Override
	public BaseWebResponse query(Integer roleId) {

		log.info("RolePowerBizImpl.query.req:" + "{roleId:"+roleId+"}");
		
		BaseWebResponse resp;
		
		try {
			
			//1.===根据角色查询分配的权限
			Map<String,Object> paraMap = new HashMap<String,Object>();
			paraMap.put("roleId", roleId);
			paraMap.put("deleteFlag", DeleteFlag.EXISTS.code());
			List<Power> powerList = rolePowerService.selectRolePowerByRoleId(paraMap);
			
			//2.===处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("roleId", roleId);
			respMap.put("list", powerList);
			
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
		
		log.info("RolePowerBizImpl.query.resp:" + JSON.toJSONString(resp));
		
		return resp;
	
	}
	
	/**
	 * 查询用户菜单对于的权限.
	 * @see com.zillionfortune.boss.biz.role.RolePowerBiz#query(java.lang.Integer)
	 */
	@Override
	public BaseWebResponse queryPower(Integer userId,Integer menuId) {

		log.info("RolePowerBizImpl.queryPower.req:" + "{userId:"+userId+",menuId:"+menuId+"}");
		
		BaseWebResponse resp;
		
		try {
			
			//1.===根据角色查询分配的权限
			Map<String,Object> paraMap = new HashMap<String,Object>();
			paraMap.put("userId", userId);
			paraMap.put("menuId", menuId);
			paraMap.put("deleteFlag", DeleteFlag.EXISTS.code());
			List<Power> powerList = rolePowerService.selectRolePowerByUserMenuId(paraMap);
			
			//2.===处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("userId", userId);
			respMap.put("menuId", menuId);
			respMap.put("list", powerList);
			
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
		
		log.info("RolePowerBizImpl.queryPower.resp:" + JSON.toJSONString(resp));
		
		return resp;
	
	}

}
