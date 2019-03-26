package com.zb.fincore.pms.service.product;


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

import javax.validation.Valid;

/**
 * 功能: 产品缓存接口类
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/6 0006 16:58
 * 版本: V1.0
 */
public interface ProductCacheService {

    /**
     * 刷新产品缓存
     *
     * @return 通用结果
     */
    BaseResponse refreshProductCache() throws Exception;

    /**
     * 查询缓存中产品详情
     *
     * @param req 产品详情查询请求对象
     * @return 产品详情询响应对象
     */
    QueryResponse<ProductModel> queryProduct(@Valid QueryCacheProductRequest req) throws Exception;

    /**
     * 查询缓存中产品库存信息
     *
     * @param req 查询请求对象
     * @return 响应对象
     */
    QueryResponse<ProductStockModel> queryProductStock(@Valid QueryCacheProductStockRequest req) throws Exception;

    /**
     * 冻结产品库存
     *
     * @param req 冻结产品库存请求对象
     * @return 响应对象
     */
    FreezeProductStockResponse freezeProductStock(@Valid FreezeProductStockRequest req) throws Exception;

    /**
     * 占用/释放/赎回/取消 库存
     *
     * @param req
     * @return
     * @throws Exception
     */
    ChangeProductStockResponse changeProductStock(ChangeProductStockRequest req) throws Exception;

    /**
     * 占用/释放/赎回/取消 库存
     * 不需要校验是否存在冻结记录 摇旺对接使用
     * @param req
     * @return
     * @throws Exception
     */
    ChangeProductStockResponse changeProductStockWithoutFreezeRecord(ChangeProductStockRequest req) throws Exception;
}
