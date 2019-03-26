package com.zb.fincore.pms.facade.product.dto.req;

import org.hibernate.validator.constraints.NotBlank;

import com.zb.fincore.pms.common.dto.BaseRequest;

/**
 * 功能: 解冻产品库存请求
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/11 0011 10:47
 * 版本: V1.0
 */
public class UnFreezeProductStockRequest extends BaseRequest {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = -4246572505185932501L;

    @NotBlank(message = "产品编号不能为空")
    private String productCode;

    @NotBlank(message = "关联订单号不能为空")
    private String refNo;


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

}
