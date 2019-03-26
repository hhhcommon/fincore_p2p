package com.zb.txs.p2p.business.enums.order;

/**
 * Function:
 * Author: created by liguoliang
 * Date: 2018/1/9 0004 下午 21:55
 * Version: 1.0
 */
public enum PayStatusEnum {
    PAYING("PAYING", "支付中"),
    PAY_SUCCESS("PAY_SUCCESS", "支付成功"),
    PAY_FAIL("PAY_FAIL", "支付失败");

    private String code;
    private String desc;

    private PayStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static boolean validateStatus(String code) {
        PayStatusEnum[] arr$ = values();
        int len$ = arr$.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            PayStatusEnum statusEnum = arr$[i$];
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
