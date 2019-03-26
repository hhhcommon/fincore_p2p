/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.op.user.vo;

import com.zillionfortune.boss.common.dto.BaseRequest;


/**
 * ClassName: UserDeleteRequestVo <br/>
 * Function: 删除用户_请求参数对象. <br/>
 * Date: 2017年2月21日 下午4:47:38 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class UserDeleteRequestVo extends BaseRequest {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;
	
	/** 邮箱  必输  */
	private String userId;
	
	/** 删除人 非必输  */
	private String deleteBy;

	
	/** set/get()  */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeleteBy() {
		return deleteBy;
	}

	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

}
