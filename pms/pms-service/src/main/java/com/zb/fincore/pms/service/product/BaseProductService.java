package com.zb.fincore.pms.service.product;


import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.facade.product.dto.req.*;
import com.zb.fincore.pms.facade.product.dto.resp.ProductStatisticsResponse;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductApprovalInfoResponse;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductInfoResponse;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zb.fincore.pms.facade.product.model.ProductStockChangeFlowModel;
import com.zb.fincore.pms.service.dal.model.Product;

import javax.validation.Valid;

/**
 * 功能: 产品数据库接口类
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/6 0006 16:57
 * 版本: V1.0
 */
public interface BaseProductService {

    /**
     * 货架系统 产品注册  基本信息注册
     * @param req
     * @return
     */
    RegisterProductResponse registerProductBaseInfo(@Valid RegisterProductBaseInfoRequest req) throws Exception;

    /**
     * 货架系统 产品注册  投资期限信息注册
     * @param req
     * @return
     */
    RegisterProductResponse registerProductPeriodInfo(@Valid RegisterProductPeriodInfoRequest req) throws Exception;

    /**
     * 货架系统 产品注册 投资收益信息注册
     * @param req
     * @return
     */
    RegisterProductResponse registerProductProfitInfo(@Valid RegisterProductProfitInfoRequest req) throws Exception;

    /**
     * 货架系统 产品注册 产品库存信息注册
     * @param req
     * @return
     */
    void registerProductStockInfo(@Valid RegisterProductBaseInfoRequest req, Product product) throws Exception;

//    /**
//     * 货架系统 产品注册 产品合同信息注册
//     * @param req
//     * @return
//     */
//    RegisterProductResponse registerProductContractInfo(RegisterProductContractInfoRequest req) throws Exception;

    /**
     * 货架系统 产品注册
     * @param req
     * @return
     */
    RegisterProductResponse registerProduct(@Valid RegisterProductRequest req) throws Exception;

    /**
     * 产品审核
     * @param req
     * @return
     */
    BaseResponse approveProduct(@Valid ApproveProductRequest req) throws Exception;

    /**
     * 产品上线(销售状态)
     * 将产品状态为 已部署 设置为上线状态
     * @param req
     * @return
     */
    BaseResponse putProductOnLine(@Valid UpdateProductSaleStatusRequest req) throws Exception;

    /**
     * 产品下线(销售状态)
     * description : 将产品状态为 上线 设置为下线状态
     *
     * @return
     */
    BaseResponse putProductOffLine(@Valid UpdateProductSaleStatusRequest req) throws Exception;

    /**
     * 产品上架接口，用于C端显示
     * @param req
     * @return
     */
    BaseResponse putProductDisplay(@Valid UpdateProductDisplayStatusRequest req) throws Exception;

    /**
     * 产品下架接口，用于C端显示
     * @param req
     * @return
     */
    BaseResponse putProductUnDisplay(@Valid UpdateProductDisplayStatusRequest req) throws Exception;

    /**
     * 产品详情
     * @param req
     * @return
     */
    QueryProductInfoResponse queryProductInfo(@Valid QueryProductInfoRequest req) throws Exception;

    /**
     * 产品列表
     * @param req
     * @return
     */
    PageQueryResponse<ProductModel> queryProductListForYw(@Valid QueryProductListRequest req) throws Exception;

    /**
     * 产品列表
     * @param req
     * @return
     */
    PageQueryResponse<ProductModel> queryProductList(@Valid QueryProductListRequest req) throws Exception;

    /**
     * 产品列表
     * @param req
     * @return
     */
    PageQueryResponse<ProductModel> queryProductListForP2P(@Valid QueryProductListRequest req) throws Exception;

    /**
     * 供交易系统调用  产品列表查询
     * @param req
     * @return
     */
    PageQueryResponse<ProductModel> queryProductListForTrade(@Valid QueryProductListRequest req) throws Exception;

    /**
     * 查询产品库存变更记录流水
     *
     * @param req
     * @return
     * @throws Exception
     */
    PageQueryResponse<ProductStockChangeFlowModel> queryProductStockChangeFlowList(QueryProductStockChangeFlowRequest req) throws Exception;

    /**
     * 查询产品审核信息列表
     * @param req
     * @return
     * @throws Exception
     */
    QueryProductApprovalInfoResponse queryProductApprovalList(@Valid QueryProductApprovalListRequest req) throws Exception;

    /**
     * P2P在售/售罄产品列表查询
     * @param req
     * @return
     */
    PageQueryResponse<ProductModel> queryProductListForP2PApp(@Valid QueryProductListRequestForP2P req) throws Exception;

    /**
     * 在售/售罄产品列表查询（供马上贷用）
     * @param req
     * @return
     */
    PageQueryResponse<ProductModel> queryProductListForMSD(@Valid QueryProductListRequestForP2P req) throws Exception;

    /**
     * 产品列表ByBean
     * @param req
     * @return
     */
    //PageQueryResponse<ProductModel> queryProductListByBean(@Valid QueryProductListRequest req) throws Exception;

    /**
     * 产品信息更新
     * @param req
     * @return
     */
    BaseResponse updateProductInfo(@Valid UpdateProductInfoRequest req) throws Exception;

//    /**
//     * 产品募集金额更新
//     *
//     * @param req
//     * @return
//     */
//    BaseResponse updateCollectAmount(@Valid UpdateProductCollectAmountRequest req);

    /**
     * 消息 更新产品募集状态
     */
    BaseResponse updateProductCollectStatus(@Valid UpdateProductCollectStatusRequest req) throws Exception;

    /**
     * 更新产品同步状态
     * @param req
     * @return
     */
    BaseResponse updateProductSynStatus(@Valid UpdateProductSyncStatusRequest req) throws Exception;

    /**
     * 产品确认成立
     * @param req
     * @return
     */
    BaseResponse confirmProductEstablished(@Valid UpdateProductCollectStatusRequest req) throws Exception;

    /**
     * 产品募集期开始 JOB调用
     * @return
     */
    BaseResponse startProductRaising() throws Exception;

//    /**
//     * 募集期结束 --》产品待成立    JOB调用
//     * @return
//     * @throws Exception
//     */
//    BaseResponse putProductWaitingEstablish() throws Exception;

    /**
     * 募集期结束 --》募集完成   JOB调用
     * @return
     * @throws Exception
     */
    BaseResponse putProductValuing() throws Exception;

    /**
     * 募集期结束  --》募集完成     JOB调用 实际处理逻辑
     *
     * @return
     * @throws Exception
     */
    void doProductValuing(Product p) throws Exception;

//    /**
//     * 产品起息开始--存续期
//     * @param req
//     * @return
//     */
//    BaseResponse putProductInValue(UpdateProductCollectStatusRequest req) throws Exception;
//
//    /**
//     * 产品存续期结束 --》产品到期 JOB调用
//     * @return
//     * @throws Exception
//     */
//    BaseResponse putProductOutValue() throws Exception;

//    /**
//     * 产品存续期 开始起息 JOB调用
//     * @return
//     */
//    BaseResponse putProductInValueOfJob() throws Exception;

//    /**
//     * 产品待兑付
//     * @return
//     */
//    BaseResponse putProductWaitRedeem(@Valid UpdateProductCollectStatusRequest req) throws Exception;

//    /**
//     *JOB 阶梯信息更新
//     */
//    BaseResponse updateProductLadderInfo() throws Exception;

    /**
     * 产品统计接口
     * @return
     * @throws Exception
     */
    ProductStatisticsResponse productStatistics(QueryProductListRequest req) throws Exception;

}
