/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.op.power.vo;

import com.zillionfortune.boss.common.dto.BaseRequest;

/**
 * ClassName: PowerModifyRequestVo <br/>
 * Function:  修改权限请求参数对象. <br/>
 * Date: 2017年2月20日 下午3:22:47 <br/>
 *
 * @author zhengrunlong@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class PowerModifyRequestVo extends BaseRequest {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;

	/** 权限名称 */
	private String name;
	
	/** 操作Code */
	private String operationCode;
	
	/** 请求地址 */
	private String requestUrl;
	
	/** 菜单Id */
	private Integer menuId;
	
	/** 备注  */
	private String remark;
	
	/** 权限Id */
	private Integer powerId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getPowerId() {
		return powerId;
	}

	public void setPowerId(Integer powerId) {
		this.powerId = powerId;
	}

}
