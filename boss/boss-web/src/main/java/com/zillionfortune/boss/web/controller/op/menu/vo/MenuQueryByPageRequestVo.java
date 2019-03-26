package com.zillionfortune.boss.web.controller.op.menu.vo;

import com.zillionfortune.boss.common.dto.BasePageRequest;

/**
 * ClassName: MenuQueryByPageRequest <br/>
 * Function: 查询菜单（分页）Request. <br/>
 * Date: 2017年2月22日 下午1:59:09 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class MenuQueryByPageRequestVo extends BasePageRequest {
	
	private static final long serialVersionUID = 1L;

	/**
	 * userId:用户Id.
	 */
	private Integer userId;
	
	/**
	 * parentId:父菜单Id.
	 */
	private Integer parentId;
	
	/**
	 * name:菜单名称.
	 */
	private String name;
	
	/**
	 * 获取userId的值.
	 *
	 * @return userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * 设置userId的值.
	 *
	 * @param  userId
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
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

}
