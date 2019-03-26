/**
 * 
 */
package com.zb.p2p.customer.api.entity.orc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 证件扫描返回信息
 * @author guolitao
 *
 */
@ApiModel
public class ORCInfo {

	@ApiModelProperty(value="证件号码")
	private String idCardNo;//	证件号码	String
	@ApiModelProperty(value="证件类型")
	private String idCardType;// 证件类型	String	1：身份证  
	@ApiModelProperty(value="真实姓名")
	private String realName;//	真实姓名	String	
	@ApiModelProperty(value="住址")
	private String address;//	住址	String	 
	@ApiModelProperty(value="民族")
	private String nation;//	民族	String	
	@ApiModelProperty(value="生日")
	private String birth;//	生日 	String	 
	@ApiModelProperty(value="性别")
	private String gender;//	性别 	String	
	@ApiModelProperty(value="签发日期")
	private String issuingDate;//	签发日期	String	
	@ApiModelProperty(value="签发机关")
	private String issuingAuthority;//	签发机关	String	 
	@ApiModelProperty(value="失效日期")
	private String expiryDate;//	失效日期	String	
	
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getIdCardType() {
		return idCardType;
	}
	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getIssuingDate() {
		return issuingDate;
	}
	public void setIssuingDate(String issuingDate) {
		this.issuingDate = issuingDate;
	}
	public String getIssuingAuthority() {
		return issuingAuthority;
	}
	public void setIssuingAuthority(String issuingAuthority) {
		this.issuingAuthority = issuingAuthority;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	
}
