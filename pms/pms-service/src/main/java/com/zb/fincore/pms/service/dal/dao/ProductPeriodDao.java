package com.zb.fincore.pms.service.dal.dao;

import com.zb.fincore.pms.service.dal.model.ProductPeriod;

/**
 * 功能: 产品期限信息数据访问对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/1 0001 10:20
 * 版本: V1.0
 */
public interface ProductPeriodDao {

    int deleteByPrimaryKey(Long id);

    int insert(ProductPeriod record);

    int insertSelective(ProductPeriod record);

    ProductPeriod selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductPeriod record);

    int updateByPrimaryKey(ProductPeriod record);

    ProductPeriod selectProductPeriodInfoByProductCode(String productCode);

    ProductPeriod selectProductPeriodInfoByProductId(Long productId);

    int updateActualTimeByProductCode(ProductPeriod productPeriod);
}
