package com.zillionfortune.boss.facade.operation.dto.member;

import com.zillionfortune.boss.facade.common.dto.BaseResponse;


public class OperationLegalPersonAuditResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;
	
	private String memberId;
	
	private String authGrade;

	public String getAuthGrade() {
		return authGrade;
	}

	public void setAuthGrade(String authGrade) {
		this.authGrade = authGrade;
	}
	
	public OperationLegalPersonAuditResponse(){
		
	}

	public OperationLegalPersonAuditResponse(String respCode,String resultCode,String resultMsg){
		super(respCode,resultCode,resultMsg);
	}
	
	public OperationLegalPersonAuditResponse(String respCode,String resultMsg){
		super(respCode,resultMsg);
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

}
