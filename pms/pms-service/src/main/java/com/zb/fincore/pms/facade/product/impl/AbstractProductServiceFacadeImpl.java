package com.zb.fincore.pms.facade.product.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.common.exception.ExceptionHandler;
import com.zb.fincore.pms.facade.product.ProductServiceFacade;
import com.zb.fincore.pms.facade.product.dto.req.ApproveProductRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductApprovalListRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductInfoRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductListRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductStockChangeFlowRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductBaseInfoRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductPeriodInfoRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductProfitInfoRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductRequest;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductCollectAmountRequest;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductCollectStatusRequest;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductDisplayStatusRequest;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductInfoRequest;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductSaleStatusRequest;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductSyncStatusRequest;
import com.zb.fincore.pms.facade.product.dto.resp.ProductStatisticsResponse;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductApprovalInfoResponse;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductInfoResponse;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zb.fincore.pms.facade.product.model.ProductStockChangeFlowModel;
import com.zb.fincore.pms.service.product.ProductService;

/**
 * 功能: 产品数据库Facade实现类
 * 创建: MABIAO
 * 日期: 2017/4/6 0006 16:58
 * 版本: V1.0
 *
 */
@Service
public abstract class AbstractProductServiceFacadeImpl implements ProductServiceFacade {

    @Autowired
    private ProductService productService;

    @Autowired
    private ExceptionHandler exceptionHandler;


    /**
     * 货架系统 产品注册
     * @param req
     * @return
     */
    @Override
    public RegisterProductResponse registerProduct(RegisterProductRequest req) {
        try {
            return productService.registerProduct(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e, RegisterProductResponse.class);
        }
    }

    /**
     * 货架系统 产品注册  基本信息注册
     * @param req
     * @return
     */
    @Override
    public RegisterProductResponse registerProductBaseInfo(RegisterProductBaseInfoRequest req) {
        try {
            return productService.registerProductBaseInfo(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e, RegisterProductResponse.class);
        }
    }

    /**
     * 货架系统 产品注册  投资期限信息注册
     * @param req
     * @return
     */
    @Override
    public RegisterProductResponse registerProductPeriodInfo(RegisterProductPeriodInfoRequest req) {
        try {
            return productService.registerProductPeriodInfo(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e, RegisterProductResponse.class);
        }
    }

    /**
     * 货架系统 产品注册 投资收益信息注册
     * @param req
     * @return
     */
    @Override
    public RegisterProductResponse registerProductProfitInfo(RegisterProductProfitInfoRequest req) {
        try {
            return productService.registerProductProfitInfo(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e, RegisterProductResponse.class);
        }
    }

//    /**
//     * 货架系统 产品注册 产品合同信息注册
//     * @param req
//     * @return
//     */
//    @Override
//    public RegisterProductResponse registerProductContractInfo(RegisterProductContractInfoRequest req) {
//        try {
//            return productService.registerProductContractInfo(req);
//        } catch (Exception e) {
//            return exceptionHandler.handleException(e, RegisterProductResponse.class);
//        }
//    }

    /**
     * 产品审核
     * @param req
     * @return
     */
    @Override
    public BaseResponse approveProduct(ApproveProductRequest req) {
        try {
            return productService.approveProduct(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e,PageQueryResponse.class);
        }
    }

    /**
     * 产品上线(销售状态)
     * 将产品状态为 已部署 设置为上线状态
     * @param req
     * @return
     */
    @Override
    public BaseResponse putProductOnLine(UpdateProductSaleStatusRequest req) {
        try {
            return productService.putProductOnLine(req);
        }catch (Exception e){
            return exceptionHandler.handleException(e);
        }
    }

    /**
     * 产品下线(销售状态)
     * description : 将产品状态为 上线 设置为下线状态
     *
     * @return
     */
    @Override
    public BaseResponse putProductOffLine(UpdateProductSaleStatusRequest req) {
        try {
            return productService.putProductOffLine(req);
        }catch (Exception e){
            return exceptionHandler.handleException(e);
        }
    }

