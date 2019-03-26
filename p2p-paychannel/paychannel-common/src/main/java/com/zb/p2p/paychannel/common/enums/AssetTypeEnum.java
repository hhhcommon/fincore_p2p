package com.zb.p2p.paychannel.common.enums;

public enum AssetTypeEnum {
    YSZC(1, "原始资产"),
    ZZZC(2, "债转资产");

    private Integer code;
    private String desc;

    AssetTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 通过代码获取枚举项
     *
     * @param code
     * @return
     */
    public static AssetTypeEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (AssetTypeEnum assetTypeEnum : AssetTypeEnum.values()) {
            if (assetTypeEnum.getCode().intValue() == code.intValue()) {
                return assetTypeEnum;
            }
        }
        return null;
    }
}
