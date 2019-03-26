/*
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.retrofit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zb.txs.logging.retrofit.OkHttpCatInterceptor;
import com.zb.txs.logging.retrofit.OkhttpLoggingInterceptor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * Function:   retrofit http网络请求框架配置 <br/>
 * Date:   2017年09月21日 上午10:25 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Configuration
public class RetrofitModule {

    @Value("${financial.domain}")
    private String financialDomain;

    @Value("${pay.domain}")
    private String payDomain;

    @Value("${pms.domain}")
    private String pmsDomain;

    @Value("${member.domain}")
    private String memberDomain;

    @Value("${txsProduct.domain}")
    private String txsProductDomain;

    @Primary
    @Bean("financialRetrofit")
    public Retrofit financialRetrofit(ObjectMapper objectMapper) {
        return new Retrofit.Builder()
                .baseUrl(financialDomain)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava2
                .client(okHttpClient())
                .build();
    }

    @Bean("payRetrofit")
    public Retrofit payRetrofit(ObjectMapper objectMapper) {
        return new Retrofit.Builder()
                .baseUrl(payDomain)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava2
                .client(okHttpClient())
                .build();
    }

    @Bean("pmsRetrofit")
    public Retrofit pmsRetrofit(ObjectMapper objectMapper) {
        return new Retrofit.Builder()
                .baseUrl(pmsDomain)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava2
                .client(okHttpClient())
                .build();
    }

    @Bean("memberRetrofit")
    public Retrofit memberRetrofit(ObjectMapper objectMapper) {
        return new Retrofit.Builder()
                .baseUrl(memberDomain)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava2
                .client(okHttpClient())
                .build();
    }

    @Bean("txsProductRetrofit")
    public Retrofit txsProductRetrofit(ObjectMapper objectMapper) {
        return new Retrofit.Builder()
                .baseUrl(txsProductDomain)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava2
                .client(okHttpClient())
                .build();
    }

    private OkHttpClient okHttpClient() {
        OkhttpLoggingInterceptor okhttpLoggingInterceptor = new OkhttpLoggingInterceptor();
        okhttpLoggingInterceptor.setLevel(OkhttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.MILLISECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(okhttpLoggingInterceptor)
                .addInterceptor(new OkHttpCatInterceptor())
                .build();
    }
}
