package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(description = "余额信息")
public class BalanceInfoRes {
	@ApiModelProperty(value="可用金额（元）",required=true)
	private String usableAmount;
}
