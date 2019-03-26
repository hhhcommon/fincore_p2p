package com.zb.p2p.trade.common.enums;

/**
 * <p> 债权持有状态 </p>
 *
 * @author Vinson
 * @version CreditorStatusEnum.java v1.0 2018/4/20 19:30 Zhengwenquan Exp $
 */
public enum CreditorStatusEnum {

    INIT("INIT", "初始化"),
    WAIT_CASH("WAIT_CASH", "待回款(持有中)"),
    TRANSFERING("TRANSFERING", "转让回款中"),
    FINISHED("FINISHED", "已完成(已结清)");

    private final String code;
    private final String message;

    CreditorStatusEnum(String code, String message){
        this.code = code;
        this.message = message;
    }

    public static CreditorStatusEnum getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (CreditorStatusEnum memberType : CreditorStatusEnum.values()) {
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
