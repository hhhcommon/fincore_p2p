/**
 *
 */
package com.zb.p2p.customer.dao.query.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PerCustCond {
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "证件号，固定填身份证号")
    private String idCardNo;
    @ApiModelProperty(value = "证件类型，固定填01")
    private String idCardType;
    @ApiModelProperty(value = "会员ID")
    private Long customerId;
    @ApiModelProperty(value = " 登录密码")
    private String loginPwd;
}
