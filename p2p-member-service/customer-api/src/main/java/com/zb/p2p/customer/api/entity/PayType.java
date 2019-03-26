/**
 * 
 */
package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author guolitao
 *
 */
@ApiModel
public class PayType {

	@ApiModelProperty(value="支付方式  1 银行卡支付 2余额支付 3活期支付")
	private String type;
	@ApiModelProperty(value="支付方式描述")
	private String desc;
	
	public PayType(PayTypeEnum typeEnum){
		this.type = typeEnum.code;
		this.desc = typeEnum.desc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static enum PayTypeEnum{
		CARD("1","银行卡支付"),ACCOUNT("2","余额支付"),E_ACCOUNT("3","活期支付");
		private String code;
		private String desc;
		PayTypeEnum(String code,String desc){
			this.code = code;
			this.desc = desc;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
	}
}
