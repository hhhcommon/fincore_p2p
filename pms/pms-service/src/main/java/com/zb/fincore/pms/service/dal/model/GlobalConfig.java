package com.zb.fincore.pms.service.dal.model;

/**
 * 功能: 序列数据持久对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/3/29 0029 09:36
 * 版本: V1.0
 */
public class GlobalConfig {

    private Integer id;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 分组名称描述
     */
    private String groupNameDesc;

    /**
     * 属性名称
     */
    private String propertyName;

    /**
     * 属性值
     */
    private String propertyValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getGroupNameDesc() {
        return groupNameDesc;
    }

    public void setGroupNameDesc(String groupNameDesc) {
        this.groupNameDesc = groupNameDesc;
    }
}