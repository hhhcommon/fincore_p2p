package com.zb.fincore.pms.facade.product.dto.req;


import org.hibernate.validator.constraints.NotBlank;

import com.zb.fincore.pms.common.dto.PageQueryRequest;

/**
 * 查询产品列表请求参数
 *
 * @author
 * @create 2016-12-22 19:52
 */
public class QueryProductListRequestForP2P extends PageQueryRequest {

    /**
     * 在售/售罄区分
     */
	@NotBlank(message = "在售/售罄区分字段不能为空")
    private String saleFlag;
	
    /**
     * 按照预期年化收益率排序(ASC:预期年化收益率升序，DESC：预期年化收益率降序)
     */
    private String orderByYieldRate;

    /**
     * 按照产品期限排序(ASC:产品期限升序，DESC：产品期限降序)
     */
    private String orderByInvestPeriod;

    /**
     * 按照起投金额排序(ASC:起投金额升序，DESC：起投金额降序)
     */
    private String orderByMinInvestAmount;
    
    /**
     * 产品的类型代码(01:现金管理, 02:定期类, 03:净值型,04:阶梯收益,05:N日循环计划)
     */
    private String patternCode;

    public String getSaleFlag() {
        return saleFlag;
    }

    public void setSaleFlag(String saleFlag) {
        this.saleFlag = saleFlag;
    }

    public String getOrderByYieldRate() {
        return orderByYieldRate;
    }

    public void setOrderByYieldRate(String orderByYieldRate) {
        this.orderByYieldRate = orderByYieldRate;
    }

    public String getOrderByInvestPeriod() {
        return orderByInvestPeriod;
    }

    public void setOrderByInvestPeriod(String orderByInvestPeriod) {
        this.orderByInvestPeriod = orderByInvestPeriod;
    }

    public String getOrderByMinInvestAmount() {
        return orderByMinInvestAmount;
    }

    public void setOrderByMinInvestAmount(String orderByMinInvestAmount) {
        this.orderByMinInvestAmount = orderByMinInvestAmount;
    }

	public String getPatternCode() {
		return patternCode;
	}

	public void setPatternCode(String patternCode) {
		this.patternCode = patternCode;
	}
}
