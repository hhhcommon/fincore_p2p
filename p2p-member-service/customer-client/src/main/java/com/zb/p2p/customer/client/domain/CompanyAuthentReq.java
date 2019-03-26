package com.zb.p2p.customer.client.domain;

import lombok.Data;

/**
 * <p> 机构认证接口 </p>
 *
 * @author Vinson
 * @version CompanyAuthentReq.java v1.0 2018/3/9 17:49 Zhengwenquan Exp $
 */
@Data
public class CompanyAuthentReq {

    private String name;

    /**
     * 工商注册号/社会统一代码
     */
    private String registerNo;
    /**
     * 认证完成后跳转URL（自定义）
     */
    private String successUrl;
    /**
     * 渠道:ZD-资鼎
     */
    private String saleChannel;

    /**
     * 认证类型-枚举值：
     * FULL：强认证 - 认证链接需打1分钱认证 法律效应更强
     * BASIC：基础认证
     */
    private String authentType;
}
