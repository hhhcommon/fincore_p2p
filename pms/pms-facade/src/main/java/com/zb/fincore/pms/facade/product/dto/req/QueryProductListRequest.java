package com.zb.fincore.pms.facade.product.dto.req;


import com.zb.fincore.pms.common.dto.PageQueryRequest;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 查询产品列表请求参数
 *
 * @author
 * @create 2016-12-22 19:52
 */
public class QueryProductListRequest extends PageQueryRequest {

    /**
     * 产品编号
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品展示名
     */
    private String productDisplayName;

    /**
     * 产品线ID
     */
    private Long productLineId;

    /**
     * 产品线编号
     */
    private String productLineCode;

    /**
     * 资产ID
     */
    private String assetPoolCode;

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
     * 显示状态
     */
    private Integer displayStatus;

    /**
     * 风险等级
     */
    private String riskLevel;

    /**
     * 审核状态
     */
    private Integer approvalStatus;

    /**
     * 查询创建开始时间
     */
    private String beginCreateTime;

    /**
     * 查询创建结束时间
     */
    private String endCreateTime;

    /**
     * 审核开始时间
     */
    private String approvalStartTime;

    /**
     * 审核结束时间
     */
    private String approvalEndTime;

    /**
     * 同步状态
     */
    private Integer syncStatus;

    /**
     * 募集截止时间
     */
    private Date saleEndTime;
    
    /**
     * P2P交易日切用募集截止时间
     */
    private Date saleEndTimeForDailyCut;

    /**
     * 借款订单编号
     */
    private String loanOrderNo;

    /**
     * 募集方式
     */
    private String collectMode;

    /**
     * 根据上线时间排序
     * ASC：按照上线时间升序
     * DESC：按照上线时间降序
     */
    private String orderByOnlineTime;
    
    /**
     * 产品开放类型（IN:对内、OUT:对外）
     */
    private String openType;
    
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

    public Integer getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(Integer displayStatus) {
        this.displayStatus = displayStatus;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Integer getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Integer approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getBeginCreateTime() {
        return beginCreateTime;
    }

    public void setBeginCreateTime(String beginCreateTime) {
        this.beginCreateTime = beginCreateTime;
    }

    public String getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public String getProductLineCode() {
        return productLineCode;
    }

    public void setProductLineCode(String productLineCode) {
        this.productLineCode = productLineCode;
    }

    public String getApprovalStartTime() {
        return approvalStartTime;
    }

    public void setApprovalStartTime(String approvalStartTime) {
        this.approvalStartTime = approvalStartTime;
    }

    public String getApprovalEndTime() {
        return approvalEndTime;
    }

    public void setApprovalEndTime(String approvalEndTime) {
        this.approvalEndTime = approvalEndTime;
    }

    public Integer getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    public Date getSaleEndTime() {
        return saleEndTime;
    }

    public void setSaleEndTime(Date saleEndTime) {
        this.saleEndTime = saleEndTime;
    }

	public Date getSaleEndTimeForDailyCut() {
		return saleEndTimeForDailyCut;
	}

	public void setSaleEndTimeForDailyCut(Date saleEndTimeForDailyCut) {
		this.saleEndTimeForDailyCut = saleEndTimeForDailyCut;
	}

    public String getLoanOrderNo() {
        return loanOrderNo;
    }

    public void setLoanOrderNo(String loanOrderNo) {
        this.loanOrderNo = loanOrderNo;
    }

    public String getCollectMode() {
        return collectMode;
    }

    public void setCollectMode(String collectMode) {
        this.collectMode = collectMode;
    }

    public String getOrderByOnlineTime() {
        return orderByOnlineTime;
    }

    public void setOrderByOnlineTime(String orderByOnlineTime) {
        this.orderByOnlineTime = orderByOnlineTime;
    }

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}
}
