package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p> 个人实名认证请求 </p>
 *
 * @author Vinson
 * @version MemberVerifyReq.java v1.0 2018/3/8 18:30 Zhengwenquan Exp $
 */
@Data
@ApiModel
public class MemberVerifyReq {

    @ApiModelProperty(value="会员ID")
    private String customerId;

    @ApiModelProperty(value="认证类型(默认为两要素认证)")
    private String verifyType;

    @ApiModelProperty(value="真实姓名")
    @NotNull(message = "真实姓名不能为空")
    @Size(min = 1, message = "真实姓名不能为空")
    private String memberName;

    @ApiModelProperty(value="身份证号")
    @NotNull(message = "身份证号不能为空")
    @Size(min = 18, max = 18, message = "身份证号长度必须为18位数")
    private String certificateNo;

}
