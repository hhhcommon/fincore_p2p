package com.zillionfortune.boss.web.controller.op.menu.vo;

import com.zillionfortune.boss.common.dto.BasePageRequest;

/**
 * ClassName: MenuQueryRequestVo <br/>
 * Function: 查询菜单Request. <br/>
 * Date: 2017年2月22日 下午1:59:09 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class MenuQueryRequestVo extends BasePageRequest {
	
	private static final long serialVersionUID = 1L;

	/**
	 * menuId:菜单Id.
	 */
	private Integer menuId;

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
	
}
