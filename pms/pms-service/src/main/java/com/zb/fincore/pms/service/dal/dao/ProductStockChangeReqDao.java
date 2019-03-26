package com.zb.fincore.pms.service.dal.dao;

import com.zb.fincore.pms.service.dal.model.ProductStockChangeReq;

import java.util.List;

/**
 * 功能: 产品库存变更请求数据访问对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/6 0006 10:07
 * 版本: V1.0
 */
public interface ProductStockChangeReqDao {

    int deleteByPrimaryKey(Long id);

    int insert(ProductStockChangeReq record);

    int insertSelective(ProductStockChangeReq record);

    ProductStockChangeReq selectByPrimaryKey(Long id);

    ProductStockChangeReq select(ProductStockChangeReq record);

    List<ProductStockChangeReq> queryProductStockChangeReqList(ProductStockChangeReq record);

    int updateByPrimaryKeySelective(ProductStockChangeReq record);

    int updateByPrimaryKey(ProductStockChangeReq record);
}
