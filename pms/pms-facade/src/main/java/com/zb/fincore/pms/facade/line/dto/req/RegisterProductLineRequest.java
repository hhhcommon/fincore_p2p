package com.zb.fincore.pms.facade.line.dto.req;

import com.zb.fincore.pms.common.dto.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 功能: 注册产品线请求对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/3/28 0028 15:26
 * 版本: V1.0
 */
public class RegisterProductLineRequest extends BaseRequest {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = -4464984595397048222L;

    /**
     * 产品线名称
     */
    @NotBlank(message = "产品线名称不能为空")
    private String lineName;

    /**
     * 产品线展示名称
     */
    @NotBlank(message = "产品线展示名称不能为空")
    private String lineDisplayName;

    /**
     * 产品线形态代码
     */
    @NotBlank(message = "产品线形态代码不能为空")
    private String patternCode;

    /**
     * 产品线的渠道
     */
    @NotBlank(message = "产品线销售渠道代码不能为空")
    private String saleChannelCode;

    /**
     * 产品线风险等级
     */
    @NotBlank(message = "产品线风险等级不能为空")
    private String riskLevel;

    /**
     * 产品线描述
     */
    private String description;

    /**
     * 创建人
     */
    @NotBlank(message = "创建人不能为空")
    private String createBy;

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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
