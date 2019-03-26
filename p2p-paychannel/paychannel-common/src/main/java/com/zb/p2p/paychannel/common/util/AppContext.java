package com.zb.p2p.paychannel.common.util;

public class AppContext {

	private static ThreadLocal<AppContext> appContext = new ThreadLocal<>();
	
	private Long customerId;

	private AppContext() {
		super();
	}
	
	public static AppContext getInstance() {
		AppContext instance = appContext.get();
		if(instance == null) {
			instance = new AppContext();
			appContext.set(instance);
		}
		return instance;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	
}
