/*
 * Copyright (c) 2017, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.service.role.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zillionfortune.boss.dal.dao.RoleDao;
import com.zillionfortune.boss.dal.dao.RolePowerDao;
import com.zillionfortune.boss.dal.entity.Role;
import com.zillionfortune.boss.dal.entity.RolePower;
import com.zillionfortune.boss.service.role.RoleService;

/**
 * ClassName: RoleServiceImpl <br/>
 * Function: 运营后台角色管理service层接口实现. <br/>
 * Date: 2017年2月21日 下午4:30:33 <br/>
 *
 * @author pengting
 * @version 
 * @since JDK 1.7
 */
@Component
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao opRoleDao;
	@Autowired
	private RolePowerDao opRolePowerDao;

	/**
	 * @see com.zillionfortune.boss.service.role.RoleService#add(com.zillionfortune.boss.dal.entity.Role)
	 */
	@Override
	public void add(Role opRole) {
		opRoleDao.insertSelective(opRole);
	}

	
	/**
	 * @see com.zillionfortune.boss.service.role.RoleService#update(com.zillionfortune.boss.dal.entity.Role)
	 */
	@Override
	public void update(Role opRole) {
		opRoleDao.updateByPrimaryKeySelective(opRole);
	}

	/**
	 * @see com.zillionfortune.boss.service.role.RoleService#queryById(java.lang.Integer)
	 */
	@Override
	public Role queryById(Integer id) {
		return opRoleDao.selectByPrimaryKey(id);
	}
	
	@Override
	public Role queryOpRole(Role opRole) {
		List<Role> rsList = queryList(opRole);
		if (rsList != null && !rsList.isEmpty()) {
			return rsList.get(0);
		}
		return null;
	}

	/**
	 * @see com.zillionfortune.boss.service.role.RoleService#queryList(com.zillionfortune.boss.dal.entity.Role)
	 */
	@Override
	public List<Role> queryList(Role opRole) {
		return opRoleDao.selectBySelective(opRole);
	}

	@Override
	public int selectBySelectiveCount(Role record) {
		return opRoleDao.selectBySelectiveCount(record);
	}
	
	@Override
	@Transactional
	public void delete(Role opRole) {
		update(opRole);
		
		RolePower opRolePower = new RolePower();
		opRolePower.setRoleId(opRole.getId());
		// 删除角色权限关联表数据
		opRolePowerDao.deleteBySelective(opRolePower);
	}


	@Override
	public List<Role> selectByUserId(Integer userId) {
		return opRoleDao.selectByUserId(userId);
	}
}