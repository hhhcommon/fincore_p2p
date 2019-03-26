/**
 * 
 */
package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author guolitao
 *
 */
@ApiModel
@Data
public class CustomerDetail extends RealNameInfo {

	@ApiModelProperty(value="会员ID")
	private String customerId;
	@ApiModelProperty(value="名称")
	private String name;
	@ApiModelProperty(value="证件号")
	private String idCardNo;
	@ApiModelProperty(value="证件类型,01-身份证")
	private String idCardType;
	@ApiModelProperty(value="银行卡号")
	private CustomerCardBin bankCard;
	@ApiModelProperty(value="手机号")
	private String mobile;
	@ApiModelProperty(value="是否开资金账户")
	private String isOpenAccount;
	@ApiModelProperty(value="注册时间，yyyy-MM-dd HH:mm:ss")
	private String registerTime;
	@ApiModelProperty(value="渠道客户ID")
	private String channelCustomerId;
	@ApiModelProperty(value="渠道类型，如TXS-唐小僧")
	private String channelCode;
	@ApiModelProperty(value="txs 银行卡信息")
	private  TxsCusInfo txsCusInfo;

	@ApiModelProperty(value=" 登录密码")
	private String loginPwd;

}
