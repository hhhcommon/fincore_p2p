package com.zb.fincore.pms.facade.product;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.facade.product.dto.req.ChangeProductStockForP2PRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductListRequestForP2P;
import com.zb.fincore.pms.facade.product.dto.req.UnFreezeProductStockRequest;
import com.zb.fincore.pms.facade.product.dto.resp.ChangeProductStockResponse;
import com.zb.fincore.pms.facade.product.dto.resp.UnFreezeProductStockResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;

/**
 * 功能: 产品缓存服务接口
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/17 0017 09:44
 * 版本: V1.0
 */
public interface ProductCacheServiceForP2PFacade extends BaseProductCacheServiceFacade {

    /**
     * 解冻产品库存（供直销系统调用）
     *
     * @param req 冻结产品库存请求对象
     * @return 响应对象
     */
    UnFreezeProductStockResponse unFreezeProductStock(UnFreezeProductStockRequest req);


//    /**
//     * 占用/释放/赎回/取消 库存
//     *
//     * @param req
//     * @return
//     * @throws Exception
//     */
//    ChangeProductStockResponse changeProductStock(ChangeProductStockForP2PRequest reqForP2P);

    /**
     * 刷新产品在售列表（第一页）缓存
     *
     * @return 通用结果
     */
    BaseResponse refreshOnSaleProductListForP2PCache();

    /**
     * 刷新产品售罄列表（第一页）缓存
     *
     * @return 通用结果
     */
    BaseResponse refreshSoldOutProductListForP2PCache();

    /**
     * 在售/售罄产品列表查询
     * @param req
     * @return
     */
    PageQueryResponse<ProductModel> queryProductListForP2PApp(QueryProductListRequestForP2P req);
}
