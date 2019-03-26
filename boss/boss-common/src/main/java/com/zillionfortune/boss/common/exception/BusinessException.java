package com.zillionfortune.boss.common.exception;

/**
 * 基础业务异常类
 * Created by zhangxin on 2016/12/13.
 */
public class BusinessException extends Exception {

    private static final long serialVersionUID = 1L;

    private String message;

    private Throwable exception;

    public BusinessException() {
    	
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(String message, Throwable e) {
        super(message);
        this.message = message;
        this.exception = e;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }
}
