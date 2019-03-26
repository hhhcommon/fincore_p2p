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
public class QueryProductApprovalModel {
	/** productId:产品Id **/
	private String productId;
	
	/** productCode:产品编号 **/
	private String productCode;
	
	/** sign:授权级别 **/
	private String sign;
	
	/** approvalStatus:审核状态 **/
	private String approvalStatus;
	
	/** approvalSuggestion:审核意见 **/
	private String approvalSuggestion;
	
	/** approvalBy:审核人 **/
	private String approvalBy;
	
	/** approvalTime:审核时间 **/
	private String approvalTime;
	
	private ProductPeriodModel productPeriodModel;
	
	private ProductProfitModel productProfitModel;
	
	private ProductStockModel productStockModel;

	public String getProductId() {
		return productId;
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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getApprovalSuggestion() {
		return approvalSuggestion;
	}

	public void setApprovalSuggestion(String approvalSuggestion) {
		this.approvalSuggestion = approvalSuggestion;
	}

	public String getApprovalBy() {
		return approvalBy;
	}

	public void setApprovalBy(String approvalBy) {
		this.approvalBy = approvalBy;
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
