package com.zb.fincore.pms.web.controller.p2p;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.facade.product.ProductJobServiceFacade;
import com.zb.fincore.pms.facade.product.ProductNFTServiceForP2PFacade;
import com.zb.fincore.pms.facade.product.ProductServiceForP2PFacade;
import com.zb.fincore.pms.facade.product.dto.req.ApproveProductRequest;
import com.zb.fincore.pms.facade.product.dto.req.NoticeProductStockSelloutRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductInfoForTradeRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductInfoRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductListRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductRequestNFT;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductRequestSB;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductCollectStatusRequest;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductSaleStatusRequest;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductInfoResponse;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;

/**
 * 供内部系统调用的REST接口
 * Created on 2017/8/17.
 */
@RestController("productForP2PController")
@RequestMapping(value = "/productForP2PService")
public class ProductController {

    @Autowired
    private ProductServiceForP2PFacade productServiceForP2PFacade;
    
    @Autowired
    private ProductNFTServiceForP2PFacade productNFTServiceForP2PFacade;
    
    @Autowired
    private ProductJobServiceFacade productJobServiceFacade;

//    /**
//     * 货架系统 P2P定期产品注册 V2.0
//     * <p/>
//     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productForP2PService/registerProduct.json
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
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productForP2PService/sb/registerProduct.json
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
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productForP2PService/nft/registerProduct.json
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
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productForP2PService/approveProduct.json 
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
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productForP2PService/putProductOnLine.json
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
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productForP2PService/putProductOffLine.json
     *
     * @return
     */
    @RequestMapping(value = "/putProductOffLine", method = RequestMethod.POST)
    public BaseResponse putProductOffLine(@RequestBody UpdateProductSaleStatusRequest req) {
        BaseResponse response = productServiceForP2PFacade.putProductOffLine(req);
        return response;
    }

    /**
     * 货架系统 P2P定期产品详情查询
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productForP2PService/queryProductInfo.json
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
     * 供交易系统调用 产品详情查询
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productForP2PService/queryProductInfoForTrade.json
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
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productForP2PService/queryProductListForTrade.json
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
     * 更新产品募集状态 V2.0 没用到
     * description : 修改募集状态为“兑付完成”
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productForP2PService/updateProductCollectStatus.json
     *
     * @return
     */
    @RequestMapping(value = "/updateProductCollectStatus", method = RequestMethod.POST)
    public BaseResponse updateProductCollectStatus(@RequestBody UpdateProductCollectStatusRequest req) {
        return productServiceForP2PFacade.updateProductCollectStatus(req);
    }
    
    /**
     * 供订单系统调用 库存售完通知产品
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productForP2PService/noticeProductStockSellout.json
     *
     * @return
     */
    @RequestMapping(value = "/noticeProductStockSellout", method = RequestMethod.POST)
    public BaseResponse noticeProductStockSellout(@RequestBody NoticeProductStockSelloutRequest req) {
        return productServiceForP2PFacade.noticeProductStockSellout(req);
    }
}
