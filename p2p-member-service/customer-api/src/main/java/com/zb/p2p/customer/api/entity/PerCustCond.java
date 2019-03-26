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
@ApiModel(value = "个人会员查询条件", description="个人会员查询条件")
@Data
public class PerCustCond extends CustCond{
	@ApiModelProperty(value="手机号")
    private String mobile;
    @ApiModelProperty(value="证件号，固定填身份证号")
    private String idCardNo;
    @ApiModelProperty(value="证件类型，固定填01")
    private String idCardType;
    @ApiModelProperty(value="会员ID")
    private String customerId;
	@ApiModelProperty(value=" 登录密码")
	private String loginPwd;

}
