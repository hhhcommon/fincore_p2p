package com.zb.fincore.pms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.facade.product.ProductJobServiceFacade;
import com.zb.fincore.pms.facade.product.ProductServiceFacade;
import com.zb.fincore.pms.facade.product.dto.req.ApproveProductRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductApprovalListRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductInfoRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductListRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductStockChangeFlowRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductBaseInfoRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductContractInfoRequest;
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

/**
 * 产品工厂控制器
 *
 * @author
 * @create 2017-02-22 17:12
 */
@RestController
@RequestMapping(value = "/productService")
public class ProductController {

    @Autowired
    ProductServiceFacade productServiceFacade;

    @Autowired
    ProductJobServiceFacade productJobServiceFacade;


    /**
     * 货架系统 产品注册
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/registerProduct.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/registerProduct", method = RequestMethod.POST)
    public RegisterProductResponse registerProduct(@RequestBody RegisterProductRequest req) {
        RegisterProductResponse response = productServiceFacade.registerProduct(req);
        return response;
    }

    /**
     * 分步提交使用  暂不提供给外部
     * 货架系统 产品基础信息注册
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/registerProductBaseInfo.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/registerProductBaseInfo", method = RequestMethod.POST)
    public RegisterProductResponse registerProductBaseInfo(@RequestBody RegisterProductBaseInfoRequest req) {
        RegisterProductResponse response = productServiceFacade.registerProductBaseInfo(req);
        return response;
    }

    /**
     * 分步提交使用  暂不提供给外部
     * 货架系统 产品期限信息注册
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/registerProductPeriodInfo.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/registerProductPeriodInfo", method = RequestMethod.POST)
    public RegisterProductResponse registerProductPeriodInfo(@RequestBody RegisterProductPeriodInfoRequest req) {
        RegisterProductResponse response = productServiceFacade.registerProductPeriodInfo(req);
        return response;
    }

    /**
     * 分步提交使用  暂不提供给外部
     * 货架系统 产品投资收益信息注册
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/registerProductProfitInfo.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/registerProductProfitInfo", method = RequestMethod.POST)
    public RegisterProductResponse registerProductProfitInfo(@RequestBody RegisterProductProfitInfoRequest req) {
        RegisterProductResponse response = productServiceFacade.registerProductProfitInfo(req);
        return response;
    }

//  /**
//  * 分步提交使用  暂不提供给外部
//  * 货架系统 产品合同信息注册
//  * <p/>
//  * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/registerProductContractInfo.json
//  *
//  * @param req
//  * @return
//  */
// @RequestMapping(value = "/registerProductContractInfo", method = RequestMethod.POST)
// public RegisterProductResponse registerProductContractInfo(@RequestBody RegisterProductContractInfoRequest req) {
//     RegisterProductResponse response = productServiceFacade.registerProductContractInfo(req);
//     return response;
// }

    /**
     * 货架系统 产品审核
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/approveProduct.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/approveProduct", method = RequestMethod.POST)
    public BaseResponse approveProduct(@RequestBody ApproveProductRequest req) {
        BaseResponse response = productServiceFacade.approveProduct(req);
        return response;
    }

    /**
     * 产品上线(销售状态)
     * description : 将产品状态为 已部署 设置为上线状态
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/putProductOnLine.json
     *
     * @return
     */
    @RequestMapping(value = "/putProductOnLine", method = RequestMethod.POST)
    public BaseResponse putProductOnLine(@RequestBody UpdateProductSaleStatusRequest req) {
        BaseResponse response = productServiceFacade.putProductOnLine(req);
        return response;
    }

    /**
     * 产品下线(销售状态)
     * description : 将产品销售状态(已部署、上线) 设置为下线状态
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/putProductOffLine.json
     *
     * @return
     */
    @RequestMapping(value = "/putProductOffLine", method = RequestMethod.POST)
    public BaseResponse putProductOffLine(@RequestBody UpdateProductSaleStatusRequest req) {
        BaseResponse response = productServiceFacade.putProductOffLine(req);
        return response;
    }

