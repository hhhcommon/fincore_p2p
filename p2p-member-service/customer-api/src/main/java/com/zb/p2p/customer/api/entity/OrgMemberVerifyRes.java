package com.zb.p2p.customer.api.entity;/**
 * Created by zhengwenquan on 2018/3/7.
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p> 机构认证响应结果 </p>
 *
 * @author Vinson
 * @version OrgMemberVerifyRes.java v1.0 2018/3/9 18:30 Zhengwenquan Exp $
 */
@Data
@ApiModel
public class OrgMemberVerifyRes {

    @ApiModelProperty(value="认证链接（第三方公司）")
    private String authentUrl;

    public OrgMemberVerifyRes(String authentUrl) {
        this.authentUrl = authentUrl;
    }

}
