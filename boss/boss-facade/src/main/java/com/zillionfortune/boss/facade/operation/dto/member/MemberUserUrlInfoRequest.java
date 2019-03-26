package com.zillionfortune.boss.facade.operation.dto.member;


import com.zillionfortune.boss.facade.common.dto.BaseRequest;

public class MemberUserUrlInfoRequest  extends BaseRequest {

	private static final long serialVersionUID = 1L;
	
	private String memberId;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	
	
}
