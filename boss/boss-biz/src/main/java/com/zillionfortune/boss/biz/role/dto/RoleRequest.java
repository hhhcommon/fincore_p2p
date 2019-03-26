/*
 * Copyright (c) 2017, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.biz.role.dto;

import com.zillionfortune.boss.common.dto.BasePageRequest;

/**
 * ClassName: RoleRequest <br/>
 * Function: 操作角色请求参数对象. <br/>
 * Date: 2017年2月21日 下午5:30:19 <br/>
 *
 * @author pengting
 * @version 
 * @since JDK 1.7
 */
public class RoleRequest extends BasePageRequest {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
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
	
	/** 用户Id  */
	private Integer userId;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}