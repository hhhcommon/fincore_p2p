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
 * 产品注册请求对象
 *
 * @author
 * @create 2017-04-10 10:32
 */
public class RegisterProductPeriodInfoRequest extends BaseRequest{


    /**
     * 产品编码
     */
    private String productCode;

    /*********************产品注册期限信息字段 start *****************************/
    /**
     * 预期上线时间
     */
    private Date expectOnlineTime;

    /**
     * 预期下线时间
     */
    private Date expectOfflineTime;

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
    private Integer investPeriod;

    /**
     * 资产期限单位(1:天 2：周 3：月 4:年)
     */
    private Integer investPeriodUnit;

    /**
     * 投资期限描述,如3个月期
     */
    private String investPeriodDescription;

    /**
     * 投资循环周期
     */
    @NotNull(message = "投资循环周期不能为空")
    private Integer investPeriodLoopUnit;
    
    /**
     * 锁定期
     */
    private Integer lockPeriod;
    
    /**
     * 锁定期单位(1:天 2：周 3：月 4:年)
     */
    private Integer lockPeriodUnit;

    /*********************产品注册期限信息字段 end *****************************/

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Date getExpectOnlineTime() {
        return expectOnlineTime;
    }

    public void setExpectOnlineTime(Date expectOnlineTime) {
        this.expectOnlineTime = expectOnlineTime;
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

    public Date getExpectOfflineTime() {
        return expectOfflineTime;
    }

    public void setExpectOfflineTime(Date expectOfflineTime) {
        this.expectOfflineTime = expectOfflineTime;
    }

    public Integer getInvestPeriod() {
        return investPeriod;
    }

    public void setInvestPeriod(Integer investPeriod) {
        this.investPeriod = investPeriod;
    }

    public Integer getInvestPeriodUnit() {
        return investPeriodUnit;
    }

    public void setInvestPeriodUnit(Integer investPeriodUnit) {
        this.investPeriodUnit = investPeriodUnit;
    }

    public String getInvestPeriodDescription() {
        return investPeriodDescription;
    }

    public void setInvestPeriodDescription(String investPeriodDescription) {
        this.investPeriodDescription = investPeriodDescription;
    }

    public Integer getInvestPeriodLoopUnit() {
        return investPeriodLoopUnit;
    }

    public void setInvestPeriodLoopUnit(Integer investPeriodLoopUnit) {
        this.investPeriodLoopUnit = investPeriodLoopUnit;
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

	public Date getCeaseTime() {
		return ceaseTime;
	}

	public void setCeaseTime(Date ceaseTime) {
		this.ceaseTime = ceaseTime;
	}

}
