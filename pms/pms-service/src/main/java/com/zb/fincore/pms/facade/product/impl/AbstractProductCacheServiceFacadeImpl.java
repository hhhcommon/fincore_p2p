package com.zb.fincore.pms.facade.product.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.QueryResponse;
import com.zb.fincore.pms.common.exception.ExceptionHandler;
import com.zb.fincore.pms.facade.line.dto.req.QueryCacheProductRequest;
import com.zb.fincore.pms.facade.line.dto.req.QueryCacheProductStockRequest;
import com.zb.fincore.pms.facade.product.ProductCacheServiceFacade;
import com.zb.fincore.pms.facade.product.dto.req.ChangeProductStockRequest;
import com.zb.fincore.pms.facade.product.dto.req.FreezeProductStockRequest;
import com.zb.fincore.pms.facade.product.dto.resp.ChangeProductStockResponse;
import com.zb.fincore.pms.facade.product.dto.resp.FreezeProductStockResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zb.fincore.pms.facade.product.model.ProductStockModel;
import com.zb.fincore.pms.service.product.ProductCacheService;

/**
 * 功能: 产品缓存Facade实现类
 * 创建: MABIAO
 * 日期: 2017/4/6 0006 16:58
 * 版本: V1.0
 */
@Service
public class AbstractProductCacheServiceFacadeImpl implements ProductCacheServiceFacade {

    @Autowired
    private ProductCacheService productCacheService;

    @Autowired
    private ExceptionHandler exceptionHandler;

    /**
     * 刷新产品缓存
     *
     * @return 通用结果
     */
    @Override
    public BaseResponse refreshProductCache() {
        try {
            return productCacheService.refreshProductCache();
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
    }

    /**
     * 查询缓存中产品详情
     *
     * @param req 产品详情查询请求对象
     * @return 产品详情询响应对象
     */
    @Override
    public QueryResponse<ProductModel> queryProduct(QueryCacheProductRequest req) {
        try {
            return productCacheService.queryProduct(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e, QueryResponse.class);
        }
    }

    /**
     * 查询缓存中产品库存信息
     *
     * @param req 查询请求对象
     * @return 响应对象
     */
    @Override
    public QueryResponse<ProductStockModel> queryProductStock(QueryCacheProductStockRequest req) {
        try {
            return productCacheService.queryProductStock(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e, QueryResponse.class);
        }
    }

    /**
     * 冻结产品库存
     *
     * @param req 冻结产品库存请求对象
     * @return 响应对象
     */
    public FreezeProductStockResponse freezeProductStock(FreezeProductStockRequest req) {
        try {
            return productCacheService.freezeProductStock(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e, FreezeProductStockResponse.class);
        }
    }

    /**
     * 占用/释放/赎回/取消 库存
     *
     * @param req
     * @return
     * @throws Exception
     */
    public ChangeProductStockResponse changeProductStock(ChangeProductStockRequest req) {
        try {
            return productCacheService.changeProductStock(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e, ChangeProductStockResponse.class);
        }
    }

    /**
     * 占用/释放/赎回/取消 库存
     *
     * @param req
     * @return
     * @throws Exception
     */
    public ChangeProductStockResponse changeProductStockWithoutFreezeRecord(ChangeProductStockRequest req) {
        try {
            return productCacheService.changeProductStockWithoutFreezeRecord(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e, ChangeProductStockResponse.class);
        }
    }
}
