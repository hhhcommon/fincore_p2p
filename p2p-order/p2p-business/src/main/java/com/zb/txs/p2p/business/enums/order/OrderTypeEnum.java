package com.zb.txs.p2p.business.enums.order;

/**
 * Function:
 * Author: created by liguoliang
 * Date: 2018/1/9 0004 下午 21:55
 * Version: 1.0
 */
public enum OrderTypeEnum {
    MATCH("MATCH", "匹配回调"),
    CASH("CASH", "兑付回调");

    private String code;
    private String desc;

    private OrderTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static boolean validateStatus(String code) {
        OrderTypeEnum[] arr$ = values();
        int len$ = arr$.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            OrderTypeEnum statusEnum = arr$[i$];
            if (statusEnum.getCode().equals(code)) {
                return true;
            }
        }

        return false;
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
