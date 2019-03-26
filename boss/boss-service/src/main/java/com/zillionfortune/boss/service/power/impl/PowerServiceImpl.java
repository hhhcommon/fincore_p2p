/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.service.power.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zillionfortune.boss.dal.dao.PowerDao;
import com.zillionfortune.boss.dal.entity.Power;
import com.zillionfortune.boss.service.power.PowerService;

/**
 * ClassName: PowerServiceImpl <br/>
 * Function: 运营后台权限管理service层接口实现. <br/>
 * Date: 2017年2月21日 下午3:36:24 <br/>
 *
 * @author zhengrunlong@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Service
public class PowerServiceImpl implements PowerService {

	@Autowired
	private PowerDao powerDao;
	
	/**
	 * 新增权限信息.
	 * @see com.zillionfortune.boss.service.power.PowerService#insertSelective(com.zillionfortune.boss.dal.entity.Power)
	 */
	@Override
	public int insertSelective(Power power) {
		
		return powerDao.insertSelective(power);
	}

	/**
	 * 删除权限.
	 * @see com.zillionfortune.boss.service.power.PowerService#deleteByPrimaryKey(java.lang.Integer)
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		
		return powerDao.deleteByPrimaryKey(id);
	}

	/**
	 * 更新权限.
	 * @see com.zillionfortune.boss.service.power.PowerService#updateByPrimaryKeySelective(com.zillionfortune.boss.dal.entity.Power)
	 */
	@Override
	public int updateByPrimaryKeySelective(Power power) {
		
		return powerDao.updateByPrimaryKeySelective(power);
	}

	/**
	 * 根据主键查询权限.
	 * @see com.zillionfortune.boss.service.power.PowerService#selectByPrimaryKey(java.lang.Integer)
	 */
	@Override
	public Power selectByPrimaryKey(Integer id) {
		
		return powerDao.selectByPrimaryKey(id);
	}

	/**
	 * 根据条件查询.
	 * @see com.zillionfortune.boss.service.power.PowerService#selectBySelective(com.zillionfortune.boss.dal.entity.Power)
	 */
	@Override
	public List<Power> selectBySelective(Power power) {
		
		return powerDao.selectBySelective(power);
	}

	/**
	 * 根据条件统计条数.
	 * @see com.zillionfortune.boss.service.power.PowerService#selectBySelectiveCount(com.zillionfortune.boss.dal.entity.Power)
	 */
	@Override
	public int selectBySelectiveCount(Power power) {
		
		return powerDao.selectBySelectiveCount(power);
	}

}