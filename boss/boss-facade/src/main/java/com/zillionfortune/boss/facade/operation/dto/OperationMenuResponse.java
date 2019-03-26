package com.zillionfortune.boss.facade.operation.dto;

import java.io.Serializable;
import java.util.List;

public class OperationMenuResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	/**名称*/
	private String name;
	/**上级节点ID*/
	private Integer parentId;
	/**菜单连接*/
	private String referUrl;
	/**编码*/
	private String code;
	/**显示顺序*/
	private Integer displayOrder;
	/**是否有效*/
	private Integer isValid;
	/**图标*/
	private String icon;
	/**子节点*/
	private List<OperationMenuResponse> children;

	public List<OperationMenuResponse> getChildren() {
		return children;
	}

	public void setChildren(List<OperationMenuResponse> children) {
		this.children = children;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

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

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getReferUrl() {
		return referUrl;
	}

	public void setReferUrl(String referUrl) {
		this.referUrl = referUrl;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
