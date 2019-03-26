package com.zb.p2p.customer.api.entity;/**
 * Created by zhengwenquan on 2018/3/7.
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * <p> 机构注册响应结果 </p>
 *
 * @author Vinson
 * @version OrgMemberVerifyRes.java v1.0 2018/3/9 18:30 Zhengwenquan Exp $
 */
@Data
@ApiModel
public class RegisterOrgMemberRes {

    @ApiModelProperty(value="机构ID")
    private Long orgId;


    @ApiModelProperty(value="已注册标记(0-新注册，1-已注册)")
    private Integer regFlag;

    public RegisterOrgMemberRes(Long orgId, Integer regFlag) {
        this.orgId = orgId;
        this.regFlag = regFlag;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
