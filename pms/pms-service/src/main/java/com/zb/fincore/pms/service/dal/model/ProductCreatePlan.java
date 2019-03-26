/*
 * ProductCreatePlan.java
 * Copyright (c) 2017, 资邦金服（上海）网络科技有限公司. All Rights Reserved 
 * 2018-04-17 14:20:53
 */
package com.zb.fincore.pms.service.dal.model;

import java.math.BigDecimal;
import java.util.Date;

import com.zb.fincore.pms.common.model.BaseDo;

public class ProductCreatePlan {
    /**
     * @Fields id 自增主键
     */
    private Long id;
    /**
     * @Fields productCode 产品编码
     */
    private String productCode;
    /**
     * @Fields productLineCode 产品线编码
     */
    private String productLineCode;
    /**
     * @Fields countStockTime 预计计算库存时间
     */
    private String countStockTime;
    /**
     * @Fields realCountStockTime 实际计算库存时间
     */
    private String realCountStockTime;
    /**
     * @Fields openProductTime 预计开放产品时间
     */
    private String openProductTime;
    /**
     * @Fields realOpenProductTime 实际开放产品时间
     */
    private String realOpenProductTime;
    /**
     * @Fields planTime 计划日期，指是哪一天的(YYYY-MM-DD)
     */
    private String planTime;
    /**
     * @Fields countStockStatus 计算库存状态(INIT:初始化, SUCCESS:成功，FAIL:失败)
     */
    private String countStockStatus;
    /**
     * @Fields openProductStatus 开放产品状态(INIT:初始化, SUCCESS:成功，FAIL:失败)
     */
    private String openProductStatus;
    /**
     * @Fields totalAmount 库存金额
     */
    private BigDecimal totalAmount;
    /**
     * @Fields openType 计划开放类型(IN:对内, OUT:对外)
     */
    private String openType;
    /**
     * @Fields memo 备注
     */
    private String memo;
    /**
     * @Fields createTime 创建时间
     */
    private Date createTime;
    /**
     * @Fields createBy 创建者
     */
    private String createBy;
    /**
     * @Fields modifyTime 最后更新时间
     */
    private Date modifyTime;
    /**
     * @Fields modifyBy 最后更新者
     */
    private String modifyBy;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductLineCode() {
		return productLineCode;
	}
	public void setProductLineCode(String productLineCode) {
		this.productLineCode = productLineCode;
	}
	public String getCountStockTime() {
		return countStockTime;
	}
	public void setCountStockTime(String countStockTime) {
		this.countStockTime = countStockTime;
	}
	public String getRealCountStockTime() {
		return realCountStockTime;
	}
	public void setRealCountStockTime(String realCountStockTime) {
		this.realCountStockTime = realCountStockTime;
	}
	public String getOpenProductTime() {
		return openProductTime;
	}
	public void setOpenProductTime(String openProductTime) {
		this.openProductTime = openProductTime;
	}
	public String getRealOpenProductTime() {
		return realOpenProductTime;
	}
	public void setRealOpenProductTime(String realOpenProductTime) {
		this.realOpenProductTime = realOpenProductTime;
	}
	public String getPlanTime() {
		return planTime;
	}
	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}
	public String getCountStockStatus() {
		return countStockStatus;
	}
	public void setCountStockStatus(String countStockStatus) {
		this.countStockStatus = countStockStatus;
	}
	public String getOpenProductStatus() {
		return openProductStatus;
	}
	public void setOpenProductStatus(String openProductStatus) {
		this.openProductStatus = openProductStatus;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getOpenType() {
		return openType;
	}
	public void setOpenType(String openType) {
		this.openType = openType;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

}