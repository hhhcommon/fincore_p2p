/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.user.dto;

import com.zillionfortune.boss.common.dto.BaseRequest;


/**
 * ClassName: UserQueryListRequest <br/>
 * Function: 用户列表_请求参数对象. <br/>
 * Date: 2017年2月21日 下午4:47:38 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class UserQueryListRequest extends BaseRequest {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;
	
	/** 邮箱  必输  */
	private String userId;
	

	/** set/get()  */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
