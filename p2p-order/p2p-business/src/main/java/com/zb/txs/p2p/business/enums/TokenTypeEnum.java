package com.zb.txs.p2p.business.enums;

import lombok.Getter;

/**
 * Function:   功能描述 <br/>
 * Date:   2017年10月12日 下午6:25 <br/>
 *
 * @author liguoliang@zillionfortune.com
 */

@Getter
public enum TokenTypeEnum {
    UN_KNOW("UN_KNOW", "0"),
    H5("h5", "1"),
    IOS("ios", "2"),
    ANDROID("android", "3"),
    APP_KEY("appKey","5");


    private TokenTypeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    private String name;
    private String value;

    private static final TokenTypeEnum[] TOKEN_TYPE_ENUMS = values();

    public static String getValue(String name) {
        for (TokenTypeEnum tokenTypeEnum : TOKEN_TYPE_ENUMS) {
            if (tokenTypeEnum.getName().equalsIgnoreCase(name)) {
                return tokenTypeEnum.getValue();
            }
        }
        return UN_KNOW.getValue();
    }
}
