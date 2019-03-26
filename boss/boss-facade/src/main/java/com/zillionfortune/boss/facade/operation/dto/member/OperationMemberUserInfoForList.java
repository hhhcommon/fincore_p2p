package com.zillionfortune.boss.facade.operation.dto.member;

import java.io.Serializable;
import java.util.Date;



public class OperationMemberUserInfoForList implements Serializable{

	private static final long serialVersionUID = 1L;

	private String memberId;
	/**企业商户号*/
	private String customerNo;
	/**企业名称*/
	private String enterpriseName;
	/**营业执照号/统一社会信用代码号*/
	private String certificateNo;
	/**证件类型*/
	private String certificateType;
	/**证件有效期*/
	private String certExpDate;
	/**法人姓名*/
	private String legalPersonName;
	/**法人证件类型*/
	private Integer legalPersonCertificateType;
	/**法人证件号码*/
	private String legalPersonCertificateNo;
	/**法人证件有效期*/
	private String legalPersonCertExpDate;
	/**认证等级	0：未认证；1：已认证；2：强认证*/
	private Integer authGrade;
	/**企业审核状态	0：待审核；1：审核通过；2：审核不通过；*/
	private String enterpriseAuditStatus;
	/**法人审核状态	0：待审核；1：审核通过；2：审核不通过； */
	private String legalPersonAuditStatus;
	/**企业注册地址*/
	private String registerAddress;
	/**联系电话*/
	private String phoneNo;
	/**开户银行分支行*/
	private String branchBankName;
	/**银行账号*/
	private String bankAccountNo;
	/**邮编*/
	private String postCode;
	
	/**企业审核意见*/
	private String enterpriseAuditComment; 
	/**法人审核意见*/
	private String legalPersonAuditComment;
	
	/**审核意见*/
	private String auditCommnet;
	
	public String getAuditCommnet() {
		return auditCommnet;
	}
	public void setAuditCommnet(String auditCommnet) {
		this.auditCommnet = auditCommnet;
	}
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
	public Integer getAuthGrade() {
		return authGrade;
	}
	public void setAuthGrade(Integer authGrade) {
		this.authGrade = authGrade;
	}
	public String getRegisterAddress() {
		return registerAddress;
	}
	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getBranchBankName() {
		return branchBankName;
	}
	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}
	public String getBankAccountNo() {
		return bankAccountNo;
	}
	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
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
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public String getLegalPersonName() {
		return legalPersonName;
	}
	public void setLegalPersonName(String legalPersonName) {
		this.legalPersonName = legalPersonName;
	}
	public Integer getLegalPersonCertificateType() {
		return legalPersonCertificateType;
	}
	public void setLegalPersonCertificateType(Integer legalPersonCertificateType) {
		this.legalPersonCertificateType = legalPersonCertificateType;
	}
	public String getLegalPersonCertificateNo() {
		return legalPersonCertificateNo;
	}
	public void setLegalPersonCertificateNo(String legalPersonCertificateNo) {
		this.legalPersonCertificateNo = legalPersonCertificateNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCertificateType() {
		return certificateType;
	}
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}
	public String getEnterpriseAuditStatus() {
		return enterpriseAuditStatus;
	}
	public void setEnterpriseAuditStatus(String enterpriseAuditStatus) {
		this.enterpriseAuditStatus = enterpriseAuditStatus;
	}
	public String getLegalPersonAuditStatus() {
		return legalPersonAuditStatus;
	}
	public void setLegalPersonAuditStatus(String legalPersonAuditStatus) {
		this.legalPersonAuditStatus = legalPersonAuditStatus;
	}
	public String getCertExpDate() {
		return certExpDate;
	}
	public void setCertExpDate(String certExpDate) {
		this.certExpDate = certExpDate;
	}
	public String getLegalPersonCertExpDate() {
		return legalPersonCertExpDate;
	}
	public void setLegalPersonCertExpDate(String legalPersonCertExpDate) {
		this.legalPersonCertExpDate = legalPersonCertExpDate;
	}
	
}
