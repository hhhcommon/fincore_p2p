package com.zb.fincore.pms.service.dal.dao;

import com.zb.fincore.pms.common.model.Page;
import com.zb.fincore.pms.service.dal.model.ProductApproval;

import java.util.List;

/**
 * 功能: 产品审核记录数据访问对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/1 0001 10:19
 * 版本: V1.0
 */
public interface ProductApprovalDao {

    int deleteByPrimaryKey(Long id);

    int insert(ProductApproval record);

    int insertSelective(ProductApproval record);

    ProductApproval selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductApproval record);

    int updateByPrimaryKey(ProductApproval record);

    /**
     * 查询库存变更流水数量
     */
    int queryProductApprovalListCount(ProductApproval record);

    /**
     * 查询库存变更流水列表
     */
    List<ProductApproval> queryProductApprovalList(ProductApproval record);

}
