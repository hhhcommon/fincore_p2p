package com.zb.p2p.api.conf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Component
@ConfigurationProperties(prefix="env.whitelist")
public class WhitelistConf {
	private List<String> allowedUrl = new ArrayList<String>();
	private List<String> allowedNotNeedLoginUrl = new ArrayList<String>();	
	

	public List<String> getAllowedNotNeedLoginUrl() {
		return allowedNotNeedLoginUrl;
	}

	public void setAllowedNotNeedLoginUrl(List<String> allowedNotNeedLoginUrl) {
		this.allowedNotNeedLoginUrl = allowedNotNeedLoginUrl;
	}

	public List<String> getAllowedUrl() {
		return allowedUrl;
	}

	public void setAllowedUrl(List<String> allowedUrl) {
		this.allowedUrl = allowedUrl;
	}
	
	//private List<String> allowedNoLoginUrl;
	
	
}
