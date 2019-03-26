package com.zb.fincore.pms.common.enums;

/**
 * ProductApprovalStatusEnum
 * 产品审核状态枚举
 * @author MABIAO
 */

public enum ProductApprovalStatusEnum {
	
	/** 10 - 提交审核 */
    WAIT_APPROVAL(10, "提交审核"),
    /** 20 - 审核通过 */
    APPROVAL_SUCCESS(20, "审核通过"),
    /** 30 - 审核未通过 */
    APPROVAL_FAILURE(30, "审核未通过");

    private int code;
    private String desc;

    ProductApprovalStatusEnum(int code, String desc) {
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
    public static ProductApprovalStatusEnum getEnumItem(int code) {
        for (ProductApprovalStatusEnum item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }

}
