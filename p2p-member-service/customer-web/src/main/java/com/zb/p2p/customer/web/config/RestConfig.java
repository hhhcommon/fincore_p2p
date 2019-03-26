package com.zb.p2p.customer.web.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


import lombok.Data;

@Data
@ConfigurationProperties(prefix="rest")
@Component
public class RestConfig {

	private Integer readTimeout;
	
	private Integer connectionTimeout;
	

}
