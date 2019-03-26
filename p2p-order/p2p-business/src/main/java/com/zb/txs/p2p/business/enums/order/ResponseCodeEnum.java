package com.zb.txs.p2p.business.enums.order;

/**
 * Created by liguoliang on 2017/9/1.
 */
public enum ResponseCodeEnum {
    RESPONSE_SUCCESS("0000", "响应成功"),
    RESPONSE_FAIL("9999", "响应失败"),
    RESPONSE_NOT_FUND("9000", "未找到信息"),
    RESPONSE_SERIAL_REPEAT("9001", "单号重复"),
    RESPONSE_PARAM_FAIL("9003", "参数校验失败");

    private String code;
    private String desc;

    ResponseCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

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
}
