package com.zb.fincore.pms.facade.product.model;

import com.zb.fincore.pms.common.model.BaseModel;

import java.math.BigDecimal;

/**
 * 功能: 产品库存变更请求数据模型
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/6 0006 10:08
 * 版本: V1.0
 */
public class ProductStockChangeReqModel extends BaseModel {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = -1820999550070825823L;

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
     * 变更状态(1:处理中,2:处理成功,3:处理失败)
     */
    private Integer status;

    /**
     * 备注
     */
    private String memo;

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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
