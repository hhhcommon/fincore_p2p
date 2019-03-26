package com.zb.p2p.api;

import com.zb.p2p.api.filter.CrossFilter;
import com.zb.p2p.api.filter.LoginTokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@ServletComponentScan
@EnableZuulProxy
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class ApiWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiWebApplication.class, args);
    }

    @Bean
    public LoginTokenFilter loginTokenFilter() {
        return new LoginTokenFilter();
    }

    @Bean
    public CrossFilter crossFilter() {
        return new CrossFilter();
    }
}
