package com.zb.fincore.pms.facade.product.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.exception.ExceptionHandler;
import com.zb.fincore.pms.facade.product.InterfaceRetryJobServiceFacade;
import com.zb.fincore.pms.service.product.InterfaceRetryService;

/**
 * 功能: 接口请求重试job Facade实现类
 * 创建: 开运
 * 日期: 2017/4/6 0006 16:58
 * 版本: V1.0
 *
 */
@Service
public class InterfaceRetryJobServiceFacadeImpl implements InterfaceRetryJobServiceFacade {
	
	private static Logger logger = LoggerFactory.getLogger(InterfaceRetryJobServiceFacadeImpl.class);

    @Autowired
    private InterfaceRetryService interfaceRetryService;

    @Autowired
    private ExceptionHandler exceptionHandler;


    @Override
    public BaseResponse putNotifyRetry() {
        BaseResponse baseResponse = BaseResponse.build();
        try {
            baseResponse = interfaceRetryService.putNotifyRetry();
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
        return baseResponse;
    }
}
