package com.zb.p2p.customer.common.enums;

/**
 * <p> 机构证件类型枚举 </p>
 *
 * @author Vinson
 * @version $Id: OrgCardTypeEnum.java, v 0.1 Mar 10, 2018 7:52:40 PM Zhengwenquan Exp $
 */
public enum OrgCardTypeEnum {
    YING_YE_ZHI_ZHAO("1", "营业执照"),
    XIN_YONG_DAI_MA("2", "社会统一信用代码"),
    JI_GOU_DAI_MA("3", "组织机构代码");

    /**
     * 代码
     */
    private final String code;
    /**
     * 信息
     */
    private final String desc;

    OrgCardTypeEnum(String code, String message) {
        this.code = code;
        this.desc = message;
    }

    /**
     * 通过代码获取枚举项
     *
     * @param code
     * @return
     */
    public static OrgCardTypeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (OrgCardTypeEnum yesNoEnum : OrgCardTypeEnum.values()) {
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