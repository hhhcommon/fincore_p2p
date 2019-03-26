package com.zb.p2p.customer.api.entity.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by laoguoliang
 */
@ApiModel
@Data
public class CardBinReq {

    @ApiModelProperty(value="银行卡号")
    private String cardNo;

}
