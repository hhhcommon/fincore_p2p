package com.zb.txs.p2p.business.order.response;

import lombok.Data;

import java.io.Serializable;

/**
 * Function:  查询每日收益(单个查询)  <br/>
 * Date:  2017/9/27 17:56 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */
@Data
public class CardsResponse implements Serializable {
    private String idCardNo;
    private String idCardName;
    private String cardNo;
    private String cardType;
    private String cardTypeDesc;
    private String bankCode;
    private String bankName;
    private String signId;
    private String accountType;
    private String phoneNo;
    private String cardId;
    private String[] bankDesc;
    private String payMax;
    private String payDayMax;
    private String payMonthMax;
}
