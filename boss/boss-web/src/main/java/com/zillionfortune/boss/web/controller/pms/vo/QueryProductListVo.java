/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.pms.vo;

/**
 * ClassName: QueryProductListVo <br/>
 * Function: 产品列表查询用Vo. <br/>
 * Date: 2017年5月10日 下午2:16:29 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class QueryProductListVo {
	/** productCode:产品编号 **/
	private String productCode;
	
	/** productName:产品名称 **/
	private String productName;
	
	/** productDisplayName:产品显示名 **/
	private String productDisplayName;
	
	/** productLineId:产品线Id **/
	private String productLineId;
	
	/** productLineCode:产品线编号 **/
	private String productLineCode;
	
	/** assetPoolCode:资产池编号 **/
	private String assetPoolCode;
	
	/** patternCode:产品类型 **/
	private String patternCode;
	
	/** saleChannelCode:销售渠道 **/
	private String saleChannelCode;
	
	/** joinChannelCode:接入渠道 **/
	private String joinChannelCode;
	
	/** totalAmount:募集总规模 **/
	private String totalAmount;
	
	/** saleStatus:销售状态 **/
	private String saleStatus;
	
	/** collectStatus:募集状态 **/
	private String collectStatus;
	
	/** displayStatus:显示状态(用于C端) **/
	private String displayStatus;
	
	/** riskLevel:风险等级 **/
	private String riskLevel;
	
	/** approvalStatus:产品审核状态 **/
	private String approvalStatus;
	
	/** beginCreateTime:查询创建开始时间 **/
	private String beginCreateTime;
	
	/** endCreateTime:查询创建结束时间 **/
	private String endCreateTime;
	
	/** approvalStartTime:审核开始时间 **/
	private String approvalStartTime;
	
	/** approvalEndTime:审核结束时间 **/
	private String approvalEndTime;
	
	/** pageNo:页码(当前页) **/
	private int pageNo;
	
	/** pageSize:分页大小(每页数量) **/
	private int pageSize;
	
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDisplayName() {
		return productDisplayName;
	}

	public void setProductDisplayName(String productDisplayName) {
		this.productDisplayName = productDisplayName;
	}

	public String getProductLineId() {
		return productLineId;
	}

	public void setProductLineId(String productLineId) {
		this.productLineId = productLineId;
	}

	public String getProductLineCode() {
		return productLineCode;
	}

	public void setProductLineCode(String productLineCode) {
		this.productLineCode = productLineCode;
	}

	public String getAssetPoolCode() {
		return assetPoolCode;
	}

	public void setAssetPoolCode(String assetPoolCode) {
		this.assetPoolCode = assetPoolCode;
	}

	public String getPatternCode() {
		return patternCode;
	}

	public void setPatternCode(String patternCode) {
		this.patternCode = patternCode;
	}

	public String getSaleChannelCode() {
		return saleChannelCode;
	}

	public void setSaleChannelCode(String saleChannelCode) {
		this.saleChannelCode = saleChannelCode;
	}

	public String getJoinChannelCode() {
		return joinChannelCode;
	}

	public void setJoinChannelCode(String joinChannelCode) {
		this.joinChannelCode = joinChannelCode;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}

	public String getCollectStatus() {
		return collectStatus;
	}

	public void setCollectStatus(String collectStatus) {
		this.collectStatus = collectStatus;
	}

	public String getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(String displayStatus) {
		this.displayStatus = displayStatus;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
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

	public String getApprovalStartTime() {
		return approvalStartTime;
	}

	public void setApprovalStartTime(String approvalStartTime) {
		this.approvalStartTime = approvalStartTime;
	}

	public String getApprovalEndTime() {
		return approvalEndTime;
	}

	public void setApprovalEndTime(String approvalEndTime) {
		this.approvalEndTime = approvalEndTime;
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
