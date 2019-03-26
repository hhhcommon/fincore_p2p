package com.zb.fincore.pms.web.test.p2p;

import com.zb.fincore.pms.facade.product.InterfaceRetryJobServiceFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.facade.product.ProductJobServiceFacade;

/**
 * 产品工厂控制器
 *
 * @author
 * @create 2017-02-22 17:12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ProductJobTest {

    @Autowired
    private ProductJobServiceFacade productJobServiceFacade;

    @Autowired
    private InterfaceRetryJobServiceFacade interfaceRetryJobServiceFacade;
    

    /**
     * 募集开始 job
     * description : 
     */
    @Test
    public void startProductRaising() {
        BaseResponse response = productJobServiceFacade.startProductRaising();
        System.out.println(response.getRespMsg());
    }
    
    /**
     * 募集结束 job
     * description : 
     */
    @Test
    public void putProductValuing() {
        BaseResponse response =  productJobServiceFacade.putProductValuing();
        System.out.println(response.getRespMsg());
    }

    /**
     * 接口重试 job
     * description :
     */
    @Test
    public void putNotifyRetry() {
        BaseResponse response =  interfaceRetryJobServiceFacade.putNotifyRetry();
        System.out.println(response.getRespMsg());
    }
}
