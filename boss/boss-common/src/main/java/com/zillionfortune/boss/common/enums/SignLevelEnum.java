package com.zillionfortune.boss.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * ClassName: SignLevelEnum <br/>
 * Function: 授权级别枚举. <br/>
 * Date: 2017年5月16日 下午6:09:35 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public enum SignLevelEnum {

	ASSET_APPROVE_LEVEL1("ASSET_APPROVE_LEVEL1", "资产审核一审"),
	ASSET_APPROVE_LEVEL2("ASSET_APPROVE_LEVEL2","资产审核二审");

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
    public static SignLevelEnum getEnum(String code) {
        for (SignLevelEnum item : values()) {
            if (StringUtils.equals(item.getCode(), code)) {
                return item;
            }
        }
        return null;
    }

    private SignLevelEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
