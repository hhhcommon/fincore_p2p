package com.zb.p2p.trade.common.exception;

import com.zb.fincore.common.enums.ResultCodeEnum;

import java.io.Serializable;

/**
 * Function: 基础业务异常类. <br/>
 *
 * @author fangyang@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class BusinessException extends RuntimeException implements Serializable{
    private String code;

    private String message;

    private Throwable exception;

    public BusinessException() {

    }

    public BusinessException(String message) {
        super(message);
        this.code = ResultCodeEnum.FAIL.code();
        this.message = message;
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
