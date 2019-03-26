package com.zb.fincore.pms.common.enums;

/**
 * ProductInvestTypeEnum
 * 产品计息方式枚举
 *
 * @author
 */
public enum ProductInvestTypeEnum {
	/** 一次性还本付息 */
    ONCE_PAY_ALL(1, "一次性还本付息"),
    /** 按季付息 到期还本 */
    QUARTER_INTERESTS(2, "按季付息 到期还本"),
    /** 按月付息，到期还本 */
    MOTH_INTERESTS(3, "按月付息，到期还本"),
    /** 等额本息 */
    AVERAGE_CAPITAL_PLUS_INTEREST(4, "等额本息");

    private int code;
    private String desc;

    ProductInvestTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 通过code获取enum对象
     *
     * @param code
     * @return
     */
    public static ProductInvestTypeEnum getEnumItem(int code) {
        for (ProductInvestTypeEnum item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }

    /**
     * 通过code获取enum code desc
     *
     * @param code
     * @return
     */
    public static String getEnumCodeDesc(int code) {
        for (ProductInvestTypeEnum item : values()) {
            if (item.getCode() == code) {
                return item.getDesc();
            }
        }
        return null;
    }

}
