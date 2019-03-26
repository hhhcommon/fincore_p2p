/*
 * Copyright (c) 2017, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.web.controller.op.role.vo;

import com.zillionfortune.boss.common.dto.BaseRequest;

/**
 * ClassName: RoleRequestVo <br/>
 * Function: 角色操作 请求参数对象. <br/>
 * Date: 2017年2月22日 上午9:30:24 <br/>
 *
 * @author pengting
 * @version 
 * @since JDK 1.7
 */
public class RoleRequestVo extends BaseRequest {
	
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * roleId:角色id
	 */
	private Integer roleId;
	
	/**
	 * name:	角色名称
	 */
	private String name;
	
	/**
	 * signLevel:	授权级别
	 */
	private String signLevel;
	
	/**
	 * remark:	备注
	 */
	private String remark;
	
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSignLevel() {
		return signLevel;
	}
	public void setSignLevel(String signLevel) {
		this.signLevel = signLevel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
