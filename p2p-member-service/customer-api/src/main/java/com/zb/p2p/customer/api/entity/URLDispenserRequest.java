package com.zb.p2p.customer.api.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class URLDispenserRequest implements Serializable {
    private static final long serialVersionUID = 8898356480594477120L;

    @ApiModelProperty(value = "客户编号")
    private String clientNo;//客户编号
    @ApiModelProperty(value = "客户端流水号")
    private String clientSeqNo;//客户端流水号
    @ApiModelProperty(value = "唐小僧会员号")
    private String txsAccountId;//唐小僧会员号
    @ApiModelProperty(value = "源页面 1-理财首页2-我的页面3-我的银行卡 4-首页 5-预约产品页 6-我的礼券-适用产品")
    private String sourcePage;//源页面
    @ApiModelProperty(value = "手机")
    private String mobile;
    @ApiModelProperty(value = " 参数 6的时候 为 {“qjsCouponId”:7778889000000033}")
    private String sourceParam;

}
