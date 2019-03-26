package com.zb.txs.p2p.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class WebModule extends WebMvcConfigurerAdapter {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(ObjectMapper objectMapper) {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new MappingJackson2HttpMessageConverter(objectMapper));
        return  new RestTemplate(messageConverters);
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .exposedHeaders(
//                        "Access-Control-Allow-Headers",
//                        "Access-Control-Allow-Methods",
//                        "Access-Control-Allow-Origin",
//                        "Access-Control-Max-Age"
//                )
//                .allowedHeaders(
//                        "*")
//                .allowedMethods("*")
//
//                .allowedOrigins(origins.split(","));
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor(authClient, restTemplate,objectMapper))
//                .addPathPatterns(
//                        "/p2p/trading/order/**"
////                        ,"/p2p/asset/**"
////                        ,"/p2p/investment/**"
//                );
//    }
}
