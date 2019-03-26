package com.zillionfortune.boss.facade.operation.dto.member;

import com.zillionfortune.boss.facade.common.dto.BaseRequest;

public class OperationLegalPersonAuditRequest  extends BaseRequest {

	private static final long serialVersionUID = 1L;

	private String memberId;
	
	/** 企业审核状态  0：待审核；1：审核通过；2：审核不通过；*/
	private Integer enterpriseAuditStatus;
	/** 法人审核状态 0：待审核；1：审核通过；2：审核不通过；*/
	private Integer legalPersonAuditStatus;
	/**企业审核意见*/
	private String enterpriseAuditComment; 
	/**法人审核意见*/
	private String legalPersonAuditComment;
	
	public String getEnterpriseAuditComment() {
		return enterpriseAuditComment;
	}
	public void setEnterpriseAuditComment(String enterpriseAuditComment) {
		this.enterpriseAuditComment = enterpriseAuditComment;
	}
	public String getLegalPersonAuditComment() {
		return legalPersonAuditComment;
	}
	public void setLegalPersonAuditComment(String legalPersonAuditComment) {
		this.legalPersonAuditComment = legalPersonAuditComment;
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
	
}
