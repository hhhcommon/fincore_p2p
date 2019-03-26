/**
 * 
 */
package com.zb.p2p.customer.api.entity;

import lombok.Data;

/**
 * 登录返回的信息，比静态信息多了个loginToken
 * @author guolitao
 *
 */
@Data
public class LoginResp {
	private String loginToken;//登录token
	private String isCodeKaptcha;//登录是否需要输入验证码
	private String isTXSUser;//是否TXS用户
	private String flag;
	private String desc;
}
