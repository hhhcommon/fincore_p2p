package com.zillionfortune.boss.web.controller.ams.vo;

public class AssetQueryListVO {
	private String assetCode;
	private String assetName;
	private Integer assetType;
	private String financeSubjectCode;
	private Integer collectStatus;
	private int pageNo;
	private int pageSize;
	public String getAssetCode() {
		return assetCode;
	}
	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public Integer getAssetType() {
		return assetType;
	}
	public void setAssetType(Integer assetType) {
		this.assetType = assetType;
	}
	public String getFinanceSubjectCode() {
		return financeSubjectCode;
	}
	public void setFinanceSubjectCode(String financeSubjectCode) {
		this.financeSubjectCode = financeSubjectCode;
	}
	public Integer getCollectStatus() {
		return collectStatus;
	}
	public void setCollectStatus(Integer collectStatus) {
		this.collectStatus = collectStatus;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
