package com.zillionfortune.boss.web.controller.ams.vo;

import java.math.BigDecimal;
import java.util.Date;

public class AssetAddVO {
	private String assetName;
	private Integer assetType;
	private BigDecimal assetAmount;
	private Integer valueDays;
	private Date establishTime;
	private Date valueStartTime;
	private Date valueEndTime;
	private Date expireTime;
	private Integer repaymentType;
	private BigDecimal yieldRate;
	private String projectDesc;
	private String repayGuarenteeMode;
	private String creditMode;
	private String financeSubjectCode;
	private String provideLoanCode;
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
	public BigDecimal getAssetAmount() {
		return assetAmount;
	}
	public void setAssetAmount(BigDecimal assetAmount) {
		this.assetAmount = assetAmount;
	}
	public Integer getValueDays() {
		return valueDays;
	}
	public void setValueDays(Integer valueDays) {
		this.valueDays = valueDays;
	}
	
	public Date getEstablishTime() {
		return establishTime;
	}
	public void setEstablishTime(Date establishTime) {
		this.establishTime = establishTime;
	}
	public Date getValueStartTime() {
		return valueStartTime;
	}
	public void setValueStartTime(Date valueStartTime) {
		this.valueStartTime = valueStartTime;
	}
	public Date getValueEndTime() {
		return valueEndTime;
	}
	public void setValueEndTime(Date valueEndTime) {
		this.valueEndTime = valueEndTime;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public Integer getRepaymentType() {
		return repaymentType;
	}
	public void setRepaymentType(Integer repaymentType) {
		this.repaymentType = repaymentType;
	}
	public BigDecimal getYieldRate() {
		return yieldRate;
	}
	public void setYieldRate(BigDecimal yieldRate) {
		this.yieldRate = yieldRate;
	}
	public String getProjectDesc() {
		return projectDesc;
	}
	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}
	public String getRepayGuarenteeMode() {
		return repayGuarenteeMode;
	}
	public void setRepayGuarenteeMode(String repayGuarenteeMode) {
		this.repayGuarenteeMode = repayGuarenteeMode;
	}
	public String getCreditMode() {
		return creditMode;
	}
	public void setCreditMode(String creditMode) {
		this.creditMode = creditMode;
	}
	public String getFinanceSubjectCode() {
		return financeSubjectCode;
	}
	public void setFinanceSubjectCode(String financeSubjectCode) {
		this.financeSubjectCode = financeSubjectCode;
	}
	public String getProvideLoanCode() {
		return provideLoanCode;
	}
	public void setProvideLoanCode(String provideLoanCode) {
		this.provideLoanCode = provideLoanCode;
	}
	
}