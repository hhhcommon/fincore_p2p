/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.user.dto;

import java.util.List;

import com.zillionfortune.boss.common.dto.BaseRequest;

/**
 * ClassName: UserRoleAddRequest <br/>
 * Function: 分配角色（新增）Request. <br/>
 * Date: 2017年2月27日 上午10:12:43 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class UserRoleAddRequest extends BaseRequest {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;

	/** 用户Id */
	private Integer userId;
	
	/** 角色Id列表  */
	private List<Integer> roleIds;
	
	/**
	 * 获取userId的值.
	 *
	 * @return userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * 设置userId的值.
	 *
	 * @param  userId
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * 获取roleIds的值.
	 *
	 * @return roleIds
	 */
	public List<Integer> getRoleIds() {
		return roleIds;
	}

	/**
	 * 设置roleIds的值.
	 *
	 * @param  roleIds
	 */
	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
	}

}
