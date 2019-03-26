package com.zillionfortune.boss.facade.operation.dto.member;

import com.zillionfortune.boss.facade.common.dto.BaseRequest;

public class OperationMemberUserRequest  extends BaseRequest {

	private static final long serialVersionUID = 1L;

	private String memberId;
	
	private Integer pageSize;
	
	private Integer currentPage;
	
	private Integer enterpriseAuditStatus;
	
	private Integer legalPersonAuditStatus;
	/**商户号*/
	private String customerNo;	
	/**企业名称*/
	private String enterpriseName;	
	/**注册手机号*/
	private Integer mobile;	
	/**法人姓名*/
	private String legalPersonName;	

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

	public Integer getMobile() {
		return mobile;
	}

	public void setMobile(Integer mobile) {
		this.mobile = mobile;
	}

	public String getLegalPersonName() {
		return legalPersonName;
	}

	public void setLegalPersonName(String legalPersonName) {
		this.legalPersonName = legalPersonName;
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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	
	
}
