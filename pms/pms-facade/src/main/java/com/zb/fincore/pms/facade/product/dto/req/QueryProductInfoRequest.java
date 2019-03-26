package com.zb.fincore.pms.facade.product.dto.req;


import com.zb.fincore.pms.common.dto.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 查询产品详情请求参数
 *
 * @author
 * @create 2016-12-22 19:52
 */
public class QueryProductInfoRequest extends BaseRequest {

    @NotBlank(message = "产品编号不能为空")
    private String productCode;

    private String status;
    
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
