package com.zb.fincore.pms.web.controller.p2p.internal.three;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zb.fincore.ams.common.dto.BaseResponse;
import com.zb.fincore.ams.common.dto.QueryResponse;
import com.zb.fincore.ams.facade.dto.req.QueryPoolRequest;
import com.zb.fincore.ams.facade.model.PoolModel;
import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.exception.BusinessException;
import com.zb.fincore.pms.service.ams.AMSService;
import com.zb.fincore.pms.service.order.OrderService;
import com.zb.fincore.pms.service.trade.TradeService;

/**
 * 其他模块(供开发http查询方便)
 * Created on 2017/8/17.
 */
@RestController("threeInternalController")
@RequestMapping(value = "/p2p/internal/three")
public class ThreeInternalController {

    @Autowired
    private AMSService amsService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private TradeService tradeService;
    
    
    /**
     * 异常处理
     *
     * @param e
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends BaseResponse> T handleException(Exception e, Class clazz) {
        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            return BaseResponse.build(clazz, StringUtils.isBlank(be.getResultCode()) ? Constants.FAIL_RESP_CODE : be.getResultCode(), be.getResultMsg(),
                    be.getException() != null ? be.getException().toString() : "");
        } else {
            return BaseResponse.build(clazz, Constants.FAIL_RESP_CODE, Constants.FAIL_RESP_DESC, e.toString());
        }
    }
    
    /**
     * 【资管】查询资产池详情 V3.0 (无需加密)
     * <p/>
     * 测试时浏览器地址栏输入：/p2p/internal/three/ams/queryAutoInvestAmtHttp.json
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/ams/queryAutoInvestAmtHttp", method = RequestMethod.POST)
    public QueryResponse<PoolModel> queryAutoInvestAmtHttp(@RequestBody QueryPoolRequest req) {
        try {
        	return amsService.queryPoolInfoHttp(req);
        } catch (Exception e) {
        	return this.handleException(e, QueryResponse.class);
        }
    }
    
    
    /**
     * 【订单】查询昨日可用复投金额 V3.0
     * <p/>
     * 测试时浏览器地址栏输入：/p2p/internal/three/order/queryAutoInvestAmtHttp.json
     *
     * @param req
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/order/queryAutoInvestAmtHttp", method = RequestMethod.POST)
    public BigDecimal queryAutoInvestAmtHttp(@RequestBody String reqDate) throws Exception {
    	return orderService.queryAutoInvestAmtHttp(reqDate);
    }
    
    
}
