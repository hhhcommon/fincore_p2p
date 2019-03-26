package com.zb.fincore.pms.web.controller.p2p;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.facade.product.InterfaceRetryJobServiceFacade;
import com.zb.fincore.pms.facade.product.ProductJobServiceFacade;

/**
 * 供内部系统调用的REST接口
 * Created on 2017/8/17.
 */
@RestController("productJobForP2PController")
@RequestMapping(value = "/p2p/product/internal/job")
public class ProductInternalJobController {

    @Autowired
    private ProductJobServiceFacade productJobServiceFacade;

    @Autowired
    private InterfaceRetryJobServiceFacade interfaceRetryJobServiceFacade;
    

    
    /**
     * 产品募集期 开始 <br/>
     * description : 产品状态【待募集 -> 募集期状态】<br/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/p2p/product/internal/job/startProductRaising.json <br/>
     *
     * @return
     */
    @RequestMapping(value = "/startProductRaising", method = RequestMethod.POST)
    public BaseResponse startProductRaising() {
        BaseResponse response = productJobServiceFacade.startProductRaising();
        return response;
    }
    
    /**
     * 产品募集期 结束 <br/>
     * description : 产品状态【募集期 -> 募集完成 】 <br/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/p2p/product/internal/job/putProductValuing.json <br/>
     *
     * @return
     */
    @RequestMapping(value = "/putProductValuing", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse putProductValuing() {
        BaseResponse response = productJobServiceFacade.putProductValuing();
        return response;
    }

    /**
     * 接口请求重试 <br/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/p2p/product/internal/job/putNotifyRetry.json <br/>
     *
     * @return
     */
    @RequestMapping(value = "/putNotifyRetry", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse putNotifyRetry() {
        BaseResponse response = interfaceRetryJobServiceFacade.putNotifyRetry();
        return response;
    }
    
}
