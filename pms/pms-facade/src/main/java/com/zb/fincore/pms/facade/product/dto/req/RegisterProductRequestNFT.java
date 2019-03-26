package com.zb.fincore.pms.facade.product.dto.req;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.zb.fincore.pms.common.dto.BaseRequest;
import com.zb.fincore.pms.common.enums.OpenTypeEnum;
import com.zb.fincore.pms.common.enums.RegisterTypeEnum;

/**
 * 产品注册请求对象
 *
 * @author
 * @create 2017-04-10 10:32
 */
public class RegisterProductRequestNFT extends BaseRequest{


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
     * 产品线编号
     */
    private String productLineCode;

    /**
     * 产品线名称
     */
    private String productLineName;

    /**
     * 资产池类型
     */
    @NotNull(message = "产品关联的资产池类型不能为空")
    private Integer assetPoolType;

    /**
     * 资产池编号
     */
    @NotBlank(message = "产品关联的资产池编号不能为空")
    private String assetPoolCode;

    /**
     * 资产池名称
     */
    @NotBlank(message = "产品关联的资产池名称不能为空")
    private String assetPoolName;
    
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
     * 资金结算方
     */
    @NotBlank(message = "资金结算方不能为空")
    private String fundSettleParty;

    /**
     * 日历模式
     */
    @NotNull(message = "日历模式不能为空")
    private Integer calendarMode;
    
    /**
     * 注册类型（AUTO:自动,UNAUTO:非自动）
     */
    private String registerType = RegisterTypeEnum.UNAUTO.getCode();
    
    /**
     * 开放类型（IN:对内,OUT:对外）
     */
    private String openType = OpenTypeEnum.OUT.getCode();
    
    /*********************产品注册基本信息字段 end *****************************/


    /*********************产品注册期限信息字段 start *****************************/
    /**
     * 锁定期
     */
    @NotNull(message = "锁定期不能为空")
    private Integer lockPeriod;
    
    /**
     * 锁定期单位(1:天 2：周 3：月 4:年)
     */
    private Integer lockPeriodUnit;
    
    /**
     * 募集起始时间
     */
    private Date saleStartTime;

    /**
     * 募集截止时间
     */
    private Date saleEndTime; 

    /*********************产品注册期限信息字段 end *****************************/


    /*********************产品注册投资信息字段 start *****************************/
    /**
     * 起投金额
     */
    @NotNull(message = "产品起投金额不能为空")
    @DecimalMin(value = "0.00", message = "产品起投金额必须大于等于零")
    private BigDecimal minInvestAmount;

    /**
     * 单笔投资限额
     */
    private BigDecimal singleMaxInvestAmount;

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

	public Long getProductLineId() {
		return productLineId;
	}

	public void setProductLineId(Long productLineId) {
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

	public Integer getAssetPoolType() {
		return assetPoolType;
	}

	public void setAssetPoolType(Integer assetPoolType) {
		this.assetPoolType = assetPoolType;
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

	public String getFundSettleParty() {
		return fundSettleParty;
	}

	public void setFundSettleParty(String fundSettleParty) {
		this.fundSettleParty = fundSettleParty;
	}

	public Integer getCalendarMode() {
		return calendarMode;
	}

	public void setCalendarMode(Integer calendarMode) {
		this.calendarMode = calendarMode;
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

	public Integer getLockPeriod() {
		return lockPeriod;
	}

	public void setLockPeriod(Integer lockPeriod) {
		this.lockPeriod = lockPeriod;
	}

	public Integer getLockPeriodUnit() {
		return lockPeriodUnit;
	}

	public void setLockPeriodUnit(Integer lockPeriodUnit) {
		this.lockPeriodUnit = lockPeriodUnit;
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

	public BigDecimal getMinInvestAmount() {
		return minInvestAmount;
	}

	public void setMinInvestAmount(BigDecimal minInvestAmount) {
		this.minInvestAmount = minInvestAmount;
	}

	public BigDecimal getSingleMaxInvestAmount() {
		return singleMaxInvestAmount;
	}

	public void setSingleMaxInvestAmount(BigDecimal singleMaxInvestAmount) {
		this.singleMaxInvestAmount = singleMaxInvestAmount;
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
    
}
