package com.zb.fincore.pms.facade.product.dto.req;


import com.zb.fincore.pms.common.dto.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 产品库存变更请求参数
 *
 * @author
 * @create 2016-12-22 19:52
 */
public class ChangeProductStockRequest extends BaseRequest {

    @NotNull(message = "产品编码不能为空")
    private String productCode;

    @NotNull(message = "影响类型不能为空")
    private Integer changeType;

    @NotBlank(message = "关联订单号不能为空")
    private String refNo;

    /**
     * 变更金额
     */
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
}
