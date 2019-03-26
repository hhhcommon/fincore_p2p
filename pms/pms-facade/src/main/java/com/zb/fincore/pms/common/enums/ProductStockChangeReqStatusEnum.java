package com.zb.fincore.pms.common.enums;

/**
 * ProductStockChangeReqStatusEnum
 * 产品库存变更请求状态枚举
 * 2017年4月12日14:03:03
 *
 * @author mabiao
 */
public enum ProductStockChangeReqStatusEnum {

    HANDLING(1, "正在处理"),
    HANDLE_SUCCESS(2, "处理成功"),
    HANDLE_FAILED(3,"处理失败");

    private int code;
    private String desc;

    ProductStockChangeReqStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
     * @return
     */
    public static ProductStockChangeReqStatusEnum getEnumItem(int code) {
        for (ProductStockChangeReqStatusEnum item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }
}
