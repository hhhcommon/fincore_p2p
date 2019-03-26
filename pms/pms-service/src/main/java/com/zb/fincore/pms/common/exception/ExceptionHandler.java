package com.zb.fincore.pms.common.exception;

import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.dto.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 功能: 异常处理类
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/2/9 0009 09:29
 * 版本: V1.0
 */
@Component
public class ExceptionHandler {

    /**
     * 异常处理
     *
     * @param e
     * @return
     */
    public BaseResponse handleException(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            return BaseResponse.build(be.getResultCode(), be.getResultMsg());
        } else {
            return BaseResponse.build(Constants.FAIL_RESP_CODE, Constants.FAIL_RESP_DESC, e.toString());
        }
    }

    /**
     * 异常处理
     *
     * @param e
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends BaseResponse> T handleException(Exception e, Class clazz) {
        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            return BaseResponse.build(clazz, StringUtils.isBlank(be.getResultCode()) ? Constants.FAIL_RESP_CODE : be.getResultCode(), be.getResultMsg(),
                    be.getException() != null ? be.getException().toString() : "");
        } else {
            return BaseResponse.build(clazz, Constants.FAIL_RESP_CODE, Constants.FAIL_RESP_DESC, e.toString());
        }
    }
}
