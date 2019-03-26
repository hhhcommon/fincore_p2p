package com.zb.p2p.customer.api.entity.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by laoguoliang
 */
@ApiModel
public class ChannelUnBind extends ChannelUnBindReq{
    @ApiModelProperty(value="解绑结果")
    private String status;

    public ChannelUnBind() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
