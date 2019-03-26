package com.zb.fincore.pms.facade.product.model;

import com.zb.fincore.pms.common.model.BaseModel;

import java.math.BigDecimal;

/**
 * 功能: 产品投资收益信息数据模型
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/1 0001 09:51
 * 版本: V1.0
 */
public class ProductProfitModel extends BaseModel {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = -3888308244236055375L;

    /**
     * 产品主键
     */
    private Long productId;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 起投金额
     */
    private BigDecimal minInvestAmount;

    /**
     * 单笔投资限额
     */
    private BigDecimal singleMaxInvestAmount;

    /**
     * 个人最大投资限额
     */
    private BigDecimal maxInvestAmount;

    /**
     * 最低可持有金额
     */
    private BigDecimal minHoldAmount;

    /**
     * 递增金额(步长)
     */
    private BigDecimal increaseAmount;

    /**
     * 年基础计息周期(360, 365, 366三种枚举值)
     */
    private Integer basicInterestsPeriod;

    /**
     * 产品成立条件
     */
    private String establishCondition;

    /**
     * 计量单位(1:份额, 2:人民币元)
     */
    private Integer unit;

    /**
     * 收益方式
     */
    private Integer profitType;

    /**
     * 计息方式code
     */
    private Integer calculateInvestType;

    /**
     * 计息方式code desc
     */
    private String calculateInvestTypeDesc;

    /**
     * 最低预期收益率
     */
    private BigDecimal minYieldRate;

    /**
     * 最高预期收益率
     */
    private BigDecimal maxYieldRate;

    /**
     * 当期利率
     */
    private BigDecimal currentYieldRate;

    /**
     * 加息利率
     */
    private BigDecimal addYieldRate;

    /**
     * 浮动利率
     */
    private BigDecimal floatingYieldRate;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public BigDecimal getMinInvestAmount() {
        return minInvestAmount;
    }

    public void setMinInvestAmount(BigDecimal minInvestAmount) {
        this.minInvestAmount = minInvestAmount;
    }

    public BigDecimal getSingleMaxInvestAmount() {
        return singleMaxInvestAmount;
    }

    public void setSingleMaxInvestAmount(BigDecimal singleMaxInvestAmount) {
        this.singleMaxInvestAmount = singleMaxInvestAmount;
    }

    public BigDecimal getMaxInvestAmount() {
        return maxInvestAmount;
    }

    public void setMaxInvestAmount(BigDecimal maxInvestAmount) {
        this.maxInvestAmount = maxInvestAmount;
    }

    public BigDecimal getIncreaseAmount() {
        return increaseAmount;
    }

    public void setIncreaseAmount(BigDecimal increaseAmount) {
        this.increaseAmount = increaseAmount;
    }

    public Integer getBasicInterestsPeriod() {
        return basicInterestsPeriod;
    }

    public void setBasicInterestsPeriod(Integer basicInterestsPeriod) {
        this.basicInterestsPeriod = basicInterestsPeriod;
    }

    public String getEstablishCondition() {
        return establishCondition;
    }

    public void setEstablishCondition(String establishCondition) {
        this.establishCondition = establishCondition;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Integer getProfitType() {
        return profitType;
    }

    public void setProfitType(Integer profitType) {
        this.profitType = profitType;
    }

    public Integer getCalculateInvestType() {
        return calculateInvestType;
    }

    public void setCalculateInvestType(Integer calculateInvestType) {
        this.calculateInvestType = calculateInvestType;
    }

    public BigDecimal getMinYieldRate() {
        return minYieldRate;
    }

    public void setMinYieldRate(BigDecimal minYieldRate) {
        this.minYieldRate = minYieldRate;
    }

    public BigDecimal getMaxYieldRate() {
        return maxYieldRate;
    }

    public void setMaxYieldRate(BigDecimal maxYieldRate) {
        this.maxYieldRate = maxYieldRate;
    }

    public BigDecimal getCurrentYieldRate() {
        return currentYieldRate;
    }

    public void setCurrentYieldRate(BigDecimal currentYieldRate) {
        this.currentYieldRate = currentYieldRate;
    }

    public BigDecimal getAddYieldRate() {
        return addYieldRate;
    }

    public void setAddYieldRate(BigDecimal addYieldRate) {
        this.addYieldRate = addYieldRate;
    }

    public BigDecimal getMinHoldAmount() {
        return minHoldAmount;
    }

    public void setMinHoldAmount(BigDecimal minHoldAmount) {
        this.minHoldAmount = minHoldAmount;
    }

    public BigDecimal getFloatingYieldRate() {
        return floatingYieldRate;
    }

    public void setFloatingYieldRate(BigDecimal floatingYieldRate) {
        this.floatingYieldRate = floatingYieldRate;
    }

    public String getCalculateInvestTypeDesc() {
        return calculateInvestTypeDesc;
    }

    public void setCalculateInvestTypeDesc(String calculateInvestTypeDesc) {
        this.calculateInvestTypeDesc = calculateInvestTypeDesc;
    }
}
