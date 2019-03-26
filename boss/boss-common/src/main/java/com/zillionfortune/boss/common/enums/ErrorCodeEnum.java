package com.zillionfortune.boss.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 错误码枚举
 * 
 * @author litaiping
 * @version  ErrorCodeEnum.java, v 0.1 2016-7-27 上午9:50:25
 */
public enum ErrorCodeEnum {

    ILLEGAL_PARAMETER("ILLEGAL_PARAMETER", "非法参数"),
    SYSTEM_ERROR("SYSTEM_ERROR","系统异常");

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
     * @return ProductEnum
     */
    public static ErrorCodeEnum getEnum(String code) {
        for (ErrorCodeEnum item : values()) {
            if (StringUtils.equals(item.getCode(), code)) {
                return item;
            }
        }
        return null;
    }

    private ErrorCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
