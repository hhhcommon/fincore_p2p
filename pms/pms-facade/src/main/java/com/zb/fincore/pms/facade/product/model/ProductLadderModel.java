package com.zb.fincore.pms.facade.product.model;

import com.zb.fincore.pms.common.model.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能: 产品阶梯信息数据模型
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/6 0006 17:13
 * 版本: V1.0
 */
public class ProductLadderModel extends BaseModel {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = -4353815726449211422L;

    /**
     * 产品主键
     */
    private Long productId;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 投资期限
     */
    private Integer investPeriod;

    /**
     * 投资循环周期
     */
    private Integer investPeriodLoopUnit;

    /**
     * 投资总循环次数
     */
    private Integer investPeriodLoopCount;

    /**
     * 当前循环轮次
     */
    private Integer investPeriodLoopIndex;

    /**
     * 当期收益率
     */
    private BigDecimal yieldRate;

    /**
     * 手续费率
     */
    private BigDecimal poundage;

    /**
     * 阶梯起息时间
     */
    private Date valueStartTime;

    /**
     * 阶梯止息时间
     */
    private Date valueEndTime;

    /**
     * 下一开放赎回时间
     */
    private Date nextRedeemTime;

    /**
     * 下一回款时间
     */
    private Date nextRepayTime;

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

    public Integer getInvestPeriod() {
        return investPeriod;
    }

    public void setInvestPeriod(Integer investPeriod) {
        this.investPeriod = investPeriod;
    }

    public Integer getInvestPeriodLoopUnit() {
        return investPeriodLoopUnit;
    }

    public void setInvestPeriodLoopUnit(Integer investPeriodLoopUnit) {
        this.investPeriodLoopUnit = investPeriodLoopUnit;
    }

    public Integer getInvestPeriodLoopCount() {
        return investPeriodLoopCount;
    }

    public void setInvestPeriodLoopCount(Integer investPeriodLoopCount) {
        this.investPeriodLoopCount = investPeriodLoopCount;
    }

    public Integer getInvestPeriodLoopIndex() {
        return investPeriodLoopIndex;
    }

    public void setInvestPeriodLoopIndex(Integer investPeriodLoopIndex) {
        this.investPeriodLoopIndex = investPeriodLoopIndex;
    }

    public BigDecimal getYieldRate() {
        return yieldRate;
    }

    public void setYieldRate(BigDecimal yieldRate) {
        this.yieldRate = yieldRate;
    }

    public Date getValueStartTime() {
        return valueStartTime;
    }

    public void setValueStartTime(Date valueStartTime) {
        this.valueStartTime = valueStartTime;
    }

    public Date getValueEndTime() {
        return valueEndTime;
    }

    public void setValueEndTime(Date valueEndTime) {
        this.valueEndTime = valueEndTime;
    }

    public Date getNextRedeemTime() {
        return nextRedeemTime;
    }

    public void setNextRedeemTime(Date nextRedeemTime) {
        this.nextRedeemTime = nextRedeemTime;
    }

    public Date getNextRepayTime() {
        return nextRepayTime;
    }

    public void setNextRepayTime(Date nextRepayTime) {
        this.nextRepayTime = nextRepayTime;
    }

    public BigDecimal getPoundage() {
        return poundage;
    }

    public void setPoundage(BigDecimal poundage) {
        this.poundage = poundage;
    }
}
