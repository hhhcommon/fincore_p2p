package com.zb.fincore.pms.facade.product.dto.req;

import com.zb.fincore.pms.common.dto.PageQueryRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 查询产品审核列表信息请求对象
 *
 * @author
 * @create 2017-05-03 17:50
 */
public class QueryProductApprovalListRequest extends PageQueryRequest {

    @NotBlank(message = "产品编号不能为空")
    private String productCode;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
