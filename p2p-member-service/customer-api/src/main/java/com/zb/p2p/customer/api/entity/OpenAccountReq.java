package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p> 个人或机构开户请求 </p>
 *
 * @author Vinson
 * @version OpenAccountReq.java v1.0 2018/3/7 18:30 Zhengwenquan Exp $
 */
@ApiModel
public class OpenAccountReq {

    @ApiModelProperty(value="会员ID")
    @NotNull(message = "会员Id不能为空")
    @Size(min = 1, message = "会员Id不能为空")
    private String memberId;

    @ApiModelProperty(value="系统来源（MSD-马上贷，TXS-唐小僧）")
    @NotNull(message = "系统来源不能为空")
    @Size(min = 1, message = "系统来源不能为空")
    private String sourceId;

    @ApiModelProperty(value="会员类型(10-个人,20-机构)")
    @NotNull(message = "会员类型不能为空")
    @Size(min = 1, message = "会员类型不能为空")
    private String memberType;

    @ApiModelProperty(value="账户类型")
    @NotNull(message = "会员类型不能为空")
    @Size(min = 1, message = "账户类型不能为空")
    private String accountType;

    @ApiModelProperty(value="账户用途")
    @NotNull(message = "账户类型不能为空")
    @Size(min = 1, message = "账户用途不能为空")
    private String accountPurpose;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountPurpose() {
        return accountPurpose;
    }

    public void setAccountPurpose(String accountPurpose) {
        this.accountPurpose = accountPurpose;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
