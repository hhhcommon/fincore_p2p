package com.zillionfortune.boss.facade.operation.dto.member;

import java.io.Serializable;
import java.util.Date;



public class OperationMemberUserInfo implements Serializable{

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
	private Date certExpDate;
	/**法人姓名*/
	private String legalPersonName;
	/**法人证件类型*/
	private Integer legalPersonCertificateType;
	/**法人证件号码*/
	private String legalPersonCertificateNo;
	/**法人证件有效期*/
	private Date legalPersonCertExpDate;
	/**企业审核意见*/
	private String enterpriseAuditComment; 
	/**法人审核意见*/
	private String legalPersonAuditComment;
	/**企业注册地址*/
	private String registerAddress;	
	/**联系电话*/
	private Integer phoneNo	;
	/**认证等级*/
	private Integer authGrade;	
	/**企业审核状态*/
	private Integer enterpriseAuditStatus;	
	/**法人审核状态*/
	private Integer legalPersonAuditStatus;	

	
	public String getRegisterAddress() {
		return registerAddress;
	}
	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}
	public Integer getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(Integer phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Integer getAuthGrade() {
		return authGrade;
	}
	public void setAuthGrade(Integer authGrade) {
		this.authGrade = authGrade;
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
	public Date getLegalPersonCertExpDate() {
		return legalPersonCertExpDate;
	}
	public void setLegalPersonCertExpDate(Date legalPersonCertExpDate) {
		this.legalPersonCertExpDate = legalPersonCertExpDate;
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
	public Date getCertExpDate() {
		return certExpDate;
	}
	public void setCertExpDate(Date certExpDate) {
		this.certExpDate = certExpDate;
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
	
}
