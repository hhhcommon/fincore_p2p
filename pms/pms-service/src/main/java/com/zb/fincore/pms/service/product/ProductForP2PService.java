package com.zb.fincore.pms.service.product;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.facade.product.dto.req.CancelProductRequest;
import com.zb.fincore.pms.facade.product.dto.req.NoticeProductStockSelloutRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductInfoForTradeRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductRequestSB;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductInfoResponse;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;

import javax.validation.Valid;

/**
 * 功能: 产品数据库接口类
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/6 0006 16:57
 * 版本: V1.0
 */
public interface ProductForP2PService extends BaseProductService {
	/**
     * 供交易系统调用  产品详情查询
     * @param req
     * @return
     */
	QueryProductInfoResponse queryProductInfoForTrade(@Valid QueryProductInfoForTradeRequest req) throws Exception;

	/**
     * 供订单系统调用 库存售完通知产品
     */
    BaseResponse noticeProductStockSellout(@Valid NoticeProductStockSelloutRequest req) throws Exception;

    //-----------------------------------------------------
    //	唐小僧p2p 定期产品 增加设计定期散标产品相关接口 start
    //-----------------------------------------------------
    /**
     * 货架系统 P2P定期产品注册 —— 散标类型，一个企业对应一个借款单 V2.0
     * @param req
     * @return
     */
    RegisterProductResponse registerProductSB(@Valid RegisterProductRequestSB req) throws Exception;

    /**
     * 货架系统 产品作废
     * @param req
     * @return
     * @throws Exception
     */
    BaseResponse putProductCancel(@Valid CancelProductRequest req) throws Exception;

    //-----------------------------------------------------
    //	唐小僧票p2p 增加设计定期散标产品相关接口 start
    //-----------------------------------------------------

}
