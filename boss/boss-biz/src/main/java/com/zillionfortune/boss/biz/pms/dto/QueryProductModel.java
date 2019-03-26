/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.pms.dto;

import com.zb.fincore.pms.facade.product.model.ProductPeriodModel;
import com.zb.fincore.pms.facade.product.model.ProductProfitModel;
import com.zb.fincore.pms.facade.product.model.ProductStockModel;

/**
 * ClassName: QueryProductModel <br/>
 * Function: 产品列表查询返回结果Model. <br/>
 * Date: 2017年5月9日 上午11:06:45 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class QueryProductModel {
	/** productId:产品Id **/
	private String productId;
	
	/** productCode:产品编号 **/
	private String productCode;
	
	/** productName:产品名称 **/
	private String productName;
	
	/** productDisplayName:展示名称 **/
	private String productDisplayName;
	
	/** productLineId:产品线Id **/
	private String productLineId;
	
	/** productLineCode:产品线编号 **/
	private String productLineCode;
	
	/** productLineName:产品线名称 **/
	private String productLineName;
	
	/** patternCode:产品类型 **/
	private String patternCode;
	
	/** maxYieldRate:预期年化收益率上限 **/
	private String maxYieldRate;
	
	/** minYieldRate:预期年化收益率下限 **/
	private String minYieldRate;
	
	/** totalAmount:产品规模 **/
	private String totalAmount;
	
	/** investPeriod:产品期限 **/
	private String investPeriod;
	
	/** saleChannelCode:销售渠道 **/
	private String saleChannelCode;
	
	/** riskLevel:风险等级 **/
	private String riskLevel;
	
	/** saleStatus:销售状态 **/
	private String saleStatus;
	
	/** collectStatus:募集状态 **/
	private String collectStatus;
	
	/** approvalStatus:审核状态 **/
	private String approvalStatus;
	
	/** submitApprovalTime:提交审核时间 **/
	private String submitApprovalTime;
	
	/** approvalTime:审核时间 **/
	private String approvalTime;
	
	private String loanOrderNoSet;
	
	private String collectMode;
	
	private ProductPeriodModel productPeriodModel;
	
	private ProductProfitModel productProfitModel;
	
	private ProductStockModel productStockModel;

	public String getProductId() {
		return productId;
	}

	public String getLoanOrderNoSet() {
		return loanOrderNoSet;
	}

	public void setLoanOrderNoSet(String loanOrderNoSet) {
		this.loanOrderNoSet = loanOrderNoSet;
	}

	public String getCollectMode() {
		return collectMode;
	}

	public void setCollectMode(String collectMode) {
		this.collectMode = collectMode;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

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

	public String getProductLineName() {
		return productLineName;
	}

	public void setProductLineName(String productLineName) {
		this.productLineName = productLineName;
	}

	public String getPatternCode() {
		return patternCode;
	}

	public void setPatternCode(String patternCode) {
		this.patternCode = patternCode;
	}

	public String getMaxYieldRate() {
		return maxYieldRate;
	}

	public void setMaxYieldRate(String maxYieldRate) {
		this.maxYieldRate = maxYieldRate;
	}

	public String getMinYieldRate() {
		return minYieldRate;
	}

	public void setMinYieldRate(String minYieldRate) {
		this.minYieldRate = minYieldRate;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getInvestPeriod() {
		return investPeriod;
	}

	public void setInvestPeriod(String investPeriod) {
		this.investPeriod = investPeriod;
	}

	public String getSaleChannelCode() {
		return saleChannelCode;
	}

	public void setSaleChannelCode(String saleChannelCode) {
		this.saleChannelCode = saleChannelCode;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
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

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getSubmitApprovalTime() {
		return submitApprovalTime;
	}

	public void setSubmitApprovalTime(String submitApprovalTime) {
		this.submitApprovalTime = submitApprovalTime;
	}

	public String getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(String approvalTime) {
		this.approvalTime = approvalTime;
	}

	public ProductPeriodModel getProductPeriodModel() {
		return productPeriodModel;
	}

	public void setProductPeriodModel(ProductPeriodModel productPeriodModel) {
		this.productPeriodModel = productPeriodModel;
	}

	public ProductProfitModel getProductProfitModel() {
		return productProfitModel;
	}

	public void setProductProfitModel(ProductProfitModel productProfitModel) {
		this.productProfitModel = productProfitModel;
	}

	public ProductStockModel getProductStockModel() {
		return productStockModel;
	}

	public void setProductStockModel(ProductStockModel productStockModel) {
		this.productStockModel = productStockModel;
	}

}
