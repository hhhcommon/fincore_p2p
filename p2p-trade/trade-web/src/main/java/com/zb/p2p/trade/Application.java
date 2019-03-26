package com.zb.p2p.trade;

import com.zb.p2p.trade.web.config.FeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;
import org.springframework.util.Assert;

import java.io.File;


@ServletComponentScan
@SpringBootApplication
@MapperScan("com.zb.p2p.trade.persistence")
@ImportResource(locations = {"classpath:application-bean.xml"})
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.zb.p2p", defaultConfiguration = FeignConfig.class)
public class Application {

    final static String APP_CONFIG_PATH = "TRADE_APP_CONF_PATH";
    final static String LOG_CONFIG_PATH = "TRADE_LOG_CONF_PATH";

    public static void main(String[] args) {
        String envPath = System.getenv(APP_CONFIG_PATH);
        Assert.notNull(envPath, APP_CONFIG_PATH + " is blank");
        if (!envPath.endsWith(File.separator)) {
            envPath = envPath + File.separator;
        }
        System.setProperty("spring.config.location", envPath);//合同使用到这个系统变量，修改的时候请注意
        String logEnvPath = System.getenv(LOG_CONFIG_PATH);
        Assert.notNull(logEnvPath, LOG_CONFIG_PATH + " is blank");
        if (!logEnvPath.endsWith(File.separator)) {
            logEnvPath = logEnvPath + File.separator;
        }
        System.setProperty("log.base", logEnvPath);
        SpringApplication.run(Application.class, args);
    }

}
