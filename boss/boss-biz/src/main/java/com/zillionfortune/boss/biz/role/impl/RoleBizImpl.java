/*
 * Copyright (c) 2017, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.biz.role.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zillionfortune.boss.biz.role.RoleBiz;
import com.zillionfortune.boss.biz.role.dto.RoleRequest;
import com.zillionfortune.boss.biz.role.dto.RoleResponse;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.DeleteFlag;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.utils.BeanUtilsWrapper;
import com.zillionfortune.boss.common.utils.PageBean;
import com.zillionfortune.boss.dal.entity.OperationHistory;
import com.zillionfortune.boss.dal.entity.Role;
import com.zillionfortune.boss.dal.entity.UserRole;
import com.zillionfortune.boss.service.history.OperationHistoryService;
import com.zillionfortune.boss.service.role.RoleService;
import com.zillionfortune.boss.service.user.UserRoleService;

/**
 * ClassName: RoleBizImpl <br/>
 * Function: 运营后台角色管理biz层接口实现. <br/>
 * Date: 2017年2月21日 下午5:40:00 <br/>
 * 
 * @author pengting
 * @version
 * @since JDK 1.7
 */
@Component
public class RoleBizImpl implements RoleBiz {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private OperationHistoryService operationHistoryService;

	/**
	 * @see com.zillionfortune.boss.biz.role.RoleBiz#add(com.zillionfortune.boss.biz.role.dto.RoleRequest)
	 */
	@Override
	public BaseWebResponse add(RoleRequest req) {
		log.info("add.req:" + JSON.toJSONString(req));

		BaseWebResponse resp = null;

		try {
			// 判断角色名是否已存在
			Role checkOpRole = new Role();
			checkOpRole.setName(req.getName());
			checkOpRole.setDeleteFlag(DeleteFlag.EXISTS.code());

			checkOpRole = roleService.queryOpRole(checkOpRole);
			if (checkOpRole != null) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.ROLE_NAME_IS_EXIST.code(), ResultCode.ROLE_NAME_IS_EXIST.desc());
				return resp;
			}

			// 创建新增角色对象
			Role opRole = new Role();
			BeanUtilsWrapper.copyProperties(opRole, req);

			roleService.add(opRole);
			// 处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());

			Map<String, String> respMap = new HashMap<String, String>();

			resp.setData(respMap);

			// 日志插入
			OperationHistory history = new OperationHistory();
			history.setUserId(req.getUserId());
			history.setOperationType("add");
			history.setCreateBy(req.getCreateBy());
			history.setContent("角色管理->添加角色");

