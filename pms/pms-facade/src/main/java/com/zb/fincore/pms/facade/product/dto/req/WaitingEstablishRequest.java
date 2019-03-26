package com.zb.fincore.pms.facade.product.dto.req;


import com.zb.fincore.pms.common.dto.BaseRequest;

import java.math.BigDecimal;

/**
 * 募集结束通知消息请求
 *
 * @author
 * @create 2016-12-22 19:52
 */
public class WaitingEstablishRequest extends BaseRequest {

    private String productCode;

    /**
     * 实际募集金额
     */
    private BigDecimal collectAmount;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public BigDecimal getCollectAmount() {
        return collectAmount;
    }

    public void setCollectAmount(BigDecimal collectAmount) {
        this.collectAmount = collectAmount;
    }
}
