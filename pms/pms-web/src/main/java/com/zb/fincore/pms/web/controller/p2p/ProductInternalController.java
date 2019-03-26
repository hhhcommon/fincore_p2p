package com.zb.fincore.pms.web.controller.p2p;

import com.zb.fincore.pms.facade.product.dto.req.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.common.model.GenericQueryListResponse;
import com.zb.fincore.pms.common.model.GenericResponse;
import com.zb.fincore.pms.facade.product.ProductNFTServiceForP2PFacade;
import com.zb.fincore.pms.facade.product.ProductServiceForP2PFacade;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductInfoResponse;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;

/**
 * 供唐小僧调用接口   不解密
 * Created on 2017/8/17.
 */
@RestController("productInternalForP2PController")
@RequestMapping(value = "/internal/productForP2PService")
public class ProductInternalController {

    @Autowired
    private ProductServiceForP2PFacade productServiceForP2PFacade;

    @Autowired
    private ProductNFTServiceForP2PFacade productNFTServiceForP2PFacade;

//    /**
//     * 货架系统 P2P定期产品注册 —— 支持多借款单 V2.0
//     * <p/>
//     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productForP2PService/registerProduct.json
//     *
//     * @param req
//     * @return
//     */
//    @RequestMapping(value = "/registerProduct", method = RequestMethod.POST)
//    public RegisterProductResponse registerProduct(@RequestBody RegisterProductRequest req) {
//        RegisterProductResponse response = productServiceForP2PFacade.registerProduct(req);
//        return response;
//    }

    /**
     * 货架系统 P2P定期产品注册 —— 散标类型，一个企业对应一个借款单 V2.0
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productForP2PService/registerProduct.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/registerProduct", method = RequestMethod.POST)
    public RegisterProductResponse registerProductSB(@RequestBody RegisterProductRequestSB req) {
        RegisterProductResponse response = productServiceForP2PFacade.registerProductSB(req);
        return response;
    }

    /**
     * 货架系统 P2P定期产品注册 V3.0
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productForP2PService/nft/registerProduct.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/nft/registerProduct", method = RequestMethod.POST)
    public RegisterProductResponse registerProductNFT(@RequestBody RegisterProductRequestNFT req) {
        RegisterProductResponse response = productNFTServiceForP2PFacade.registerProductNFT(req);
        return response;
    }

    /**
     * 货架系统 产品审核
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productForP2PService/approveProduct.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/approveProduct", method = RequestMethod.POST)
    public BaseResponse approveProduct(@RequestBody ApproveProductRequest req) {
        BaseResponse response = productServiceForP2PFacade.approveProduct(req);
        return response;
    }

    /**
     * 产品上线(销售状态)
     * description : 将产品状态为 已部署 设置为上线状态
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productForP2PService/putProductOnLine.json
     *
     * @return
     */
    @RequestMapping(value = "/putProductOnLine", method = RequestMethod.POST)
    public BaseResponse putProductOnLine(@RequestBody UpdateProductSaleStatusRequest req) {
        BaseResponse response = productServiceForP2PFacade.putProductOnLine(req);
        return response;
    }

    /**
     * 产品下线(销售状态)
     * description : 将产品状态为 上线 设置为下线状态
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productForP2PService/putProductOffLine.json
     *
     * @return
     */
    @RequestMapping(value = "/putProductOffLine", method = RequestMethod.POST)
    public BaseResponse putProductOffLine(@RequestBody UpdateProductSaleStatusRequest req) {
        BaseResponse response = productServiceForP2PFacade.putProductOffLine(req);
        return response;
    }

