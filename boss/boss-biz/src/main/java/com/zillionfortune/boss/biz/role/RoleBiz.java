/*
 * Copyright (c) 2017, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.biz.role;

import com.zillionfortune.boss.biz.role.dto.RoleRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

/**
 * ClassName: RoleBiz <br/>
 * Function: 运营后台角色管理biz层接口定义.. <br/>
 * Date: 2017年2月21日 下午5:24:57 <br/>
 *
 * @author pengting
 * @version 
 * @since JDK 1.7
 */
public interface RoleBiz {

	/**
	 * add:添加角色. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse add(RoleRequest req);
	
	/**
	 * findRoleName:查找角色名称是否已存在. <br/>
	 *
	 * @param roleName
	 * @return
	 */
	public BaseWebResponse findRoleName(String roleName) throws Exception ;
	
	
	/**
	 * delete:删除角色. <br/>
	 *
	 * @param id
	 * @return
	 */
	public BaseWebResponse delete(RoleRequest req);
	
	/**
	 * update:修改角色. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse update(RoleRequest req);
	
	/**
	 * queryByPage:分页查询角色. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse queryByPage(RoleRequest req);
	
	/**
	 * query:查询角色. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse query(Integer roleId);

	public BaseWebResponse queryList(RoleRequest vo);
}