package com.zb.txs.p2p.business.order.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Function:  TransactionlogResp  <br/>
 * Date:  2017/9/27 18:46 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */
@Builder
@Data
public class TransactionlogResp implements Serializable {

    private String id;
    /**
     * 交易金额
     */
    private String tranAmount;
    /**
     * 描述
     */
    private String detailText;

    private Date created;

    private String productName;

    /**
     * 交易类型
     */
    private Integer transactionLogType;

}
