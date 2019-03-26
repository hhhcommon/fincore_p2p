/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.pms.dto;

import com.zb.fincore.pms.common.enums.OpenTypeEnum;
import com.zb.fincore.pms.common.enums.RegisterTypeEnum;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ClassName: QueryProductDetailModel <br/>
 * Function: 产品详情查询返回结果Model. <br/>
 * Date: 2017年5月9日 上午11:06:45 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class QueryProductDetailModel {
	// -----------产品基本信息--------------
	/** productId:产品Id **/
	private String productId;
	
	/** productCode:产品编号 **/
	private String productCode;
	
	/** productName:产品名称 **/
	private String productName;
	
	/** productDisplayName:展示名称 **/
	private String productDisplayName;
	
	/** productLineId:产品线Id **/
	private String productLineId;
	
	/** productLineCode:产品线编号 **/
	private String productLineCode;
	
	/** productLineName:产品线名称 **/
	private String productLineName;
	
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
	private String informationDisclosure;
	
	/** introduction:产品介绍 **/
	private String introduction;

    /**
     * 注册类型（AUTO:自动,UNAUTO:非自动）
     */
    private String registerType = RegisterTypeEnum.UNAUTO.getCode() ;

	// -----------产品交易信息--------------
	/** totalAmount:募集总规模 **/
	private String totalAmount;
	/** 预约募集金额 */
	private String reservationTotalAmount;
	/** 已募集金额 */
	private String actualTotalAmount;
	
	/** increaseAmount:步长（递增金额） **/
	private String increaseAmount;
	
	/** assetPoolType:资产池类型 **/
	private String assetPoolType;
	
	/** maxInvestAmount:个人限投 **/
	private String maxInvestAmount;
	
	/** minInvestAmount:起购金额 **/
	private String minInvestAmount;
	
	/** assetPoolCode:资产池编号 **/
	private String assetPoolCode;
	
	/** assetPoolName:资产池名称 **/
	private String assetPoolName;
	
	/** minHoldAmount:最低可持有金额 **/
	private String minHoldAmount;
	
	/** fundSettleParty:资金结算方 **/
	private String fundSettleParty;
	
	/** maxYieldRate:预期年化收益率上限 **/
	private String maxYieldRate;
	
	/** minYieldRate:预期年化收益率下限 **/
	private String minYieldRate;
	
	// -----------产品库存--------------
	/** saleAmount:已募集金额 **/
	private String saleAmount;
	
	/** stockAmount:剩余可投金额 **/
	private String stockAmount;
	
	/** redeemAmount:已赎回金额 **/
	private String redeemAmount;
	
	// -----------产品业务信息--------------
	/** saleStartTime:募集起始日 **/
	private String saleStartTime;
	
	/** saleEndTime:募集截止日 **/
	private String saleEndTime;
	
	/** valueTime:起息日 **/
	private String valueTime;
	
	/** investPeriodLoopUnit:循环周期 **/
	private String investPeriodLoopUnit;
	
	/** expireTime:到期日 **/
	private String expireTime;
	
	/** expectClearTime:到期回款日（预期结清时间） **/
	private String expectClearTime;
	
	/** investPeriod:产品期限 **/
	private String investPeriod;
	
	/** loopIncreaseYieldRate:收益递增系数 **/
	private String loopIncreaseYieldRate;
	
	/** clearTime:结清日 **/
	private String clearTime;
	
	/** nextRedeemTime:下一开放赎回日 **/
	private String nextRedeemTime;
	
	/** nextRepayTime:下一回款日 **/
	private String nextRepayTime;
	
	/** currentYieldRate:当期利率 **/
	private String currentYieldRate;
	
	/** floatingYieldRate:浮动利率 **/
	private String floatingYieldRate;
	
	/** investPeriodLoopCount:总循环轮次 **/
	private String investPeriodLoopCount;
	
	/** investPeriodLoopIndex:当前循环轮次 **/
	private String investPeriodLoopIndex;
	
	/** productLadderList:阶梯收益列表 **/
	private String productLadderList;

	private String loanOrderNoSet;
	
	private String collectMode;
	
	private String expectEstablishTime;

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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDisplayName() {
		return productDisplayName;
	}

	public void setProductDisplayName(String productDisplayName) {
		this.productDisplayName = productDisplayName;
	}

	public String getProductLineId() {
		return productLineId;
	}

	public void setProductLineId(String productLineId) {
		this.productLineId = productLineId;
	}

	public String getProductLineCode() {
		return productLineCode;
	}

	public void setProductLineCode(String productLineCode) {
		this.productLineCode = productLineCode;
	}

	public String getProductLineName() {
		return productLineName;
	}

	public void setProductLineName(String productLineName) {
		this.productLineName = productLineName;
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

	public String getInformationDisclosure() {
		return informationDisclosure;
	}

	public void setInformationDisclosure(String informationDisclosure) {
		this.informationDisclosure = informationDisclosure;
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

	public String getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(String saleAmount) {
		this.saleAmount = saleAmount;
	}

	public String getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(String stockAmount) {
		this.stockAmount = stockAmount;
	}

	public String getRedeemAmount() {
		return redeemAmount;
	}

	public void setRedeemAmount(String redeemAmount) {
		this.redeemAmount = redeemAmount;
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

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
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

	public String getLoopIncreaseYieldRate() {
		return loopIncreaseYieldRate;
	}

	public void setLoopIncreaseYieldRate(String loopIncreaseYieldRate) {
		this.loopIncreaseYieldRate = loopIncreaseYieldRate;
	}

	public String getClearTime() {
		return clearTime;
	}

	public void setClearTime(String clearTime) {
		this.clearTime = clearTime;
	}

	public String getNextRedeemTime() {
		return nextRedeemTime;
	}

	public void setNextRedeemTime(String nextRedeemTime) {
		this.nextRedeemTime = nextRedeemTime;
	}

	public String getNextRepayTime() {
		return nextRepayTime;
	}

	public void setNextRepayTime(String nextRepayTime) {
		this.nextRepayTime = nextRepayTime;
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

	public String getInvestPeriodLoopCount() {
		return investPeriodLoopCount;
	}

	public void setInvestPeriodLoopCount(String investPeriodLoopCount) {
		this.investPeriodLoopCount = investPeriodLoopCount;
	}

	public String getInvestPeriodLoopIndex() {
		return investPeriodLoopIndex;
	}

	public void setInvestPeriodLoopIndex(String investPeriodLoopIndex) {
		this.investPeriodLoopIndex = investPeriodLoopIndex;
	}

	public String getProductLadderList() {
		return productLadderList;
	}

	public void setProductLadderList(String productLadderList) {
		this.productLadderList = productLadderList;
	}

	public String getReservationTotalAmount() {
		return reservationTotalAmount;
	}

	public void setReservationTotalAmount(String reservationTotalAmount) {
		this.reservationTotalAmount = reservationTotalAmount;
	}

	public String getActualTotalAmount() {
		return actualTotalAmount;
	}

	public void setActualTotalAmount(String actualTotalAmount) {
		this.actualTotalAmount = actualTotalAmount;
	}

	public String getExpectEstablishTime() {
		return expectEstablishTime;
	}

	public void setExpectEstablishTime(String expectEstablishTime) {
		this.expectEstablishTime = expectEstablishTime;
	}

    public String getRegisterType(String registerType) {
        return this.registerType;
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
