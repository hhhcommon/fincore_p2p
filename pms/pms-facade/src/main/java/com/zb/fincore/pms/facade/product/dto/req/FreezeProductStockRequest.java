package com.zb.fincore.pms.facade.product.dto.req;

import com.zb.fincore.pms.common.dto.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 功能: 冻结产品库存请求
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/11 0011 10:47
 * 版本: V1.0
 */
public class FreezeProductStockRequest extends BaseRequest {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = -4246572505185932501L;

    @NotBlank(message = "产品编号不能为空")
    private String productCode;

    @NotBlank(message = "关联订单号不能为空")
    private String refNo;

    @NotNull(message = "冻结金额不能为空")
    @DecimalMin(value = "0.00", message = "冻结金额必须大于零")
    private BigDecimal changeAmount;

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
}
