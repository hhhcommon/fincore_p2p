package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel
public class CheckCodeRes {
	
    @ApiModelProperty(value="授权注册的临时口令 若是老用户 则返回 loginToken")
	private String token;
    @ApiModelProperty(value="下一步  1-授权   2-激活  3-其他 4-开户绑卡页 5-开户绑卡处理中页")
	private String nextStep;
//    @ApiModelProperty(value="手机号 ")
//	private String mobile;
    @ApiModelProperty(value=" 1-理财首页2-我的页面3-我的银行卡 4-首页 5-预约产品页 6-我的礼券-适用产品 ")
    private String sourcePage;
    @ApiModelProperty(value=" 参数 6的时候 为 {“qjsCouponId”:7778889000000033}")
    private String sourceParam;
    @ApiModelProperty(value="会员ID摘要")
    private String customerId;
    
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getNextStep() {
		return nextStep;
	}
	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}
//	public String getMobile() {
//		return mobile;
//	}
//	public void setMobile(String mobile) {
//		this.mobile = mobile;
//	}
	public String getSourcePage() {
		return sourcePage;
	}
	public void setSourcePage(String sourcePage) {
		this.sourcePage = sourcePage;
	}
	public String getSourceParam() {
		return sourceParam;
	}
	public void setSourceParam(String sourceParam) {
		this.sourceParam = sourceParam;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
    
    
    
}
