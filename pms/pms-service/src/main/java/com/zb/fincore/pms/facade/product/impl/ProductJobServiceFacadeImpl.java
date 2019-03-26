package com.zb.fincore.pms.facade.product.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.exception.ExceptionHandler;
import com.zb.fincore.pms.facade.product.ProductJobServiceFacade;
import com.zb.fincore.pms.service.product.ProductCacheForP2PService;
import com.zb.fincore.pms.service.product.ProductService;

/**
 * 功能: 产品数据库Facade实现类
 * 创建: MABIAO
 * 日期: 2017/4/6 0006 16:58
 * 版本: V1.0
 *
 */
@Service
public class ProductJobServiceFacadeImpl implements ProductJobServiceFacade {
	
	private static Logger logger = LoggerFactory.getLogger(ProductJobServiceFacadeImpl.class);

    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProductCacheForP2PService productCacheForP2PService;

    @Autowired
    private ExceptionHandler exceptionHandler;

    /**
     * 产品募集期开始 JOB调用
     * @return
     */
    @Override
    public BaseResponse startProductRaising() {
    	BaseResponse baseResponse = BaseResponse.build();
        try {
        	baseResponse = productService.startProductRaising();
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
        
        try {
			if (baseResponse.getRespCode().equals(Constants.SUCCESS_RESP_CODE)) {
				productCacheForP2PService.refreshOnSaleProductListForP2PCache();
	        	productCacheForP2PService.refreshSoldOutProductListForP2PCache();
			}
		} catch (Exception e) {
			logger.error("【募集期开始job】刷新缓存失败!"+"\n"+"{}", e);
		}
        return baseResponse;
    }
    
//    /**
//     * 募集期结束 --》产品待成立    JOB调用
//     * @return
//     */
//    @Override
//    public BaseResponse putProductWaitingEstablish() {
//        try {
//            return productService.putProductWaitingEstablish();
//        } catch (Exception e) {
//            return exceptionHandler.handleException(e, BaseResponse.class);
//        }
//    }
    
    /**
     * 募集期结束 --》募集完成    JOB调用
     * @return
     */
    @Override
    public BaseResponse putProductValuing() {
    	BaseResponse baseResponse = BaseResponse.build();
        try {
        	baseResponse = productService.putProductValuing();
        } catch (Exception e) {
            return exceptionHandler.handleException(e, BaseResponse.class);
        }
        
        try {
			if (baseResponse.getRespCode().equals(Constants.SUCCESS_RESP_CODE)) {
				productCacheForP2PService.refreshOnSaleProductListForP2PCache();
	        	productCacheForP2PService.refreshSoldOutProductListForP2PCache();
			}
		} catch (Exception e) {
			logger.error("【募集期结束job】刷新缓存失败!"+"\n"+"{}", e);
		}
        return baseResponse;
    }

//    /**
//     * 产品存续期 开始起息 JOB调用
//     * @return
//     */
//    @Override
//    public BaseResponse putProductInValueOfJob() {
//        try {
//            return productService.putProductInValueOfJob();
//        } catch (Exception e) {
//            return exceptionHandler.handleException(e);
//        }
//    }
//    
//    /**
//     * 产品存续期结束 --》产品到期 JOB调用
//     * @return
//     */
//    @Override
//    public BaseResponse putProductOutValue() {
//        try {
//            return productService.putProductOutValue();
//        } catch (Exception e) {
//            return exceptionHandler.handleException(e,BaseResponse.class);
//        }
//    }

//    /**
//     * JOB 阶梯信息更新
//     * description : 阶梯产品每个收益阶段结束，更新为新的阶段阶梯信息
//     * @return
//     */
//    @Override
//    public BaseResponse updateProductLadderInfo() {
//        try {
//            return productService.updateProductLadderInfo();
//        } catch (Exception e) {
//            return exceptionHandler.handleException(e);
//        }
//    }
}
