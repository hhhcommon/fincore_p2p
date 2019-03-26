package com.zb.fincore.pms.facade.product.dto.req;

import com.zb.fincore.pms.common.dto.BaseRequest;

import java.util.List;

/**
 * 功能: 货架系统 产品作废 请求对象
 * 创建: kaiyun
 * 日期: 2017/4/11 0011 10:47
 */
public class CancelProductRequest extends BaseRequest {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = -4246572505185932501L;

    private List<String> productCodeList;

    public List<String> getProductCodeList() {
        return productCodeList;
    }

    public void setProductCodeList(List<String> productCodeList) {
        this.productCodeList = productCodeList;
    }
}
