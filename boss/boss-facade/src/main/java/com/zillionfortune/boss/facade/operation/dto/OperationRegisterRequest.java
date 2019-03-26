package com.zillionfortune.boss.facade.operation.dto;

import com.zillionfortune.boss.facade.common.dto.BaseRequest;

public class OperationRegisterRequest extends BaseRequest {

	private static final long serialVersionUID = 1L;

	private String email;
	
	private String mobile;
	
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
