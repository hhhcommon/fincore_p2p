package com.zb.fincore.pms.web.controller.p2p;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zb.fincore.pms.facade.product.ProductServiceNFTFacade;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductRequestNFT;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;

/**
 * 供内部系统调用的REST接口
 * Created on 2017/8/17.
 */
@RestController("productNFTController")
@RequestMapping(value = "/productNFTService")
public class ProductNFTController {

    @Autowired
    private ProductServiceNFTFacade productServiceNFTFacade;
    

    /**
     * 货架系统 P2P定期产品注册
     * <p/>
     * 测试时浏览器地址栏输入：http://localhost:8080/pms/productNFTService/registerProductNFT.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/registerProductNFT", method = RequestMethod.POST)
    public RegisterProductResponse registerProductNFT(@RequestBody RegisterProductRequestNFT req) {
        RegisterProductResponse response = productServiceNFTFacade.registerProductNFT(req);
        return response;
    }
}
