/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.user.dto;

import com.zillionfortune.boss.common.dto.BaseRequest;

/**
 * ClassName: UserModifyPasswordRequest <br/>
 * Function: 修改登录密码_请求参数对象. <br/>
 * Date: 2017年2月21日 下午4:47:38 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class UserModifyPasswordRequest extends BaseRequest {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;
	
	/** 邮箱 必输  */
	private String userId;
	
	/** 原始登录密码 必输  */
	private String originalPwd;
	
	/** 新登录密码 必输  */
	private String newPwd;
	
	/** 确认登录密码 必输  */
	private String repeatPwd;

	
	/** set/get()  */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOriginalPwd() {
		return originalPwd;
	}

	public void setOriginalPwd(String originalPwd) {
		this.originalPwd = originalPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getRepeatPwd() {
		return repeatPwd;
	}

	public void setRepeatPwd(String repeatPwd) {
		this.repeatPwd = repeatPwd;
	}

}
