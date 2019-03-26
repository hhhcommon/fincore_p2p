package com.zb.p2p.customer.client.msdfinance;

import com.zb.p2p.customer.client.domain.TelephoneAuthReq;
import com.zb.p2p.customer.common.model.BaseRes;
import feign.Headers;
import feign.RequestLine;

/**
 * <p> 调用马上贷后端服务Client </p>
 *
 * @author Vinson
 * @version MsdFinanceClient.java v1.0 2018/3/9 14:20 Zhengwenquan Exp $
 */
public interface MsdFinanceClient {

    /**
     * 实名认证操作
     * @param req
     * @return
     */
    @RequestLine("POST /telecomAuth/phoneAuth")
    @Headers("Content-Type: application/json")
    BaseRes<String> memberVerify(TelephoneAuthReq req);
}
