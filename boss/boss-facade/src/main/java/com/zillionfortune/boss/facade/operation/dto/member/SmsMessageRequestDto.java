package com.zillionfortune.boss.facade.operation.dto.member;

import com.zillionfortune.boss.facade.common.dto.BaseRequest;

public class SmsMessageRequestDto   extends BaseRequest {

	private static final long serialVersionUID = 1L;
	
	private String sysCode;
	
	private String smsCode;
	
	private String mobile;
	
	private String content;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


}
