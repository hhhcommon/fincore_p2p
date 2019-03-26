package com.zb.p2p.paychannel.common.model;

import com.zb.p2p.paychannel.common.enums.AppCodeEnum;

public class BaseRes<T> {

    public static final String SUCCESS_CODE = "0000";

    private String code;
    private String message;
    private T data;

    public BaseRes() {
        this.code = SUCCESS_CODE;
        this.message = "成功";
    }

    public BaseRes(AppCodeEnum appCodeEnum) {
        this.resetCode(appCodeEnum);
    }

    public BaseRes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseRes(boolean success) {
        if (success) {
            this.code = SUCCESS_CODE;
            this.message = "成功";
        } else {
            this.code = "9999";
            this.message = "失败";
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void failure(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void success(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "{"
                + "code:" + code
                + ",message:" + message
                + ",data:" + (data == null ? null : data.toString())
                + "}";
    }

    public void success() {
        this.resetCode(AppCodeEnum.RESPONSE_SUCCESS);
    }

    public void error() {
        this.resetCode(AppCodeEnum.RESPONSE_FAIL);
    }

    public void resetCode(AppCodeEnum appCodeEnum) {
        this.code = appCodeEnum.getCode();
        this.message = appCodeEnum.getMessage();
    }

    public boolean whetherSuccess() {
        return SUCCESS_CODE.equals(this.getCode());
    }
}
