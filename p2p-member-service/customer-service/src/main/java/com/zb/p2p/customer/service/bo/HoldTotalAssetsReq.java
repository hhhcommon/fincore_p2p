package com.zb.p2p.customer.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HoldTotalAssetsReq {
	 
	@ApiModelProperty(value="")
	private String memberId;
	
	@ApiModelProperty(value="收益日期")
	private String interestDate;
	
	private String caller; //caller  调用方   JHHY：金核会员；TXS为空
	 
	
	 
	
}
