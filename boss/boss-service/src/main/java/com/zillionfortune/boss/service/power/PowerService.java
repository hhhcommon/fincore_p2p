/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.service.power;

import java.util.List;

import com.zillionfortune.boss.dal.entity.Power;

/**
 * ClassName: PowerService <br/>
 * Function: 运营后台权限管理service层接口定义. <br/>
 * Date: 2017年2月20日 下午3:37:58 <br/>
 *
 * @author zhengrunlong@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public interface PowerService {

	/**
	 * insertSelective:新增权限信息. <br/>
	 *
	 * @param power
	 * @return
	 */
	public int insertSelective(Power power);
	
	/**
	 * deleteByPrimaryKey:删除权限. <br/>
	 *
	 * @param id
	 * @return
	 */
	public int deleteByPrimaryKey(Integer id);
	
	/**
	 * updateByPrimaryKeySelective:更新权限. <br/>
	 *
	 * @param record
	 * @return
	 */
	public int updateByPrimaryKeySelective(Power power);
	
	
	/**
	 * selectByPrimaryKey:根据主键查询权限. <br/>
	 *
	 * @param id
	 * @return
	 */
	public Power selectByPrimaryKey(Integer id);
	
	/**
	 * selectBySelective:根据条件查询 <br/>
	 *
	 * @param power
	 * @return
	 */
	public List<Power> selectBySelective(Power power);
	
	/**
	 * selectBySelectiveCount:根据条件统计条数. <br/>
	 *
	 * @param power
	 * @return
	 */
	public int selectBySelectiveCount(Power power);
}
