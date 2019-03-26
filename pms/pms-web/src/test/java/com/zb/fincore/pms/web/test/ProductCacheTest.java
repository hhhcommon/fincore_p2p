package com.zb.fincore.pms.web.test;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.fincore.common.utils.JsonUtils;
import com.zb.fincore.pms.common.dto.QueryResponse;
import com.zb.fincore.pms.common.enums.ChangeProductStockTypeEnum;
import com.zb.fincore.pms.facade.line.dto.req.QueryCacheProductRequest;
import com.zb.fincore.pms.facade.line.dto.req.QueryCacheProductStockRequest;
import com.zb.fincore.pms.facade.product.ProductCacheServiceFacade;
import com.zb.fincore.pms.facade.product.dto.req.ChangeProductStockRequest;
import com.zb.fincore.pms.facade.product.dto.req.FreezeProductStockRequest;
import com.zb.fincore.pms.facade.product.dto.resp.ChangeProductStockResponse;
import com.zb.fincore.pms.facade.product.dto.resp.FreezeProductStockResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zb.fincore.pms.facade.product.model.ProductStockModel;

/**
 * 功能: 产品缓存服务RESTFUL接口
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/12 0012 18:47
 * 版本: V1.0
 */
public class ProductCacheTest extends AbstractJunitTest {

    @Autowired
    private ProductCacheServiceFacade productCacheServiceFacade;

    /**
     * 刷新产品缓存
     */
//    @Test
    public void refreshProductCache() {
        productCacheServiceFacade.refreshProductCache();
    }

    /**
     * 查询缓存中产品详情
     */
//    @Test
    public void queryProduct() {
        QueryCacheProductRequest req = new QueryCacheProductRequest();
        req.setProductCode("0417040004");
        QueryResponse<ProductModel> modelList = productCacheServiceFacade.queryProduct(req);
    }

    /**
     * 查询缓存中产品库存信息
     */
//    @Test
    public void queryProductStock() {
        QueryCacheProductStockRequest req = new QueryCacheProductStockRequest();
        req.setProductCode("0217090005");
        QueryResponse<ProductStockModel> modelList = productCacheServiceFacade.queryProductStock(req);
        System.out.println("查询缓存中产品库存信息响应对象：" + JsonUtils.object2Json(modelList));
    }

    /**
     * 冻结产品库存
     */
    @Test
    public void freezeProductStock() {
        FreezeProductStockRequest req = new FreezeProductStockRequest();
        req.setProductCode("0217090005");
        req.setChangeAmount(new BigDecimal("5000"));
        req.setRefNo(System.currentTimeMillis()+"");
        FreezeProductStockResponse response = productCacheServiceFacade.freezeProductStock(req);
        System.out.println(response.getStatus()+":"+response.getRespMsg());
    }

    /**
     * 占用/释放/赎回/取消 库存
     *
     * @return
     */
    @Test
    public void changeProductStock() {
        ChangeProductStockRequest req = new ChangeProductStockRequest();
        req.setRefNo("NO1001001");
        req.setProductCode("0217090005");
        req.setChangeType(ChangeProductStockTypeEnum.RELEASE.getCode());
        ChangeProductStockResponse response = productCacheServiceFacade.changeProductStock(req);
        System.out.println(response.getStatus()+":"+response.getRespMsg());
    }
}
