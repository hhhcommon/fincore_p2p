package com.zb.fincore.pms.service.dal.dao;

import com.zb.fincore.pms.service.dal.model.ProductStock;

/**
 * 功能: 产品库存信息数据访问对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/1 0001 10:21
 * 版本: V1.0
 */
public interface ProductStockDao {

    int deleteByPrimaryKey(Long id);

    int insert(ProductStock record);

    int insertSelective(ProductStock record);

    ProductStock selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductStock record);

    int updateByPrimaryKey(ProductStock record);

    int updateStockWithLock(ProductStock record);

    /**
     * 根据产品编号查询库存
     *
     * @param productCode
     * @return
     */
    ProductStock selectProductStockByProductCode(String productCode);
    
    /**
     * 根据产品code更新 <br/>
     *
     * @param record
     * @return
     */
    int updateByProductCode(ProductStock record);
}
