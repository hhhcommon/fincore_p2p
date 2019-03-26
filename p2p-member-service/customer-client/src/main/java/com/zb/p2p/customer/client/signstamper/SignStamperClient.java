package com.zb.p2p.customer.client.signstamper;

import com.zb.p2p.customer.client.domain.CompanyAuthentReq;
import com.zb.p2p.customer.client.domain.CompanyAuthentRes;
import feign.Headers;
import feign.RequestLine;

/**
 * <p> 调用电子签章(合同中心)服务Client </p>
 *
 * @author Vinson
 * @version SignStamperClient.java v1.0 2018/3/9 14:20 Zhengwenquan Exp $
 */
public interface SignStamperClient {

    /**
     * 机构认证操作
     * @param req
     * @return
     */
    @RequestLine("POST /companyAuthent")
    @Headers("Content-Type: application/json")
    CompanyAuthentRes orgMemberVerify(CompanyAuthentReq req);

    /**
     * 查看机构认证结果
     * @param req
     * @return
     */
    @RequestLine("POST /companyAuthentResultUrl")
    @Headers("Content-Type: application/json")
    CompanyAuthentRes getMemberVerifyInfo(CompanyAuthentReq req);
}
