package com.zb.txs.p2p.jackson;

import com.fasterxml.jackson.databind.Module;
import com.zb.txs.foundation.response.jackson.JacksonModules;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonModule {

    @Bean
    public Module dateModule(){
        return JacksonModules.dateModule();
    }

    @Bean
    public Module resultModule(){
        return JacksonModules.resultModule();
    }

}
