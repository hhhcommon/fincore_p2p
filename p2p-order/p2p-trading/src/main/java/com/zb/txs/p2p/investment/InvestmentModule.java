package com.zb.txs.p2p.investment;

import com.zb.txs.p2p.investment.httpclient.InvestmentClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;

@Configuration
@ComponentScan("com.zb.txs.p2p.investment")
public class InvestmentModule {
    @Bean
    public InvestmentClient investmentClient (@Qualifier("financialRetrofit") Retrofit retrofit){
        return  retrofit.create(InvestmentClient.class);
    }
}
