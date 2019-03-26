package com.zb.fincore.pms.facade.product.impl;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductListRequestForP2P;
import com.zb.fincore.pms.facade.product.dto.req.UnFreezeProductStockRequest;
import com.zb.fincore.pms.facade.product.dto.resp.UnFreezeProductStockResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.fincore.pms.common.exception.ExceptionHandler;
import com.zb.fincore.pms.facade.product.ProductCacheServiceForP2PFacade;
import com.zb.fincore.pms.facade.product.dto.req.ChangeProductStockForP2PRequest;
import com.zb.fincore.pms.facade.product.dto.resp.ChangeProductStockResponse;
import com.zb.fincore.pms.service.product.ProductCacheForP2PService;

/**
 * 功能: 产品缓存Facade实现类
 * 创建: MABIAO
 * 日期: 2017/4/6 0006 16:58
 * 版本: V1.0
 */
@Service
public class ProductCacheServiceForP2PFacadeImpl extends AbstractProductCacheServiceFacadeImpl implements ProductCacheServiceForP2PFacade {

    @Autowired
    private ProductCacheForP2PService productCacheForP2PService;

    @Autowired
    private ExceptionHandler exceptionHandler;


    /**
     * 解冻产品库存（供直销系统调用）
     *
     * @param req 冻结产品库存请求对象
     * @return 响应对象
     */
    @Override
    public UnFreezeProductStockResponse unFreezeProductStock(UnFreezeProductStockRequest req) {
        try {
            return productCacheForP2PService.unFreezeProductStock(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e, UnFreezeProductStockResponse.class);
        }
    }

//    /**
//     * 占用/释放/赎回/取消 库存
//     *
//     * @param reqForP2P
//     * @return
//     * @throws Exception
//     */
//    public ChangeProductStockResponse changeProductStock(ChangeProductStockForP2PRequest reqForP2P) {
//        try {
//            return productCacheForP2PService.changeProductStock(reqForP2P);
//        } catch (Exception e) {
//            return exceptionHandler.handleException(e, ChangeProductStockResponse.class);
//        }
//    }

    /**
     * 刷新产品在售列表（第一页）缓存
     *
     * @return 通用结果
     */
    @Override
    public BaseResponse refreshOnSaleProductListForP2PCache() {
        try {
            return productCacheForP2PService.refreshOnSaleProductListForP2PCache();
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
    }

    /**
     * 刷新产品售罄列表（第一页）缓存
     *
     * @return 通用结果
     */
    @Override
    public BaseResponse refreshSoldOutProductListForP2PCache() {
        try {
            return productCacheForP2PService.refreshSoldOutProductListForP2PCache();
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
    }

    /**
     * 在售/售罄产品列表查询
     * @param req
     * @return
     */
    @Override
    public PageQueryResponse<ProductModel> queryProductListForP2PApp(QueryProductListRequestForP2P req) {
        try {
            return productCacheForP2PService.queryProductListForP2PApp(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e,PageQueryResponse.class);
        }
    }

}
