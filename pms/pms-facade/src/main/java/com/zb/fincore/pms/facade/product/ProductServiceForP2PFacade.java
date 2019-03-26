package com.zb.fincore.pms.facade.product;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.facade.product.dto.req.*;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductInfoResponse;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;

/**
 * 功能: 产品数据库服务接口
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/6 0006 16:55
 * 版本: V1.0
 */
public interface ProductServiceForP2PFacade extends BaseProductServiceFacade{
	/**
     * 供交易系统调用  产品列表查询
     * @param req
     * @return
     */
    PageQueryResponse<ProductModel> queryProductListForTrade(QueryProductListRequest req);
    
    /**
     * 供交易系统调用 产品详情查询
     * @param req
     * @return
     */
    QueryProductInfoResponse queryProductInfoForTrade(QueryProductInfoForTradeRequest req);
    
    /**
     * 供订单系统调用 库存售完通知产品
     */
    BaseResponse noticeProductStockSellout(NoticeProductStockSelloutRequest req);

    /**
     * 在售/售罄产品列表查询
     * @param req
     * @return
     */
    PageQueryResponse<ProductModel> queryProductListForP2PApp(QueryProductListRequestForP2P req);

    /**
     * 在售/售罄产品列表查询（供马上贷独立理财端）
     * @param req
     * @return
     */
    PageQueryResponse<ProductModel> queryProductListForMSD(QueryProductListRequestForP2P req);
    
    
    //-----------------------------------------------------
    //	唐小僧票p2p 增加设计定期散标产品相关接口 start
    //-----------------------------------------------------
    /**
     * 货架系统 P2P定期散标产品 —— 产品注册 （ 散标类型，一个企业对应一个借款单）
     * @param req
     * @return
     */
    RegisterProductResponse registerProductSB(RegisterProductRequestSB req);

    /**
     * 货架系统 产品作废
     * @param req
     * @return
     * @throws Exception
     */
    BaseResponse putProductCancel(CancelProductRequest req);
    
    //-----------------------------------------------------
    //	唐小僧票p2p 增加设计定期散标产品相关接口 end
    //-----------------------------------------------------
    
}
