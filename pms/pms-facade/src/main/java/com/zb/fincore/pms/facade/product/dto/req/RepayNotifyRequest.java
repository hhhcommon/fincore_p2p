package com.zb.fincore.pms.facade.product.dto.req;


import com.zb.fincore.pms.common.dto.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

/**
 * 回款通知消息请求
 *
 * @author
 * @create 2016-12-22 19:52
 */
public class RepayNotifyRequest extends BaseRequest {

    /**
     * 产品编号
     */
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
