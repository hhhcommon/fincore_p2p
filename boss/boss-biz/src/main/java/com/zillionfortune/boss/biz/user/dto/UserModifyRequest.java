/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.user.dto;

import com.zillionfortune.boss.common.dto.BaseRequest;

/**
 * ClassName: UserModifyRequest <br/>
 * Function: 新增用户_请求参数对象. <br/>
 * Date: 2017年2月21日 下午4:47:38 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class UserModifyRequest extends BaseRequest {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;
	
	/** 邮箱 必输  */
	private String userId;

	/** 邮箱 非必输  */
	private String email;
	
	/** 手机号 非必输  */
	private String mobile;
	
	/** 登录密码  非必输  */
	private String password;
	
	/** 确认登录密码  非必输  */
	private String repeatPwd;
	
	/** 用户名  非必输  */
	private String userName;
	
	/** 真实姓名  非必输  */
	private String realName;
	
	
	/** set/get()  */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatPwd() {
		return repeatPwd;
	}

	public void setRepeatPwd(String repeatPwd) {
		this.repeatPwd = repeatPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

}
