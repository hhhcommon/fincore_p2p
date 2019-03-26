package com.zb.p2p.trade.common.enums;

import lombok.Getter;

/**
 * <p> 兑付流程状态 </p>
 *
 * @author Vinson
 * @version CashStatusEnum.java v1.0 2018/4/20 19:30 Zhengwenquan Exp $
 */
public enum CashStatusEnum {
    INIT("INIT","0", "初始化", true),
    CHECK_SUCCESS("CHECK_SUCCESS", "校验成功"),
    CHECK_FAIL("CHECK_FAIL", "校验失败"),

    CASH_WAIT_ACTUAL("CASH_WAIT_ACTUAL","0", "待计算实收", false),

    CASH_WAIT_PERFORM("CASH_WAIT_PERFORM","0", "待发放",false),

    CASHING("CASHING", "6", "转账中"),

    CASH_TRANSFERRED("CASH_TRANSFERRED", "已转让"),

    CASH_RETRY("CASH_RETRY", "提现重试"),
    CASH_SUCCESS("CASH_SUCCESS", "7", "转账成功", false),

    CASH_FAIL("CASH_FAIL", "9", "转账失败，支付明确返回失败的不再发起重试"),
    CASH_EXCEPTION("CASH_EXCEPTION","8", "支付转账异常，需要发起重试"),
    CASHING_CARD("CASHING_CARD", "提现到卡中"),
    CASHED_CARD_SUCCESS("CASHED_CARD_SUCCESS", "提现到卡完成"),
    CASHED_CARD_FAIL("CASHED_CARD_FAIL", "提现到卡失败，支付明确返回失败的不再发起重试"),
    CASHED_CARD_NOTIFY("CASHED_CARD_NOTIFY", "提现到卡通知完成");

    private String code;
    @Getter
    private String value;
    private String desc;
    private boolean isAutoNext;

    CashStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    CashStatusEnum(String code, String value, String desc) {
        this.code = code;
        this.value = value;
        this.desc = desc;
    }

    CashStatusEnum(String code, String value, String desc, boolean isAutoNext) {
        this.code = code;
        this.value = value;
        this.desc = desc;
        this.isAutoNext = isAutoNext;
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

    public boolean isAutoNext(){
        return this.isAutoNext;
    }

    public static String getStatusDesc(String code) {
        for (CashStatusEnum item : values()) {
            if (item.getCode().equals(code)) {
                return item.getDesc();
            }
        }
        return null;
    }

    public static CashStatusEnum getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (CashStatusEnum item : CashStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }

        return null;
    }
}
