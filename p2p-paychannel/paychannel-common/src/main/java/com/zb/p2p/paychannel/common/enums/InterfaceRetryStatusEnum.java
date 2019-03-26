package com.zb.p2p.paychannel.common.enums;

/**
 * Created by zhangxin on 2017/4/7.
 */
public enum InterfaceRetryStatusEnum {
    INIT("INIT","初始化"),
    SUCCESS("SUCCESS","处理成功"),
    FAILURE("FAILURE","处理失败");

    private String code;
    private String desc;

    InterfaceRetryStatusEnum(String code, String desc) {
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
