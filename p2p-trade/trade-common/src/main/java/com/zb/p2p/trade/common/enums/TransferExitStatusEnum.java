package com.zb.p2p.trade.common.enums;

/**
 * <p> 用户退出申请状态 </p>
 *
 * @author Vinson
 * @version TransferExitStatusEnum.java v1.0 2018/4/20 19:30 Zhengwenquan Exp $
 */
public enum TransferExitStatusEnum {

    INIT("INIT", "初始化"),
    EXITING("EXITING", "退出中"),
    EXITED("EXITED", "已全部退出");

    private final String code;
    private final String message;

    TransferExitStatusEnum(String code, String message){
        this.code = code;
        this.message = message;
    }

    public static TransferExitStatusEnum getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (TransferExitStatusEnum statusEnum : TransferExitStatusEnum.values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum;
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
