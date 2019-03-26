package com.zillionfortune.boss.web.controller.ams.vo;

public class QueryAssetApprovalListVO {
	private String assetCode;
	private String assetName;
	private String financeSubjectCode;
	private Integer assetType;
	private String beginCreateTime;
	private String endCreateTime;
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
	public String getFinanceSubjectCode() {
		return financeSubjectCode;
	}
	public void setFinanceSubjectCode(String financeSubjectCode) {
		this.financeSubjectCode = financeSubjectCode;
	}
	public Integer getAssetType() {
		return assetType;
	}
	public void setAssetType(Integer assetType) {
		this.assetType = assetType;
	}
	public String getBeginCreateTime() {
		return beginCreateTime;
	}
	public void setBeginCreateTime(String beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}
	public String getEndCreateTime() {
		return endCreateTime;
	}
	public void setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
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
