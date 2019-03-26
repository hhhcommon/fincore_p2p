/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.role;

import com.zillionfortune.boss.biz.role.dto.RolePowerAddRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

/**
 * ClassName: RolePowerBiz <br/>
 * Function: 运营后台角色分配权限管理biz层接口定义. <br/>
 * Date: 2017年2月20日 下午3:37:58 <br/>
 *
 * @author zhengrunlong@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public interface RolePowerBiz {
	
	/**
	 * add:分配权限方法. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse add(RolePowerAddRequest req);
	
	/**
	 * query:查询角色对应的权限方法. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse query(Integer roleId);
	
	/**
	 * queryPower:查询用户菜单对于的权限. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse queryPower(Integer userId,Integer menuId);
	
}
