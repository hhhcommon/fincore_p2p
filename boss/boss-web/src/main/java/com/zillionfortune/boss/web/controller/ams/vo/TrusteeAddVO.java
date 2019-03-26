package com.zillionfortune.boss.web.controller.ams.vo;

import java.math.BigDecimal;

public class TrusteeAddVO {
	private String trusteeName;
	private Integer certType;
	private String certNo;
	private BigDecimal registeredCapital;
	private String trusteeAddress;
	private String legalPersonName;
	private String legalPersonCertNo;
	private String businessScope;
	private String introduction;
	public String getTrusteeName() {
		return trusteeName;
	}
	public void setTrusteeName(String trusteeName) {
		this.trusteeName = trusteeName;
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
	public String getTrusteeAddress() {
		return trusteeAddress;
	}
	public void setTrusteeAddress(String trusteeAddress) {
		this.trusteeAddress = trusteeAddress;
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

}
