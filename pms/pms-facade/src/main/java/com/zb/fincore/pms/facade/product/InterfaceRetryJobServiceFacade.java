package com.zb.fincore.pms.facade.product;

import com.zb.fincore.pms.common.dto.BaseResponse;

/**
 * 接口请求重试job
 */
public interface InterfaceRetryJobServiceFacade {

    /**
     * 通知重试
     * description :
     * @return
     */
    BaseResponse putNotifyRetry();

}
