package com.zillionfortune.boss.facade.operation.dto;

import com.zillionfortune.boss.facade.common.dto.BaseResponse;

public class OperationUserMenuResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;
	
	private Object data;

	public OperationUserMenuResponse(){
		super();
	}
	
	public OperationUserMenuResponse(String respCode,String resultCode,String resultMsg){
		super(respCode,resultCode,resultMsg);
	}
	
	public OperationUserMenuResponse(String respCode,String resultMsg){
		super(respCode,resultMsg);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
