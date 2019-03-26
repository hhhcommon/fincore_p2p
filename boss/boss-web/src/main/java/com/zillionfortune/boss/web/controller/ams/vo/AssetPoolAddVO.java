package com.zillionfortune.boss.web.controller.ams.vo;

import java.math.BigDecimal;

/**
 * 新增资产池VO
 * 
 * @author litaiping
 *
 */
public class AssetPoolAddVO {
	/** 资产池名称 */
	private String poolName;
	
	/** 资产池类型 1:定向委托 */
	private Integer poolType;
	
	/** 资产池总额上限  */
	private BigDecimal limitAmount;
	
	/** 发行方编码 */
	private String financeSubjectCode;
	
	/** 受托方编码 */
	private String trusteeCode;
	
	/** 资产池描述 */
	private String poolDesc;

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public Integer getPoolType() {
		return poolType;
	}

	public void setPoolType(Integer poolType) {
		this.poolType = poolType;
	}

	public BigDecimal getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(BigDecimal limitAmount) {
		this.limitAmount = limitAmount;
	}

	public String getFinanceSubjectCode() {
		return financeSubjectCode;
	}

	public void setFinanceSubjectCode(String financeSubjectCode) {
		this.financeSubjectCode = financeSubjectCode;
	}

	public String getTrusteeCode() {
		return trusteeCode;
	}

	public void setTrusteeCode(String trusteeCode) {
		this.trusteeCode = trusteeCode;
	}

	public String getPoolDesc() {
		return poolDesc;
	}

	public void setPoolDesc(String poolDesc) {
		this.poolDesc = poolDesc;
	}
	
}
