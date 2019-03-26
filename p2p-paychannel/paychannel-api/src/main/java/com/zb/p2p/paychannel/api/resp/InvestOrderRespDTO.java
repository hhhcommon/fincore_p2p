package com.zb.p2p.paychannel.api.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by limingxin on 2017/9/1.
 */
@Data
public class InvestOrderRespDTO implements Serializable {
    private Long id;

    private String memberId;
}
