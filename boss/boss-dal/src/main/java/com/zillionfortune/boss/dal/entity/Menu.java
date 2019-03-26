package com.zillionfortune.boss.dal.entity;

import java.util.List;

public class Menu  extends BaseEntity {
	
    private String code;

    private String name;

    private Integer parentId;

    private Integer displayOrder;

    private String memo;

    private String referUrl;

    private Integer isValid;

    private String icon;
    
	/**子节点*/
	private List<Menu> subList;
	
	public List<Menu> getSubList() {
		return subList;
	}

	public void setSubList(List<Menu> subList) {
		this.subList = subList;
	}

	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getReferUrl() {
        return referUrl;
    }

    public void setReferUrl(String referUrl) {
        this.referUrl = referUrl == null ? null : referUrl.trim();
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
        this.icon = icon == null ? null : icon.trim();
    }
}