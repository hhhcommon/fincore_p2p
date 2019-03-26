package com.zb.p2p.customer.web.config;

import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.common.model.BaseRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ValidationException;

/**
 * <p> 描述 </p>
 *
 * @author Vinson
 * @version CommonExceptionAdvice.java v1.0 2018/3/21 20:28 Zhengwenquan Exp $
 */
@ControllerAdvice
@Slf4j
public class CommonExceptionAdvice {

    /**
     * 业务校验失败
     */
    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    public BaseRes handleValidationException(ValidationException e) {
        log.error("业务参数校验失败", e);
        return new BaseRes(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode(), e.getMessage());
    }

    /**
     * 请求参数校验失败
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseRes handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("请求参数校验失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        return new BaseRes(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode(), error.getDefaultMessage());
    }

}
