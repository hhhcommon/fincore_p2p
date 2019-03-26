package com.zillionfortune.boss.dal.entity;

public class RolePower  extends BaseEntity {

    private Integer roleId;

    private Integer powerId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPowerId() {
        return powerId;
    }

    public void setPowerId(Integer powerId) {
        this.powerId = powerId;
    }

}