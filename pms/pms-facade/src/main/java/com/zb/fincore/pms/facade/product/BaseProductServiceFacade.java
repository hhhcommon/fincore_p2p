package com.zb.fincore.pms.facade.product;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.facade.product.dto.req.*;
import com.zb.fincore.pms.facade.product.dto.resp.ProductStatisticsResponse;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductApprovalInfoResponse;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductInfoResponse;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zb.fincore.pms.facade.product.model.ProductStockChangeFlowModel;

/**
 * 功能: 产品数据库服务接口
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/6 0006 16:55
 * 版本: V1.0
 */
public interface BaseProductServiceFacade {

    /**
     * 货架系统 产品注册  基本信息注册
     * @param req
     * @return
     */
    RegisterProductResponse registerProductBaseInfo(RegisterProductBaseInfoRequest req);


    /**
     * 货架系统 产品注册  投资期限信息注册
     * @param req
     * @return
     */
    RegisterProductResponse registerProductPeriodInfo(RegisterProductPeriodInfoRequest req);


    /**
     * 货架系统 产品注册 投资收益信息注册
     * @param req
     * @return
     */
    RegisterProductResponse registerProductProfitInfo(RegisterProductProfitInfoRequest req);

//    /**
//     * 货架系统 产品注册 产品合同信息注册
//     * @param req
//     * @return
//     */
//    RegisterProductResponse registerProductContractInfo(RegisterProductContractInfoRequest req);

    /**
     * 货架系统 产品注册
     * @param req
     * @return
     */
    RegisterProductResponse registerProduct(RegisterProductRequest req);

    /**
     * 产品审核
     * @param req
     * @return
     */
    BaseResponse approveProduct(ApproveProductRequest req);

    /**
     * 产品上线(销售状态)
     * 将产品状态为 已部署 设置为上线状态
     * @param req
     * @return
     */
    BaseResponse putProductOnLine(UpdateProductSaleStatusRequest req);

    /**
     * 产品下线(销售状态)
     * description : 将产品状态为 上线 设置为下线状态
     *
     * @return
     */
    BaseResponse putProductOffLine(UpdateProductSaleStatusRequest req);

    /**
     * 产品上架接口，用于C端显示
     * @param req
     * @return
     */
    BaseResponse putProductDisplay(UpdateProductDisplayStatusRequest req);

    /**
     * 产品下架接口，用于C端显示
     * @param req
     * @return
     */
    BaseResponse putProductUnDisplay(UpdateProductDisplayStatusRequest req);

    /**
     * 产品详情
     * @param req
     * @return
     */
    QueryProductInfoResponse queryProductInfo(QueryProductInfoRequest req);

    /**
     * 产品列表
     * @param req
     * @return
     */
    PageQueryResponse<ProductModel> queryProductList(QueryProductListRequest req);

    /**
     * 产品列表
     * @param req
     * @return
     */
    PageQueryResponse<ProductModel> queryProductListForBoss(QueryProductListRequest req);

    /**
     * 查询产品库存变更流水
     *
     * @param req
     * @return
     * @throws Exception
     */
    PageQueryResponse<ProductStockChangeFlowModel> queryProductStockChangeFlowList(QueryProductStockChangeFlowRequest req);

    /**
     * 查询产品审核信息列表
     * @param req
     * @return
     * @throws Exception
     */
    QueryProductApprovalInfoResponse queryProductApprovalList(QueryProductApprovalListRequest req);


    /**
     * 产品列表ByBean
     * @param req
     * @return
     */
    //PageQueryResponse<ProductModel> queryProductListByBean(QueryProductListRequest req);

    /**
     * 产品信息更新
     * @param req
     * @return
     */
    BaseResponse updateProductInfo(UpdateProductInfoRequest req);

    /**
     * 消息 更新产品募集状态
     */
    BaseResponse updateProductCollectStatus(UpdateProductCollectStatusRequest req);

    /**
     * 更新产品同步状态
     * @param req
     * @return
     */
    BaseResponse updateProductSynStatus(UpdateProductSyncStatusRequest req);

//    /**
//     * 更新产品募集金额
//     *
//     * @param req
//     * @return
//     */
//	BaseResponse updateCollectAmount(UpdateProductCollectAmountRequest req);

	/**
     * 产品确认成立
     * @param req
     * @return
     */
    BaseResponse confirmProductEstablished(UpdateProductCollectStatusRequest req);

//    /**
//     * 产品起息开始--存续期
//     * @param req
//     * @return
//     */
//    BaseResponse putProductInValue(UpdateProductCollectStatusRequest req);
//
//    /**
//     * 产品到期 --》待兑付
//     */
//    BaseResponse putProductWaitRedeem(UpdateProductCollectStatusRequest req);

    /**
     * 产品统计接口
     * @return
     * @throws Exception
     */
    ProductStatisticsResponse productStatistics(QueryProductListRequest req);

}
