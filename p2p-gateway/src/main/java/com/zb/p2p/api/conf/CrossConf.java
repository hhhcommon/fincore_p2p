package com.zb.p2p.api.conf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Component
@ConfigurationProperties(prefix="env.cross")
public class CrossConf {
	private List<String> allowOriginList = new ArrayList<String>();

	public List<String> getAllowOriginList() {
		return allowOriginList;
	}

	public void setAllowOriginList(List<String> allowOriginList) {
		this.allowOriginList = allowOriginList;
	}
	
}
