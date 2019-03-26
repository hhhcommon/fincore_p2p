package com.zb.fincore.pms.facade.product.dto.req;


import com.zb.fincore.pms.common.dto.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 产品同步状态变更请求参数
 *
 * @author
 * @create 2016-12-22 19:52
 */
public class UpdateProductSyncStatusRequest extends BaseRequest {

    @NotBlank(message = "产品编号不能为空")
    private String productCode;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

}
