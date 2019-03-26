package com.zb.fincore.pms.service.dal.dao;

import com.zb.fincore.pms.service.dal.model.ProductProfit;

/**
 * 功能: 产品投资收益信息数据访问对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/1 0001 10:20
 * 版本: V1.0
 */
public interface ProductProfitDao {

    int deleteByPrimaryKey(Long id);

    int insert(ProductProfit record);

    int insertSelective(ProductProfit record);

    ProductProfit selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductProfit record);

    int updateByPrimaryKey(ProductProfit record);

    ProductProfit selectProductProfitInfoByProductCode(String productCode);

    ProductProfit selectProductProfitInfoByProductId(Long productId);
}
