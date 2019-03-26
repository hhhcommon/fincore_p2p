/**
 * 
 */
package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实名信息
 * @author guolitao
 *
 */
@ApiModel
public class RealNameInfo {
	@ApiModelProperty(value="是否实名，0否1是")
	private Integer isRealName;
	@ApiModelProperty(value="是否绑卡，0否1是")
	private Integer isBindCard;
	@ApiModelProperty(value="是否已评测，0否1是")
	private Integer isRiskRate;
	@ApiModelProperty(value="能否投资新手标，0不可购1可购2锁定")
	private Integer canFresh;
	@ApiModelProperty(value="是否投资新手标，0否1是")
	private Integer buyFreshProductStatus;
	@ApiModelProperty(value="是否投资定期0否1是")
	private Integer buyFixedProductStatus;
	@ApiModelProperty(value="是否激活活期账户0否1是")
	private Integer isActiveEAccount;
	@ApiModelProperty(value="是否开通银行存管,0-否1-是2-处理中3-失败")
	private Integer isDepositManage;
	
	public RealNameInfo() {
		super();
	}
	public RealNameInfo(Integer isRealName, Integer isBindCard, Integer isRiskRate) {
		super();
		this.isRealName = isRealName;
		this.isBindCard = isBindCard;
		this.isRiskRate = isRiskRate;
	}
	public Integer getIsRealName() {
		return isRealName;
	}
	public void setIsRealName(Integer isRealName) {
		this.isRealName = isRealName;
	}
	public Integer getIsBindCard() {
		return isBindCard;
	}
	public void setIsBindCard(Integer isBindCard) {
		this.isBindCard = isBindCard;
	}
	public Integer getIsRiskRate() {
		return isRiskRate;
	}
	public void setIsRiskRate(Integer isRiskRate) {
		this.isRiskRate = isRiskRate;
	}
	public Integer getCanFresh() {
		return canFresh;
	}
	public void setCanFresh(Integer canFresh) {
		this.canFresh = canFresh;
	}
	public Integer getBuyFreshProductStatus() {
		return buyFreshProductStatus;
	}
	public void setBuyFreshProductStatus(Integer buyFreshProductStatus) {
		this.buyFreshProductStatus = buyFreshProductStatus;
	}
	public Integer getBuyFixedProductStatus() {
		return buyFixedProductStatus;
	}
	public void setBuyFixedProductStatus(Integer buyFixedProductStatus) {
		this.buyFixedProductStatus = buyFixedProductStatus;
	}
	public Integer getIsActiveEAccount() {
		return isActiveEAccount;
	}
	public void setIsActiveEAccount(Integer isActiveEAccount) {
		this.isActiveEAccount = isActiveEAccount;
	}
	public Integer getIsDepositManage() {
		return isDepositManage;
	}
	public void setIsDepositManage(Integer isDepositManage) {
		this.isDepositManage = isDepositManage;
	}
	
	
	
}
