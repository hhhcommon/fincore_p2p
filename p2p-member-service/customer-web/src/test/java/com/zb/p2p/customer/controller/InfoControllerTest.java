package com.zb.p2p.customer.controller;

import com.alibaba.fastjson.JSONObject;
import com.zb.p2p.customer.api.entity.MemberVerifyReq;
import com.zb.p2p.customer.api.entity.OpenAccountReq;
import com.zb.p2p.customer.api.entity.OrgMemberInfoReq;
import com.zb.p2p.customer.api.entity.OrgMemberVerifyReq;
import com.zb.p2p.customer.base.BaseControllerTest;
import com.zb.p2p.customer.client.domain.QueryAccountBalanceReq;
import com.zb.p2p.customer.client.domain.QueryAccountBalanceRes;
import com.zb.p2p.customer.client.domain.RealNameAuthReq;
import com.zb.p2p.customer.client.payment.PaymentClient;
import com.zb.p2p.customer.common.enums.AccountPurposeEnum;
import com.zb.p2p.customer.common.model.BaseRes;
import com.zb.p2p.customer.common.util.CustomerConstants;
import com.zb.p2p.customer.service.convert.CustomerInfoConvert;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class InfoControllerTest extends BaseControllerTest {

    @Autowired
    private PaymentClient paymentClient;

    @Test
    public void openAccountTest() throws Exception {
        // 请求
        OpenAccountReq req = new OpenAccountReq();
        req.setMemberId("100001");
        req.setMemberType("20");
        req.setSourceId(CustomerConstants.PAYMENT_SOURCEID_MSD);
        req.setAccountType(CustomerConstants.PAYMENT_ACCOUNT_TYPE_LOGIC);
        req.setAccountPurpose(AccountPurposeEnum._102.getCode());//账户用途(102:出借投资账户)

        String requestJson = JSONObject.toJSONString(req);

        try {
            MvcResult result = mvc.perform(
                    MockMvcRequestBuilders
                            .post("/info/openAccount")
                            .contentType("application/json;charset=UTF-8")
                            .content(requestJson)
            )
                    .andExpect(status().is(200))
//                        .andExpect(jsonPath("$.code").value("0000"))
                    .andReturn();
            System.out.println("###########结果内容#############" + result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void verifyMemberTest() throws Exception {
        // 请求
        MemberVerifyReq req = new MemberVerifyReq();
        req.setCustomerId("10000000000186");
//        req.setMobile("13999999999");
        req.setMemberName("张三");
        req.setCertificateNo("432503200202277671");

        RealNameAuthReq authReq = CustomerInfoConvert.convert2RealNameAuthReq(req);

        String requestJson = JSONObject.toJSONString(authReq);

        try {
            MvcResult result = mvc.perform(
                    MockMvcRequestBuilders
                            .post("/info/verifyMember")
                            .contentType("application/json;charset=UTF-8")
                            .header("customerId", "10000000000186")
                            .content(requestJson)
            )
                    .andExpect(status().is(200))
//                        .andExpect(jsonPath("$.code").value("0000"))
                    .andReturn();
            System.out.println("###########结果内容#############" + result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void verifyOrgMemberTest() throws Exception {
        // 请求
        OrgMemberVerifyReq req = new OrgMemberVerifyReq();
        req.setOrgId("100004");
//        req.setOrgName("圆通物流（天津）有限公司");
        req.setSuccessUrl("www.baidu.com");
        req.setSaleChannel("ZD");
        req.setAuthentType("FULL");

        String requestJson = JSONObject.toJSONString(req);

        try {
            MvcResult result = mvc.perform(
                    MockMvcRequestBuilders
                            .post("/info/verifyOrgMember")
                            .contentType("application/json;charset=UTF-8")
                            .content(requestJson)
            )
                    .andExpect(status().is(200))
//                        .andExpect(jsonPath("$.code").value("0000"))
                    .andReturn();
            System.out.println("###########结果内容#############" + result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queryAccountBalanceTest() throws Exception {
        // 请求
        QueryAccountBalanceReq req = new QueryAccountBalanceReq();
//        req.setMemberId("100001");
//        req.setSourceId(CustomerConstants.PAYMENT_SOURCEID_MSD);
//        req.setAccountPurpose(AccountPurposeEnum._102.getCode());//账户用途(102:出借投资账户)

//        String requestJson = JSONObject.toJSONString(req);

        BaseRes<QueryAccountBalanceRes> result;
        try {
            result = paymentClient.queryAccountBalance(req);
            System.out.println("###########结果内容#############" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void orgMemberInfoTest() throws Exception {
        // 请求
        OrgMemberInfoReq req = new OrgMemberInfoReq();
        req.setSourceId("MSD_");

        String requestJson = JSONObject.toJSONString(req);

        try {
            MvcResult result = mvc.perform(
                    MockMvcRequestBuilders
                            .post("/info/orgMemberInfo")
                            .contentType("application/json;charset=UTF-8")
                            .content(requestJson)
            )
                    .andExpect(status().is(200))
//                        .andExpect(jsonPath("$.code").value("0000"))
                    .andReturn();
            System.out.println("###########结果内容#############" + result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}