package com.zb.fincore.pms.service.product;


import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.facade.product.dto.req.ChangeProductStockForP2PRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductListRequestForP2P;
import com.zb.fincore.pms.facade.product.dto.req.UnFreezeProductStockRequest;
import com.zb.fincore.pms.facade.product.dto.resp.ChangeProductStockResponse;
import com.zb.fincore.pms.facade.product.dto.resp.UnFreezeProductStockResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;

import javax.validation.Valid;

/**
 * 功能: 产品缓存接口类
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/6 0006 16:58
 * 版本: V1.0
 */
public interface ProductCacheForP2PService extends ProductCacheService {

    /**
     * 解冻产品库存（供直销系统调用）
     *
     * @param req 冻结产品库存请求对象
     * @return 响应对象
     */
    UnFreezeProductStockResponse unFreezeProductStock(UnFreezeProductStockRequest req) throws Exception;

//    /**
//     * 占用/释放/赎回/取消 库存
//     *
//     * @param reqForP2P
//     * @return
//     * @throws Exception
//     */
//    ChangeProductStockResponse changeProductStock(@Valid ChangeProductStockForP2PRequest reqForP2P) throws Exception;

    /**
     * 刷新产品在售列表（第一页）缓存
     *
     * @return 通用结果
     */
    BaseResponse refreshOnSaleProductListForP2PCache() throws Exception;

    /**
     * 刷新产品售罄列表（第一页）缓存
     *
     * @return 通用结果
     */
    BaseResponse refreshSoldOutProductListForP2PCache() throws Exception;

    /**
     * P2P在售/售罄产品列表查询
     * @param req
     * @return
     */
    PageQueryResponse<ProductModel> queryProductListForP2PApp(@Valid QueryProductListRequestForP2P req) throws Exception;

}
