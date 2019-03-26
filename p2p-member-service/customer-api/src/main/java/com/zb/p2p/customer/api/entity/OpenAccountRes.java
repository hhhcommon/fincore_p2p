package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p> 个人或机构开户响应 </p>
 *
 * @author Vinson
 * @version OpenAccountReq.java v1.0 2018/3/7 18:30 Zhengwenquan Exp $
 */
@Data
@ApiModel
public class OpenAccountRes {

    @ApiModelProperty(value="账户号码")
    private String accountNo;

    public OpenAccountRes(String accountNo) {
        this.accountNo = accountNo;
    }

}
