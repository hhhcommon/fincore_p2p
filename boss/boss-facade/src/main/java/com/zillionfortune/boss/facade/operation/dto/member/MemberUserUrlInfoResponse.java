package com.zillionfortune.boss.facade.operation.dto.member;

import com.zillionfortune.boss.facade.common.dto.BaseResponse;


public class MemberUserUrlInfoResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;
	
	private String memberId;
	
	private String authGrade;
	
	private String customerId;
	
	/**	证件类型*/
	private String certificateType;
	/**	证件号码*/
	private String certificateNo;
	/**	营业执照下载地址*/
	private String businessLicenceUrl;
	/**	组织机构代码证下载地址*/
	private String organizationCodeCertificateUrl;	
	/**	税务登记证（国税）下载地址*/
	private String taxRegistrationCertificateUrl;	
	/**	税务登记证（地税）下载地址*/
	private String taxRegistrationCertificateLocalUrl;	
	/**	法人代表身份证正面下载地址*/
	private String legalPersonCertificateFrontUrl;	
	/**	法人代表身份证反面下载地址*/
	private String legalPersonCertificateBackUrl;	
	/**	开户许可证下载地址*/
	private String accountOpeningLicenseUrl	;
	/**	法定代表人／负责人授权委托书下载地址*/
	private String powerOfAttorneyUrl;	
	/**	企业服务申请书下载地址*/
	private String serviceApplicationUrl	;


	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getBusinessLicenceUrl() {
		return businessLicenceUrl;
	}

	public void setBusinessLicenceUrl(String businessLicenceUrl) {
		this.businessLicenceUrl = businessLicenceUrl;
	}

	public String getOrganizationCodeCertificateUrl() {
		return organizationCodeCertificateUrl;
	}

	public void setOrganizationCodeCertificateUrl(
			String organizationCodeCertificateUrl) {
		this.organizationCodeCertificateUrl = organizationCodeCertificateUrl;
	}

	public String getTaxRegistrationCertificateUrl() {
		return taxRegistrationCertificateUrl;
	}

	public void setTaxRegistrationCertificateUrl(
			String taxRegistrationCertificateUrl) {
		this.taxRegistrationCertificateUrl = taxRegistrationCertificateUrl;
	}

	public String getTaxRegistrationCertificateLocalUrl() {
		return taxRegistrationCertificateLocalUrl;
	}

	public void setTaxRegistrationCertificateLocalUrl(
			String taxRegistrationCertificateLocalUrl) {
		this.taxRegistrationCertificateLocalUrl = taxRegistrationCertificateLocalUrl;
	}

	public String getLegalPersonCertificateFrontUrl() {
		return legalPersonCertificateFrontUrl;
	}

	public void setLegalPersonCertificateFrontUrl(
			String legalPersonCertificateFrontUrl) {
		this.legalPersonCertificateFrontUrl = legalPersonCertificateFrontUrl;
	}

	public String getLegalPersonCertificateBackUrl() {
		return legalPersonCertificateBackUrl;
	}

	public void setLegalPersonCertificateBackUrl(
			String legalPersonCertificateBackUrl) {
		this.legalPersonCertificateBackUrl = legalPersonCertificateBackUrl;
	}

	public String getAccountOpeningLicenseUrl() {
		return accountOpeningLicenseUrl;
	}

	public void setAccountOpeningLicenseUrl(String accountOpeningLicenseUrl) {
		this.accountOpeningLicenseUrl = accountOpeningLicenseUrl;
	}

	public String getPowerOfAttorneyUrl() {
		return powerOfAttorneyUrl;
	}

	public void setPowerOfAttorneyUrl(String powerOfAttorneyUrl) {
		this.powerOfAttorneyUrl = powerOfAttorneyUrl;
	}

	public String getServiceApplicationUrl() {
		return serviceApplicationUrl;
	}

	public void setServiceApplicationUrl(String serviceApplicationUrl) {
		this.serviceApplicationUrl = serviceApplicationUrl;
	}

	public String getAuthGrade() {
		return authGrade;
	}

	public void setAuthGrade(String authGrade) {
		this.authGrade = authGrade;
	}
	
	public MemberUserUrlInfoResponse(){
		
	}

	public MemberUserUrlInfoResponse(String respCode,String resultCode,String resultMsg){
		super(respCode,resultCode,resultMsg);
	}
	
	public MemberUserUrlInfoResponse(String respCode,String resultMsg){
		super(respCode,resultMsg);
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

}
