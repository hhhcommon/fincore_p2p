package com.zb.p2p.customer.api.entity.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by laoguoliang
 */
@ApiModel
public class UnBindReq {
    @ApiModelProperty(value="短信验证码")
    private String smsCode;
    @ApiModelProperty(value="银行卡绑定id")
    private String bindId;
    
    @ApiModelProperty(value="")
    private String customerId;
    
    public UnBindReq() {
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }
    
    @Override
    public String toString(){
        return "UnBindReq ["
                + "bindId=" + bindId + 
                "]";
    }

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}
