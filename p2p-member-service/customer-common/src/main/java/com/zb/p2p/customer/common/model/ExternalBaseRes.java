package com.zb.p2p.customer.common.model;

import com.zb.p2p.customer.common.enums.AppCodeEnum;

public class ExternalBaseRes<T> {

    public static final String SUCCESS_CODE = "0000";

    private String code;
    private String msg;
    private T data;

    public ExternalBaseRes() {
        this.code = SUCCESS_CODE;
        this.msg = "成功";
    }

    public ExternalBaseRes(boolean success) {
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

    

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    

    public void success() {
        this.resetCode(AppCodeEnum._0000_SUCCESS);
    }

    public void error() {
        this.resetCode(AppCodeEnum._9999_ERROR);
    }

    public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void resetCode(AppCodeEnum appCodeEnum) {
        this.code = appCodeEnum.getCode();
        this.msg = appCodeEnum.getMessage();
    }

    public boolean whetherSuccess() {
        return SUCCESS_CODE.equals(this.getCode());
    }
}
