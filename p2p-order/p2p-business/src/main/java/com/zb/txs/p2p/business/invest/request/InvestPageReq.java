package com.zb.txs.p2p.business.invest.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liguoliang on 2017/12/7 0007.
 */
@Data
public class InvestPageReq implements Serializable {
    private String transType;
    private String memberId;

    private Integer pageSize = 10;

    private Integer pageNo;

    private Date startDate;
    private Date endDate;

    private String productCode;

}
