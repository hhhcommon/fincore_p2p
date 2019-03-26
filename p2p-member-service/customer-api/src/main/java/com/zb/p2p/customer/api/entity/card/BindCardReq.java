package com.zb.p2p.customer.api.entity.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by laoguoliang
 */
@ApiModel
public class BindCardReq {
    @ApiModelProperty(value="短信验证码")
    private String smsCode;
    @ApiModelProperty(value="订单号")
    private String orderNo;
    
    public BindCardReq() {
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    @Override
    public String toString(){
        return "BindCardReq ["
                + "smsCode=" + smsCode + 
                ", orderNo=" + orderNo +
                "]";
    }
}
