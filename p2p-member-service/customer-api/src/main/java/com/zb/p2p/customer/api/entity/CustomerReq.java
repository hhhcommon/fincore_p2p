/**
 *
 */
package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员请求：封装请求通用字段
 */
@ApiModel
@Data
public class CustomerReq {

    @ApiModelProperty(value = "客户端标识")
    private String clientId;
    @ApiModelProperty(value = "子会员ID")
    private String customerId;
    @ApiModelProperty(value = "会员ID")
    private String memberId;
    @ApiModelProperty(value = "手机")
    private String mobile;
    @ApiModelProperty(value = "登录token")
    private String loginToken;
    @ApiModelProperty(value = "机构会员ID")
    private String orgId;
    @ApiModelProperty(value = "验证码")
    private String codeKaptcha;
    @ApiModelProperty(value = "类型")
    private String type;
    @ApiModelProperty(value = " 登录密码")
    private String loginPwd;
    @ApiModelProperty(value = " 设备序列号")
    private String serialNo;
    @ApiModelProperty(value = " 设备名称")
    private String name;
    @ApiModelProperty(value = " 设备型号")
    private String model;
    @ApiModelProperty(value = "原密码")
    private String originPwd;
    @ApiModelProperty(value = " 新密码")
    private String newPwd;
    @ApiModelProperty(value = " 确认密码")
    private String confirmPwd;
    @ApiModelProperty(value = " 会员类型")
    private String memberType;
}
