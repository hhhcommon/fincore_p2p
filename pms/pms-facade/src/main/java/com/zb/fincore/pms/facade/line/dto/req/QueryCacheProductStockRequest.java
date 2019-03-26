package com.zb.fincore.pms.facade.line.dto.req;

import com.zb.fincore.pms.common.dto.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 功能: 查询缓存中产品库存请求对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/12 0012 18:40
 * 版本: V1.0
 */
public class QueryCacheProductStockRequest extends BaseRequest {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 8393149560388081062L;

    /**
     * 产品线代码
     */
    @NotBlank(message = "产品编码不能为空")
    private String productCode;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
