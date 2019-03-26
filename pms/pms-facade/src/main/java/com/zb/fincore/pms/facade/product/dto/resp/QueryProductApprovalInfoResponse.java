package com.zb.fincore.pms.facade.product.dto.resp;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.facade.product.model.ProductApprovalModel;
import com.zb.fincore.pms.facade.product.model.ProductModel;

import java.util.List;

/**
 * 产品审核信息查询响应对象
 *
 * @author
 * @create 2017-05-05 10:46
 */
public class QueryProductApprovalInfoResponse extends BaseResponse{

    /**
     * 产品展示名称
     */
    private String productDisplayName;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 销售渠道代码
     */
    private String saleChannelCode;

    /**
     * 产品审核信息信息
     */
    private List<ProductApprovalModel> dataList;


    public QueryProductApprovalInfoResponse(){
    }

    public QueryProductApprovalInfoResponse(String respCode, String resultMsg) {
        super(respCode, resultMsg);
    }

    public QueryProductApprovalInfoResponse(String respCode, String resultCode, String resultMsg) {
        super(respCode, resultCode, resultMsg);
    }

    public static QueryProductApprovalInfoResponse build() {
        return new QueryProductApprovalInfoResponse();
    }

    public static QueryProductApprovalInfoResponse build(String respCode, String resultCode, String resultMsg) {
        return new QueryProductApprovalInfoResponse(respCode, resultCode, resultMsg);
    }

    public String getProductDisplayName() {
        return productDisplayName;
    }

    public void setProductDisplayName(String productDisplayName) {
        this.productDisplayName = productDisplayName;
    }

    public String getSaleChannelCode() {
        return saleChannelCode;
    }

    public void setSaleChannelCode(String saleChannelCode) {
        this.saleChannelCode = saleChannelCode;
    }

    public List<ProductApprovalModel> getDataList() {
        return dataList;
    }

    public void setDataList(List<ProductApprovalModel> dataList) {
        this.dataList = dataList;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
