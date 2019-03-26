package com.zb.p2p.customer;

import feign.Logger;
import feign.Retryer;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.Assert;

import java.io.File;


@EnableTransactionManagement
@ServletComponentScan
@SpringBootApplication
@MapperScan("com.zb.p2p.customer.dao")
@ImportResource(locations = {"classpath:application-bean.xml"})
@EnableEurekaClient
@EnableFeignClients
public class Application {

    final static String APP_CONFIG_PATH = "MEMBER_APP_ENV";

    final static String LOG_CONFIG_PATH = "MEMBER_LOG_ENV";

    public static void main(String[] args) {
        String envPath = System.getenv(APP_CONFIG_PATH);
        Assert.notNull(envPath, APP_CONFIG_PATH + " is blank");
        if (!envPath.endsWith(File.separator))
            envPath = envPath + File.separator;
        String configPath = envPath + File.separator;
        System.setProperty("spring.config.location", configPath);
        String logEnvPath = System.getenv(LOG_CONFIG_PATH);
        Assert.notNull(logEnvPath, LOG_CONFIG_PATH + " is blank");
        if (!logEnvPath.endsWith(File.separator)) {
            logEnvPath = logEnvPath + File.separator;
        }
        String logPath = logEnvPath + File.separator;
        System.setProperty("log.base", logPath);

        SpringApplication.run(Application.class, args);
    }


    /**
     * cloud-feign日志级别
     *
     * @return
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return feign.Logger.Level.FULL;
    }

    /**
     * cloud-feign不重试
     *
     * @return
     */
    @Bean
    public Retryer feignRetryer() {
        return Retryer.NEVER_RETRY;
    }

}
