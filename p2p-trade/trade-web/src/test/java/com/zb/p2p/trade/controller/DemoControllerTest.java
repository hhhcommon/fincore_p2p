package com.zb.p2p.trade.controller;

import com.zb.p2p.trade.base.BaseControllerTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class DemoControllerTest extends BaseControllerTest {



    @Test
    public void syncOrgMemberInfoTest() throws Exception {
        // 请求
//        SyncCorpInfoReq req = new SyncCorpInfoReq();
//        req.setLastId(null);
//        req.setPageSize(50);
//
//        String requestJson = JSONObject.toJSONString(req);
//
//        try {
//            MvcResult result = mvc.performCash(
//                    MockMvcRequestBuilders
//                            .post("/sync/syncOrgMemberIfo")
//                            .contentType("application/json;charset=UTF-8")
//                            .content(requestJson)
//            )
//                    .andExpect(status().is(200))
////                        .andExpect(jsonPath("$.code").value("0000"))
//                    .andReturn();
//            System.out.println("###########结果内容#############" + result.getResponse().getContentAsString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}