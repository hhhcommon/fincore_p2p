package com.zillionfortune.boss.facade.operation.dto;

import com.zillionfortune.boss.facade.common.dto.BaseRequest;

public class OperationLoginRequest extends BaseRequest {

	private static final long serialVersionUID = 1L;

	/**用户名*/
	private String name;
	/**密码*/
	private String password;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
