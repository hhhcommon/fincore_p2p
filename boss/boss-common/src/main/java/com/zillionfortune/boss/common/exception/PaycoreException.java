package com.zillionfortune.boss.common.exception;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.zillionfortune.boss.common.enums.ErrorCodeEnum;


/**
 * PaycoreException
 * 
 * @author mengkai
 * @version  PaycoreException.java, v 0.1 2016-7-27 上午9:25:37
 */
public class PaycoreException extends Exception {

    private static final long serialVersionUID = -2713112751005442797L;
    
    private String             errorCode;

    private String             errorMsg;

    public PaycoreException(ErrorCodeEnum errorCodeEnum,String errorMsg) {
        this.errorCode=errorCodeEnum.getCode();
        this.errorMsg=errorCodeEnum.getDesc()+":"+errorMsg;
    }
    
    public PaycoreException(String errorMsg) {
        this.errorCode=ErrorCodeEnum.ILLEGAL_PARAMETER.getCode();
        this.errorMsg=errorMsg;
    }

    public PaycoreException(String paramString, Throwable paramThrowable) {
        super(paramString, paramThrowable);
    }

    public PaycoreException(String paramString, Throwable paramThrowable, String errorCode) {
        super(paramString, paramThrowable);
        this.errorCode = errorCode;
    }

    public PaycoreException(String paramString, Throwable paramThrowable, String errorCode,
                                String errorMsg) {
        super(paramString, paramThrowable);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
