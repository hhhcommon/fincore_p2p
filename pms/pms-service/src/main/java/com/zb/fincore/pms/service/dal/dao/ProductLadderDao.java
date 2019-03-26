package com.zb.fincore.pms.service.dal.dao;

import com.zb.fincore.pms.service.dal.model.ProductLadder;

import java.util.List;

/**
 * 功能: 产品阶梯信息数据访问对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/1 0001 10:20
 * 版本: V1.0
 */
public interface ProductLadderDao {
    int deleteByPrimaryKey(Long id);

    int insert(ProductLadder record);

    int insertSelective(ProductLadder record);

    ProductLadder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductLadder record);

    int updateByPrimaryKey(ProductLadder record);

    /**
     * 查询有下一阶梯的阶梯信息
     * @param record
     * @return
     */
    List<ProductLadder> selectNextLadderList(ProductLadder record);
}