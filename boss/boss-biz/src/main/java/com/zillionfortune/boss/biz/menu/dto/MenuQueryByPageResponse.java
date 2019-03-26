package com.zillionfortune.boss.biz.menu.dto;

import java.util.Date;
import java.util.List;

public class MenuQueryByPageResponse {
	
	private Integer menuId;
	
    private String code;

    private String name;

    private Integer parentId;

    private Integer displayOrder;

    private String remark;

    private String url;

    private Integer isValid;

    private String icon;
    
    private Date createTime;

    private String createBy;
    
    private Date modifyTime;

    private String modifyBy;
    
    private Integer deleteFlag;
    
	/**子节点*/
	private List<MenuQueryByPageResponse> subList;

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
	 * 获取code的值.
	 *
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置code的值.
	 *
	 * @param  code
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * 获取createTime的值.
	 *
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置createTime的值.
	 *
	 * @param  createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取createBy的值.
	 *
	 * @return createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * 设置createBy的值.
	 *
	 * @param  createBy
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * 获取modifyTime的值.
	 *
	 * @return modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置modifyTime的值.
	 *
	 * @param  modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 获取modifyBy的值.
	 *
	 * @return modifyBy
	 */
	public String getModifyBy() {
		return modifyBy;
	}

	/**
	 * 设置modifyBy的值.
	 *
	 * @param  modifyBy
	 */
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	/**
	 * 获取deleteFlag的值.
	 *
	 * @return deleteFlag
	 */
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * 设置deleteFlag的值.
	 *
	 * @param  deleteFlag
	 */
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * 获取subList的值.
	 *
	 * @return subList
	 */
	public List<MenuQueryByPageResponse> getSubList() {
		return subList;
	}

	/**
	 * 设置subList的值.
	 *
	 * @param  subList
	 */
	public void setSubList(List<MenuQueryByPageResponse> subList) {
		this.subList = subList;
	}

}