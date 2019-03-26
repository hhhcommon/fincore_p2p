package com.zb.p2p.trade.common.enums;

/**
 * Function:
 * Author: created by mengkai
 * Date: 2017/8/31 0031 下午 2:55
 * Version: 1.0
 */
public enum ContractEnum {
    INIT("INIT", "初始化"),
    FAIL("FAIL", "错误"),
    SIGN_SUCCESS("SIGN_SUCCESS", "盖章成功");

    private String code;
    private String desc;

    ContractEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
