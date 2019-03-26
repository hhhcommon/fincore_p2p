package com.zb.fincore.pms.service.dal.dao;

import com.zb.fincore.pms.common.model.Page;
import com.zb.fincore.pms.service.dal.model.ProductLine;

import java.util.List;

/**
 * 功能: 产品线数据访问对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/3/29 0029 09:31
 * 版本: V1.0
 */
public interface ProductLineDao {

    /**
     * 根据ID主键查询产品线
     *
     * @param id ID主键
     * @return 产品线数据持久对象
     */
    ProductLine selectByPrimaryKey(Long id);

    /**
     * 根据产品线代码查询产品线
     *
     * @param lineCode 产品线代码
     * @return 产品线数据持久对象
     */
    ProductLine selectByCode(String lineCode);

    /**
     * 根据查询条件查询产品线
     *
     * @param productLine 查询条件
     * @return 产品线数据持久对象
     */
    ProductLine select(ProductLine productLine);

    /**
     * 根据查询条件查询产品线数量
     *
     * @param productLine 查询条件
     * @return 产品线数量
     */
    int selectCount(ProductLine productLine);

    /**
     * 根据查询条件分页查询产品线列表
     *
     * @param productLine 查询条件
     * @param page        分页参数
     * @return 产品线数据持久对象集合
     */
    List<ProductLine> selectListByPage(ProductLine productLine, Page page);

    /**
     * 根据查询条件查询产品线列表
     *
     * @param productLine 查询条件
     * @return 产品线数据持久对象集合
     */
    List<ProductLine> selectList(ProductLine productLine);

    /**
     * 插入产品线记录
     *
     * @param productLine 插入对象
     * @return 受影响行数
     */
    int insertSelective(ProductLine productLine);

    /**
     * 更新产品线记录
     *
     * @param productLine 更新对象
     * @return 受影响行数
     */
    int updateSelective(ProductLine productLine);

    /**
     * 根据产品线名称和产品线展示名称查询产品线数量
     *
     * @param productLine 查询条件
     * @return 产品线数量
     */
    int selectProductLineCountByNames(ProductLine productLine);

    /**
     * 根据产品线展示名称查询产品线数量
     *
     * @param lineDisplayName 查询条件
     * @return 产品线数量
     */
    int selectProductLineCountByDisplayName(String lineDisplayName);
}
