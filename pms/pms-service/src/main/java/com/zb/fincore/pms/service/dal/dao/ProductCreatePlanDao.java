/*
 * ProductCreatePlanDao.java
 * Copyright (c) 2017, 资邦金服（上海）网络科技有限公司. All Rights Reserved 
 * 2018-04-17 14:20:53
 */
package com.zb.fincore.pms.service.dal.dao;

import java.util.List;
import java.util.Map;

import com.zb.fincore.pms.service.dal.model.ProductCreatePlan;

public interface ProductCreatePlanDao {
	
    int deleteByPrimaryKey(Long id);

    int insertSelective(ProductCreatePlan record);

    ProductCreatePlan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductCreatePlan record);
    
    /**
     * 根据产品code查询产品计划信息
     * @param productCode
     * @return
     */
    ProductCreatePlan queryProductPlanByCode(String productCode);
    
    /**
     * 根据条件查询产品列表ByBean
     * @param record
     * @return
     */
    List<ProductCreatePlan> queryProductPlanListByBean(ProductCreatePlan record);
    
    /**
     * 根据条件查询产品列表ByMap
     * @param params
     * @return
     */
    List<ProductCreatePlan> queryProductPlanListByMap(Map<String, Object> params);
    
}