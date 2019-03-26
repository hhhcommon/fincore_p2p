package com.zb.p2p.facade.service.internal;

import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.api.resp.product.ProductDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangxin on 2017/9/5.
 */
public interface ProductInternalService {

    /**
     * 根据产品编号查询产品
     * @param productCode
     * @return
     */
    CommonResp<ProductDTO> queryProductInfoByProductCode(String productCode);

    /**
     * 根据资产+起息时间+期限 查询产品
     * @param assetPoolCode
     * @param valueTime
     * @param investPeriod
     * @return
     */
    CommonResp<ProductDTO> queryProductInfoForAssetMatch(String assetPoolCode, Date valueTime, Integer investPeriod);

    /**
     * 根据日期查询产品列表
     * @param date
     * @return
     */
    List<ProductDTO> queryProductListByDate(Date date);
     
}
