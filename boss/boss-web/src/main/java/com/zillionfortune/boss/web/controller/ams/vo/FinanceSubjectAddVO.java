package com.zillionfortune.boss.web.controller.ams.vo;

import java.math.BigDecimal;

public class FinanceSubjectAddVO {
	private String subjectName;
	private Integer certType;
	private String certNo;
	private BigDecimal registeredCapital;
	private String subjectAddress;
	private String legalPersonName;
	private String legalPersonCertNo;
	private String businessScope;
	private String introduction;
	private String subjectShowName;
	private String certNoShowName;
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public Integer getCertType() {
		return certType;
	}
	public void setCertType(Integer certType) {
		this.certType = certType;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public BigDecimal getRegisteredCapital() {
		return registeredCapital;
	}
	public void setRegisteredCapital(BigDecimal registeredCapital) {
		this.registeredCapital = registeredCapital;
	}
	public String getSubjectAddress() {
		return subjectAddress;
	}
	public void setSubjectAddress(String subjectAddress) {
		this.subjectAddress = subjectAddress;
	}
	public String getLegalPersonName() {
		return legalPersonName;
	}
	public void setLegalPersonName(String legalPersonName) {
		this.legalPersonName = legalPersonName;
	}
	public String getLegalPersonCertNo() {
		return legalPersonCertNo;
	}
	public void setLegalPersonCertNo(String legalPersonCertNo) {
		this.legalPersonCertNo = legalPersonCertNo;
	}
	public String getBusinessScope() {
		return businessScope;
	}
	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getSubjectShowName() {
		return subjectShowName;
	}
	public void setSubjectShowName(String subjectShowName) {
		this.subjectShowName = subjectShowName;
	}
	public String getCertNoShowName() {
		return certNoShowName;
	}
	public void setCertNoShowName(String certNoShowName) {
		this.certNoShowName = certNoShowName;
	}
	
}
