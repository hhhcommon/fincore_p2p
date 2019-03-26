/* 
* Copyright (c) 2017, 资邦金服(上海)网络科技有限公司. All Rights Reserved.
*
*/

package com.zb.txs.p2p.business.order.response;

import lombok.Data;

import java.io.Serializable;

/**
 * Function:   天鼋预约申请回复 <br/>
 * Date:   2017年09月29日 下午4:45 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Data
public class AppointReservationResp implements Serializable {

    /**
     * 外部预约单编号
     */
    private String extReservationNo;

    /**
     * 天鼋预约单编号
     */
    private String reservationNo;

    /**
     * 预约人memberId
     */
    private String memberId;

    /**
     * 预约时间(yyyy-MM-dd HH:mm:ss)
     */
    private String reservationTime;

    /**
     * 预约状态
     */
    private String reservationStatus;
}
