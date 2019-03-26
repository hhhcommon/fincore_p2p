package com.zb.fincore.pms.web.controller;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.QueryResponse;
import com.zb.fincore.pms.facade.line.dto.req.QueryCacheProductRequest;
import com.zb.fincore.pms.facade.line.dto.req.QueryCacheProductStockRequest;
import com.zb.fincore.pms.facade.product.ProductCacheServiceFacade;
import com.zb.fincore.pms.facade.product.dto.req.ChangeProductStockRequest;
import com.zb.fincore.pms.facade.product.dto.req.FreezeProductStockRequest;
import com.zb.fincore.pms.facade.product.dto.resp.ChangeProductStockResponse;
import com.zb.fincore.pms.facade.product.dto.resp.FreezeProductStockResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zb.fincore.pms.facade.product.model.ProductStockModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能: 产品缓存服务RESTFUL接口
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/12 0012 18:47
 * 版本: V1.0
 */
@RestController
@RequestMapping(value = "/productCacheService")
public class ProductCacheController {

    @Autowired
    private ProductCacheServiceFacade productCacheServiceFacade;

    /**
     * 刷新产品缓存
     *
     * @return 通用结果
     */
    @RequestMapping(value = "/refreshProductCache", method = RequestMethod.POST)
    public BaseResponse refreshProductCache() {
        return productCacheServiceFacade.refreshProductCache();
    }

    /**
     * 查询缓存中产品详情
     *
     * @param req 产品详情查询请求对象
     * @return 产品详情询响应对象
     */
    @RequestMapping(value = "/queryProduct", method = RequestMethod.POST)
    public QueryResponse<ProductModel> queryProduct(@RequestBody QueryCacheProductRequest req) {
        return productCacheServiceFacade.queryProduct(req);
    }

    /**
     * 查询缓存中产品库存信息
     *
     * @param req 查询请求对象
     * @return 响应对象
     */
    @RequestMapping(value = "/queryProductStock", method = RequestMethod.POST)
    public QueryResponse<ProductStockModel> queryProductStock(@RequestBody QueryCacheProductStockRequest req) {
        return productCacheServiceFacade.queryProductStock(req);
    }

    /**
     * 冻结产品库存
     *
     * @param req 冻结产品库存请求对象
     * @return 响应对象
     */
    @RequestMapping(value = "/freezeProductStock", method = RequestMethod.POST)
    public FreezeProductStockResponse freezeProductStock(@RequestBody FreezeProductStockRequest req) {
        return productCacheServiceFacade.freezeProductStock(req);
    }

    /**
     * 占用/释放/赎回/取消 库存
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/changeProductStock", method = RequestMethod.POST)
    public ChangeProductStockResponse changeProductStock(@RequestBody ChangeProductStockRequest req) {
        return productCacheServiceFacade.changeProductStock(req);
    }
}
