package com.zb.p2p.customer.common.enums;

/**
 * <p> 开户的账户类型（目前支持逻辑户） </p>
 *
 * @author Vinson
 * @version MemberType.java v1.0 2018/3/7 19:30 Zhengwenquan Exp $
 */
public enum AccountTypeEnum {

//    VIR("VIR", "虚拟账户"),
//    ELC("ELC", "电子账户"),
    LOGIC("logic", "逻辑账户");

    private final String code;
    private final String message;

    private AccountTypeEnum(String code, String message){
        this.code = code;
        this.message = message;
    }

    public static AccountTypeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (AccountTypeEnum memberType : AccountTypeEnum.values()) {
            if (memberType.getCode().equals(code)) {
                return memberType;
            }
        }

        return null;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
