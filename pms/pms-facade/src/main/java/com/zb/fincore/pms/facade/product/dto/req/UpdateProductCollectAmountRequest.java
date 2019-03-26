package com.zb.fincore.pms.facade.product.dto.req;


import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.zb.fincore.pms.common.dto.BaseRequest;

/**
 * 产品募集金额变更请求参数
 *
 * @author
 * @create 2016-12-22 19:52
 */
public class UpdateProductCollectAmountRequest extends BaseRequest {
	@NotNull(message = "产品编号能为空")
    private String productCode;
    @NotNull(message = "产品募集金额不能为空")
    @DecimalMin(value = "0.00", message = "产品募集金额必须大于等于零")
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
