package com.zb.p2p.common.exception;

import com.zb.p2p.enums.ResponseCodeEnum;
import com.zb.p2p.facade.api.resp.CommonResp;
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
    public CommonResp handleException(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            return CommonResp.build(be.getResultCode(), be.getResultMsg());
        } else {
            return CommonResp.build(ResponseCodeEnum.RESPONSE_FAIL.getCode(), ResponseCodeEnum.RESPONSE_FAIL.getDesc());
        }
    }
}
