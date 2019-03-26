package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by haoyifen on 16-9-28 2016 下午5:53
 */
@ApiModel
public class KaptchaSMSMsg {
	@ApiModelProperty(value="会员手机")
    private String mobile;
	//@ApiModelProperty(value="验证码，4位")
    //private String codeKaptcha;//4位验证

    public KaptchaSMSMsg() {
    }

    public KaptchaSMSMsg(String mobile) {
        this.mobile = mobile;
        //this.codeKaptcha = codeKaptcha;
    }

    public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
