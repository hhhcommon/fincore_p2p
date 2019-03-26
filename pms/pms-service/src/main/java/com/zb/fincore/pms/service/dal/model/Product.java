package com.zb.fincore.pms.service.dal.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.zb.fincore.pms.common.model.BaseDo;

/**
 * 功能: 产品信息数据持久对象
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/1 0001 10:23
 * 版本: V1.0
 */
public class Product extends BaseDo {

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品展示名称
     */
    private String productDisplayName;

    /**
     * 产品线主键
     */
    private Long productLineId;

    /**
     * 产品线主键
     */
    private String productLineCode;

    /**
     * 产品线名称
     */
    private String productLineName;

    /**
     * 资产池类型
     */
    private Integer assetPoolType;

    /**
     * 资产池编号
     */
    private String assetPoolCode;

    /**
     * 资产池名称
     */
    private String assetPoolName;
    
    /**
     * 借款订单编号集
     */
    private String loanOrderNoSet;
    
    /**
     * 募集方式
     */
    private String collectMode;

    /**
     * 产品形态代码
     */
    private String patternCode;

    /**
     * 销售渠道代码
     */
    private String saleChannelCode;

    /**
     * 接入渠道
     */
    private String joinChannelCode;

    /**
     * 产品总规模
     */
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
     * 募集状态
     */
    private Integer displayStatus;

    /**
     * 产品风险等级
     */
    private String riskLevel;

    /**
     * 资金结算方
     */
    private String fundSettleParty;

    /**
     * 是否对港澳台客户开放
     */
    private Integer isOpenHmt;

    /**
     * 日历模式
     */
    private Integer calendarMode;

    /**
     * 产品介绍
     */
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
     * 最终审核时间
     */
    private Date lastApprovalTime;

    /**
     * 归档时间
     */
    private Date archiveTime;

    /**
     * 同步状态(0:未同步,1:已同步)
     */
    private Integer syncStatus;

    /**
     * 注册类型（AUTO:自动,UNAUTO:非自动）
     */
    private String registerType;
    
    /**
     * 开放类型（IN:对内,OUT:对外）
     */
    private String openType;
    
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

    /**
     * 产品期限信息
     */
    private ProductPeriod productPeriod;

    /**
     * 产品投资限制及收益信息
     */
    private ProductProfit productProfit;

    /**
     * 产品库存信息
     */
    private ProductStock productStock;

    /**
     * 产品审核信息
     */
    private ProductApproval productApproval;

    /**
     * 产品合同信息列表
     */
    private List<ProductContract> productContractList;

    /**
     * 产品阶梯信息列表
     */
    private List<ProductLadder> productLadderList;


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
        return isOpenHmt;
    }

    public void setIsOpenHMT(Integer isOpenHmt) {
        this.isOpenHmt = isOpenHmt;
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

    public String getJoinChannelCode() {
        return joinChannelCode;
    }

    public void setJoinChannelCode(String joinChannelCode) {
        this.joinChannelCode = joinChannelCode;
    }

    public Integer getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(Integer displayStatus) {
        this.displayStatus = displayStatus;
    }

    public Integer getIsOpenHmt() {
        return isOpenHmt;
    }

    public void setIsOpenHmt(Integer isOpenHmt) {
        this.isOpenHmt = isOpenHmt;
    }

    public ProductPeriod getProductPeriod() {
        return productPeriod;
    }

    public void setProductPeriod(ProductPeriod productPeriod) {
        this.productPeriod = productPeriod;
    }

    public ProductProfit getProductProfit() {
        return productProfit;
    }

    public void setProductProfit(ProductProfit productProfit) {
        this.productProfit = productProfit;
    }

    public ProductStock getProductStock() {
        return productStock;
    }

    public void setProductStock(ProductStock productStock) {
        this.productStock = productStock;
    }

    public List<ProductContract> getProductContractList() {
        return productContractList;
    }

    public void setProductContractList(List<ProductContract> productContractList) {
        this.productContractList = productContractList;
    }

    public ProductApproval getProductApproval() {
        return productApproval;
    }

    public void setProductApproval(ProductApproval productApproval) {
        this.productApproval = productApproval;
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

	public Date getLastApprovalTime() {
        return lastApprovalTime;
    }

    public void setLastApprovalTime(Date lastApprovalTime) {
        this.lastApprovalTime = lastApprovalTime;
    }

    public String getProductLineName() {
        return productLineName;
    }

    public void setProductLineName(String productLineName) {
        this.productLineName = productLineName;
    }

    public Integer getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
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

	public List<ProductLadder> getProductLadderList() {
        return productLadderList;
    }

    public void setProductLadderList(List<ProductLadder> productLadderList) {
        this.productLadderList = productLadderList;
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
