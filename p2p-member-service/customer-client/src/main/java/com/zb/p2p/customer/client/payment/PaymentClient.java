package com.zb.p2p.customer.client.payment;

import com.alibaba.fastjson.JSONObject;
import com.zb.p2p.customer.client.domain.*;
import com.zb.p2p.customer.common.model.BaseRes;
import feign.Headers;
import feign.RequestLine;

/**
 * <p> 调用支付服务Client </p>
 *
 * @author Vinson
 * @version PaymentClient.java v1.0 2018/3/8 14:20 Zhengwenquan Exp $
 */
public interface PaymentClient {

    /**
     * 开户操作
     * @param jsonReq
     * @return
     */
    @RequestLine("POST /openAccount")
    @Headers("Content-Type: application/json")
    PaymentResponse openAccount(JSONObject jsonReq);

    /**
     * 查询余额
     * @param req
     * @return
     */
    @RequestLine("POST /queryAccountBalance")
    @Headers("Content-Type: application/json")
    BaseRes<QueryAccountBalanceRes> queryAccountBalance(QueryAccountBalanceReq req);

    /**
     * 手机号、姓名、身份证实名认证操作
     * @param req
     * @return
     */
    @RequestLine("POST /phoneAuth")
    @Headers("Content-Type: application/json")
    PaymentResponse memberPhoneVerify(TelephoneAuthReq req);

    /**
     * 姓名、身份证实名认证操作
     * @param req
     * @return
     */
    @RequestLine("POST /realNameAuth")
    @Headers("Content-Type: application/json")
    PaymentResponse memberRealNameVerify(RealNameAuthReq req);
}
