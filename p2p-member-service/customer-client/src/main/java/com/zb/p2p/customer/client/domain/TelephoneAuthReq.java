package com.zb.p2p.customer.client.domain;

import lombok.Data;

/**
 * <p> 个人实名认证外部请求 </p>
 *
 * @author Vinson
 * @version MemberVerifyReq.java v1.0 2018/3/9 18:30 Zhengwenquan Exp $
 */
@Data
public class TelephoneAuthReq {

    // 手机号码
    private String phoneNo;

    // 真实姓名
    private String name;

    // 身份证号
    private String certNo;

}
