/**
 * 
 */
package com.zb.p2p.customer.api.entity.currency;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author guolitao
 *
 */
@ApiModel
public class CustomerEaccountBalance {

	@ApiModelProperty(value="账户余额")
	private String totalBalance;//	账户余额	BigDecimal	是	默认 0.00
	@ApiModelProperty(value="昨日收益")
	private String predayIncome;//	昨日收益	BigDecimal	是	默认 0.00
	@ApiModelProperty(value="累计收益")
	private String totalIncome;//	累计收益	BigDecimal	是	默认 0.00
	public String getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(String totalBalance) {
		this.totalBalance = totalBalance;
	}
	public String getPredayIncome() {
		return predayIncome;
	}
	public void setPredayIncome(String predayIncome) {
		this.predayIncome = predayIncome;
	}
	public String getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(String totalIncome) {
		this.totalIncome = totalIncome;
	}
	
	
}
