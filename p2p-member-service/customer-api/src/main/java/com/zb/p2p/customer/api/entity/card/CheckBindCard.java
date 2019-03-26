package com.zb.p2p.customer.api.entity.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by laoguoliang
 */
@ApiModel
public class CheckBindCard {
    @ApiModelProperty(value="开户可否状态 1可开 2不可开")
    private int status;

    public CheckBindCard() {
    }
    
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString(){
        return "CheckBindCard ["
                + "status=" + status + 
                "]";
    }
}
