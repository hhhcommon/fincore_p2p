package com.zb.fincore.pms.facade.product.impl;

import com.zb.fincore.pms.facade.product.dto.req.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.common.exception.ExceptionHandler;
import com.zb.fincore.pms.facade.product.ProductServiceForP2PFacade;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductInfoResponse;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zb.fincore.pms.service.product.ProductCacheForP2PService;
import com.zb.fincore.pms.service.product.ProductForP2PService;

import java.util.List;

/**
 * 功能: 产品数据库Facade实现类
 * 日期: 2017/4/6 0006 16:58
 * 版本: V1.0
 *
 */
@Service
public class ProductServiceForP2PFacadeImpl extends AbstractProductServiceFacadeImpl implements ProductServiceForP2PFacade {
	
	private static Logger logger = LoggerFactory.getLogger(ProductServiceForP2PFacadeImpl.class);

    @Autowired
    private ProductForP2PService productForP2PService;
    
    @Autowired
    private ProductCacheForP2PService productCacheForP2PService;

    @Autowired
    private ExceptionHandler exceptionHandler;

    /**
     * 货架系统 P2P定期产品注册 —— 支持多借款单 V2.0
     * @param req
     * @return
     */
    @Override
    public RegisterProductResponse registerProduct(RegisterProductRequest req) {
        try {
            return productForP2PService.registerProduct(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e, RegisterProductResponse.class);
        }
    }
    
    /**
     * 货架系统 P2P定期产品注册 —— 散标类型，一个企业对应一个借款单 V2.0
     * @param req
     * @return
     */
    @Override
    public RegisterProductResponse registerProductSB(RegisterProductRequestSB req) {
        try {
            return productForP2PService.registerProductSB(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e, RegisterProductResponse.class);
        }
    }

    /**
     * 产品审核
     * @param req
     * @return
     */
    @Override
    public BaseResponse approveProduct(ApproveProductRequest req) {
        try {
            return productForP2PService.approveProduct(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
    }
    
    /**
     * 产品上线(销售状态)
     * 将产品状态为 已部署 设置为上线状态
     * @param req
     * @return
     */
    @Override
    public BaseResponse putProductOnLine(UpdateProductSaleStatusRequest req) {
    	BaseResponse baseResponse = BaseResponse.build();
        try {
        	baseResponse = productForP2PService.putProductOnLine(req);
        }catch (Exception e){
            return exceptionHandler.handleException(e);
        }
        
        try {
			if (baseResponse.getRespCode().equals(Constants.SUCCESS_RESP_CODE)) {
				productCacheForP2PService.refreshOnSaleProductListForP2PCache();
	        	productCacheForP2PService.refreshSoldOutProductListForP2PCache();
			}
		} catch (Exception e) {
			logger.error("【产品上线】刷新缓存失败!"+"\n"+"{}", e);
		}
		return baseResponse;
    }
    
    /**
     * 产品下线(销售状态)
     * 将产品状态为 上线 设置为下线状态
     * @param req
     * @return
     */
    @Override
    public BaseResponse putProductOffLine(UpdateProductSaleStatusRequest req) {
    	BaseResponse baseResponse = BaseResponse.build();
        try {
        	baseResponse = productForP2PService.putProductOffLine(req);
        }catch (Exception e){
            return exceptionHandler.handleException(e);
        }
        
        if (baseResponse.getRespCode().equals(Constants.SUCCESS_RESP_CODE)) {
        	try {
    			productCacheForP2PService.refreshOnSaleProductListForP2PCache();
    	        productCacheForP2PService.refreshSoldOutProductListForP2PCache();
    		} catch (Exception e) {
    			logger.error("【产品上线】刷新缓存失败!"+"\n"+"{}", e);
    		}
        }
        
		return baseResponse;
    }

    /**
     * 产品详情
     * @param req
     * @return
     */
    @Override
    public QueryProductInfoResponse queryProductInfo(QueryProductInfoRequest req) {
        try {
            return productForP2PService.queryProductInfo(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e, QueryProductInfoResponse.class);
        }
    }
    
    /**
     * 待上架产品列表
     * @param req
     * @return
     */
    @Override
    public PageQueryResponse<ProductModel> queryProductList(QueryProductListRequest req) {
    	try {
            return productForP2PService.queryProductListForP2P(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e,PageQueryResponse.class);
        }
    }

    /**
     * 供交易系统调用  产品列表查询
     * @param req
     * @return
     */
	@Override
	public PageQueryResponse<ProductModel> queryProductListForTrade(QueryProductListRequest req) {
		try {
            return productForP2PService.queryProductListForTrade(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e,PageQueryResponse.class);
        }
	}

	/**
     * 供交易系统调用  产品详情查询
     * @param req
     * @return
     */
	@Override
	public QueryProductInfoResponse queryProductInfoForTrade(QueryProductInfoForTradeRequest req) {
		try {
            return productForP2PService.queryProductInfoForTrade(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e, QueryProductInfoResponse.class);
        }
	}
	
	/**
     * 在售/售罄产品列表查询
     * @param req
     * @return
     */
    @Override
    public PageQueryResponse<ProductModel> queryProductListForP2PApp(QueryProductListRequestForP2P req) {
        try {
            return productForP2PService.queryProductListForP2PApp(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e,PageQueryResponse.class);
        }
    }

    /**
     * 在售/售罄产品列表查询（供马上贷用）
     * @param req
     * @return
     */
    @Override
    public PageQueryResponse<ProductModel> queryProductListForMSD(QueryProductListRequestForP2P req) {
        try {
            return productForP2PService.queryProductListForMSD(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e,PageQueryResponse.class);
        }
    }
	
	/**
     * 更新产品募集状态
     */
    @Override
    public BaseResponse updateProductCollectStatus(UpdateProductCollectStatusRequest req) {
        try {
            return productForP2PService.updateProductCollectStatus(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
    }
	
    /**
     * 供订单系统调用 库存售完通知产品
     */
	@Override
	public BaseResponse noticeProductStockSellout(
			NoticeProductStockSelloutRequest req) {
		BaseResponse baseResponse = BaseResponse.build();
		try {
			baseResponse = productForP2PService.noticeProductStockSellout(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
		
		try {
			if (baseResponse.getRespCode().equals(Constants.SUCCESS_RESP_CODE)) {
				productCacheForP2PService.refreshOnSaleProductListForP2PCache();
	        	productCacheForP2PService.refreshSoldOutProductListForP2PCache();
			}
		} catch (Exception e) {
			logger.error("【供订单系统调用 库存售完通知产品】刷新缓存失败!"+"\n"+"{}", e);
		}
		return baseResponse;
	}

    /**
     * 供唐小僧货架系统调用 产品作废
     */
    @Override
    public BaseResponse putProductCancel(CancelProductRequest req) {
        try {
            return productForP2PService.putProductCancel(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
    }

}
