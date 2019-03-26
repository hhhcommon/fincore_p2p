package com.zb.p2p.trade.common.model;

import com.zb.fincore.common.enums.ResultCodeEnum;
import com.zb.p2p.trade.common.enums.AppCodeEnum;

public class CommonResp<T> {

    public static final String SUCCESS_CODE = "0000";

    private String code;
    private String msg;
    private T data;

    public CommonResp() {
        this.code = SUCCESS_CODE;
        this.msg = "成功";
    }

    public CommonResp(AppCodeEnum appCodeEnum) {
        this.resetCode(appCodeEnum.getCode(), appCodeEnum.getMessage());
    }

    public CommonResp(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CommonResp(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public CommonResp(boolean success) {
        if (success) {
            this.code = SUCCESS_CODE;
            this.msg = "成功";
        } else {
            this.code = "9999";
            this.msg = "失败";
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{"
                + "code:" + code
                + ",msg:" + msg
                + ",data:" + (data == null ? null : data.toString())
                + "}";
    }

    public void success() {
        this.resetCode(AppCodeEnum._0000_SUCCESS.getCode(), AppCodeEnum._0000_SUCCESS.getMessage());
    }

    public void error() {
        this.resetCode(AppCodeEnum._9999_ERROR.getCode(), AppCodeEnum._1111_ERROR.getMessage());
    }

    public void resetCode(String code, String message) {
        this.code = code;
        this.msg = message;
    }

    public static <T> CommonResp<T> build(String code, String msg, T data) {
        return new CommonResp(code, msg, data);
    }

    public static <T> CommonResp<T> success(T data) {
        return new CommonResp(ResultCodeEnum.SUCCESS.code(), ResultCodeEnum.SUCCESS.desc(), data);
    }

    public static CommonResp failure() {
        return new CommonResp(ResultCodeEnum.FAIL.code(), ResultCodeEnum.FAIL.desc(), null);
    }

    public boolean whetherSuccess() {
        return SUCCESS_CODE.equals(this.getCode());
    }
}
