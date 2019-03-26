package com.zb.p2p.trade.common.enums;

public enum RepaymentFeeStatusEnum {

    INIT("INIT", "初始化"),
    WAIT_REPAY("WAIT_REPAY", "待还"),
    PROCESSING("PROCESSING", "处理中"),
    NORMAL_REPAID("NORMAL_REPAID", "正常已还"),
    OVERDUE_REPAID("OVERDUE_REPAID", "逾期已还"),
    REPAY_FAIL("REPAY_FAIL", "失败");

    private String code;
    private String desc;

    RepaymentFeeStatusEnum(String code, String desc) {
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

    public static String getByCode(String code) {
        for (RepaymentFeeStatusEnum item : values()) {
            if (item.getCode().equals(code)) {
                return item.getDesc();
            }
        }
        return null;
    }
}
