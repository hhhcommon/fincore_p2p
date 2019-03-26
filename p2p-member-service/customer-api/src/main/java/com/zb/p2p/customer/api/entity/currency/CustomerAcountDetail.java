/**
 * 
 */
package com.zb.p2p.customer.api.entity.currency;

import com.zb.p2p.customer.api.entity.CustomerDetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author guolitao
 *
 */
@ApiModel
public class CustomerAcountDetail extends CustomerDetail {

	@ApiModelProperty(value="活期账户详情")
	private CustomerEaccountBalance eBalance;
	@ApiModelProperty(value="账户余额")
	private String accountAmount;
	public CustomerEaccountBalance geteBalance() {
		return eBalance;
	}
	public void seteBalance(CustomerEaccountBalance eBalance) {
		this.eBalance = eBalance;
	}
	public String getAccountAmount() {
		return accountAmount;
	}
	public void setAccountAmount(String accountAmount) {
		this.accountAmount = accountAmount;
	}
	
}
