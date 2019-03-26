package com.zb.fincore.pms.facade.product.dto.req;


import com.zb.fincore.pms.common.dto.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 产品募集状态变更请求参数
 *
 * @author
 * @create 2017-4-11 10:05:44
 */
public class UpdateProductCollectStatusRequest extends BaseRequest {

    @NotBlank(message = "产品编号不能为空")
    private String productCode;

    /**
     * 募集状态
     */
    private Integer collectStatus;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(Integer collectStatus) {
        this.collectStatus = collectStatus;
    }
}
