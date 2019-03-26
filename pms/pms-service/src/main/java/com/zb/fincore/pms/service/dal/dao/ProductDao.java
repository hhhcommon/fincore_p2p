package com.zb.fincore.pms.service.dal.dao;

import com.zb.fincore.pms.common.model.Page;
import com.zb.fincore.pms.service.dal.model.Product;

import java.util.List;
import java.util.Map;

/**
 * 功能: 产品信息数据访问对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/1 0001 10:37
 * 版本: V1.0
 */
public interface ProductDao {

    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    /**
     * 根据产品线编号查询未归档产品
     * @param lineId 产品线id
     * @return 产品线数量
     */
    int selectUnArchiveProductListByLineId(Long lineId);

    /**
     * 根据产品code查询产品信息
     * @param productCode
     * @return
     */
    Product selectProductByCode(String productCode);
    
    /**
     * 根据产品code查询产品信息
     * @param productCode
     * @return
     */
    Product selectProductByCodeForUpdate(String productCode);

    /**
     * 根据产品Id更新销售状态
     * @param params
     * @return
     */
    int updateProductSaleStatusById(Map<String, Object> params);

    /**
     * 根据产品Id更新募集状态
     * @param params
     * @return
     */
    int updateProductCollectStatusById(Map<String, Object> params);

    /**
     * 根据产品Id更新显示状态
     * @param params
     * @return
     */
    int updateProductDisplayStatusById(Map<String, Object> params);

    /**
     * 根据产品Id更新审核状态
     * @param params
     * @return
     */
    int updateProductApprovalStatusById(Map<String, Object> params);

    /**
     * 根据产品名称和产品线展示名称查询产品数量
     *
     * @param product 查询条件
     * @return 产品数量
     */
    int selectProductCountByNames(Product product);
    int selectProductCountByNamesMap(Map<String, Object> params);
    int selectProductCountByNumberPeriodMap(Map<String, Object> params);

    /**
     * 根据产品编号查询产品详情
     * @param productCode
     * @return
     */
    Product queryProductDetailByProductCode(String productCode);

    /**
     * 根据条件查询查询产品数量
     * @param params
     * @return
     */
    int queryProductListCount(Map<String, Object> params);

    /**
     * 根据条件查询查询产品数量ByBean
     * @param record
     * @return
     */
    int queryProductListCountByBean(Product record);

    /**
     * 根据条件查询产品列表
     * @param params
     * @return
     */
    List<Product> queryProductListByCondition(Map<String, Object> params,Page page);

    /**
     * 根据条件查询产品列表ByBean
     * @param record
     * @return
     */
    List<Product> queryProductListByBean(Product record);

    /**
     * 查询是否存在相同展示名
     * @param productDisplayName
     * @return
     */
    int selectProductCountByDisplayName(String productDisplayName);

    /**
     * 产品状态更新列表
     * @param params
     * @return
     */
    List<Product> queryProductListForUpdateStatus(Map<String, Object> params);

    /**
     * 根据产品Id更新产品同步状态
     * @param params
     * @return
     */
    int updateProductSyncStatusById(Map<String, Object> params);

    /**
     * 根据产品Id更新产品归档时间
     * @param params
     * @return
     */
    int updateProductArchiveTimeById(Map<String, Object> params);

    /**
     * 在售产品列表查询 P2P
     * @param params
     * @return
     */
    List<Product> queryOnSaleProductListForP2P(Map<String, Object> params, Page page);

    /**
     * 售罄产品列表查询 P2P
     * @param params
     * @return
     */
    List<Product> querySoldOutProductListForP2P(Map<String, Object> params, Page page);
}
