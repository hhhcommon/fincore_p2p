package com.zillionfortune.boss.web.controller.pms.vo;

import com.zb.fincore.pms.common.dto.PageQueryRequest;

/**
 * 功能: 查询产品线列表请求对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/3/29 0029 14:00
 * 版本: V1.0
 */
public class QueryProductLineListVO extends PageQueryRequest {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1706050275824192057L;

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
    private String salesChannelCode;

    /**
     * 风险等级
     */
    private String riskLevel;

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

    public String getSalesChannelCode() {
        return salesChannelCode;
    }

    public void setSalesChannelCode(String salesChannelCode) {
        this.salesChannelCode = salesChannelCode;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}
