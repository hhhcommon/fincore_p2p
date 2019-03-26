package com.zb.fincore.pms.facade.product.dto.req;


import com.zb.fincore.pms.common.dto.PageQueryRequest;

/**
 * 产品库存变更流水查询请求参数
 *
 * @author
 * @create 2016-12-22 19:52
 */
public class QueryProductStockChangeFlowRequest extends PageQueryRequest {

    private String productCode;

    private String refNo;

    private Integer changeType;

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

    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }
}
