package com.zb.fincore.pms.common.enums;

/**
 * 产品锁定期单位Enum
 *
 * @author
 * @create 2016-12-19 11:13
 */
public enum ProductLockPeriodUnitEnum {

    DAY(1, "天"),
    WEEKLY(2, "周"),
    MONTH(3, "月"),
    YEAR(4, "年");

    private int code;
    private String desc;

    ProductLockPeriodUnitEnum(int code, String desc) {
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
    public static ProductLockPeriodUnitEnum getEnumItem(int code) {
        for (ProductLockPeriodUnitEnum item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }
}
