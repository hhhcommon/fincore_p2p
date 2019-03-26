package com.zillionfortune.boss.facade.operation.dto.member;

import java.io.Serializable;
import java.util.Date;



public class OperationMemberUserInfoForMobile implements Serializable{

	private static final long serialVersionUID = 1L;

	private String memberId;
	
	private String mobile;
	
	private String email;
	
	private String status;
	
	private String operatorId;

	private String userName;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
