package com.zb.fincore.pms.web.controller.p2p;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.facade.product.ProductPlanJobServiceFacade;

/**
 * 供内部系统调用的REST接口
 * Created on 2017/8/17.
 */
@RestController("productPlanInternalJobController")
@RequestMapping(value = "/p2p/product/plan/internal/job")
public class ProductPlanInternalJobController {

    @Autowired
    private ProductPlanJobServiceFacade productPlanJobServiceFacade;

    
    /**
     * 创建产品计划 <br/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/p2p/product/plan/internal/job/createProductPlan.json <br/>
     *
     * @return
     */
    @RequestMapping(value = "/createProductPlan", method = RequestMethod.POST)
    public BaseResponse createProductPlan() {
        BaseResponse response = productPlanJobServiceFacade.createProductPlan();
        return response;
    }
    
    /**
     * 计算产品计划库存 <br/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/p2p/product/plan/internal/job/countProductPlanStock.json <br/>
     *
     * @return
     */
    @RequestMapping(value = "/countProductPlanStock", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse countProductPlanStock() {
        BaseResponse response = productPlanJobServiceFacade.countProductPlanStock();
        return response;
    }
    
    /**
     * 开放产品计划 <br/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/p2p/product/plan/internal/job/openProductPaln.json <br/>
     *
     * @return
     */
    @RequestMapping(value = "/openProductPaln", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse openProductPaln() {
        BaseResponse response = productPlanJobServiceFacade.openProductPaln();
        return response;
    }

}
