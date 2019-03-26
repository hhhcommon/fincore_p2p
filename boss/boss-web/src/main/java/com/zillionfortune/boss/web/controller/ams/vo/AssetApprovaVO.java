package com.zillionfortune.boss.web.controller.ams.vo;

public class AssetApprovaVO {
	private String assetCode;
	private String approvalBy;
	private String sign;
	private Integer approvalStatus;
	private String approvalSuggestion;
	public String getAssetCode() {
		return assetCode;
	}
	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}
	public String getApprovalBy() {
		return approvalBy;
	}
	public void setApprovalBy(String approvalBy) {
		this.approvalBy = approvalBy;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public Integer getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(Integer approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getApprovalSuggestion() {
		return approvalSuggestion;
	}
	public void setApprovalSuggestion(String approvalSuggestion) {
		this.approvalSuggestion = approvalSuggestion;
	}
	
}
