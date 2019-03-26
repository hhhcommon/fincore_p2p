package com.zb.fincore.pms.facade.product.dto.req;


import com.zb.fincore.pms.common.dto.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 更新产品信息请求参数
 *
 * @author
 * @create 2016-12-22 19:52
 */
public class UpdateProductInfoRequest extends BaseRequest {

    @NotNull(message = "产品id不可为空")
    private Long productId;

    @NotBlank(message = "产品展示名称不能为空")
    private String productDisplayName;

    @NotBlank(message = "修改人不可为空")
    private String modifyBy;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductDisplayName() {
        return productDisplayName;
    }

    public void setProductDisplayName(String productDisplayName) {
        this.productDisplayName = productDisplayName;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }
}
