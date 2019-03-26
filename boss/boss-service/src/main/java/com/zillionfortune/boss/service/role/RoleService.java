/*
 * Copyright (c) 2017, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.service.role;

import java.util.List;

import com.zillionfortune.boss.dal.entity.Role;

/**
 * ClassName: RoleService <br/>
 * Function: 运营后台角色管理service层接口定义. <br/>
 * Date: 2017年2月21日 下午4:01:12 <br/>
 *
 * @author pengting
 * @version 
 * @since JDK 1.7
 */
public interface RoleService {

	/**
	 * add:新增角色. <br/>
	 *
	 * @param opRole
	 */
	public void add(Role opRole);
	
	/**
	 * update:修改角色信息. <br/>
	 *
	 * @param opRole
	 */
	public void update(Role opRole);
	
	/**
	 * queryById:根据Id查询角色. <br/>
	 *
	 * @param id
	 */
	public Role queryById(Integer id);
	
	/**
	 * queryOpRole: 查询角色信息. <br/>
	 *
	 * @param opRole
	 * @return
	 */
	public Role queryOpRole(Role opRole);
	
	/**
	 * queryList:查询角色列表. <br/>
	 *
	 * @param opRole
	 * @return
	 */
	public List<Role> queryList(Role opRole);
	
	public int selectBySelectiveCount(Role record);
	
	/**
	 * delete:删除角色. <br/>
	 *
	 * @param opRole
	 */
	public void delete(Role opRole);
	
	/**
	 * selectByUserId:查询用户所拥有的角色. <br/>
	 *
	 * @param userId
	 * @return
	 */
	List<Role> selectByUserId(Integer userId);
}
