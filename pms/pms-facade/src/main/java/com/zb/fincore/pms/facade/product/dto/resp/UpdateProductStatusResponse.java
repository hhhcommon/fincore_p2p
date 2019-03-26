package com.zb.fincore.pms.facade.product.dto.resp;


import com.zb.fincore.pms.common.dto.BaseResponse;

/**
 * 产品状态变更响应参数对象
 *
 * @author
 * @create 2016-12-22 19:53
 */
public class UpdateProductStatusResponse extends BaseResponse {

    public UpdateProductStatusResponse() {
    }

    public UpdateProductStatusResponse(String respCode, String resultMsg) {
        super(respCode, resultMsg);
    }

    public UpdateProductStatusResponse(String respCode, String resultCode, String resultMsg) {
        super(respCode, resultCode, resultMsg);
    }

    public static UpdateProductStatusResponse build() {
        return new UpdateProductStatusResponse();
    }

    public static UpdateProductStatusResponse build(String respCode, String resultCode, String resultMsg) {
        return new UpdateProductStatusResponse(respCode, resultCode, resultMsg);
    }
}
