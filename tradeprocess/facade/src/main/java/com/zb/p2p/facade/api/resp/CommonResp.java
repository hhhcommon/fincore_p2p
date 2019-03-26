package com.zb.p2p.facade.api.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by limingxin on 2017/8/31.
 */
@Data
public class CommonResp<T> implements Serializable {
    String code;
    String message;
    T data;

    public CommonResp() {
    }

    public CommonResp(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public CommonResp(String code, String message, T data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static CommonResp build(String code, String message) {
        return new CommonResp(code, message);
    }

    public static CommonResp build(String code, String message, String data) {
        return new CommonResp(code, message, data);
    }

    public static CommonResp build(String code, String message, Object data) {
        return new CommonResp(code, message, data);
    }
}