    /**
     * 货架系统 P2P定期产品详情查询【供唐小僧用、马上贷】
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productForP2PService/queryProductInfo.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryProductInfo", method = RequestMethod.POST)
    public QueryProductInfoResponse queryProductInfo(@RequestBody QueryProductInfoRequest req) {
        QueryProductInfoResponse response = productServiceForP2PFacade.queryProductInfo(req);
        return response;
    }

    /**
     * 货架系统P2P定期待上架产品列表查询【供唐小僧用】
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productForP2PService/queryProductList.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/net/queryProductList", method = RequestMethod.POST)
    public GenericQueryListResponse<ProductModel> queryProductList(@RequestBody QueryProductListRequest req) {
        PageQueryResponse<ProductModel> pageQueryResponse = productServiceForP2PFacade.queryProductList(req);
        GenericQueryListResponse<ProductModel> response = GenericQueryListResponse.build(GenericQueryListResponse.class);
        response.setRespCode(pageQueryResponse.getRespCode());
        response.setRespMsg(pageQueryResponse.getRespMsg());
        response.setDataList(pageQueryResponse.getDataList());
        response.setTotalCount(pageQueryResponse.getTotalCount());
        return response;
    }

    /**
     * 在售产品/售罄产品列表查询【供唐小僧用】
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productForP2PService/queryProductListForP2PApp.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryProductListForP2PApp", method = RequestMethod.POST)
    public GenericQueryListResponse<ProductModel> queryProductListForP2PApp(@RequestBody QueryProductListRequestForP2P req) {
        PageQueryResponse<ProductModel> pageQueryResponse = productServiceForP2PFacade.queryProductListForP2PApp(req);
        GenericQueryListResponse<ProductModel> response = GenericQueryListResponse.build(GenericQueryListResponse.class);
        response.setRespCode(pageQueryResponse.getRespCode());
        response.setRespMsg(pageQueryResponse.getRespMsg());
        response.setTotalCount(pageQueryResponse.getTotalCount());
        response.setDataList(pageQueryResponse.getDataList());
        return response;
    }

    /**
     * 在售产品/售罄产品列表查询【供马上贷用】
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productForP2PService/queryProductListForMSD.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryProductListForMSD", method = RequestMethod.POST)
    public GenericQueryListResponse<ProductModel> queryProductListForMSD(@RequestBody QueryProductListRequestForP2P req) {
        PageQueryResponse<ProductModel> pageQueryResponse = productServiceForP2PFacade.queryProductListForMSD(req);
        GenericQueryListResponse<ProductModel> response = GenericQueryListResponse.build(GenericQueryListResponse.class);
        response.setRespCode(pageQueryResponse.getRespCode());
        response.setRespMsg(pageQueryResponse.getRespMsg());
        response.setTotalCount(pageQueryResponse.getTotalCount());
        response.setDataList(pageQueryResponse.getDataList());
        return response;
    }

    /**
     * 供交易系统调用 产品详情查询
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productForP2PService/queryProductInfoForTrade.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryProductInfoForTrade", method = RequestMethod.POST)
    public QueryProductInfoResponse queryProductInfoForTrade(@RequestBody QueryProductInfoForTradeRequest req) {
        QueryProductInfoResponse response = productServiceForP2PFacade.queryProductInfoForTrade(req);
        return response;
    }

    /**
     * 供交易系统调用  产品列表查询
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productForP2PService/queryProductListForTrade.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryProductListForTrade", method = RequestMethod.POST)
    public PageQueryResponse<ProductModel> queryProductListForTrade(@RequestBody QueryProductListRequest req) {
        PageQueryResponse<ProductModel> response = productServiceForP2PFacade.queryProductListForTrade(req);
        return response;
    }

    /**
     * 产品上架（产品同步状态 更新），唐小僧查询上线的产品列表之后，单个产品做落库登记 . 【供唐小僧用】<br/>
     * 产品接收 修改同步状态为“已同步”
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productForP2PService/updateProductSynStatus.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/updateProductSynStatus", method = RequestMethod.POST)
    public GenericResponse updateProductSynStatus(@RequestBody UpdateProductSyncStatusRequest req) {
    	BaseResponse baseResponse = productServiceForP2PFacade.updateProductSynStatus(req);
    	GenericResponse response = GenericResponse.build();
        response.setRespCode(baseResponse.getRespCode());
        response.setRespMsg(baseResponse.getRespMsg());
        return response;
    }

    /**
     *  产品信息更新
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productForP2PService/updateProductInfo.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/updateProductInfo", method = RequestMethod.POST)
    public BaseResponse updateProductInfo(@RequestBody UpdateProductInfoRequest req) {
        BaseResponse resp = productServiceForP2PFacade.updateProductInfo(req);
        return resp;
    }

    /**
     * 供订单系统调用 库存售完通知产品
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productForP2PService/noticeProductStockSellout.json
     *
     * @return
     */
    @RequestMapping(value = "/noticeProductStockSellout", method = RequestMethod.POST)
    public BaseResponse noticeProductStockSellout(@RequestBody NoticeProductStockSelloutRequest req) {
        return productServiceForP2PFacade.noticeProductStockSellout(req);
    }

    /**
     * 产品作废（供唐小僧货架系统调用）
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productForP2PService/putProductCancel.json
     *
     * @return
     */
    @RequestMapping(value = "/putProductCancel", method = RequestMethod.POST)
    public BaseResponse putProductCancel(@RequestBody CancelProductRequest req) {
        BaseResponse response = productServiceForP2PFacade.putProductCancel(req);
        return response;
    }

}
