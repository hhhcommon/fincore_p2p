package com.zb.p2p.customer.client.domain;

import lombok.Data;

import java.util.Date;

/**
 * <p> 个人实名认证外部请求 </p>
 *
 * @author Vinson
 * @version MemberVerifyReq.java v1.0 2018/3/9 18:30 Zhengwenquan Exp $
 */
@Data
public class RealNameAuthReq {

    // 真实姓名
    private String realName;

    // 身份证号
    private String idCardNo;

    // 请求订单号
    private String orderNo;

    // 请求时间
    private String orderTime;

    // 系统标识
    private String sourceId;

}
