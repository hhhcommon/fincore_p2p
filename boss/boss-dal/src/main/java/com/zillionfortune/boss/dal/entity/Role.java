package com.zillionfortune.boss.dal.entity;

public class Role extends BaseEntity {
	
    private String name;
    
    private String signLevel;

    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
    public String getSignLevel() {
		return signLevel;
	}

	public void setSignLevel(String signLevel) {
		this.signLevel = signLevel;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}