/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.op.menu.vo;

import com.zillionfortune.boss.common.dto.BaseRequest;

/**
 * ClassName: MenuModifyRequestVo <br/>
 * Function: 修改菜单Request. <br/>
 * Date: 2017年2月22日 上午10:11:08 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class MenuModifyRequestVo extends BaseRequest {

	private static final long serialVersionUID = 1L;
	
	/**
	 * menuId:菜单Id.
	 */
	private Integer menuId;

	/**
	 * name:菜单名称.
	 */
	private String name;
	
	/**
	 * isValid:是否有效.
	 */
	private Integer isValid;
	
	/**
	 * displayOrder:显示顺序.
	 */
	private Integer displayOrder;
	
	/**
	 * url:请求url.
	 */
	private String url;
	
	/**
	 * parentId:父菜单Id.
	 */
	private Integer parentId;
	
	/**
	 * icon:图标地址.
	 */
	private String icon;
	
	/**
	 * remark:备注.
	 */
	private String remark;
	
	/**
	 * 获取menuId的值.
	 *
	 * @return menuId
	 */
	public Integer getMenuId() {
		return menuId;
	}

	/**
	 * 设置menuId的值.
	 *
	 * @param  menuId
	 */
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	/**
	 * 获取name的值.
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置name的值.
	 *
	 * @param  name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取isValid的值.
	 *
	 * @return isValid
	 */
	public Integer getIsValid() {
		return isValid;
	}

	/**
	 * 设置isValid的值.
	 *
	 * @param  isValid
	 */
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	/**
	 * 获取displayOrder的值.
	 *
	 * @return displayOrder
	 */
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * 设置displayOrder的值.
	 *
	 * @param  displayOrder
	 */
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * 获取url的值.
	 *
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置url的值.
	 *
	 * @param  url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取parentId的值.
	 *
	 * @return parentId
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * 设置parentId的值.
	 *
	 * @param  parentId
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * 获取icon的值.
	 *
	 * @return icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * 设置icon的值.
	 *
	 * @param  icon
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 获取remark的值.
	 *
	 * @return remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置remark的值.
	 *
	 * @param  remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
