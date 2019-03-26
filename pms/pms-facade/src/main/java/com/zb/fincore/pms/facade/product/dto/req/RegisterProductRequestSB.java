package com.zb.fincore.pms.facade.product.dto.req;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.zb.fincore.pms.common.dto.BaseRequest;

/**
 * 产品注册请求对象
 *
 * @author
 * @create 2017-04-10 10:32
 */
public class RegisterProductRequestSB extends BaseRequest{


	/*********************产品注册基本信息字段 start *****************************/
    private Long productId;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    @NotBlank(message = "产品名称不能为空")
    private String productName;

    /**
     * 产品展示名称
     */
    @NotBlank(message = "产品展示名称不能为空")
    private String productDisplayName;
    
    /**
     * 产品线主键
     */
    @NotNull(message = "产品所属产品线不能为空")
    private Long productLineId;

    /**
     * 借款订单编号集
     */
    @NotBlank(message = "借款订单编号集不能为空")
    private String loanOrderNoSet;
    
    /**
     * 募集方式
     */
    @NotBlank(message = "募集方式不能为空")
    private String collectMode;
    
    /**
     * 产品形态代码
     */
    @NotBlank(message = "产品形态代码不能为空")
    private String patternCode;

    /**
     * 销售渠道代码
     */
    @NotBlank(message = "产品销售渠道不能为空")
    private String saleChannelCode;

    /**
     * 产品总规模
     */
    @NotNull(message = "产品规模不能为空")
    @DecimalMin(value = "0.00", message = "产品规模必须大于等于零")
    private BigDecimal totalAmount;

    /**
     * 产品风险等级
     */
    @NotBlank(message = "产品风险等级不能为空")
    private String riskLevel;

    /**
     * 支付渠道
     */
    @NotBlank(message = "支付渠道不能为空")
    private String payChannel;
    
    /**
     * 购买方式：账户余额、至尊宝、银行卡
     */
    @NotBlank(message = "购买方式不能为空")
    private String buyWays;
    
    /**
     * 产品期数
     */
//    @NotBlank(message = "产品期数不能为空")
    private String numberPeriod;

    /*********************产品注册基本信息字段 end *****************************/


    /*********************产品注册期限信息字段 start *****************************/
    /**
     * 募集起始时间
     */
    @NotNull(message = "产品募集起始时间不能为空")
    private Date saleStartTime;

    /**
     * 募集截止时间
     */
    @NotNull(message = "产品募集截止时间不能为空")
    private Date saleEndTime;

    /**
     * 预期成立时间
     */
    @NotNull(message = "预期成立时间不能为空")
    private Date expectEstablishTime;

    /**
     * 起息时间
     */
    @NotNull(message = "产品起息时间不能为空")
    private Date valueTime;
    
    /**
     * 止息时间
     */
    @NotNull(message = "产品止息时间不能为空")
    private Date ceaseTime;

    /**
     * 预期到期日期
     */
    @NotNull(message = "产品预期到期日期不能为空")
    private Date expectExpireTime;

    /**
     * 预期结清时间
     */
    @NotNull(message = "产品预期结清日期不能为空")
    private Date expectClearTime;

    /**
     * 投资期限
     */
    @NotNull(message = "投资期限不能为空")
    private Integer investPeriod;
    
    /*********************产品注册期限信息字段 end *****************************/


    /*********************产品注册投资信息字段 start *****************************/
    /**
     * 起投金额
     */
    @NotNull(message = "产品起投金额不能为空")
    @DecimalMin(value = "0.00", message = "产品起投金额必须大于等于零")
    private BigDecimal minInvestAmount;

    /**
     * 个人最大投资限额
     */
    @NotNull(message = "个人最大投资限额不能为空")
    @DecimalMin(value = "0.00", message = "个人最大投资限额必须大于等于零")
    private BigDecimal maxInvestAmount;

    /**
     * 递增金额(步长)
     */
    @NotNull(message = "产品步长不能为空")
    @DecimalMin(value = "0.00", message = "产品步长必须大于等于零")
    private BigDecimal increaseAmount;

