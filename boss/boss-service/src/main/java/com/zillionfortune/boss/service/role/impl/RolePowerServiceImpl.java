/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.service.role.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zillionfortune.boss.dal.dao.RolePowerDao;
import com.zillionfortune.boss.dal.entity.Power;
import com.zillionfortune.boss.dal.entity.RolePower;
import com.zillionfortune.boss.service.role.RolePowerService;

/**
 * ClassName: RolePowerServiceImpl <br/>
 * Function: 运营后台角色分配权限管理service层接口实现. <br/>
 * Date: 2017年2月21日 下午3:36:24 <br/>
 *
 * @author zhengrunlong@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Service
public class RolePowerServiceImpl implements RolePowerService {

	@Autowired
	private RolePowerDao rolePowerDao;

	/**
	 * 分配权限.
	 * @see com.zillionfortune.boss.service.role.RolePowerService#insertSelective(com.zillionfortune.boss.dal.entity.RolePower)
	 */
	@Override
	public int insertSelective(RolePower power) {
		
		return rolePowerDao.insertSelective(power);
	}
	
	/**
	 * 角色分配权限.
	 * @see com.zillionfortune.boss.service.role.RolePowerService#distributePower(com.zillionfortune.boss.dal.entity.RolePower, java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int distributePower(RolePower rolePower,List<Integer> listPowerIds){
		
		//1.===先删除角色对应的所有权限记录
		RolePower rp = new RolePower();
		rp.setRoleId(rolePower.getRoleId());
		rolePowerDao.deleteBySelective(rp);
		
		//2.===插入角色对应的权限
		if(CollectionUtils.isNotEmpty(listPowerIds)){
			for(int i = 0; i < listPowerIds.size(); i++){
				
				rolePower.setId(null);
				rolePower.setPowerId(listPowerIds.get(i));
				insertSelective(rolePower);
			}
		}
		
		return CollectionUtils.isNotEmpty(listPowerIds)?listPowerIds.size():0;
		
	}
	
	/**
	 * 查询权限.
	 * @see com.zillionfortune.boss.service.role.RolePowerService#selectBySelective(com.zillionfortune.boss.dal.entity.RolePower)
	 */
	@Override
	public List<RolePower> selectBySelective(RolePower power) {
		
		return rolePowerDao.selectBySelective(power);
	}

	/**
	 * 查询角色对应的权限.
	 * @see com.zillionfortune.boss.service.role.RolePowerService#selectRolePowerByRoleId(java.lang.Integer)
	 */
	@Override
	public List<Power> selectRolePowerByRoleId(Map<String,Object> paraMap) {
		
		return rolePowerDao.selectRolePowerByRoleId(paraMap);
	}

	/**
	 * 查询用户菜单对于的权限.
	 * @see com.zillionfortune.boss.service.role.RolePowerService#selectRolePowerByUserMenuId(java.util.Map)
	 */
	@Override
	public List<Power> selectRolePowerByUserMenuId(Map<String, Object> paraMap) {
		
		return rolePowerDao.selectRolePowerByUserMenuId(paraMap);
	}

	/**
	 * 根据条件删除角色分配的权限.
	 * @see com.zillionfortune.boss.service.role.RolePowerService#deleteBySelective(com.zillionfortune.boss.dal.entity.RolePower)
	 */
	@Override
	public int deleteBySelective(RolePower rolePower) {
		
		return rolePowerDao.deleteBySelective(rolePower);
	}

}