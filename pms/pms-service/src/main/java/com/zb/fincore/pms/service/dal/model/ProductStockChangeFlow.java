package com.zb.fincore.pms.service.dal.model;

import com.zb.fincore.pms.common.model.BaseDo;

import java.math.BigDecimal;

/**
 * 功能: 产品库存变更流水数据持久对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/1 0001 10:00
 * 版本: V1.0
 */
public class ProductStockChangeFlow extends BaseDo {

    /**
     * 产品主键
     */
    private Long productId;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 外部编号
     */
    private String refNo;

    /**
     * 影响类型(1:冻结,2:占用,3:释放,4:赎回)
     */
    private Integer changeType;

    /**
     * 影响金额
     */
    private BigDecimal changeAmount;

    /**
     * 变化前冻结金额
     */
    private BigDecimal frozenAmountBefore;

    /**
     * 变化后冻结金额
     */
    private BigDecimal frozenAmountAfter;

    /**
     * 变化前已售金额
     */
    private BigDecimal saleAmountBefore;

    /**
     * 变化后已售金额
     */
    private BigDecimal saleAmountAfter;

    /**
     * 变化前库存金额
     */
    private BigDecimal stockAmountBefore;

    /**
     * 变化后库存金额
     */
    private BigDecimal stockAmountAfter;

    /**
     * 变化前已赎回金额
     */
    private BigDecimal redeemAmountBefore;

    /**
     * 变化后已赎回金额
     */
    private BigDecimal redeemAmountAfter;

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

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }

    public BigDecimal getFrozenAmountBefore() {
        return frozenAmountBefore;
    }

    public void setFrozenAmountBefore(BigDecimal frozenAmountBefore) {
        this.frozenAmountBefore = frozenAmountBefore;
    }

    public BigDecimal getFrozenAmountAfter() {
        return frozenAmountAfter;
    }

    public void setFrozenAmountAfter(BigDecimal frozenAmountAfter) {
        this.frozenAmountAfter = frozenAmountAfter;
    }

    public BigDecimal getSaleAmountBefore() {
        return saleAmountBefore;
    }

    public void setSaleAmountBefore(BigDecimal saleAmountBefore) {
        this.saleAmountBefore = saleAmountBefore;
    }

    public BigDecimal getSaleAmountAfter() {
        return saleAmountAfter;
    }

    public void setSaleAmountAfter(BigDecimal saleAmountAfter) {
        this.saleAmountAfter = saleAmountAfter;
    }

    public BigDecimal getStockAmountBefore() {
        return stockAmountBefore;
    }

    public void setStockAmountBefore(BigDecimal stockAmountBefore) {
        this.stockAmountBefore = stockAmountBefore;
    }

    public BigDecimal getStockAmountAfter() {
        return stockAmountAfter;
    }

    public void setStockAmountAfter(BigDecimal stockAmountAfter) {
        this.stockAmountAfter = stockAmountAfter;
    }

    public BigDecimal getRedeemAmountBefore() {
        return redeemAmountBefore;
    }

    public void setRedeemAmountBefore(BigDecimal redeemAmountBefore) {
        this.redeemAmountBefore = redeemAmountBefore;
    }

    public BigDecimal getRedeemAmountAfter() {
        return redeemAmountAfter;
    }

    public void setRedeemAmountAfter(BigDecimal redeemAmountAfter) {
        this.redeemAmountAfter = redeemAmountAfter;
    }
}
