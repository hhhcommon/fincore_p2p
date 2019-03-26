package com.zb.fincore.pms.facade.product.dto.req;

import com.zb.fincore.pms.common.dto.BaseRequest;
import com.zb.fincore.pms.facade.product.model.ProductContractModel;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 产品注册 投资信息字段 请求对象
 *
 * @author
 * @create 2017-04-10 10:32
 */
public class RegisterProductProfitInfoRequest extends BaseRequest{


    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品总规模
     */
    private BigDecimal totalAmount;

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
     * 最低可持有金额
     */
    @NotNull(message = "最低可持有金额不能为空")
    @DecimalMin(value = "0.00", message = "最低可持有金额必须大于等于零")
    private BigDecimal minHoldAmount;

    /**
     * 递增金额(步长)
     */
    @NotNull(message = "产品步长不能为空")
    @DecimalMin(value = "0.00", message = "产品步长必须大于等于零")
    private BigDecimal increaseAmount;

    /**
     * 年基础计息周期(360, 365, 366三种枚举值)
     */
    private Integer basicInterestsPeriod;

    /**
     * 产品成立条件
     */
    private String establishCondition;

    /**
     * 计量单位(1:份额, 2:人民币元)
     */
    private Integer unit;

    /**
     * 收益方式
     */
    private Integer profitType;

    /**
     * 计息方式
     */
    private Integer calculateInvestType;

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

    /**
     * 加息利率
     */
    private BigDecimal addYieldRate;

    /**
     * 浮动利率
     */
    @NotNull(message = "浮动利率不能为空")
    @DecimalMin(value = "0.00", message = "浮动利率必须大于等于零")
    private BigDecimal floatingYieldRate;
    /*********************产品注册投资信息字段 end *****************************/


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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

    public Integer getBasicInterestsPeriod() {
        return basicInterestsPeriod;
    }

    public void setBasicInterestsPeriod(Integer basicInterestsPeriod) {
        this.basicInterestsPeriod = basicInterestsPeriod;
    }

    public String getEstablishCondition() {
        return establishCondition;
    }

    public void setEstablishCondition(String establishCondition) {
        this.establishCondition = establishCondition;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Integer getProfitType() {
        return profitType;
    }

    public void setProfitType(Integer profitType) {
        this.profitType = profitType;
    }

    public Integer getCalculateInvestType() {
        return calculateInvestType;
    }

    public void setCalculateInvestType(Integer calculateInvestType) {
        this.calculateInvestType = calculateInvestType;
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

    public BigDecimal getAddYieldRate() {
        return addYieldRate;
    }

    public void setAddYieldRate(BigDecimal addYieldRate) {
        this.addYieldRate = addYieldRate;
    }

    public BigDecimal getMinHoldAmount() {
        return minHoldAmount;
    }

    public void setMinHoldAmount(BigDecimal minHoldAmount) {
        this.minHoldAmount = minHoldAmount;
    }

    public BigDecimal getFloatingYieldRate() {
        return floatingYieldRate;
    }

    public void setFloatingYieldRate(BigDecimal floatingYieldRate) {
        this.floatingYieldRate = floatingYieldRate;
    }
}
