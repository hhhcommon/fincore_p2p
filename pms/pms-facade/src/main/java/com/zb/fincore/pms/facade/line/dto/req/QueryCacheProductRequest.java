package com.zb.fincore.pms.facade.line.dto.req;

import com.zb.fincore.pms.common.dto.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 功能: 查询产品详情请求参数
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/13 0013 13:41
 * 版本: V1.0
 */
public class QueryCacheProductRequest extends BaseRequest {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = -5075874254782325555L;

    @NotBlank(message = "产品编号不能为空")
    private String productCode;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