    /**
     * 最低预期收益率
     */
    @NotNull(message = "最低预期收益率不能为空")
    @DecimalMin(value = "0.00", message = "最低预期收益率必须大于等于零")
    private BigDecimal minYieldRate;

    /**
     * 最高预期收益率
     */
    @NotNull(message = "最高预期收益率不能为空")
    @DecimalMin(value = "0.00", message = "最高预期收益率必须大于等于零")
    private BigDecimal maxYieldRate;
    
    /**
     * 当期利率
     */
    private BigDecimal currentYieldRate;

    /*********************产品注册投资信息字段 end *****************************/
    
    

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
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

	public String getPatternCode() {
		return patternCode;
	}

	public void setPatternCode(String patternCode) {
		this.patternCode = patternCode;
	}

	public String getSaleChannelCode() {
		return saleChannelCode;
	}

	public void setSaleChannelCode(String saleChannelCode) {
		this.saleChannelCode = saleChannelCode;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public Date getSaleStartTime() {
		return saleStartTime;
	}

	public void setSaleStartTime(Date saleStartTime) {
		this.saleStartTime = saleStartTime;
	}

	public Date getSaleEndTime() {
		return saleEndTime;
	}

	public void setSaleEndTime(Date saleEndTime) {
		this.saleEndTime = saleEndTime;
	}

	public Date getExpectEstablishTime() {
		return expectEstablishTime;
	}

	public void setExpectEstablishTime(Date expectEstablishTime) {
		this.expectEstablishTime = expectEstablishTime;
	}

	public Date getValueTime() {
		return valueTime;
	}

	public void setValueTime(Date valueTime) {
		this.valueTime = valueTime;
	}

	public Date getCeaseTime() {
		return ceaseTime;
	}

	public void setCeaseTime(Date ceaseTime) {
		this.ceaseTime = ceaseTime;
	}

	public Date getExpectExpireTime() {
		return expectExpireTime;
	}

	public void setExpectExpireTime(Date expectExpireTime) {
		this.expectExpireTime = expectExpireTime;
	}

	public Date getExpectClearTime() {
		return expectClearTime;
	}

	public void setExpectClearTime(Date expectClearTime) {
		this.expectClearTime = expectClearTime;
	}

	public Integer getInvestPeriod() {
		return investPeriod;
	}

	public void setInvestPeriod(Integer investPeriod) {
		this.investPeriod = investPeriod;
	}

	public BigDecimal getMinInvestAmount() {
		return minInvestAmount;
	}

	public void setMinInvestAmount(BigDecimal minInvestAmount) {
		this.minInvestAmount = minInvestAmount;
	}

	public BigDecimal getMaxInvestAmount() {
		return maxInvestAmount;
	}

	public void setMaxInvestAmount(BigDecimal maxInvestAmount) {
		this.maxInvestAmount = maxInvestAmount;
	}

	public BigDecimal getIncreaseAmount() {
		return increaseAmount;
	}

	public void setIncreaseAmount(BigDecimal increaseAmount) {
		this.increaseAmount = increaseAmount;
	}

	public BigDecimal getMinYieldRate() {
		return minYieldRate;
	}

	public void setMinYieldRate(BigDecimal minYieldRate) {
		this.minYieldRate = minYieldRate;
	}

	public BigDecimal getMaxYieldRate() {
		return maxYieldRate;
	}

	public void setMaxYieldRate(BigDecimal maxYieldRate) {
		this.maxYieldRate = maxYieldRate;
	}

	public BigDecimal getCurrentYieldRate() {
		return currentYieldRate;
	}

	public void setCurrentYieldRate(BigDecimal currentYieldRate) {
		this.currentYieldRate = currentYieldRate;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public Long getProductLineId() {
		return productLineId;
	}

	public void setProductLineId(Long productLineId) {
		this.productLineId = productLineId;
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

    
}
