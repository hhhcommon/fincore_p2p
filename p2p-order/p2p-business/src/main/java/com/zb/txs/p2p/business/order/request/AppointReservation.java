/* 
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.business.order.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Function:   天鼋预约申请 <br/>
 * Date:   2017年09月29日 下午4:45 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Data
public class AppointReservation implements Serializable {

    /**
     * 外部预约单编号
     */
    private String extOrderNo;

    /**
     * 预约人memberId
     */
    private String memberId;

    /**
     * 预约时间(yyyy-MM-dd HH:mm:ss)
     */
    private Date orderTime;

    /**
     * 预约金额
     */
    private BigDecimal investAmount;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 交易渠道
     */
    private String saleChannel;

    /**
     * 用户名
     */
    private String name;

    /**
     * 身份证
     */
    private String certNo;

    /**
     * 联系方式
     */
    private String telNo;

}
