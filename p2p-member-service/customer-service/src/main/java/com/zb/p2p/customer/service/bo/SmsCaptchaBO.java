package com.zb.p2p.customer.service.bo;

import lombok.Data;

@Data
public class SmsCaptchaBO {

	private String smsCaptchaVal;
	private Long createTime;
	
	public SmsCaptchaBO() {
		// TODO Auto-generated constructor stub
	}

}
