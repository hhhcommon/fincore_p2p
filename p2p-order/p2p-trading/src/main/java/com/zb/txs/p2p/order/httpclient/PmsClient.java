package com.zb.txs.p2p.order.httpclient;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.facade.product.dto.req.*;
import com.zb.fincore.pms.facade.product.dto.resp.ChangeProductStockResponse;
import com.zb.fincore.pms.facade.product.dto.resp.FreezeProductStockResponse;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductInfoResponse;
import com.zb.fincore.pms.facade.product.dto.resp.UnFreezeProductStockResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zb.txs.p2p.business.order.request.NoticePmsReq;
import com.zb.txs.p2p.business.order.response.pms.GenericQueryListResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Function:  PmsClient  <br/>
 * Date:  2018/1/10 17:19 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */
public interface PmsClient {
    /**
     * 库存售完时，通知接口天鼋产品
     *
     * @param noticePmsReq
     * @return
     */
    @POST("internal/productForP2PService/noticeProductStockSellout.json")
    Call<BaseResponse> noticeProductStockSellout(@Body NoticePmsReq noticePmsReq);

    /**
     * 查询在售/售罄产品列表（第一页）缓存接口
     *
     * @param req
     * @return
     */
    @POST("internal/productCacheForP2PService/queryProductListForP2PApp.json")
    Call<GenericQueryListResponse<ProductModel>> queryCacheProductListForP2PApp(@Body QueryProductListRequestForP2P req);

    /**
     * 产品详情
     *
     * @param req
     * @return
     */
    @POST("internal/productForP2PService/queryProductInfo.json")
    Call<QueryProductInfoResponse> queryProductInfo(@Body QueryProductInfoRequest req);

    /**
     * 冻结产品库存
     *
     * @param req
     * @return
     */
    @POST("internal/productCacheForP2PService/freezeProductStock.json")
    Call<FreezeProductStockResponse> freezeProductStock(@Body FreezeProductStockRequest req);

    /**
     * 占用库存
     *
     * @param req
     * @return
     */
    @POST("internal/productCacheForP2PService/changeProductStock.json")
    Call<ChangeProductStockResponse> changeProductStock(@Body ChangeProductStockForP2PRequest req);

    /**
     * 解冻产品库存
     *
     * @param req
     * @return
     */
    @POST("internal/productCacheForP2PService/unFreezeProductStock.json")
    Call<UnFreezeProductStockResponse> unFreezeProductStock(@Body UnFreezeProductStockRequest req);

    /**
     * 在售/售罄产品列表（供唐小僧用）接口
     *
     * @param req
     * @return
     */
    @POST("internal/productForP2PService/queryProductListForP2PApp.json")
    Call<GenericQueryListResponse<ProductModel>> queryProductListForP2PApp(@Body QueryProductListRequestForP2P req);

}
