package com.zb.fincore.pms.service.dal.model;

import com.zb.fincore.pms.common.model.BaseDo;

import java.math.BigDecimal;

/**
 * 功能: 产品库存信息数据持久对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/1 0001 09:53
 * 版本: V1.0
 */
public class ProductStock extends BaseDo {

    /**
     * 产品主键
     */
    private Long productId;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品冻结金额(下单未确认金额)
     */
    private BigDecimal frozenAmount;

    /**
     * 已售金额
     */
    private BigDecimal saleAmount;

    /**
     * 剩余库存金额(未售金额)
     */
    private BigDecimal stockAmount;

    /**
     * 已赎回金额
     */
    private BigDecimal redeemAmount;

    /**
     * 库存版本号,乐观锁
     */
    private Integer version;

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

    public BigDecimal getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    public BigDecimal getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(BigDecimal stockAmount) {
        this.stockAmount = stockAmount;
    }

    public BigDecimal getRedeemAmount() {
        return redeemAmount;
    }

    public void setRedeemAmount(BigDecimal redeemAmount) {
        this.redeemAmount = redeemAmount;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
