package com.zb.fincore.pms.web.test.p2p;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zb.fincore.common.utils.JsonUtils;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.enums.CountStockStatusEnum;
import com.zb.fincore.pms.common.enums.OpenProductStatusEnum;
import com.zb.fincore.pms.common.enums.OpenTypeEnum;
import com.zb.fincore.pms.common.utils.DataFormatUtil;
import com.zb.fincore.pms.service.dal.model.ProductCreatePlan;
import com.zb.fincore.pms.service.product.ProductCreatePlanService;

/**
 * 产品工厂控制器
 *
 * @author
 * @create 2017-02-22 17:12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
@ActiveProfiles("dev")
public class ProductCreatePlanTest {

    @Autowired
    private ProductCreatePlanService productCreatePlanService;
    

    
    /**
     * 创建产品计划
     */
    @Test
    public void createProductPlanRecord() {
    	String currentTime = DataFormatUtil.getDateTimeFormat(new Date());//当前时间
    	ProductCreatePlan pl = new ProductCreatePlan();
    	pl.setProductCode("0518040005");
    	pl.setCountStockTime(currentTime);
    	pl.setOpenProductTime(currentTime);
    	pl.setPlanTime("2018-04-20");
    	pl.setCountStockStatus(CountStockStatusEnum.INIT.getCode());
    	pl.setOpenProductStatus(OpenProductStatusEnum.INIT.getCode());
    	pl.setTotalAmount(BigDecimal.ZERO);
    	pl.setOpenType(OpenTypeEnum.OUT.getCode());
    	pl.setMemo("beizhu");
    	System.out.println("创建产品计划请求对象：" + JsonUtils.object2Json(pl));
    	int result = productCreatePlanService.insertSelective(pl);
    	System.out.println("创建产品计划响应对象：" + JsonUtils.object2Json(result));
    }
    
    /**
     * 根据产品code查询产品计划信息
     */
    @Test
    public void queryProductPlanByCode() throws Exception {
    	String productCode = "0518040004";
    	System.out.println("查询产品计划请求对象：" + JsonUtils.object2Json(productCode));
    	ProductCreatePlan result = productCreatePlanService.queryProductPlanByCode(productCode);
    	System.out.println("查询产品计划响应对象：" + JsonUtils.object2Json(result));
    }
    
    /**
     * 根据条件查询产品列表ByBean
     */
    @Test
    public void queryProductPlanListByBean() throws Exception {
    	ProductCreatePlan pl = new ProductCreatePlan();
    	pl.setProductCode("0518040004");
//    	pl.setCountStockTime(new Date());
//    	pl.setOpenProductTime(new Date());
//    	pl.setPlanTime("2018-04-20");
//    	pl.setCountStockStatus(CountStockStatusEnum.INIT.getCode());
//    	pl.setOpenProductStatus(OpenProductStatusEnum.INIT.getCode());
//    	pl.setTotalAmount(BigDecimal.ZERO);
//    	pl.setPlanProductType(OpenTypeEnum.OUT.getCode());
    	
    	System.out.println("查询产品计划请求对象：" + JsonUtils.object2Json(pl));
    	List<ProductCreatePlan> result = productCreatePlanService.queryProductPlanListByBean(pl);
    	System.out.println("查询产品计划响应对象：" + JsonUtils.object2Json(result));
    }
    
    /**
     * 产品状态更新列表ByMap
     */
    @Test
    public void queryProductPlanListByMap() throws Exception {
    	String currentDate = DataFormatUtil.getDateFormat(new Date());//当前日期
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("planTime", currentDate);
    	System.out.println("查询产品计划请求对象：" + JsonUtils.object2Json(map));
    	List<ProductCreatePlan> result = productCreatePlanService.queryProductPlanListByMap(map);
    	System.out.println("查询产品计划响应对象：" + JsonUtils.object2Json(result));
    }
    
    /**
     * 创建产品计划-job
     */
    @Test
    public void createProductPlan() throws Exception {
    	System.out.println("请求对象：" + JsonUtils.object2Json(""));
    	BaseResponse result = productCreatePlanService.createProductPlan();
    	System.out.println("响应对象：" + JsonUtils.object2Json(result));
    }
    
    /**
     * 计算产品库存-job
     */
    @Test
    public void countProductPlanStock() throws Exception {
    	System.out.println("请求对象：" + JsonUtils.object2Json(""));
    	BaseResponse result = productCreatePlanService.countProductPlanStock();
    	System.out.println("响应对象：" + JsonUtils.object2Json(result));
    }
    
    /**
     * 开放产品计划-job
     */
    @Test
    public void openProductPaln() throws Exception {
    	System.out.println("请求对象：" + JsonUtils.object2Json(""));
    	BaseResponse result = productCreatePlanService.openProductPaln();
    	System.out.println("响应对象：" + JsonUtils.object2Json(result));
    }
    
}
