package com.zb.fincore.pms.facade.product.dto.resp;

import com.zb.fincore.pms.common.dto.BaseResponse;

/**
 * 产品注册响应对象
 *
 * @author
 * @create 2017-04-10 10:46
 */
public class RegisterProductResponse extends BaseResponse{

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 产品编码
     */
    private String productCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
