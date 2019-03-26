package com.zb.fincore.pms.common.enums;

/**
 * 功能: 产品库存变更状态枚举
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/12 0012 15:14
 * 版本: V1.0
 */
public enum ChangeProductStockStatusEnum {

	/** 1 - 处理中 */
    PROCESSING(1, "处理中"),

    /** 2 - 处理成功 */
    SUCCESS(2, "处理成功"),

    /** 3 - 库存不足 */
    NOT_ENOUGH_STOCK(3, "库存不足"),

    /** 4 - 处理失败 */
    FAIL(4, "处理失败"),

    /** 5 - 处理异常 */
    ERROR(5, "处理异常");

    private int code;
    private String desc;

    ChangeProductStockStatusEnum(int code, String desc) {
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
    public static ChangeProductStockStatusEnum getItem(int code) {
        for (ChangeProductStockStatusEnum item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }
}
