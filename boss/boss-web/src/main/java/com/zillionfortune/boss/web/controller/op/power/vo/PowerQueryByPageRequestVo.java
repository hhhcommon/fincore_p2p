/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.op.power.vo;

/**
 * ClassName: PowerQueryByPageRequest <br/>
 * Function: 权限分页查询请求参数对象. <br/>
 * Date: 2017年2月21日 下午5:28:30 <br/>
 *
 * @author zhengrunlong@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class PowerQueryByPageRequestVo {

	/** 权限名称 */
	private String name;
	
	/** 菜单Id */
	private Integer menuId;
	
	/** 每页条数 */
	private Integer pageSize;
	
	/** 当前页 */
	private Integer pageNo;

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

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

}
