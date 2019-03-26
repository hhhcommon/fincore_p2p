package com.zb.p2p.trade.common.enums;

/**
 * <p> 账户用途类型枚举</p>
 *
 * @author Vinson
 * @version $Id: YesNoEnum.java, v 0.1 Mar 6, 2018 7:52:40 PM Zhengwenquan Exp $
 */
public enum AccountPurposeEnum {

    _101("101","借款账户"),
    _102("102", "出借账户"),

    _201("201","资金归集户"),
    _202("202","综合账户"),
    _203("203","风险备付金户"),
    _204("204","手续费户"),
    _205("205","授权还款户"),
    _206("206", "还款管理人账户"),
    _207("207", "担保人账户")
    ;

    /**
     * 代码
     */
    private final String code;
    /**
     * 信息
     */
    private final String desc;

    AccountPurposeEnum(String code, String message) {
        this.code = code;
        this.desc = message;
    }

    /**
     * 通过代码获取枚举项
     *
     * @param code
     * @return
     */
    public static AccountPurposeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (AccountPurposeEnum yesNoEnum : AccountPurposeEnum.values()) {
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