package com.zb.p2p.customer.common.enums;

/**
 * <p> 个人或机构开户请求 </p>
 *
 * @author Vinson
 * @version MemberType.java v1.0 2018/3/7 19:30 Zhengwenquan Exp $
 */
public enum MemberType {

    PERSONAL("10", "个人会员"), ORGANIZATION("20", "机构会员");

    private final String code;
    private final String message;

    private MemberType(String code, String message){
        this.code = code;
        this.message = message;
    }

    public static MemberType getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (MemberType memberType : MemberType.values()) {
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
