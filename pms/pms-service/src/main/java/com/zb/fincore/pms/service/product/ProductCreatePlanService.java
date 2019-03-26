package com.zb.fincore.pms.service.product;

import java.util.List;
import java.util.Map;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.service.dal.model.ProductCreatePlan;
import com.zb.fincore.pms.service.dal.model.ProductLine;

/**
 * Function: 自动创建产品计划接口类. <br/>
 * Date: 2018年4月20日 下午5:05:17 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public interface ProductCreatePlanService {
	
	/**
	 * 插入信息. <br/>
	 *
	 * @param record
	 * @return
	 */
	int insertSelective(ProductCreatePlan productCreatePlan);
	
	/**
     * 根据产品code查询产品计划信息
     * @param productCode
     * @return
     */
    ProductCreatePlan queryProductPlanByCode(String productCode) throws Exception;
    
    /**
     * 根据条件查询产品计划列表ByBean
     * @param record
     * @return
     */
    List<ProductCreatePlan> queryProductPlanListByBean(ProductCreatePlan productCreatePlan) throws Exception;
    
    /**
     * 根据条件查询产品计划列表ByMap
     * @param params
     * @return
     */
    List<ProductCreatePlan> queryProductPlanListByMap(Map<String, Object> params) throws Exception;
    
    /**
     * 创建产品计划 job调用. <br/>
     *
     * @return
     * @throws Exception
     */
    BaseResponse createProductPlan() throws Exception;
    
    /**
     * 创建产品计划 job调用 实际业务 <br/>
     *
     * @param productLine 产品线对象
     * @param nPlanCountStockInterval N复投计划产品计算库存的时间间隔（单位：分钟）
     * @param nPlanOpenTimeNodesArray N复投计划产品的开放时间节点（用#分割，单位：小时）
     * @throws Exception
     */
    void doCreateProductPlan(ProductLine productLine) throws Exception;
    
    /**
     * 计算产品计划库存 job调用. <br/>
     *
     * @return
     * @throws Exception
     */
    BaseResponse countProductPlanStock() throws Exception;
    
    /**
     * 计算产品计划库存 job调用 实际业务. <br/>
     *
     * @return
     * @throws Exception
     */
    void doCountProductPlanStock(ProductCreatePlan productCreatePlan) throws Exception;
    
    /**
     * 开放产品计划 job调用 . <br/>
     *
     * @return
     * @throws Exception
     */
    BaseResponse openProductPaln() throws Exception;
    
    /**
     * 开放产品计划(对外、对外) job调用  实际业务 <br/>
     *
     * @param productLine
     * @throws Exception
     */
    void doOpenProductPaln(ProductCreatePlan productCreatePlan) throws Exception;
    
}
