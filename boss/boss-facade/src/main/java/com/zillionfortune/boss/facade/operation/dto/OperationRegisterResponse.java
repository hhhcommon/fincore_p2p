package com.zillionfortune.boss.facade.operation.dto;

import com.zillionfortune.boss.facade.common.dto.BaseResponse;

public class OperationRegisterResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;

	public OperationRegisterResponse(){
		super();
	}
	
	public OperationRegisterResponse(String respCode,String resultCode,String resultMsg){
		super(respCode,resultCode,resultMsg);
	}
	
	public OperationRegisterResponse(String respCode,String resultMsg){
		super(respCode,resultMsg);
	}
}
