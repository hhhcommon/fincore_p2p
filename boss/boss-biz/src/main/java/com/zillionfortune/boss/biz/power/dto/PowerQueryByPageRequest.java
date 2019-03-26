package com.zillionfortune.boss.biz.power.dto;

import com.zillionfortune.boss.common.dto.BasePageRequest;

public class PowerQueryByPageRequest extends BasePageRequest {
	
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;

	/** 权限名称 */
	private String name;
	
	/** 菜单Id */
	private Integer menuId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

}
