package com.zb.p2p.customer.controller;

import com.alibaba.fastjson.JSONObject;
import com.zb.p2p.customer.api.entity.MemberVerifyReq;
import com.zb.p2p.customer.api.entity.OpenAccountReq;
import com.zb.p2p.customer.api.entity.OrgMemberVerifyReq;
import com.zb.p2p.customer.api.entity.RegisterOrgMemberReq;
import com.zb.p2p.customer.base.BaseControllerTest;
import com.zb.p2p.customer.client.domain.SyncCorpInfoReq;
import com.zb.p2p.customer.common.enums.IdCardTypeEnum;
import com.zb.p2p.customer.common.enums.OrgCardTypeEnum;
import com.zb.p2p.customer.common.util.CustomerConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class LoginControllerTest extends BaseControllerTest {

    @Test
    public void registerOrgMember() throws Exception {
        // 请求
        RegisterOrgMemberReq req = new RegisterOrgMemberReq();
        req.setOrgName("资邦金服7");
        req.setOrgCardType(OrgCardTypeEnum.JI_GOU_DAI_MA.getCode());
        req.setOrgCardNo("10000007");
        req.setOwnerName("张六");
        req.setOwnerIdCardType(IdCardTypeEnum.IDCARD.getCode());
        req.setOwnerIdCardNo("963852741852967");
        req.setSaleChannel("DLD");
//        req.setSource("MSD")
        req.setTelephone("12345678988");

        String requestJson = JSONObject.toJSONString(req);

        try {
            MvcResult result = mvc.perform(
                    MockMvcRequestBuilders
                            .post("/login/registerOrg")
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
    public void syncOrgMemberInfoTest() throws Exception {
        // 请求
        SyncCorpInfoReq req = new SyncCorpInfoReq();
        req.setLastId(null);
        req.setPageSize(50);

        String requestJson = JSONObject.toJSONString(req);

        try {
            MvcResult result = mvc.perform(
                    MockMvcRequestBuilders
                            .post("/sync/syncOrgMemberIfo")
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