package com.zb.p2p.customer.common.enums;

/**
 * Function:
 * Author: created by liguoliang
 * Date: 2017/9/4 0004 下午 3:55
 * Version: 1.0
 */
public enum SendSMSEnum {
    FINANCE_REGISTE("finance_register", "注册验证码"),
    FINANCE_LOGIN("finance_login", "登陆验证码"),
    FINANCE_BACK("finance_back", "找回密码验证码");

    private String code;
    private String desc;

    private SendSMSEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
