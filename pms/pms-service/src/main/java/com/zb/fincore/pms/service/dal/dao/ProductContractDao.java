package com.zb.fincore.pms.service.dal.dao;

import com.zb.fincore.pms.service.dal.model.ProductContract;

import java.util.List;

/**
 * 功能: 产品合同信息数据访问对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/1 0001 10:19
 * 版本: V1.0
 */
public interface ProductContractDao {

    int deleteByPrimaryKey(Long id);

    int insert(ProductContract record);

    int insertSelective(ProductContract record);

    ProductContract selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductContract record);

    int updateByPrimaryKey(ProductContract record);

    /**
     * 根据产品编号查询合同列表
     * @param productCode
     * @return
     */
    List<ProductContract> selectContractListByProductCode(String productCode);
}
