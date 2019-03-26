package com.zb.fincore.pms.facade.product;

import com.zb.fincore.pms.common.dto.BaseResponse;

/**
 * 功能: 产品计划数据库服务接口
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/6 0006 16:55
 * 版本: V1.0
 */
public interface ProductPlanJobServiceFacade {

	/**
     * 创建产品计划 job调用. <br/>
     *
     * @return
     * @throws Exception
     */
    BaseResponse createProductPlan();
    
    /**
     * 计算产品计划库存 job调用. <br/>
     *
     * @return
     * @throws Exception
     */
    BaseResponse countProductPlanStock();
    
    /**
     * 开放产品计划 job调用 . <br/>
     *
     * @return
     * @throws Exception
     */
    BaseResponse openProductPaln();

}
