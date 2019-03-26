/**
 * 
 */
package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 会员投资概要请求参数
 * @author guolitao
 *
 */
@ApiModel
public class CustInvestSummaryReq {

	@ApiModelProperty(value="会员ID")
	private String customerId;//
	@ApiModelProperty(value="是否投资新手标0否1是")
	private String buyFreshProductStatus;
	@ApiModelProperty(value="是否投资定期0否1是")
	private String buyFixedProductStatus;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getBuyFreshProductStatus() {
		return buyFreshProductStatus;
	}
	public void setBuyFreshProductStatus(String buyFreshProductStatus) {
		this.buyFreshProductStatus = buyFreshProductStatus;
	}
	public String getBuyFixedProductStatus() {
		return buyFixedProductStatus;
	}
	public void setBuyFixedProductStatus(String buyFixedProductStatus) {
		this.buyFixedProductStatus = buyFixedProductStatus;
	}
	
	
}
