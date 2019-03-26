package com.zb.fincore.pms.facade.product.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.exception.ExceptionHandler;
import com.zb.fincore.pms.facade.product.ProductPlanJobServiceFacade;
import com.zb.fincore.pms.service.product.ProductCacheForP2PService;
import com.zb.fincore.pms.service.product.ProductCreatePlanService;

/**
 * 功能: 产品数据库Facade实现类
 * 创建: MABIAO
 * 日期: 2017/4/6 0006 16:58
 * 版本: V1.0
 *
 */
@Service
public class ProductPlanJobServiceFacadeImpl implements ProductPlanJobServiceFacade {
	
	private static Logger logger = LoggerFactory.getLogger(ProductPlanJobServiceFacadeImpl.class);

    @Autowired
    private ProductCreatePlanService productCreatePlanService;
    
    @Autowired
    private ProductCacheForP2PService productCacheForP2PService;

    @Autowired
    private ExceptionHandler exceptionHandler;

	@Override
	public BaseResponse createProductPlan() {
		BaseResponse baseResponse = BaseResponse.build();
		try {
        	baseResponse = productCreatePlanService.createProductPlan();
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
		return baseResponse;
	}

	@Override
	public BaseResponse countProductPlanStock() {
		BaseResponse baseResponse = BaseResponse.build();
		try {
        	baseResponse = productCreatePlanService.countProductPlanStock();
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
		return baseResponse;
	}

	@Override
	public BaseResponse openProductPaln() {
		BaseResponse baseResponse = BaseResponse.build();
		try {
        	baseResponse = productCreatePlanService.openProductPaln();
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
		return baseResponse;
	}

}
