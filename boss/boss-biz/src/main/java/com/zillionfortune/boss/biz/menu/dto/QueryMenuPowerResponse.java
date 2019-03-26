package com.zillionfortune.boss.biz.menu.dto;

import java.util.List;

public class QueryMenuPowerResponse {
	
	private String id;
	
    private String label;
    
    private String parentId;
    
    /**子节点*/
	private List<QueryMenuPowerResponse> subList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<QueryMenuPowerResponse> getSubList() {
		return subList;
	}

	public void setSubList(List<QueryMenuPowerResponse> subList) {
		this.subList = subList;
	}

}