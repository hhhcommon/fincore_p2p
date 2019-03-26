package com.zb.fincore.pms.facade.product.dto.req;


import com.zb.fincore.pms.common.dto.BaseRequest;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 查询产品详情请求参数
 *
 * @author
 * @create 2016-12-22 19:52
 */
public class QueryProductInfoForTradeRequest extends BaseRequest {

//    @NotBlank(message = "资产池编号不能为空")
    private String assetPoolCode;

    @NotNull(message = "产品起息时间不能为空")
    private Date valueTime;
    
    @NotNull(message = "投资期限不能为空")
    private Integer investPeriod;

	public String getAssetPoolCode() {
		return assetPoolCode;
	}

	public void setAssetPoolCode(String assetPoolCode) {
		this.assetPoolCode = assetPoolCode;
	}

	public Date getValueTime() {
		return valueTime;
	}

	public void setValueTime(Date valueTime) {
		this.valueTime = valueTime;
	}

	public Integer getInvestPeriod() {
		return investPeriod;
	}

	public void setInvestPeriod(Integer investPeriod) {
		this.investPeriod = investPeriod;
	}
}
