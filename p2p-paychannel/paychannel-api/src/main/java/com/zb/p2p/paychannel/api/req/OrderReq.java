package com.zb.p2p.paychannel.api.req;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by limingxin on 2017/8/31.
 */
@Data
public class OrderReq implements Serializable{

    /*会员id*/
    private String memberId;

}
