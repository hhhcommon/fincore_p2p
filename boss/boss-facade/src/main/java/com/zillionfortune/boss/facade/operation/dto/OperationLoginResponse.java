package com.zillionfortune.boss.facade.operation.dto;

import com.zillionfortune.boss.facade.common.dto.BaseResponse;

public class OperationLoginResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;
	
	private OperationLoginUserResponse data;
	
	private String sessionId;

	public OperationLoginResponse(String respCode,String resultCode,String resultMsg){
		super(respCode,resultCode,resultMsg);
	}
	
	public OperationLoginResponse(String respCode,String resultMsg){
		super(respCode,resultMsg);
	}
	
	public OperationLoginUserResponse getData() {
		return data;
	}

	public void setData(OperationLoginUserResponse data) {
		this.data = data;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
