package com.zb.fincore.pms.web.test.p2p;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.service.order.OrderService;

/**
 * 订单服务测试
 *
 * @author
 * @create 2017-02-22 17:12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
@ActiveProfiles("dev")
public class OrderTest {

    @Autowired
    private OrderService orderService ;


//    /**
//     * 产品售罄通知接口
//     */
//    @Test
//    public void tradeNotifyOrderHttp() throws Exception{
//        List<String> productCodes = new ArrayList<String>() ;
//        productCodes.add("111111");
//        System.out.println("请求对象：" + JSONObject.toJSONString(productCodes));
//        orderService.tradeNotifyOrderHttp(productCodes);
//        System.out.println("响应对象：" );
//    }

    /**
     * 查询昨日可用复投金额 V3.0
     */
    @Test
    public void queryAutoInvestAmtHttp() throws Exception{
    	String req = "2018-04-27 00:00:00";
        System.out.println("请求对象：" + JSONObject.toJSONString(req));
        BigDecimal amt = orderService.queryAutoInvestAmtHttp(req);
        System.out.println("响应对象：" + JSONObject.toJSONString(amt));
    }

    /**
     * 自动产品开放时通知订单 V3.0
     */
    @Test
    public void newProductHttp() throws Exception{
    	String productCode = "2018-04-27 00:00:00";
        System.out.println("请求对象：" + JSONObject.toJSONString(productCode));
        BaseResponse resp = orderService.newProductHttp(productCode);
        System.out.println("响应对象：" + JSONObject.toJSONString(resp));
    }

}
