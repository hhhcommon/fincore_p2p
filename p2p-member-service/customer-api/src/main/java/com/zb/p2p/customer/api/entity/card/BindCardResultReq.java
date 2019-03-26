package com.zb.p2p.customer.api.entity.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by laoguoliang
 */
@ApiModel
public class BindCardResultReq {
    @ApiModelProperty(value="流水号")
    private String outOrderNo;

    public BindCardResultReq() {
    }

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public void setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
    }
    
    @Override
    public String toString(){
        return "BindCardResultReq"
                + "outOrderNo=" + outOrderNo + 
                "]";
    }
}
