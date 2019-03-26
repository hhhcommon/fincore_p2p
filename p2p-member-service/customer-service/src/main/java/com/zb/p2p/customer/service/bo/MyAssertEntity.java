package com.zb.p2p.customer.service.bo;

import io.swagger.annotations.ApiModelProperty;

public class MyAssertEntity {
	@ApiModelProperty(value="资产金额")
	private String assetsAmount;//资产金额
	@ApiModelProperty(value="昨日收益")
	private String ystProfit;//昨日收益
	@ApiModelProperty(value="累积收益")
	private String accumulatedProfit;
	public String getAssetsAmount() {
		return assetsAmount;
	}
	public void setAssetsAmount(String assetsAmount) {
		this.assetsAmount = assetsAmount;
	}
	public String getYstProfit() {
		return ystProfit;
	}
	public void setYstProfit(String ystProfit) {
		this.ystProfit = ystProfit;
	}
	public String getAccumulatedProfit() {
		return accumulatedProfit;
	}
	public void setAccumulatedProfit(String accumulatedProfit) {
		this.accumulatedProfit = accumulatedProfit;
	}
	
}
