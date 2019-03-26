/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.power;

import com.zillionfortune.boss.biz.power.dto.PowerAddRequest;
import com.zillionfortune.boss.biz.power.dto.PowerModifyRequest;
import com.zillionfortune.boss.biz.power.dto.PowerQueryByPageRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

/**
 * ClassName: PowerBiz <br/>
 * Function: 运营后台权限管理biz层接口定义. <br/>
 * Date: 2017年2月20日 下午3:37:58 <br/>
 *
 * @author zhengrunlong@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public interface PowerBiz {
	
	/**
	 * add:新增权限方法. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse add(PowerAddRequest req);
	
	/**
	 * modify:修改权限方法. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse modify(PowerModifyRequest req);
	
	
	/**
	 * delete:删除权限方法. <br/>
	 *
	 * @param powerId
	 * @param deleteBy
	 * @param userId
	 * @return
	 */
	public BaseWebResponse delete(Integer powerId,String deleteBy, Integer userId);
	
	/**
	 * query:根据主键查询权限. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse query(Integer powerId);
	
	/**
	 * queryByPage:查询权限(分页). <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse queryByPage(PowerQueryByPageRequest req);
	
}
