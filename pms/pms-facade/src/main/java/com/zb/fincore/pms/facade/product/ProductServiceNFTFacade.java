package com.zb.fincore.pms.facade.product;

import com.zb.fincore.pms.facade.product.dto.req.RegisterProductRequestNFT;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;


/**
 * 功能: 产品数据库服务接口
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/6 0006 16:55
 * 版本: V1.0
 */
public interface ProductServiceNFTFacade extends BaseProductServiceFacade{
	/**
     * 货架系统 P2P定期产品注册 V3.0. <br/>
     *
     * @param req
     * @return
     */
    RegisterProductResponse registerProductNFT(RegisterProductRequestNFT req);
}
