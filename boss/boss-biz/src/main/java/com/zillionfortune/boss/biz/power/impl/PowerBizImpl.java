/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.power.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zillionfortune.boss.biz.power.PowerBiz;
import com.zillionfortune.boss.biz.power.dto.PowerAddRequest;
import com.zillionfortune.boss.biz.power.dto.PowerModifyRequest;
import com.zillionfortune.boss.biz.power.dto.PowerQueryByPageRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.DeleteFlag;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.common.utils.PageBean;
import com.zillionfortune.boss.dal.entity.OperationHistory;
import com.zillionfortune.boss.dal.entity.Power;
import com.zillionfortune.boss.dal.entity.RolePower;
import com.zillionfortune.boss.service.history.OperationHistoryService;
import com.zillionfortune.boss.service.power.PowerService;
import com.zillionfortune.boss.service.role.RolePowerService;

/**
 * ClassName: PowerBizImpl <br/>
 * Function:运营后台权限管理biz层接口实现. <br/>
 * Date: 2017年2月20日 下午3:41:56 <br/>
 *
 * @author zhengrunlong@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Component
public class PowerBizImpl implements PowerBiz {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PowerService powerService;
	
	@Autowired
	private RolePowerService rolePowerService;
	
	@Autowired
	private OperationHistoryService operationHistoryService;
	
	/**
	 * 新增权限方法.
	 * @see com.zillionfortune.boss.biz.power.PowerBiz#add(com.zillionfortune.boss.biz.power.dto.PowerAddRequest)
	 */
	@Override
	public BaseWebResponse add(PowerAddRequest req) {

		log.info("PowerBizImpl.add.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp = null;
		
		try {
			
			//1.===创建权限对象
			Power opPower = new Power();
			PropertyUtils.copyProperties(opPower, req);
			opPower.setCreateTime(new Date());
			opPower.setDeleteFlag(DeleteFlag.EXISTS.code());
			
			//2.===执行新增操作
			Power power = new Power();
			power.setMenuId(req.getMenuId());
			power.setOperationCode(req.getOperationCode());
			power.setDeleteFlag(DeleteFlag.EXISTS.code());
			int count = powerService.selectBySelectiveCount(power);
			if(count > 0){
				
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.POWER_IS_EXIST.code(),ResultCode.POWER_IS_EXIST.desc());
				return resp;
			}
			
			powerService.insertSelective(opPower);
			
			//3.===日志插入
			OperationHistory history=new OperationHistory();
			history.setUserId(req.getUserId());
			history.setOperationType("add");
			history.setCreateBy(req.getCreateBy());
			history.setContent("权限管理->新增权限");
			
			operationHistoryService.insertOperationHistory(history);
			
			//4.===处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("powerId", opPower.getId());
			respMap.put("name", opPower.getName());
			
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
			
		} finally {
			log.info("PowerBizImpl.add.resp:" + JSON.toJSONString(resp));
		}
		
		return resp;
	
	}

	/**
	 * 修改权限方法.
	 * @see com.zillionfortune.boss.biz.power.PowerBiz#modify(com.zillionfortune.boss.biz.power.dto.PowerModifyRequest)
	 */
	@Override
	public BaseWebResponse modify(PowerModifyRequest req) {

		log.info("PowerBizImpl.modify.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp = null;
		
		try {
			
			//1.===创建权限对象
			Power opPower = new Power();
			PropertyUtils.copyProperties(opPower, req);
			opPower.setId(req.getPowerId());
			opPower.setModifyTime(new Date());
			
			//2.===执行修改操作
			int count = powerService.updateByPrimaryKeySelective(opPower);
			if(count == 0){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.CAN_NOT_QUERY_POWER.code(),
						ResultCode.CAN_NOT_QUERY_POWER.desc());
				return resp;
			}
			
			//3.===日志插入
			OperationHistory history=new OperationHistory();
			history.setUserId(req.getUserId());
			history.setOperationType("modify");
			history.setCreateBy(req.getModifyBy());
			history.setContent("权限管理->修改权限");
			
			operationHistoryService.insertOperationHistory(history);
			
			//4.===处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("powerId", opPower.getId());
			respMap.put("name", opPower.getName());
			
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
			
		} finally {
			log.info("PowerBizImpl.modify.resp:" + JSON.toJSONString(resp));
		}
		
		return resp;
	
	}
	
	/**
	 * 删除权限方法.
	 * @see com.zillionfortune.boss.biz.power.PowerBiz#delete(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public BaseWebResponse delete(Integer powerId, String deleteBy, Integer userId) {

		log.info("PowerBizImpl.delete.req:" + "{powerId:"+powerId+",deleteBy:"+deleteBy+"}");
		
		BaseWebResponse resp = null;
		
		try {
			
			//1.===创建权限对象
			Power opPower = new Power();
			opPower.setId(powerId);
			opPower.setModifyBy(deleteBy);
			opPower.setModifyTime(new Date());
			opPower.setDeleteFlag(DeleteFlag.DELETED.code());
			
			//2.===执行修改操作
			RolePower rolePower = new RolePower();
			rolePower.setPowerId(powerId);
			List<RolePower> rolwPowerList = rolePowerService.selectBySelective(rolePower);
			if(CollectionUtils.isNotEmpty(rolwPowerList)){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.POWER_IS_USED.code()
						,ResultCode.POWER_IS_USED.desc());
				return resp;
			}
			
			int count = powerService.updateByPrimaryKeySelective(opPower);
			if(count == 0){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.CAN_NOT_QUERY_POWER.code(),
						ResultCode.CAN_NOT_QUERY_POWER.desc());
				return resp;
			}
			
			//3.===日志插入
			OperationHistory history=new OperationHistory();
			history.setUserId(userId);
			history.setOperationType("delete");
			history.setCreateBy(deleteBy);
			history.setContent("权限管理->删除权限");
			
			operationHistoryService.insertOperationHistory(history);
			
			//4.===处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("powerId", opPower.getId());
			
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
			
		} finally {
			log.info("PowerBizImpl.delete.resp:" + JSON.toJSONString(resp));
		}
	
		return resp;
	
	}
	
	/**
	 * 根据主键查询权限.
	 * @see com.zillionfortune.boss.biz.power.PowerBiz#query(java.lang.Integer)
	 */
	@Override
	public BaseWebResponse query(Integer powerId) {

		log.info("PowerBizImpl.query.req:" + "{powerId:"+powerId+"}");
		
		BaseWebResponse resp = null;
	
		try {
			
			//1.===执行查询操作
			Power opPower = powerService.selectByPrimaryKey(powerId);
			
			//2.===处理反馈结果
			if(opPower == null){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.CAN_NOT_QUERY_POWER.code(),
						ResultCode.CAN_NOT_QUERY_POWER.desc());
				
				return resp;
			}
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			resp.setData(opPower);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			if (e instanceof BusinessException) {
				
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.FAIL.code(),
                		e.getMessage());
                
            } else {
            	
                resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
            }
			
		} finally {
			log.info("PowerBizImpl.query.resp:" + JSON.toJSONString(resp));
		}

		return resp;
	
	}
	
	/**
	 * 查询权限(分页).
	 * @see com.zillionfortune.boss.biz.power.PowerBiz#queryByPage(com.zillionfortune.boss.biz.power.dto.PowerQueryByPageRequest)
	 */
	@Override
	public BaseWebResponse queryByPage(PowerQueryByPageRequest req) {

		log.info("PowerBizImpl.queryByPage.req:" + JSON.toJSONString(req));
		
		BaseWebResponse resp;
	
		try {
			
			//1.===创建权限对象
			Power opPower = new Power();
			PropertyUtils.copyProperties(opPower, req);
			opPower.setDeleteFlag(DeleteFlag.EXISTS.code());
			
			//2.===执行查询操作
			int totalCount = powerService.selectBySelectiveCount(opPower);
			
			List<Power> powerList = null;
			if(totalCount > 0){
				powerList = powerService.selectBySelective(opPower);
			}
			
			//3.===处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("totalCount", totalCount);
			respMap.put("pageSize", req.getPageSize());
			respMap.put("pageNo", req.getPageNo());
			
			if(totalCount > 0 && req.getPageSize() !=null ){
				respMap.put("totalPage", new PageBean().countPageCount(totalCount, req.getPageSize()));
			}
			
			/*if(totalCount >0 && CollectionUtils.isNotEmpty(powerList)){
				respMap.put("list",powerList);
			}*/
			respMap.put("list",powerList);
			
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
		
		log.info("PowerBizImpl.queryByPage.resp:" + JSON.toJSONString(resp));
		
		return resp;
	
	}

}
