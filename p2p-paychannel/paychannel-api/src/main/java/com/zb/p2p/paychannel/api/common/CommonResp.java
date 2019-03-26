package com.zb.p2p.paychannel.api.common;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by limingxin on 2017/8/31.
 */
@Data
public class CommonResp<T> implements Serializable {
    String code;
    String msg;
    T data;

    public CommonResp() {
    }

    public CommonResp(String code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public CommonResp(String code, String msg, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static CommonResp build(String code, String msg) {
        return new CommonResp(code, msg);
    }

    public static CommonResp build(String code, String msg, String data) {
        return new CommonResp(code, msg, data);
    }

    public static CommonResp build(String code, String msg, Object data) {
        return new CommonResp(code, msg, data);
    }
}
