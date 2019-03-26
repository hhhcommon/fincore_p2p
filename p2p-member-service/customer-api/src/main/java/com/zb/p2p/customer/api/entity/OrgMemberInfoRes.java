package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p> 查询企业会员信息结果 </p>
 *
 * @author Vinson
 * @version OrgMemberVerifyRes.java v1.0 2018/3/9 18:30 Zhengwenquan Exp $
 */
@Data
@ApiModel
public class OrgMemberInfoRes {

    @ApiModelProperty(value = "企业会员/机构ID")
    private Long memberId;

    @ApiModelProperty(value = "综合账户")
    private String generalAccountNo;

    @ApiModelProperty(value = "风险备付金")
    private String riskReserveAccountNo;

    @ApiModelProperty(value = "授权还款户")
    private String authRepayAccountNo;

    @ApiModelProperty(value = "渠道手续费")
    private String channelFeeAccountNo;

    @ApiModelProperty(value = "资金归集户")
    private String fundCollectAccountNo;

}
