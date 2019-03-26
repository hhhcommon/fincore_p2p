package com.zb.p2p.customer.common.enums;

/**
 * <p>是否0和1枚举</p>
 *
 * @author Vinson
 * @version $Id: YesNoEnum.java, v 0.1 Mar 6, 2018 7:52:40 PM Zhengwenquan Exp $
 */
public enum YesNoEnum {
    YES(1, "是"),
    NO(0, "否");

    /**
     * 代码
     */
    private final Integer code;
    /**
     * 信息
     */
    private final String desc;

    YesNoEnum(Integer code, String message) {
        this.code = code;
        this.desc = message;
    }

    /**
     * 通过代码获取枚举项
     *
     * @param code
     * @return
     */
    public static YesNoEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }

        for (YesNoEnum yesNoEnum : YesNoEnum.values()) {
            if (yesNoEnum.getCode().equals(code)) {
                return yesNoEnum;
            }
        }

        return null;
    }

    /**
     * 根据bool 值返回
     *
     * @param code
     * @return
     */
    public static YesNoEnum getByBoolean(boolean code) {
        return code ? YesNoEnum.YES : YesNoEnum.NO;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}