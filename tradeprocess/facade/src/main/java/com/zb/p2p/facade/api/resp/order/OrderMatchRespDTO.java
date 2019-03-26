package com.zb.p2p.facade.api.resp.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by limingxin on 2017/9/1.
 */
@Data
public class OrderMatchRespDTO implements Serializable {

    private Long count;

    private List<OrderMatchInfoDTO> orderMatchInfoDTOList;

}