    /**
     * 产品上架接口，用于C端显示
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/putProductDisplay.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/putProductDisplay", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse putProductDisplay(@RequestBody UpdateProductDisplayStatusRequest req) {
        BaseResponse response = productServiceFacade.putProductDisplay(req);
        return response;
    }

    /**
     * 产品下架接口，用于C端显示
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/putProductDisplay.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/putProductUnDisplay", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse putProductUnDisplay(@RequestBody UpdateProductDisplayStatusRequest req) {
        BaseResponse response = productServiceFacade.putProductUnDisplay(req);
        return response;
    }

    /**
     * 货架系统 产品详情查询
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/queryProductInfo.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryProductInfo", method = RequestMethod.POST)
    public QueryProductInfoResponse queryProductInfo(@RequestBody QueryProductInfoRequest req) {
        QueryProductInfoResponse response = productServiceFacade.queryProductInfo(req);
        return response;
    }

    /**
     * 货架系统 产品列表查询
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/queryProductList.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryProductList", method = RequestMethod.POST)
    public PageQueryResponse<ProductModel> queryProductList(@RequestBody QueryProductListRequest req) {
        PageQueryResponse<ProductModel> response = productServiceFacade.queryProductList(req);
        return response;
    }

    /**
     * 货架系统 Boss系统产品列表查询【运营台】
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/queryProductListForBoss.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryProductListForBoss", method = RequestMethod.POST)
    public PageQueryResponse<ProductModel> queryProductListForBoss(@RequestBody QueryProductListRequest req) {
        PageQueryResponse<ProductModel> response = productServiceFacade.queryProductListForBoss(req);
        return response;
    }

    /**
     * 查询产品库存变更流水
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productStockDbService/queryProductStockChangeFlowList.json
     *
     * @return
     */
    @RequestMapping(value = "/queryProductStockChangeFlowList", method = RequestMethod.POST)
    public PageQueryResponse<ProductStockChangeFlowModel> queryProductStockChangeFlowList(@RequestBody QueryProductStockChangeFlowRequest req) {
        PageQueryResponse<ProductStockChangeFlowModel> response = productServiceFacade.queryProductStockChangeFlowList(req);
        return response;
    }

    /**
     * 查询产品审核信息列表
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/queryProductApprovalList.json
     *
     * @return
     */
    @RequestMapping(value = "/queryProductApprovalList", method = RequestMethod.POST)
    public QueryProductApprovalInfoResponse queryProductApprovalList(@RequestBody QueryProductApprovalListRequest req) {
        QueryProductApprovalInfoResponse response = productServiceFacade.queryProductApprovalList(req);
        return response;
    }

//    /**
//     * 更新产品募集金额
//     * description : 更新产品募集金额
//     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/updateCollectAmount.json
//     *
//     * @return
//     */
//    @RequestMapping(value = "/updateCollectAmount", method = RequestMethod.POST)
//    public BaseResponse updateCollectAmount(@RequestBody UpdateProductCollectAmountRequest req) {
//        BaseResponse response = productServiceFacade.updateCollectAmount(req);
//        return response;
//    }

    /**
     * 货架系统 产品信息更新
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/updateProductInfo.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/updateProductInfo", method = RequestMethod.POST)
    public BaseResponse updateProductInfo(@RequestBody UpdateProductInfoRequest req) {
        BaseResponse response = productServiceFacade.updateProductInfo(req);
        return response;
    }

    /**
     * 产品同步状态 更新  摇旺查询上线的产品列表之后，单个产品做落库登记
     * description : 产品接收 修改同步状态为“已同步”
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/updateProductSynStatus.json
     *
     * @return
     */
    @RequestMapping(value = "/updateProductSynStatus", method = RequestMethod.POST)
    public BaseResponse updateProductSynStatus(@RequestBody UpdateProductSyncStatusRequest req) {
        return productServiceFacade.updateProductSynStatus(req);
    }

