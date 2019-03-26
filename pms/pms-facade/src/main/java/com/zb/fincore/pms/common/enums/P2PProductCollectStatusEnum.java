package com.zb.fincore.pms.common.enums;

/**
 * ProductCollectStatusEnum
 * p2p 产品募集状态枚举
 * @author MABIAO
 */

public enum P2PProductCollectStatusEnum {

	/**
	 * 10:待募集
	 */
	PRODUCT_COLLECT_STATUS_WAIT_COLLECT(10, "待募集"),
	/**
	 * 11:募集期
	 */
    PRODUCT_COLLECT_STATUS_COLLECTING(11, "募集期"),
    /**
	 * 12:募集完成
	 */
    PRODUCT_COLLECT_STATUS_RAISE_COMPLETE(12, "募集完成");

    private int code;
    private String desc;

    P2PProductCollectStatusEnum(int code, String desc) {
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
    public static P2PProductCollectStatusEnum getEnumItem(int code) {
        for (P2PProductCollectStatusEnum item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }

}
