package com.zb.p2p.customer.api.entity.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class BankCard{
	@ApiModelProperty(value="银行账号")
	private String bankCardNo;//银行账号
	@ApiModelProperty(value="开户银行")
	private String bankName;//开户银行
	@ApiModelProperty(value="银行卡预留手机号")
    private String bankMobile;
	@ApiModelProperty(value="开户行所属省份")
	private String province;//	开户行所属省     会员
	@ApiModelProperty(value="开户行所属城市")
	private String city;//	开户行所属城市       会员
	@ApiModelProperty(value="开户行所属支行")
	private String branch;//	开户行所属支行     会员
	@ApiModelProperty(value="银行编码")
	private String bankCode;//   银行编码        会员
	@ApiModelProperty(value="银行账户名称")
	private String bankCardName;//   银行账户名称        会员
	@ApiModelProperty(value="注册渠道TXS")
	private String channelCode;//注册渠道 TXS
	
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankMobile() {
		return bankMobile;
	}
	public void setBankMobile(String bankMobile) {
		this.bankMobile = bankMobile;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankCardName() {
		return bankCardName;
	}
	public void setBankCardName(String bankCardName) {
		this.bankCardName = bankCardName;
	}
	
	
}