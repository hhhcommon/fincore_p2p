package com.zb.fincore.pms.common.model;

import java.util.Date;

/**
 * 功能: 数据访问类基类
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/1/10 0010 09:59
 * 版本: V1.0
 */
public class BaseDo {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 最后修改时间
     */
    private Date modifyTime;

    /**
     * 最后修改人
     */
    private String modifyBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }
}
