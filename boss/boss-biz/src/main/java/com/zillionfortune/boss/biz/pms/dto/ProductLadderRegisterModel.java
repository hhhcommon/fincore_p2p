/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.pms.dto;

/**
 * ClassName: ProductLadderRegisterModel <br/>
 * Function: 阶梯收益注册用Model. <br/>
 * Date: 2017年5月9日 上午11:06:45 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class ProductLadderRegisterModel {
	/** investPeriodLoopIndex:当前循环轮次 **/
	private String investPeriodLoopIndex;
	
	/** yieldRate:实际收益率 **/
	private String yieldRate;
	
	/** poundage:手续费 **/
	private String poundage;

	public String getInvestPeriodLoopIndex() {
		return investPeriodLoopIndex;
	}

	public void setInvestPeriodLoopIndex(String investPeriodLoopIndex) {
		this.investPeriodLoopIndex = investPeriodLoopIndex;
	}

	public String getYieldRate() {
		return yieldRate;
	}

	public void setYieldRate(String yieldRate) {
		this.yieldRate = yieldRate;
	}

	public String getPoundage() {
		return poundage;
	}

	public void setPoundage(String poundage) {
		this.poundage = poundage;
	}
}
