package com.zb.p2p.trade.web.config;

import com.alibaba.fastjson.JSON;
import com.zb.p2p.trade.common.enums.AppCodeEnum;
import com.zb.p2p.trade.common.model.CommonResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ValidationException;

/**
 * <p> 控制层整个过程统一异常处理 </p>
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
    public CommonResp handleValidationException(ValidationException e, WebRequest request) {
        log.error("业务参数{}校验失败：{}", JSON.toJSONString(request.getParameterMap()), e);
        return new CommonResp(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode(), e.getMessage());
    }

    /**
     * 请求参数校验失败
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResp handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
        log.error("请求参数{}校验失败：{}", JSON.toJSONString(request.getParameterMap()), e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        return new CommonResp(AppCodeEnum._0001_ILLEGAL_PARAMETER.getCode(), error.getDefaultMessage());
    }

}
