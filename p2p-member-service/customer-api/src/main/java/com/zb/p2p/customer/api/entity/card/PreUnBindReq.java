package com.zb.p2p.customer.api.entity.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by laoguoliang
 */
@ApiModel
public class PreUnBindReq {
    @ApiModelProperty(value="银行卡绑定id")
    private String bindId;
    
    public PreUnBindReq() {
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }
    
    @Override
    public String toString(){
        return "PreUnBindReq ["
                + "bindId=" + bindId + 
                "]";
    }
}
