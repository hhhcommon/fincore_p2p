package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class URLDispenserResp {

    @ApiModelProperty(value = "跳转URL")
    private String url;

}