			operationHistoryService.insertOperationHistory(history);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
		} finally {
			log.info("add.resp:" + JSON.toJSONString(resp));
		}

		return resp;
	}

	/**
	 * @throws Exception
	 * @see com.zillionfortune.boss.biz.role.RoleBiz#checkRoleName(java.lang.String)
	 */
	@Override
	public BaseWebResponse findRoleName(String roleName) throws Exception {
		log.info("findRoleName.req: roleName=" + roleName);

		boolean existsFlag = false;
		BaseWebResponse resp;
		try {
			// 1.创建参数对象
			Role opRole = new Role();
			opRole.setName(roleName);
			opRole.setDeleteFlag(DeleteFlag.EXISTS.code());
			// 2. 执行查询
			opRole = roleService.queryOpRole(opRole);

			if (existsFlag) {

			}
			// 角色名已存在
			if (opRole != null) {
				existsFlag = true;
			}

			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Boolean> dataMap = new HashMap<String, Boolean>();
			dataMap.put("existsFlag", existsFlag);
			resp.setData(dataMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}

		log.info("findRoleName.resp: existsFlag=" + existsFlag);

		return resp;
	}

	/**
	 * @see com.zillionfortune.boss.biz.role.RoleBiz#delete(java.lang.Integer)
	 */
	@Override
	public BaseWebResponse delete(RoleRequest req) {
		log.info("delete.req: " + JSON.toJSONString(req));

		BaseWebResponse resp = null;

		try {
			// 判断该数据是否有效
			Role checkOpRole = roleService.queryById(req.getId());
			if (checkOpRole == null) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.RECORD_IS_NOT_EXIST.code(), ResultCode.RECORD_IS_NOT_EXIST.desc());
				return resp;
			}

			// 校验是否可删除
			UserRole opUserRole = new UserRole();
			opUserRole.setRoleId(req.getId());
			List<UserRole> opUserRoleList = userRoleService.queryList(opUserRole);
			// 如果该角色下存在用户关联 则不可删除
			if (opUserRoleList != null && !opUserRoleList.isEmpty()) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.ROLE_IS_USED.code(), ResultCode.ROLE_IS_USED.desc());
				return resp;
			}

			// .创建参数对象
			Role opRole = new Role();
			opRole.setId(req.getId());
			opRole.setDeleteFlag(DeleteFlag.DELETED.code());
			opRole.setModifyBy(req.getModifyBy());
			// .执行删除
			roleService.delete(opRole);
			// .===处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());

			Map<String, String> respMap = new HashMap<String, String>();

			resp.setData(respMap);

			// 日志插入
			OperationHistory history = new OperationHistory();
			history.setUserId(req.getUserId());
			history.setOperationType("delete");
			history.setCreateBy(req.getModifyBy());
			history.setContent("角色管理->删除角色");

			operationHistoryService.insertOperationHistory(history);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
		} finally {
			log.info("add.resp:" + JSON.toJSONString(resp));
		}

		return resp;
	}

	/**
	 * @see com.zillionfortune.boss.biz.role.RoleBiz#update(com.zillionfortune.boss.biz.role.dto.RoleRequest)
	 */
	@Override
	public BaseWebResponse update(RoleRequest req) {
		log.info("update.req:" + JSON.toJSONString(req));

		BaseWebResponse resp;

		try {
			// 判断该数据是否有效
			Role checkOpRole = roleService.queryById(req.getId());
			if (checkOpRole == null) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.RECORD_IS_NOT_EXIST.code(), ResultCode.RECORD_IS_NOT_EXIST.desc());
				return resp;
			}

			// 判断角色名是否已存在
			checkOpRole = new Role();
			checkOpRole.setName(req.getName());
			checkOpRole.setDeleteFlag(DeleteFlag.EXISTS.code());

			checkOpRole = roleService.queryOpRole(checkOpRole);
			if (checkOpRole != null && checkOpRole.getId() != req.getId()) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.ROLE_NAME_IS_EXIST.code(), ResultCode.ROLE_NAME_IS_EXIST.desc());
				return resp;
			}

			// 创建角色对象
			Role opRole = new Role();
			BeanUtilsWrapper.copyProperties(opRole, req);

			roleService.update(opRole);
			// 处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());

			Map<String, String> respMap = new HashMap<String, String>();

			resp.setData(respMap);

			// 日志插入
			OperationHistory history = new OperationHistory();
			history.setUserId(req.getUserId());
			history.setOperationType("modify");
			history.setCreateBy(req.getModifyBy());
			history.setContent("角色管理->修改角色");

			operationHistoryService.insertOperationHistory(history);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
		}

		log.info("update.resp:" + JSON.toJSONString(resp));

		return resp;
	}

	@Override
	public BaseWebResponse queryByPage(RoleRequest req) {
		log.info("queryByPage.req:" + JSON.toJSONString(req));

		BaseWebResponse resp;

		try {
			// 1.===创建角色对象
			Role opRole = new Role();
			BeanUtilsWrapper.copyProperties(opRole, req);
			opRole.setDeleteFlag(DeleteFlag.EXISTS.code());
			// 2. 执行查询
			int totalCount = roleService.selectBySelectiveCount(opRole);
			List<RoleResponse> rsList = new ArrayList<RoleResponse>();
			if (totalCount > 0) {
				List<Role> opRoleList = roleService.queryList(opRole);
				// 转换结果集
				if (opRoleList != null) {
					for (Role role : opRoleList) {
						RoleResponse roleResponse = new RoleResponse();
						BeanUtilsWrapper.copyProperties(roleResponse, role);
						roleResponse.setRoleId(role.getId());
						rsList.add(roleResponse);
					}
				}

			}
			// 3.===处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());

			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("list", rsList);
			// 设置分页信息
			respMap.put("totalCount", totalCount);
			respMap.put("pageSize", req.getPageSize());
			respMap.put("pageNo", req.getPageNo());

			if (totalCount > 0 && req.getPageSize() != null) {
				respMap.put("totalPage", new PageBean().countPageCount(totalCount, req.getPageSize()));
			}

			resp.setData(respMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
		}

		log.info("queryByPage.resp:" + JSON.toJSONString(resp));

		return resp;
	}

	@Override
	public BaseWebResponse query(Integer roleId) {
		log.info("query.req: roleId=" + roleId);

		BaseWebResponse resp;

		try {
			Role opRole = roleService.queryById(roleId);
			// 处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());

			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("roleId", opRole.getId());
			respMap.put("remark", opRole.getRemark());
			respMap.put("name", opRole.getName());
			respMap.put("signLevel", opRole.getSignLevel());
			respMap.put("createTime", opRole.getCreateTime());
			resp.setData(respMap);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
		}

		log.info("query.resp:" + JSON.toJSONString(resp));

		return resp;
	}

	@Override
	public BaseWebResponse queryList(RoleRequest req) {
		log.info("RoleBizImpl.queryList.req:" + JSON.toJSONString(req));
		BaseWebResponse resp;
		try {
			// 1.===创建角色对象
			Role opRole = new Role();
			BeanUtilsWrapper.copyProperties(opRole, req);
			opRole.setDeleteFlag(DeleteFlag.EXISTS.code());
			// 2. 执行查询
			List<Role> opRoleList = roleService.queryList(opRole);
			// 3.===处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("dataList", opRoleList);
			resp.setData(respMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
		}
		log.info("RoleBizImpl.queryList.resp:" + JSON.toJSONString(resp));

		return resp;
	}
}
