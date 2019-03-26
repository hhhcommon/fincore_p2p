package com.zb.p2p.trade.common.enums;

/**
 * Function:调用其他系统sourceId定义
 * Author: created by liguoliang
 * Date: 2017/10/11 0011 下午 2:05
 * Version: 1.0
 */
public enum SourceIdEnum {
    ZD("ZD", "资鼎"),
    TXS("TXS", "唐小僧"),
    TXSTZ("TXSTZ", "唐小僧投资"),
    MSDJK("MSDJK", "马上贷借款");
    private String code;
    private String desc;

    SourceIdEnum(String code, String desc) {
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
