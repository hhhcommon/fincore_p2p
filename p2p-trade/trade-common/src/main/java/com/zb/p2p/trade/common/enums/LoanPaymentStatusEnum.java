package com.zb.p2p.trade.common.enums;

/**
 * 放款状态
 * @author tangqingqing
 *
 */
public enum LoanPaymentStatusEnum {

    LOAN_MATCH_SUCCESS("LOAN_MATCH_SUCCESS", "匹配成功"),
    LOAN_WAIT_PAY("LOAN_WAIT_PAY", "匹配成功待放款"),
    LOAN_PAYMENT_PROCESSING("LOAN_PAYMENT_PROCESSING", "放款处理中"),
    LOAN_PAYMENT_SUCCESS("LOAN_PAYMENT_SUCCESS", "放款成功"),
    LOAN_PAYMENT_FAILED("LOAN_PAYMENT_FAILED", "放款失败"),

    LOAN_EXCEPTION("LOAN_EXCEPTION", "支付转账异常，需要发起重试"),
    LOANING_CARD("LOANING_CARD", "提现到卡中"),
    LOAN_CARD_SUCCESS("CASHED_CARD_SUCCESS", "提现到卡完成");

    private String code;
    private String desc;

    LoanPaymentStatusEnum(String code, String desc) {
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

    public static LoanPaymentStatusEnum getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (LoanPaymentStatusEnum item : LoanPaymentStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }

        return null;
    }
}
