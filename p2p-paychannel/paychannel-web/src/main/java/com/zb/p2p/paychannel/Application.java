package com.zb.p2p.paychannel;

import com.zb.p2p.paychannel.common.util.StringUtils;
import com.zb.p2p.paychannel.web.config.FeignConfig;
import feign.Logger;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.client.RestTemplate;

import java.io.File;

/**
 */
@SpringBootApplication(exclude = MybatisAutoConfiguration.class)
@ImportResource(locations = {"classpath:application-bean.xml"})
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.zb.p2p", defaultConfiguration = FeignConfig.class)
//@EnableScheduling
public class Application {
    public static void main(String[] args) {
        //设置配置文件路径
        String envPath = System.getenv("MATCH_ENV_CONF_PATH".toUpperCase());
        if (StringUtils.isBlank(envPath)) {
            throw new IllegalStateException("MATCH_ENV_CONF_PATH".toUpperCase() + " is blank");
        }
        if (!envPath.endsWith(File.separator))
            envPath = envPath + File.separator;
        String configPath = envPath + File.separator;
        System.setProperty("spring.config.location", configPath);
        //设置log输出路径
        String logEnvPath = System.getenv("MATCH_LOG_CONF_PATH".toUpperCase());
        if (StringUtils.isBlank(logEnvPath)) {
            throw new IllegalStateException("MATCH_LOG_CONF_PATH".toUpperCase() + " is blank");
        }
        if (!logEnvPath.endsWith(File.separator)) {
            logEnvPath = logEnvPath + File.separator;
        }
        String logPath = logEnvPath + File.separator;
//        String logPath = logEnvPath + "log" + File.separator;
        System.setProperty("log.base", logPath);

        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate rt = new RestTemplate();
        return rt;
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return feign.Logger.Level.FULL;
    }

}
