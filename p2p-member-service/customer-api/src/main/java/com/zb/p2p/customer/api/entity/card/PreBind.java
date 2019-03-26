package com.zb.p2p.customer.api.entity.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by laoguoliang
 */
@ApiModel
public class PreBind {
    @ApiModelProperty(value="订单号")
    private String orderNo;

    public PreBind() {
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    @Override
    public String toString(){
        return "PreBind ["
                + "orderNo=" + orderNo + 
                "]";
    }
}
