package com.zb.fincore.pms.web.test;

import java.math.BigDecimal;

import com.zb.fincore.pms.facade.product.dto.req.UnFreezeProductStockRequest;
import com.zb.fincore.pms.facade.product.dto.resp.UnFreezeProductStockResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.fincore.common.utils.JsonUtils;
import com.zb.fincore.pms.common.dto.QueryResponse;
import com.zb.fincore.pms.common.enums.ChangeProductStockTypeEnum;
import com.zb.fincore.pms.facade.line.dto.req.QueryCacheProductRequest;
import com.zb.fincore.pms.facade.line.dto.req.QueryCacheProductStockRequest;
import com.zb.fincore.pms.facade.product.ProductCacheServiceForP2PFacade;
import com.zb.fincore.pms.facade.product.dto.req.ChangeProductStockForP2PRequest;
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
public class ProductCacheForP2PTest extends AbstractJunitTest {

    @Autowired
    private ProductCacheServiceForP2PFacade productCacheServiceForP2PFacade;

    /**
     * 刷新产品缓存
     */
//    @Test
    public void refreshProductCache() {
    	productCacheServiceForP2PFacade.refreshProductCache();
    }

    /**
     * 查询缓存中产品详情
     */
//    @Test
    public void queryProduct() {
        QueryCacheProductRequest req = new QueryCacheProductRequest();
        req.setProductCode("0417040004");
        QueryResponse<ProductModel> modelList = productCacheServiceForP2PFacade.queryProduct(req);
    }

    /**
     * 查询缓存中产品库存信息
     */
//    @Test
    public void queryProductStock() {
        QueryCacheProductStockRequest req = new QueryCacheProductStockRequest();
        req.setProductCode("0217090005");
        QueryResponse<ProductStockModel> modelList = productCacheServiceForP2PFacade.queryProductStock(req);
        System.out.println("查询缓存中产品库存信息响应对象：" + JsonUtils.object2Json(modelList));
    }

    /**
     * 冻结产品库存
     */
//    @Test
    public void freezeProductStock() {
        FreezeProductStockRequest req = new FreezeProductStockRequest();
        req.setProductCode("0217090005");
        req.setChangeAmount(new BigDecimal("5000"));
        req.setRefNo("NO1001003");//System.currentTimeMillis()+""
        FreezeProductStockResponse response = productCacheServiceForP2PFacade.freezeProductStock(req);
        System.out.println(response.getStatus()+":"+response.getAddition());
    }

    /**
     * 解冻产品库存
     */
    @Test
    public void unFreezeProductStock() {
        UnFreezeProductStockRequest req = new UnFreezeProductStockRequest();
        req.setProductCode("0217100035");
        req.setRefNo("NO10351008");//System.currentTimeMillis()+""
        UnFreezeProductStockResponse response = productCacheServiceForP2PFacade.unFreezeProductStock(req);
        System.out.println(response.getStatus() + ":" + response.getAddition());
    }

//    /**
//     * 占用/释放/赎回/取消 库存(P2P)
//     *
//     * @return
//     */
////    @Test
//    public void changeProductStockForP2P() {
//        ChangeProductStockForP2PRequest req = new ChangeProductStockForP2PRequest();
//        req.setRefNo("NO1001003");
//        req.setProductCode("0217090005");
//        req.setChangeType(ChangeProductStockTypeEnum.OCCUPY.getCode());
//        req.setChangeAmount(new BigDecimal("4600"));
//        ChangeProductStockResponse response = productCacheServiceForP2PFacade.changeProductStock(req);
//        System.out.println(response.getStatus()+":"+response.getAddition());
//    }
}
