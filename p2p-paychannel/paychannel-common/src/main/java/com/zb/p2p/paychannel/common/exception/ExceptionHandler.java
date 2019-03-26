package com.zb.p2p.paychannel.common.exception;

import com.zb.p2p.paychannel.common.enums.AppCodeEnum;
import com.zb.p2p.paychannel.common.model.CommonResp;
import org.springframework.stereotype.Component;

/**
 * 功能: 异常处理类
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
    public CommonResp handleException(Exception e) {
        if (e instanceof AppException) {
            AppException ae = (AppException) e;
            return CommonResp.build(ae.getCode(), ae.getMessage());
        } else {
            return CommonResp.build(AppCodeEnum.RESPONSE_FAIL.getCode(), AppCodeEnum.RESPONSE_FAIL.getMessage());
        }
    }
}
