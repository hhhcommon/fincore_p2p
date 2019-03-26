package com.zb.txs.p2p.business.order.response;

import com.zb.txs.p2p.business.enums.order.ResponseCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by liguoliang on 2019/1/9.
 */
@Data
public class CommonResponse<T> implements Serializable {
    String code;
    String msg;
    T data;

    public CommonResponse() {
    }

    public CommonResponse(String code, String msg, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> CommonResponse<T> build(String code, String msg, T data) {
        return new CommonResponse(code, msg, data);
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse(ResponseCodeEnum.RESPONSE_SUCCESS.getCode(), ResponseCodeEnum.RESPONSE_SUCCESS.getDesc(), data);
    }

    public static CommonResponse failure() {
        return new CommonResponse(ResponseCodeEnum.RESPONSE_FAIL.getCode(), ResponseCodeEnum.RESPONSE_FAIL.getDesc(), null);
    }

}
