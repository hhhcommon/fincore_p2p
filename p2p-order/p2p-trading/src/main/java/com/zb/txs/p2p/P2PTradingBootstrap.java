package com.zb.txs.p2p;


import com.zb.txs.p2p.investment.InvestmentModule;
import com.zb.txs.p2p.jackson.JacksonModule;
import com.zb.txs.p2p.order.OrderModule;
import com.zb.txs.p2p.retrofit.RetrofitModule;
import com.zb.txs.p2p.web.WebModule;
import feign.Logger;
import feign.Retryer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration(exclude = {
        JooqAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class
})

@Import({
        JacksonModule.class,
        WebModule.class,
        OrderModule.class,
        InvestmentModule.class,
        RetrofitModule.class
})

@Configuration
@EnableEurekaClient
@ServletComponentScan(basePackages = "com.zb.txs.p2p.filters")
@ComponentScan(basePackages = {"com.zb.txs.p2p.exception", "com.zb.txs.p2p.util"})
@EnableFeignClients(basePackages = {"com.zb.p2p.customer.api"})
public class P2PTradingBootstrap {

    @Bean
    Retryer feignRetryer() {
        return Retryer.NEVER_RETRY;
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        final CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true); // 允许cookies跨域
//        config.addAllowedOrigin("*");// #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
//        config.addAllowedHeader("*");// #允许访问的头信息,*表示全部
//        config.setMaxAge(18000L);// 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
//        config.addAllowedMethod("OPTIONS");// 允许提交请求的方法，*表示全部允许
//        config.addAllowedMethod("HEAD");
//        config.addAllowedMethod("GET");// 允许Get的请求方法
//        config.addAllowedMethod("PUT");
//        config.addAllowedMethod("POST");
//        config.addAllowedMethod("DELETE");
//        config.addAllowedMethod("PATCH");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }

    public static void main(String[] args) {
        SpringApplication.run(P2PTradingBootstrap.class, args);
    }
}