    /**
     * 产品待成立确认成立接口
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/confirmProductEstablished.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/confirmProductEstablished", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse confirmProductEstablished(@RequestBody UpdateProductCollectStatusRequest req) {
        BaseResponse response = productServiceFacade.confirmProductEstablished(req);
        return response;
    }

    /**
     * 产品募集期 开始
     * description : 将产品状态为 待募集的产品 设置为募集期状态
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/startProductRaising.json
     *
     * @return
     */
    @RequestMapping(value = "/startProductRaising", method = RequestMethod.POST)
    public BaseResponse startProductRaising() {
        BaseResponse response = productJobServiceFacade.startProductRaising();
        return response;
    }

//    /**
//     * 募集期结束 --》产品待成立
//     * description : 将产品状态为 募集期的产品 设置为待成立状态
//     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/putProductWaitingEstablish.json
//     *
//     * @return
//     */
//    @RequestMapping(value = "/putProductWaitingEstablish", method = RequestMethod.POST)
//    @ResponseBody
//    public BaseResponse putProductWaitingEstablish() {
//        BaseResponse response = productJobServiceFacade.putProductWaitingEstablish();
//        return response;
//    }

//  /**
//  * 产品计息开始 --》产品存续期
//  * description : 将产品状态为 已成立的产品 设置为存续期状态
//  * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/putProductInValue.json
//  *
//  * @return
//  */
// @RequestMapping(value = "/putProductInValue", method = RequestMethod.POST)
// public BaseResponse putProductInValue(@RequestBody UpdateProductCollectStatusRequest req) {
//     BaseResponse response = productServiceFacade.putProductInValue(req);
//     return response;
// }

//    /**
//     * 产品存续期结束 --》产品到期  产品到期JOB调用
//     * description : 将产品状态为 存续期的产品 设置为到期状态
//     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/putProductOutValue.json
//     *
//     * @return
//     */
//    @RequestMapping(value = "/putProductOutValue", method = RequestMethod.POST)
//    @ResponseBody
//    public BaseResponse putProductOutValue() {
//        return productJobServiceFacade.putProductOutValue();
//    }

//    /**
//     * 产品计息开始 --》产品存续期
//     * description : 将产品状态为 已成立的产品 设置为存续期状态
//     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/putProductOutValue.json
//     *
//     * @return
//     */
//    @RequestMapping(value = "/putProductInValueOfJob", method = RequestMethod.POST)
//    public BaseResponse putProductInValueOfJob() {
//        return productJobServiceFacade.putProductInValueOfJob();
//    }

//    /**
//     * 产品到期 --》产品待兑付  TA消息调用  此HTTP接口给测试使用
//     * description : 产品到期TA计算收益生成对付文件后，产品接收 修改状态“待兑付”
//     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/putProductWaitRedeem.json
//     *
//     * @return
//     */
//    @RequestMapping(value = "/putProductWaitRedeem", method = RequestMethod.POST)
//    public BaseResponse putProductWaitRedeem(@RequestBody UpdateProductCollectStatusRequest req) {
//        return productServiceFacade.putProductWaitRedeem(req);
//    }

//    /**
//     * JOB 阶梯信息更新
//     * description : 阶梯产品每个收益阶段结束，更新为新的阶段阶梯信息
//     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/updateProductLadderInfo.json
//     *
//     * @return
//     */
//    @RequestMapping(value = "/updateProductLadderInfo", method = RequestMethod.POST)
//    public BaseResponse updateProductLadderInfo() {
//        return productJobServiceFacade.updateProductLadderInfo();
//    }

    /**
     * 产品统计接口
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productService/productStatistics.json
     *
     * @return
     */
    @RequestMapping(value = "/productStatistics", method = RequestMethod.POST)
    @ResponseBody
    public ProductStatisticsResponse productStatistics(@RequestBody QueryProductListRequest req) {
        ProductStatisticsResponse response = productServiceFacade.productStatistics(req);
        return response;
    }
}
