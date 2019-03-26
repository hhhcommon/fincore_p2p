package com.zb.p2p.customer.common.enums;

/**
 * <p> 实名认证类型枚举</p>
 *
 * @author Vinson
 * @version $Id: YesNoEnum.java, v 0.1 Mar 6, 2018 7:52:40 PM Zhengwenquan Exp $
 */
public enum VerifyTypeEnum {
    XM_SFZ("01", "身份证号、姓名要素认证"),
    SJH_XM_SFZ("02", "手机号、姓名、身份证号要素认证"),
    XM_SFZ_YHK("03", "姓名、身份证号、银行卡要素认证");

    /**
     * 代码
     */
    private final String code;
    /**
     * 信息
     */
    private final String desc;

    VerifyTypeEnum(String code, String message) {
        this.code = code;
        this.desc = message;
    }

    /**
     * 通过代码获取枚举项
     *
     * @param code
     * @return
     */
    public static VerifyTypeEnum getByCode(String code) {
        if (code == null) {
            return XM_SFZ;
        }

        for (VerifyTypeEnum yesNoEnum : VerifyTypeEnum.values()) {
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