package com.zb.fincore.pms.facade.product.dto.resp;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.facade.product.model.*;

/**
 * 产品注册响应对象
 *
 * @author
 * @create 2017-04-10 10:46
 */
public class QueryProductInfoResponse extends BaseResponse{

    /**
     * 产品信息
     */
    private ProductModel productModel;

    public ProductModel getProductModel() {
        return productModel;
    }

    public QueryProductInfoResponse(){
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
    }

    public QueryProductInfoResponse(String respCode, String resultMsg) {
        super(respCode, resultMsg);
    }

    public QueryProductInfoResponse(String respCode, String resultCode, String resultMsg) {
        super(respCode, resultCode, resultMsg);
    }

    public static QueryProductInfoResponse build() {
        return new QueryProductInfoResponse();
    }

    public static QueryProductInfoResponse build(String respCode, String resultCode, String resultMsg) {
        return new QueryProductInfoResponse(respCode, resultCode, resultMsg);
    }
}
