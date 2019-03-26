package com.zillionfortune.boss.facade.operation.dto.member;

import java.util.Date;

import com.zillionfortune.boss.facade.common.dto.BaseRequest;

public class OperationMemberUserInfoRequest  extends BaseRequest {

	private static final long serialVersionUID = 1L;
	/**注册手机号*/
	private String mobile;
	/**商户号*/
	private String customerNo;
	/**企业名称*/
	private String enterpriseName;
	/**企业审核状态*/
	private Integer enterpriseAuditStatus;
	/**法人审核状态*/
	private Integer legalPersonAuditStatus;
	/**认证状态*/
	private Integer authStatus;
	/**注册时间区间	大于或等于该时间*/
	private Date registerTimeStart;
	/**注册时间区间	小于该时间*/
	private Date registerTimeEnd;
	/**法人姓名*/
	private String legalPersonName;
	
	private String memberId;
	
	private Integer pageSize;
	private Integer pageNo;
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getLegalPersonName() {
		return legalPersonName;
	}
	public void setLegalPersonName(String legalPersonName) {
		this.legalPersonName = legalPersonName;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public Integer getEnterpriseAuditStatus() {
		return enterpriseAuditStatus;
	}
	public void setEnterpriseAuditStatus(Integer enterpriseAuditStatus) {
		this.enterpriseAuditStatus = enterpriseAuditStatus;
	}
	public Integer getLegalPersonAuditStatus() {
		return legalPersonAuditStatus;
	}
	public void setLegalPersonAuditStatus(Integer legalPersonAuditStatus) {
		this.legalPersonAuditStatus = legalPersonAuditStatus;
	}
	public Integer getAuthStatus() {
		return authStatus;
	}
	public void setAuthStatus(Integer authStatus) {
		this.authStatus = authStatus;
	}
	public Date getRegisterTimeStart() {
		return registerTimeStart;
	}
	public void setRegisterTimeStart(Date registerTimeStart) {
		this.registerTimeStart = registerTimeStart;
	}
	public Date getRegisterTimeEnd() {
		return registerTimeEnd;
	}
	public void setRegisterTimeEnd(Date registerTimeEnd) {
		this.registerTimeEnd = registerTimeEnd;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	
}
