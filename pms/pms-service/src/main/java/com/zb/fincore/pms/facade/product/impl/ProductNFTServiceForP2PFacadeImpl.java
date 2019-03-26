package com.zb.fincore.pms.facade.product.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.fincore.pms.common.exception.ExceptionHandler;
import com.zb.fincore.pms.facade.product.ProductNFTServiceForP2PFacade;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductRequestNFT;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;
import com.zb.fincore.pms.service.product.ProductNFTService;

/**
 * 功能: 产品数据库Facade实现类
 * 日期: 2017/4/6 0006 16:58
 * 版本: V1.0
 *
 */
@Service
public class ProductNFTServiceForP2PFacadeImpl extends AbstractProductServiceFacadeImpl implements ProductNFTServiceForP2PFacade {

	private static Logger logger = LoggerFactory.getLogger(ProductNFTServiceForP2PFacadeImpl.class);

    @Autowired
    private ProductNFTService productNFTService;

    @Autowired
    private ExceptionHandler exceptionHandler;

    /**
     * 货架系统 产品注册
     * @param req
     * @return
     */
    @Override
    public RegisterProductResponse registerProductNFT(RegisterProductRequestNFT req) {
        try {
            return productNFTService.registerProductNFT(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e, RegisterProductResponse.class);
        }
    }

}
