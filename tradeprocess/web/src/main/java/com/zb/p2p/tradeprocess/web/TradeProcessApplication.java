package com.zb.p2p.tradeprocess.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by limingxin on 2017/8/25.
 */
@SpringBootApplication
@ImportResource("classpath*:spring/spring-basic.xml")
@PropertySource("file:${p2p_conf_path}/env.properties")
//@PropertySource("classpath:env.properties")
public class TradeProcessApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradeProcessApplication.class, args);
    }
}
