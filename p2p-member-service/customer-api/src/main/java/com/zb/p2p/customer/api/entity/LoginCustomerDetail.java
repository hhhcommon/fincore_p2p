/**
 * 
 */
package com.zb.p2p.customer.api.entity;

/**
 * 登录返回的信息，比静态信息多了个loginToken
 * @author guolitao
 *
 */
public class LoginCustomerDetail extends CustomerDetail {
	
	private String loginToken;//登录token
	private String isCodeKaptcha;//登录是否需要输入验证码

	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}

	public String getIsCodeKaptcha() {
		return isCodeKaptcha;
	}

	public void setIsCodeKaptcha(String isCodeKaptcha) {
		this.isCodeKaptcha = isCodeKaptcha;
	}
}
