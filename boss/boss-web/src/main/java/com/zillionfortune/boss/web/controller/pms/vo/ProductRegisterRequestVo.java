/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.pms.vo;

import com.zb.fincore.pms.common.enums.OpenTypeEnum;
import com.zb.fincore.pms.common.enums.RegisterTypeEnum;
import com.zillionfortune.boss.biz.pms.dto.ProductLadderRegisterModel;
import com.zillionfortune.boss.dal.entity.FileInfoConvert;

import java.util.Date;
import java.util.List;

/**
 * ClassName: ProductRegisterRequestVo <br/>
 * Function: 产品注册用Vo. <br/>
 * Date: 2017年5月9日 上午11:06:45 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class ProductRegisterRequestVo {
	/** productName:产品名称 **/
	private String productName;

    /** productDisplayName:展示名称 **/
    private String productDisplayName;
	
	/** productLineId:产品线Id **/
	private String productLineId;

    /** assetPoolCode:资产池编号 **/
    private String assetPoolCode;

    /** assetPoolName:资产池名称 **/
    private String assetPoolName;

    /** assetPoolType:资产池类型 **/
    private String assetPoolType;
	
	/** saleChannelCode:销售渠道 **/
	private String saleChannelCode;
	
	/** riskLevel:风险等级 **/
	private String riskLevel;
	
	/** patternCode:产品类型 **/
	private String patternCode;
	
	/** joinChannelCode:接入渠道 **/
	private String joinChannelCode;
	
	/** calendarMode:日历模式 **/
	private String calendarMode;
	
	/** saleStatus:销售状态 **/
	private String saleStatus;
	
	/** collectStatus:募集状态 **/
	private String collectStatus;
	
	/** informationDisclosure:信息披露 **/
	private List<FileInfoConvert> informationDisclosure;
	
	/** productLadderList:阶梯收益列表 **/
	private List<ProductLadderRegisterModel> productLadderList;
	
	/** introduction:产品介绍 **/
	private String introduction;
	
	/** totalAmount:募集总规模 **/
	private String totalAmount;
	
	/** increaseAmount:步长（递增金额） **/
	private String increaseAmount;
	

	
	/** maxInvestAmount:个人限投 **/
	private String maxInvestAmount;
	
	/** minInvestAmount:起购金额 **/
	private String minInvestAmount;
	
	/** minHoldAmount:最低可持有金额 **/
	private String minHoldAmount;
	
	/** fundSettleParty:资金结算方 **/
	private String fundSettleParty;
	
	/** maxYieldRate:预期年化收益率上限 **/
	private String maxYieldRate;
	
	/** minYieldRate:预期年化收益率下限 **/
	private String minYieldRate;
	
	/** saleStartTime:募集起始日 **/
	private String saleStartTime;
	
	/** saleEndTime:募集截止日 **/
	private String saleEndTime;
	
	/** valueTime:起息日 **/
	private String valueTime;
	
	/** investPeriodLoopUnit:循环周期 **/
	private String investPeriodLoopUnit;
	
	/** expireTime:到期日（预期） **/
	private String expectExpireTime;
	
	private String expectEstablishTime;
	
	/** expectClearTime:到期回款日（预期结清时间） **/
	private String expectClearTime;
	
	/** investPeriod:产品期限 **/
	private String investPeriod;
	
	/** investPeriodUnit:投资期限单位 **/
	private String investPeriodUnit;
	
	/** currentYieldRate:当期利率 **/
	private String currentYieldRate;
	
	/** floatingYieldRate:浮动利率 **/
	private String floatingYieldRate;
	
	private String loanOrderNoSet;
	
	private String collectMode;



    /**
     * 注册类型（AUTO:自动,UNAUTO:非自动）
     */
    private String registerType = RegisterTypeEnum.UNAUTO.getCode() ;

    /**
     * 开放类型（IN:对内,OUT:对外）
     */
    private String openType = OpenTypeEnum.OUT.getCode();

    /** 支付渠道 */
    private String payChannel;

    /** 购买方式：账户余额、至尊宝、银行卡 */
    private String buyWays;

    /** 产品期数 */
    private String numberPeriod;

    /** 止息时间 */
    private Date ceaseTime;


	public String getLoanOrderNoSet() {
		return loanOrderNoSet;
	}

	public void setLoanOrderNoSet(String loanOrderNoSet) {
		this.loanOrderNoSet = loanOrderNoSet;
	}

	public String getCollectMode() {
		return collectMode;
	}

	public void setCollectMode(String collectMode) {
		this.collectMode = collectMode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductLineId() {
		return productLineId;
	}

	public void setProductLineId(String productLineId) {
		this.productLineId = productLineId;
	}

	public String getExpectEstablishTime() {
		return expectEstablishTime;
	}

	public void setExpectEstablishTime(String expectEstablishTime) {
		this.expectEstablishTime = expectEstablishTime;
	}

	public String getSaleChannelCode() {
		return saleChannelCode;
	}

	public void setSaleChannelCode(String saleChannelCode) {
		this.saleChannelCode = saleChannelCode;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getProductDisplayName() {
		return productDisplayName;
	}

	public void setProductDisplayName(String productDisplayName) {
		this.productDisplayName = productDisplayName;
	}

	public String getPatternCode() {
		return patternCode;
	}

	public void setPatternCode(String patternCode) {
		this.patternCode = patternCode;
	}

	public String getJoinChannelCode() {
		return joinChannelCode;
	}

	public void setJoinChannelCode(String joinChannelCode) {
		this.joinChannelCode = joinChannelCode;
	}

	public String getCalendarMode() {
		return calendarMode;
	}

	public void setCalendarMode(String calendarMode) {
		this.calendarMode = calendarMode;
	}

	public String getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}

	public String getCollectStatus() {
		return collectStatus;
	}

	public void setCollectStatus(String collectStatus) {
		this.collectStatus = collectStatus;
	}

	public List<FileInfoConvert> getInformationDisclosure() {
		return informationDisclosure;
	}

	public void setInformationDisclosure(List<FileInfoConvert> informationDisclosure) {
		this.informationDisclosure = informationDisclosure;
	}

	public List<ProductLadderRegisterModel> getProductLadderList() {
		return productLadderList;
	}

	public void setProductLadderList(List<ProductLadderRegisterModel> productLadderList) {
		this.productLadderList = productLadderList;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getIncreaseAmount() {
		return increaseAmount;
	}

	public void setIncreaseAmount(String increaseAmount) {
		this.increaseAmount = increaseAmount;
	}

	public String getAssetPoolType() {
		return assetPoolType;
	}

	public void setAssetPoolType(String assetPoolType) {
		this.assetPoolType = assetPoolType;
	}

	public String getMaxInvestAmount() {
		return maxInvestAmount;
	}

	public void setMaxInvestAmount(String maxInvestAmount) {
		this.maxInvestAmount = maxInvestAmount;
	}

	public String getMinInvestAmount() {
		return minInvestAmount;
	}

	public void setMinInvestAmount(String minInvestAmount) {
		this.minInvestAmount = minInvestAmount;
	}

	public String getAssetPoolCode() {
		return assetPoolCode;
	}

	public void setAssetPoolCode(String assetPoolCode) {
		this.assetPoolCode = assetPoolCode;
	}

	public String getAssetPoolName() {
		return assetPoolName;
	}

	public void setAssetPoolName(String assetPoolName) {
		this.assetPoolName = assetPoolName;
	}

	public String getMinHoldAmount() {
		return minHoldAmount;
	}

	public void setMinHoldAmount(String minHoldAmount) {
		this.minHoldAmount = minHoldAmount;
	}

	public String getFundSettleParty() {
		return fundSettleParty;
	}

	public void setFundSettleParty(String fundSettleParty) {
		this.fundSettleParty = fundSettleParty;
	}

	public String getMaxYieldRate() {
		return maxYieldRate;
	}

	public void setMaxYieldRate(String maxYieldRate) {
		this.maxYieldRate = maxYieldRate;
	}

	public String getMinYieldRate() {
		return minYieldRate;
	}

	public void setMinYieldRate(String minYieldRate) {
		this.minYieldRate = minYieldRate;
	}

	public String getSaleStartTime() {
		return saleStartTime;
	}

	public void setSaleStartTime(String saleStartTime) {
		this.saleStartTime = saleStartTime;
	}

	public String getSaleEndTime() {
		return saleEndTime;
	}

	public void setSaleEndTime(String saleEndTime) {
		this.saleEndTime = saleEndTime;
	}

	public String getValueTime() {
		return valueTime;
	}

	public void setValueTime(String valueTime) {
		this.valueTime = valueTime;
	}

	public String getInvestPeriodLoopUnit() {
		return investPeriodLoopUnit;
	}

	public void setInvestPeriodLoopUnit(String investPeriodLoopUnit) {
		this.investPeriodLoopUnit = investPeriodLoopUnit;
	}

	public String getExpectExpireTime() {
		return expectExpireTime;
	}

	public void setExpectExpireTime(String expectExpireTime) {
		this.expectExpireTime = expectExpireTime;
	}

	public String getExpectClearTime() {
		return expectClearTime;
	}

	public void setExpectClearTime(String expectClearTime) {
		this.expectClearTime = expectClearTime;
	}

	public String getInvestPeriod() {
		return investPeriod;
	}

	public void setInvestPeriod(String investPeriod) {
		this.investPeriod = investPeriod;
	}

	public String getInvestPeriodUnit() {
		return investPeriodUnit;
	}

	public void setInvestPeriodUnit(String investPeriodUnit) {
		this.investPeriodUnit = investPeriodUnit;
	}

	public String getCurrentYieldRate() {
		return currentYieldRate;
	}

	public void setCurrentYieldRate(String currentYieldRate) {
		this.currentYieldRate = currentYieldRate;
	}

	public String getFloatingYieldRate() {
		return floatingYieldRate;
	}

	public void setFloatingYieldRate(String floatingYieldRate) {
		this.floatingYieldRate = floatingYieldRate;
	}

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getBuyWays() {
        return buyWays;
    }

    public void setBuyWays(String buyWays) {
        this.buyWays = buyWays;
    }

    public String getNumberPeriod() {
        return numberPeriod;
    }

    public void setNumberPeriod(String numberPeriod) {
        this.numberPeriod = numberPeriod;
    }

    public Date getCeaseTime() {
        return ceaseTime;
    }

    public void setCeaseTime(Date ceaseTime) {
        this.ceaseTime = ceaseTime;
    }
}
