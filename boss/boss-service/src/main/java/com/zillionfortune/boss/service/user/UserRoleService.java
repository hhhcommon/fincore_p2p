/*
 * Copyright (c) 2017, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.service.user;

import java.util.List;
import java.util.Map;

import com.zillionfortune.boss.dal.entity.Role;
import com.zillionfortune.boss.dal.entity.UserRole;

/**
 * ClassName: UserRoleService <br/>
 * Function: 用户角色Service接口. <br/>
 * Date: 2017年2月23日 上午9:20:54 <br/>
 *
 * @author pengting
 * @version 
 * @since JDK 1.7
 */
public interface UserRoleService {

	/**
	 * queryList:查询用户角色列表. <br/>
	 *
	 * @param opUserRole
	 * @return
	 */
	public List<UserRole> queryList(UserRole opUserRole);
	
	/**
	 * insertSelective:选择性插入用户角色. <br/>
	 *
	 * @param opUserRole
	 * @return
	 */
	public int insertSelective(UserRole opUserRole);
	
	/**
     * 根据用户Id查用户角色. <br/>
     *
     * @param paraMap
     * @return
     */
	public List<Role> selectRoleByUserId(Map<String,Object> paraMap);
	
	
	/**
	 * distributeRoles:为用户分配角色. <br/>
	 *
	 * @param userRole
	 * @param roleIds
	 */
	public void distributeRoles(UserRole userRole,List<Integer> roleIds);
}
