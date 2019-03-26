package com.zb.txs.p2p.business.enums.order;

/**
 * Function:
 * Author: created by liguoliang
 * Date: 2017/9/4 0004 下午 3:55
 * Version: 1.0
 */
public enum MatchStatusEnum {
    INIT("INIT", "待匹配"),
    MATCHING("MATCHING", "匹配中"),
    MATCH_SUCCESS("MATCH_SUCCESS", "匹配成功"),
    MATCH_FAIL("MATCH_FAIL", "匹配失败"),
    INVEST_FAIL("INVEST_FAIL", "投资失败"),
    ORDER_FAIL("ORDER_FAIL", "支付成功但下单失败");

    private String code;
    private String desc;

    private MatchStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static boolean validateStatus(String code) {
        MatchStatusEnum[] arr$ = values();
        int len$ = arr$.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            MatchStatusEnum statusEnum = arr$[i$];
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
