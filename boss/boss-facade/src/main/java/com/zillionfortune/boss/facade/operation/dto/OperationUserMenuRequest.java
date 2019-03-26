package com.zillionfortune.boss.facade.operation.dto;

import com.zillionfortune.boss.facade.common.dto.BaseRequest;

public class OperationUserMenuRequest extends BaseRequest {

	private static final long serialVersionUID = 1L;

	/**用户名*/
	private String name;
	
	private Integer id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
