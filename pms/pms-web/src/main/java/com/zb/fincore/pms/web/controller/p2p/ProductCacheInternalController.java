package com.zb.fincore.pms.web.controller.p2p;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.common.dto.QueryResponse;
import com.zb.fincore.pms.common.model.GenericQueryListResponse;
import com.zb.fincore.pms.facade.line.dto.req.QueryCacheProductStockRequest;
import com.zb.fincore.pms.facade.product.ProductCacheServiceForP2PFacade;
import com.zb.fincore.pms.facade.product.dto.req.*;
import com.zb.fincore.pms.facade.product.dto.resp.ChangeProductStockResponse;
import com.zb.fincore.pms.facade.product.dto.resp.FreezeProductStockResponse;
import com.zb.fincore.pms.facade.product.dto.resp.UnFreezeProductStockResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zb.fincore.pms.facade.product.model.ProductStockModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能: 产品缓存服务RESTFUL接口 不解密
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/12 0012 18:47
 * 版本: V1.0
 */
@RestController("productCacheInternalForP2PController")
@RequestMapping(value = "/internal/productCacheForP2PService")
public class ProductCacheInternalController {

    @Autowired
    private ProductCacheServiceForP2PFacade productCacheServiceForP2PFacade;


    /**
     * 刷新产品缓存
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productCacheForP2PService/refreshProductCache.json
     *
     * @return 通用结果
     */
    @RequestMapping(value = "/refreshProductCache", method = RequestMethod.POST)
    public BaseResponse refreshProductCache() {
        return productCacheServiceForP2PFacade.refreshProductCache();
    }

    /**
     * 查询缓存中产品库存信息
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productCacheForP2PService/queryProductStock.json
     *
     * @param req 查询请求对象
     * @return 响应对象
     */
    @RequestMapping(value = "/queryProductStock", method = RequestMethod.POST)
    public QueryResponse<ProductStockModel> queryProductStock(@RequestBody QueryCacheProductStockRequest req) {
        return productCacheServiceForP2PFacade.queryProductStock(req);
    }

    /**
     * 冻结产品库存
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productCacheForP2PService/freezeProductStock.json
     *
     * @param req 冻结产品库存请求对象
     * @return 响应对象
     */
    @RequestMapping(value = "/freezeProductStock", method = RequestMethod.POST)
    public FreezeProductStockResponse freezeProductStock(@RequestBody FreezeProductStockRequest req) {
        return productCacheServiceForP2PFacade.freezeProductStock(req);
    }

    /**
     * 解冻产品库存
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productCacheForP2PService/unFreezeProductStock.json
     *
     * @param req 解冻产品库存请求对象
     * @return 响应对象
     */
    @RequestMapping(value = "/unFreezeProductStock", method = RequestMethod.POST)
    public UnFreezeProductStockResponse unFreezeProductStock(@RequestBody UnFreezeProductStockRequest req) {
        return productCacheServiceForP2PFacade.unFreezeProductStock(req);
    }

    /**
     * 占用/释放/赎回/取消 库存（P2P）
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productCacheForP2PService/changeProductStock.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/changeProductStock", method = RequestMethod.POST)
    public ChangeProductStockResponse changeProductStock(@RequestBody ChangeProductStockRequest req) {
        return productCacheServiceForP2PFacade.changeProductStock(req);
    }

    /**
     * 刷新产品在售列表（第一页）缓存
     * 1、默认排序：募集结束时间升序，预期年化收益率（最低预期收益率）升序，上线时间升序
     * 2、按照预期年化收益率（最低预期收益率）升序
     * 3、按照预期年化收益率（最低预期收益率）降序
     * 4、按照产品期限升序
     * 5、按照产品期限降序
     * 6、按照起投金额升序
     * 7、按照起投金额降序
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productCacheForP2PService/refreshOnSaleProductListForP2PCache.json
     *
     * @return 通用结果
     */
    @RequestMapping(value = "/refreshOnSaleProductListForP2PCache", method = RequestMethod.POST)
    public BaseResponse refreshOnSaleProductListForP2PCache() {
        return productCacheServiceForP2PFacade.refreshOnSaleProductListForP2PCache();
    }

    /**
     * 刷新产品售罄列表（第一页）缓存
     * 排序：下线时间降序
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productCacheForP2PService/refreshSoldOutProductListForP2PCache.json
     *
     * @return 通用结果
     */
    @RequestMapping(value = "/refreshSoldOutProductListForP2PCache", method = RequestMethod.POST)
    public BaseResponse refreshSoldOutProductListForP2PCache() {
        return productCacheServiceForP2PFacade.refreshSoldOutProductListForP2PCache();
    }

    /**
     * 查询在售/售罄产品列表（第一页）缓存
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/internal/productCacheForP2PService/queryProductListForP2PApp.json
     *
     * @return 通用结果
     */
    @RequestMapping(value = "/queryProductListForP2PApp", method = RequestMethod.POST)
    public GenericQueryListResponse<ProductModel> queryProductListForP2PApp(@RequestBody QueryProductListRequestForP2P req) {
        PageQueryResponse<ProductModel> pageQueryResponse = productCacheServiceForP2PFacade.queryProductListForP2PApp(req);
        GenericQueryListResponse<ProductModel> response = GenericQueryListResponse.build(GenericQueryListResponse.class);
        response.setRespCode(pageQueryResponse.getRespCode());
        response.setRespMsg(pageQueryResponse.getRespMsg());
        response.setDataList(pageQueryResponse.getDataList());
        return response;
    }



}
