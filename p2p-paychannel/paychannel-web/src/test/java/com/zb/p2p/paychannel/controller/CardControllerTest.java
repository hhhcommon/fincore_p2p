package com.zb.p2p.paychannel.controller;

import com.zb.p2p.paychannel.base.BaseControllerTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class CardControllerTest extends BaseControllerTest {

    private final String memberHost = "http://127.0.0.1:8762/";

    @Test
    public void getCardList() throws Exception {
        List<String> contents = new ArrayList<>();
        contents.add(
                "{" +
                        "\"bankCode\": \"CCB\"" +
                        "}"
        );

        contents.forEach(c -> {
            try {
                MvcResult result = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/card/cardList")
                                .contentType("application/json;charset=UTF-8")
                                .content(c)
                        )
                        .andExpect(status().is(200))
//                        .andExpect(jsonPath("$.code").value("0000"))
                        .andReturn();
                System.out.println("###########################" + result.getResponse().getContentAsString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}