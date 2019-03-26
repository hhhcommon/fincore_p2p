package com.zb.fincore.pms.facade.product.dto.resp;

import com.zb.fincore.pms.common.dto.BaseResponse;

import java.math.BigDecimal;

/**
 * 功能: 冻结产品库存响应
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/12 0012 15:13
 * 版本: V1.0
 */
public class FreezeProductStockResponse extends BaseResponse {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 7680421907511305071L;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 外部编号
     */
    private String refNo;

    /**
     * 冻结金额
     */
    private BigDecimal changeAmount;

    /**
     * 处理状态,ChangeProductStockStatusEnum
     */
    private Integer status;

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

    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public boolean isFreezeSuccess() {
        return (this.isSuccess() && this.status == 2);
    }
}
