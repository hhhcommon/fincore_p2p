package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 授权注册接口响应
 * @author liujia
 *
 */
@ApiModel
public class AuthregisterRes {
    @ApiModelProperty(value="logintoken")
	private String logintoken;
    @ApiModelProperty(value="下一页面(1-开户绑卡页， 3-其他)")
    private String nextPage;
    @ApiModelProperty(value="会员ID摘要")
    private String customerId;

	public String getLogintoken() {
		return logintoken;
	}

	public void setLogintoken(String logintoken) {
		this.logintoken = logintoken;
	}

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
    
    
    
}
