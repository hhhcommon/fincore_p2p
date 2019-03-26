package com.zb.fincore.pms.facade.product;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.QueryResponse;
import com.zb.fincore.pms.facade.line.dto.req.QueryCacheProductRequest;
import com.zb.fincore.pms.facade.line.dto.req.QueryCacheProductStockRequest;
import com.zb.fincore.pms.facade.product.dto.req.ChangeProductStockRequest;
import com.zb.fincore.pms.facade.product.dto.req.FreezeProductStockRequest;
import com.zb.fincore.pms.facade.product.dto.resp.ChangeProductStockResponse;
import com.zb.fincore.pms.facade.product.dto.resp.FreezeProductStockResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zb.fincore.pms.facade.product.model.ProductStockModel;

/**
 * 功能: 产品缓存服务接口
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/17 0017 09:44
 * 版本: V1.0
 */
public interface BaseProductCacheServiceFacade {

    /**
     * 刷新产品缓存
     *
     * @return 通用结果
     */
    BaseResponse refreshProductCache();

    /**
     * 查询缓存中产品详情
     *
     * @param req 产品详情查询请求对象
     * @return 产品详情询响应对象
     */
    QueryResponse<ProductModel> queryProduct(QueryCacheProductRequest req);

    /**
     * 查询缓存中产品库存信息
     *
     * @param req 查询请求对象
     * @return 响应对象
     */
    QueryResponse<ProductStockModel> queryProductStock(QueryCacheProductStockRequest req);

    /**
     * 冻结产品库存
     *
     * @param req 冻结产品库存请求对象
     * @return 响应对象
     */
    FreezeProductStockResponse freezeProductStock(FreezeProductStockRequest req);

    /**
     * 占用/释放/赎回/取消 库存
     *
     * @param req
     * @return
     * @throws Exception
     */
    ChangeProductStockResponse changeProductStock(ChangeProductStockRequest req);

    /**
     * 占用/释放/赎回/取消 库存
     * 无冻结记录下占用 赎回等操作库存
     * @param req
     * @return
     * @throws Exception
     */
    ChangeProductStockResponse changeProductStockWithoutFreezeRecord(ChangeProductStockRequest req);
}