    /**
     * 产品上架接口，用于C端显示
     * @param req
     * @return
     */
    public BaseResponse putProductDisplay(UpdateProductDisplayStatusRequest req){
        try {
            return productService.putProductDisplay(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
    }

    /**
     * 产品下架接口，用于C端显示
     * @param req
     * @return
     */
    public BaseResponse putProductUnDisplay(UpdateProductDisplayStatusRequest req){
        try {
            return productService.putProductUnDisplay(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
    }

    /**
     * 产品详情
     * @param req
     * @return
     */
    public QueryProductInfoResponse queryProductInfo(QueryProductInfoRequest req){
        try {
            return productService.queryProductInfo(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e,QueryProductInfoResponse.class);
        }
    }

    /**
     * 产品列表
     * @param req
     * @return
     */
    public PageQueryResponse<ProductModel> queryProductList(QueryProductListRequest req){
        try {
            return productService.queryProductListForYw(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e,PageQueryResponse.class);
        }
    }

    /**
     * 运营台查询产品列表
     * @param req
     * @return
     */
    @Override
    public PageQueryResponse<ProductModel> queryProductListForBoss(QueryProductListRequest req) {
        try {
            return productService.queryProductList(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e,PageQueryResponse.class);
        }
    }

    /**
     * 查询产品库存变更流水
     *
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public PageQueryResponse<ProductStockChangeFlowModel> queryProductStockChangeFlowList(QueryProductStockChangeFlowRequest req) {
        try {
            return productService.queryProductStockChangeFlowList(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e, PageQueryResponse.class);
        }
    }

    @Override
    public QueryProductApprovalInfoResponse queryProductApprovalList(QueryProductApprovalListRequest req) {
        try {
            return productService.queryProductApprovalList(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e, QueryProductApprovalInfoResponse.class);
        }
    }

    /**
     * 产品信息更新
     * @param req
     * @return
     */
    public BaseResponse updateProductInfo(UpdateProductInfoRequest req){
        try {
            return productService.updateProductInfo(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e,PageQueryResponse.class);
        }
    }

    /**
     * 消息 更新产品募集状态
     */
    @Override
    public BaseResponse updateProductCollectStatus(UpdateProductCollectStatusRequest req) {
        try {
            return productService.updateProductCollectStatus(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
    }

    @Override
    public BaseResponse updateProductSynStatus(UpdateProductSyncStatusRequest req) {
        try {
            return productService.updateProductSynStatus(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
    }

//    /**
//     * 更新产品募集金额
//     * description : 更新产品totalAmount
//     *
//     * @return
//     */
//    @Override
//    public BaseResponse updateCollectAmount(UpdateProductCollectAmountRequest req) {
//        try {
//            return productService.updateCollectAmount(req);
//        }catch (Exception e){
//            return exceptionHandler.handleException(e);
//        }
//    }

    /**
     * 产品确认成立
     * @param req
     * @return
     */
    @Override
    public BaseResponse confirmProductEstablished(UpdateProductCollectStatusRequest req) {
        try {
            return productService.confirmProductEstablished(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
    }

//    /**
//     * 产品起息开始--存续期
//     * @param req
//     * @return
//     */
//    @Override
//    public BaseResponse putProductInValue(UpdateProductCollectStatusRequest req){
//        try {
//            return productService.putProductInValue(req);
//        } catch (Exception e) {
//            return exceptionHandler.handleException(e,PageQueryResponse.class);
//        }
//    }
//
//    /**
//     * 产品到期 --》待兑付
//     */
//    @Override
//    public BaseResponse putProductWaitRedeem(UpdateProductCollectStatusRequest req) {
//        try {
//            return productService.putProductWaitRedeem(req);
//        } catch (Exception e) {
//            return exceptionHandler.handleException(e);
//        }
//    }

    @Override
    public ProductStatisticsResponse productStatistics(QueryProductListRequest req) {
        try {
            return productService.productStatistics(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e, ProductStatisticsResponse.class);
        }
    }

}
