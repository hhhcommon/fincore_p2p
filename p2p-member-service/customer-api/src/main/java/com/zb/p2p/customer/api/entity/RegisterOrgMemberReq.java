package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p> 机构注册请求 </p>
 *
 * @author Vinson
 * @version RegisterOrgMemberReq.java v1.0 2018/3/9 18:30 Zhengwenquan Exp $
 */
@Data
@ApiModel
public class RegisterOrgMemberReq {

    @ApiModelProperty(value="机构名称")
    @NotNull(message = "机构名称不能为空")
    @Size(min = 1, message = "机构名称不能为空")
    private String orgName;

    @ApiModelProperty(value="机构证件类型")
    @NotNull(message = "机构证件类型不能为空")
    @Size(min = 1, message = "机构证件类型不能为空")
    private String orgCardType;

    @ApiModelProperty(value="机构证件代码")
    @NotNull(message = "机构证件代码不能为空")
    @Size(min = 1, message = "机构证件代码不能为空")
    private String orgCardNo;

    @ApiModelProperty(value="法人姓名")
    @NotNull(message = "法人姓名不能为空")
    @Size(min = 1, message = "法人姓名不能为空")
    private String ownerName;

    @ApiModelProperty(value="法人证件类型（01-身份证）")
    @NotNull(message = "法人证件类型不能为空")
    @Size(min = 1, message = "法人证件类型不能为空")
    private String ownerIdCardType;

    @ApiModelProperty(value="法人证件号码")
    @NotNull(message = "法人证件号码不能为空")
    @Size(min = 1, message = "法人证件号码不能为空")
    private String ownerIdCardNo;

    @ApiModelProperty(value="联系电话")
    @NotNull(message = "联系电话不能为空")
    @Size(min = 7, message = "联系电话不能小于7位")
    private String telephone;

    @ApiModelProperty(value="渠道代码")
    @NotNull(message = "渠道代码不能为空")
    @Size(min = 1, message = "渠道代码不能为空")
    private String saleChannel;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
