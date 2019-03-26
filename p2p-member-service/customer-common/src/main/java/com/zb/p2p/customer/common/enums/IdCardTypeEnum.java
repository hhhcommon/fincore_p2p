package com.zb.p2p.customer.common.enums;

/**
 * <p> 证件类型枚举</p>
 *
 * @author Vinson
 * @version $Id: YesNoEnum.java, v 0.1 Mar 6, 2018 7:52:40 PM Zhengwenquan Exp $
 */
public enum IdCardTypeEnum {
    IDCARD("01", "身份证"),
    PASSPORT("02", "护照");

    /**
     * 代码
     */
    private final String code;
    /**
     * 信息
     */
    private final String desc;

    private IdCardTypeEnum(String code, String message) {
        this.code = code;
        this.desc = message;
    }

    /**
     * 通过代码获取枚举项
     *
     * @param code
     * @return
     */
    public static IdCardTypeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (IdCardTypeEnum yesNoEnum : IdCardTypeEnum.values()) {
            if (yesNoEnum.getCode().equals(code)) {
                return yesNoEnum;
            }
        }

        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}