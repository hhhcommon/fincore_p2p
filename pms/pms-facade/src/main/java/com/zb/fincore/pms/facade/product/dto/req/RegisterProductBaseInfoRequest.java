package com.zb.fincore.pms.facade.product.dto.req;

import com.zb.fincore.pms.common.dto.BaseRequest;
import com.zb.fincore.pms.common.enums.OpenTypeEnum;
import com.zb.fincore.pms.common.enums.RegisterTypeEnum;
import com.zb.fincore.pms.facade.product.model.ProductContractModel;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 产品注册请求对象
 *
 * @author
 * @create 2017-04-10 10:32
 */
public class RegisterProductBaseInfoRequest extends BaseRequest{


    /*********************产品注册基本信息字段 start *****************************/
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
//    @NotNull(message = "产品所属产品线不能为空")
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
//    @NotNull(message = "产品关联的资产池类型不能为空")
    private Integer assetPoolType;

    /**
     * 资产池主键
     */
//    @NotBlank(message = "产品关联的资产池编号不能为空")
    private String assetPoolCode;

    /**
     * 资产池名称
     */
//    @NotBlank(message = "产品关联的资产池名称不能为空")
    private String assetPoolName;
    
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
     * 接入渠道
     */
    @NotBlank(message = "产品接入渠道不能为空")
    private String joinChannelCode;

    /**
     * 产品总规模
     */
    @NotNull(message = "产品规模不能为空")
    @DecimalMin(value = "0.00", message = "产品规模必须大于等于零")
    private BigDecimal totalAmount;

    /**
     * 销售状态
     */
    private Integer saleStatus;

    /**
     * 募集状态
     */
    private Integer collectStatus;

    /**
     * 产品风险等级
     */
    @NotBlank(message = "产品风险等级不能为空")
    private String riskLevel;

    /**
     * 资金结算方
     */
    private String fundSettleParty;

    /**
     * 是否对港澳台客户开放
     */
    private Integer isOpenHMT;

    /**
     * 日历模式
     */
    private Integer calendarMode;

    /**
     * 产品介绍
     */
    @NotBlank(message = "产品介绍不能为空")
    private String introduction;

    /**
     * 审核需要签名
     */
    private String approvalRequireSign;

    /**
     * 已审核签名
     */
    private String approvalSign;

    /**
     * 审核状态
     */
    private Integer approvalStatus;

    /**
     * 归档时间
     */
    private Date archiveTime;
    
    /**
     * 注册类型（AUTO:自动,UNAUTO:非自动）
     */
    private String registerType = RegisterTypeEnum.UNAUTO.getCode() ;
    
    /**
     * 开放类型（IN:对内,OUT:对外）
     */
    private String openType = OpenTypeEnum.OUT.getCode();
    /**
     * 支付渠道
     */
    private String payChannel;
    
    /**
     * 购买方式：账户余额、至尊宝、银行卡
     */
    private String buyWays;
    
    /**
     * 产品期数
     */
    private String numberPeriod;
    
    /*********************产品注册基本信息字段 end *****************************/

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

    public String getAssetPoolCode() {
        return assetPoolCode;
    }

    public void setAssetPoolCode(String assetPoolCode) {
        this.assetPoolCode = assetPoolCode;
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

    public String getJoinChannelCode() {
        return joinChannelCode;
    }

    public void setJoinChannelCode(String joinChannelCode) {
        this.joinChannelCode = joinChannelCode;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(Integer saleStatus) {
        this.saleStatus = saleStatus;
    }

    public Integer getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(Integer collectStatus) {
        this.collectStatus = collectStatus;
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

    public Integer getIsOpenHMT() {
        return isOpenHMT;
    }

    public void setIsOpenHMT(Integer isOpenHMT) {
        this.isOpenHMT = isOpenHMT;
    }

    public Integer getCalendarMode() {
        return calendarMode;
    }

    public void setCalendarMode(Integer calendarMode) {
        this.calendarMode = calendarMode;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getApprovalRequireSign() {
        return approvalRequireSign;
    }

    public void setApprovalRequireSign(String approvalRequireSign) {
        this.approvalRequireSign = approvalRequireSign;
    }

    public String getApprovalSign() {
        return approvalSign;
    }

    public void setApprovalSign(String approvalSign) {
        this.approvalSign = approvalSign;
    }

    public Integer getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Integer approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Date getArchiveTime() {
        return archiveTime;
    }

    public void setArchiveTime(Date archiveTime) {
        this.archiveTime = archiveTime;
    }

    public Integer getAssetPoolType() {
        return assetPoolType;
    }

    public void setAssetPoolType(Integer assetPoolType) {
        this.assetPoolType = assetPoolType;
    }

    public String getAssetPoolName() {
        return assetPoolName;
    }

    public void setAssetPoolName(String assetPoolName) {
        this.assetPoolName = assetPoolName;
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

	public String getProductLineName() {
        return productLineName;
    }

    public void setProductLineName(String productLineName) {
        this.productLineName = productLineName;
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
}
