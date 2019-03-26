package com.zillionfortune.boss.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * ClassName: HookTypeEnum <br/>
 * Function: 勾选类型枚举. <br/>
 * Date: 2017年5月16日 下午6:09:35 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public enum FileTypeEnum {

	PDF("1", "PDF"),
	WORD("2","WORD");

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
    public static FileTypeEnum getEnum(String code) {
        for (FileTypeEnum item : values()) {
            if (StringUtils.equals(item.getCode(), code)) {
                return item;
            }
        }
        return null;
    }

    private FileTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
