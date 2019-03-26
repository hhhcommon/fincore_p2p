/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.user;

import com.zillionfortune.boss.biz.user.dto.UserRoleAddRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

/**
 * ClassName: UserRoleBiz <br/>
 * Function: 运营平台用户分配角色管理biz层接口. <br/>
 * Date: 2017年2月27日 上午10:14:50 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public interface UserRoleBiz {
	
	/**
	 * add:分配角色. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse add(UserRoleAddRequest req);
	
	/**
     * 根据用户Id查用户角色. <br/>
     *
     * @param userId
     * @return
     */
	BaseWebResponse selectRoleByUserId(Integer userId);
	
}
