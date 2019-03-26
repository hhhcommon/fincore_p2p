/*
 * Copyright (c) 2017, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.service.user.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zillionfortune.boss.dal.dao.UserRoleDao;
import com.zillionfortune.boss.dal.entity.Role;
import com.zillionfortune.boss.dal.entity.UserRole;
import com.zillionfortune.boss.service.user.UserRoleService;

/**
 * ClassName: UserRoleServiceImpl <br/>
 * Function: 用户角色Service接口实现. <br/>
 * Date: 2017年2月23日 上午9:22:21 <br/>
 *
 * @author pengting
 * @version 
 * @since JDK 1.7
 */
@Component
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleDao opUserRoleDao;
	
	/**
	 * @see com.zillionfortune.boss.service.user.UserRoleService#queryList(com.zillionfortune.boss.dal.entity.UserRole)
	 */
	@Override
	public List<UserRole> queryList(UserRole opUserRole) {
		return opUserRoleDao.selectBySelective(opUserRole);
	}

	/**
	 * 选择性插入用户角色.
	 * @see com.zillionfortune.boss.service.user.UserRoleService#addUserRole(com.zillionfortune.boss.dal.entity.UserRole)
	 */
	@Override
	public int insertSelective(UserRole opUserRole) {
		return opUserRoleDao.insertSelective(opUserRole);
	}

	@Override
	public List<Role> selectRoleByUserId(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		return opUserRoleDao.selectRoleByUserId(paraMap);
	}
	
	/**
	 * 为用户分配角色.
	 * @see com.zillionfortune.boss.service.user.UserRoleService#distributeRoles(com.zillionfortune.boss.dal.entity.UserRole, java.util.List)
	 */
	@Override
	@Transactional
	public void distributeRoles(UserRole userRole, List<Integer> roleIds) {
		// 清空该用户的关联角色
		UserRole opUserRole = new UserRole();
		opUserRole.setUserId(userRole.getUserId());
		opUserRoleDao.deleteBySelective(opUserRole);
		
		// 重新分配角色
		if(CollectionUtils.isNotEmpty(roleIds)){
			for(int i = 0; i < roleIds.size(); i++){
				userRole.setRoleId(roleIds.get(i));
				insertSelective(userRole);
			}
		}
	}

}
