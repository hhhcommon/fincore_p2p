package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p> 查询企业会员信息请求 </p>
 *
 * @author Vinson
 * @version OrgMemberInfoReq.java v1.0 2018/3/19 10:30 Zhengwenquan Exp $
 */
@Data
@ApiModel
public class OrgMemberInfoReq {

    @ApiModelProperty(value = "机构查询的系统来源标识")
    @NotNull(message = "系统来源标识不能为空")
    @Size(min = 1, message = "系统来源标识不能为空")
    private String sourceId;

}
