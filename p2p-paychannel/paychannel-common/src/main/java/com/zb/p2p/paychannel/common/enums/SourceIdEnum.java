package com.zb.p2p.paychannel.common.enums;

/**
 * Function:
 * Author: created by liguoliang
 * Date: 2017/9/4 0004 下午 3:55
 * Version: 1.0
 */
public enum SourceIdEnum {
    MSD("MSD", "马上贷"),
    ZD("ZD", "资鼎"),
    TXS("TXS", "唐小僧");

    private String code;
    private String desc;

    private SourceIdEnum(String code, String desc) {
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
