package com.zb.fincore.pms.facade.product.dto.resp;

import com.zb.fincore.pms.common.dto.BaseResponse;

import java.math.BigDecimal;

/**
 * 功能: 产品库存变更响应参数
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/14 0014 15:51
 * 版本: V1.0
 */
public class ChangeProductStockResponse extends BaseResponse {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = -1889276895066921482L;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 外部编号
     */
    private String refNo;

    /**
     * 变更类型
     */
    private Integer changeType;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
