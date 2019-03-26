package com.zb.fincore.pms.service.dal.dao;

import com.zb.fincore.pms.common.model.Page;
import com.zb.fincore.pms.service.dal.model.ProductStockChangeFlow;

import java.util.List;

/**
 * 功能: 产品库存变更流水数据访问对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/1 0001 10:21
 * 版本: V1.0
 */
public interface ProductStockChangeFlowDao {

    int deleteByPrimaryKey(Long id);

    int insert(ProductStockChangeFlow record);

    int insertSelective(ProductStockChangeFlow record);

    ProductStockChangeFlow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductStockChangeFlow record);

    int updateByPrimaryKey(ProductStockChangeFlow record);

    /**
     * 查询库存变更流水数量
     */
    int queryProductStockChangeFlowCount(ProductStockChangeFlow record);

    /**
     * 查询库存变更流水列表
     */
    List<ProductStockChangeFlow> queryProductStockChangeFlowList(ProductStockChangeFlow record,Page page);
}
