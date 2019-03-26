package com.zb.txs.p2p.business.order.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Function:  通知天鼋产品售罄  <br/>
 * Date:  2017/9/27 17:55 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */
@Data
public class NoticePmsReq implements Serializable {

    /**
     * 产品编号
     */
    private String productCode;

}