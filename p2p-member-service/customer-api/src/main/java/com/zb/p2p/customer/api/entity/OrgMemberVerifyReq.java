package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p> 机构认证请求 </p>
 *
 * @author Vinson
 * @version OrgMemberVerifyReq.java v1.0 2018/3/9 18:30 Zhengwenquan Exp $
 */
@Data
@ApiModel
public class OrgMemberVerifyReq {

    @ApiModelProperty(value="机构ID")
    @NotNull(message = "机构ID不能为空")
    @Size(min = 1, message = "机构ID不能为空")
    private String orgId;

    @ApiModelProperty(value="渠道代码")
    @NotNull(message = "渠道代码不能为空")
    @Size(min = 1, message = "渠道代码不能为空")
    private String saleChannel;

    @ApiModelProperty(value="认证成功后跳转的地址")
    private String successUrl;

    /**
     * 认证类型-枚举值：
     * FULL：强认证 - 认证链接需打1分钱认证 法律效应更强
     * BASIC：基础认证
     */
    @ApiModelProperty(value="认证类型-默认传FULL")
    private String authentType;

}
