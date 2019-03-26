/*
 * Copyright (c) 2017, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.biz.role.dto;

import java.util.Date;

/**
 * ClassName: RoleResponse <br/>
 * Function: 角色操作响应对象. <br/>
 * Date: 2017年2月23日 下午12:19:41 <br/>
 *
 * @author pengting
 * @version 
 * @since JDK 1.7
 */
public class RoleResponse {

	private Integer roleId;
	private String name;
	private String signLevel;
	private Date createTime;
	private Date modifyTime;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
