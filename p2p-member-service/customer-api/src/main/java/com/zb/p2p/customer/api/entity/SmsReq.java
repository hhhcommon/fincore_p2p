package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class SmsReq {

	private String bizCode;//业务编号
	
	private String phone;//手机号
	
	private String content;//内容
	/**
	 * msgMode=100：基于节点发送 会根据nodeKey找到对应的模板进行发送.
	 * 
	 * msgMode=2XX：短信直接发送 201：短信营销类直接发送， 202：短信安全类直接发送， 203：短信账户类直接发送，
	 * 204：短信交易类直接发送， 205：短信通知类直接发送， 206：短信验证码类直接发送 直接根据content发送。
	 * 
	 * 
	 * 
	 */
	private String msgMode;
	

	
}
