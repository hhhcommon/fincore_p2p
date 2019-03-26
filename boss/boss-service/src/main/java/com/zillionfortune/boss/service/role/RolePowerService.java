/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.service.role;

import java.util.List;
import java.util.Map;

import com.zillionfortune.boss.dal.entity.Power;
import com.zillionfortune.boss.dal.entity.RolePower;

/**
 * ClassName: RolePowerService <br/>
 * Function: 运营后台角色分配权限管理RolePowerService层接口定义. <br/>
 * Date: 2017年2月20日 下午3:37:58 <br/>
 *
 * @author zhengrunlong@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public interface RolePowerService {

	/**
	 * insertSelective:分配权限. <br/>
	 *
	 * @param power
	 * @return
	 */
	public int insertSelective(RolePower rolePower);
	
	/**
	 * distributePower:角色分配权限. <br/>
	 *
	 * @param rolePower
	 * @param listPowerIds
	 * @return
	 */
	public int distributePower(RolePower rolePower,List<Integer> listPowerIds);
	
	/**
	 * selectBySelective:查询权限 <br/>
	 *
	 * @param power
	 * @return
	 */
	public List<RolePower> selectBySelective(RolePower rolePower);
	
	/**
	 * selectRolePowerByRoleId:查询角色对应的权限 <br/>
	 *
	 * @param power
	 * @return
	 */
	public List<Power> selectRolePowerByRoleId(Map<String,Object> paraMap);
	
	/**
	 * selectRolePowerByUserId:查询用户菜单对于的权限 <br/>
	 *
	 * @param power
	 * @return
	 */
	public List<Power> selectRolePowerByUserMenuId(Map<String,Object> paraMap);
	
	/**
	 * deleteBySelective:根据条件删除角色分配的权限. <br/>
	 *
	 * @param rolePower
	 * @return
	 */
	public int deleteBySelective(RolePower rolePower);
	
}
