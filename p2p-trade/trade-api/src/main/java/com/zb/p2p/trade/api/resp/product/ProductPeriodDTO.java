package com.zb.p2p.trade.api.resp.product;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能: 产品期限信息数据模型
 * 日期: 2017/4/1 0001 10:17
 * 版本: V1.0
 */
public class ProductPeriodDTO implements Serializable{

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = -5410424256635488285L;

    /**
     * 产品主键
     */
    private Long productId;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 预期上线时间
     */
    private Date expectOnlineTime;

    /**
     * 实际上线时间
     */
    private Date onlineTime;

    /**
     * 预期下线时间
     */
    private Date expectOfflineTime;

    /**
     * 实际下线时间
     */
    private Date offlineTime;

    /**
     * 募集起始时间
     */
    private Date saleStartTime;

    /**
     * 募集截止时间
     */
    private Date saleEndTime;

    /**
     * 实际募集完成时间
     */
    private Date saleOutTime;

    /**
     * 预期成立时间
     */
    private Date expectEstablishTime;

    /**
     * 实际成立时间
     */
    private Date establishTime;

    /**
     * 起息时间
     */
    private Date valueTime;

    /**
     * 预期到期日期
     */
    private Date expectExpireTime;

    /**
     * 到期时间
     */
    private Date expireTime;

    /**
     * 预期结清时间
     */
    private Date expectClearTime;

    /**
     * 实际结清时间
     */
    private Date clearTime;

    /**
     * 投资期限
     */
    private Integer investPeriod;

    /**
     * 资产期限单位(1:天 2：周 3：月 4:年)
     */
    private Integer investPeriodUnit;

    /**
     * 投资期限描述,如3个月期
     */
    private String investPeriodDescription;

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

    public Date getExpectOnlineTime() {
        return expectOnlineTime;
    }

    public void setExpectOnlineTime(Date expectOnlineTime) {
        this.expectOnlineTime = expectOnlineTime;
    }

    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Date getExpectOfflineTime() {
        return expectOfflineTime;
    }

    public void setExpectOfflineTime(Date expectOfflineTime) {
        this.expectOfflineTime = expectOfflineTime;
    }

    public Date getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(Date offlineTime) {
        this.offlineTime = offlineTime;
    }

    public Date getSaleStartTime() {
        return saleStartTime;
    }

    public void setSaleStartTime(Date saleStartTime) {
        this.saleStartTime = saleStartTime;
    }

    public Date getSaleEndTime() {
        return saleEndTime;
    }

    public void setSaleEndTime(Date saleEndTime) {
        this.saleEndTime = saleEndTime;
    }

    public Date getSaleOutTime() {
        return saleOutTime;
    }

    public void setSaleOutTime(Date saleOutTime) {
        this.saleOutTime = saleOutTime;
    }

    public Date getExpectEstablishTime() {
        return expectEstablishTime;
    }

    public void setExpectEstablishTime(Date expectEstablishTime) {
        this.expectEstablishTime = expectEstablishTime;
    }

    public Date getEstablishTime() {
        return establishTime;
    }

    public void setEstablishTime(Date establishTime) {
        this.establishTime = establishTime;
    }

    public Date getValueTime() {
        return valueTime;
    }

    public void setValueTime(Date valueTime) {
        this.valueTime = valueTime;
    }

    public Date getExpectExpireTime() {
        return expectExpireTime;
    }

    public void setExpectExpireTime(Date expectExpireTime) {
        this.expectExpireTime = expectExpireTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getExpectClearTime() {
        return expectClearTime;
    }

    public void setExpectClearTime(Date expectClearTime) {
        this.expectClearTime = expectClearTime;
    }

    public Date getClearTime() {
        return clearTime;
    }

    public void setClearTime(Date clearTime) {
        this.clearTime = clearTime;
    }

    public Integer getInvestPeriod() {
        return investPeriod;
    }

    public void setInvestPeriod(Integer investPeriod) {
        this.investPeriod = investPeriod;
    }

    public Integer getInvestPeriodUnit() {
        return investPeriodUnit;
    }

    public void setInvestPeriodUnit(Integer investPeriodUnit) {
        this.investPeriodUnit = investPeriodUnit;
    }

    public String getInvestPeriodDescription() {
        return investPeriodDescription;
    }

    public void setInvestPeriodDescription(String investPeriodDescription) {
        this.investPeriodDescription = investPeriodDescription;
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
}
