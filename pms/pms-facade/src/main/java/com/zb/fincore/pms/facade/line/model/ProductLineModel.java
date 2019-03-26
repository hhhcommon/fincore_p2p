package com.zb.fincore.pms.facade.line.model;

import com.zb.fincore.pms.common.model.BaseModel;

/**
 * 功能: 产品线数据模型
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/3/29 0029 09:40
 * 版本: V1.0
 */
public class ProductLineModel extends BaseModel {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = -2462995956308616207L;

    /**
     * 产品线代码
     */
    private String lineCode;

    /**
     * 产品线名称
     */
    private String lineName;

    /**
     * 产品线展示名称
     */
    private String lineDisplayName;

    /**
     * 产品线形态代码
     */
    private String patternCode;

    /**
     * 产品线状态
     */
    private Integer status;

    /**
     * 产品线展示状态
     */
    private Integer displayStatus;

    /**
     * 产品线销售渠道代码
     */
    private String saleChannelCode;

    /**
     * 风险等级
     */
    private String riskLevel;

    /**
     * 描述
     */
    private String description;

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineDisplayName() {
        return lineDisplayName;
    }

    public void setLineDisplayName(String lineDisplayName) {
        this.lineDisplayName = lineDisplayName;
    }

    public String getPatternCode() {
        return patternCode;
    }

    public void setPatternCode(String patternCode) {
        this.patternCode = patternCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(Integer displayStatus) {
        this.displayStatus = displayStatus;
    }

    public String getSaleChannelCode() {
        return saleChannelCode;
    }

    public void setSaleChannelCode(String saleChannelCode) {
        this.saleChannelCode = saleChannelCode;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
