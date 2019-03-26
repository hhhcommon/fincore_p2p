/**
 * 
 */
package com.zb.p2p.customer.api.entity;

import java.util.List;

import com.zb.p2p.customer.api.entity.card.BankCard;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 企业会员详情
 * @author guolitao
 *
 */
@ApiModel
public class OrgCustomerDetail {

	//会员信息
	@ApiModelProperty(value="会员ID")
	private String orgId;//会员ID	
	//机构相关信息
	@ApiModelProperty(value="机构名称")
	private String orgName;//机构名称
	@ApiModelProperty(value="证件类型")
	private String idCardType;//证件类型
	@ApiModelProperty(value="证件号码")
	private String idCardNo;//证件号码
	@ApiModelProperty(value="法人姓名")
	private String ownerName;//法人姓名
	@ApiModelProperty(value="法人证件类型 ")
	private String ownerIdCardType;//法人证件类型 
	@ApiModelProperty(value="法人证件号码")
	private String ownerIdCardNo;//法人证件号码
	@ApiModelProperty(value="联系电话")
	private String telephone;//联系电话
	
	//银行卡信息
	@ApiModelProperty(value="银行卡列表")
	private List<BankCard> bankCardList;
	
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}


	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getIdCardType() {
		return idCardType;
	}
	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerIdCardType() {
		return ownerIdCardType;
	}
	public void setOwnerIdCardType(String ownerIdCardType) {
		this.ownerIdCardType = ownerIdCardType;
	}
	public String getOwnerIdCardNo() {
		return ownerIdCardNo;
	}
	public void setOwnerIdCardNo(String ownerIdCardNo) {
		this.ownerIdCardNo = ownerIdCardNo;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public List<BankCard> getBankCardList() {
		return bankCardList;
	}
	public void setBankCardList(List<BankCard> bankCardList) {
		this.bankCardList = bankCardList;
	}
	
	
}
