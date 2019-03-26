package com.zb.p2p.facade.api.req;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangxin on 2017/9/5.
 */
@Data
public class ProductReq implements Serializable {
    /**
     * 产品编号
     */
    private String productCode;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品展示名称
     */
    private String productDisplayName;
    /**
     * 产品线编号
     */
    private String productLineCode;
    /**
     * 产品的类型代码(01:现金管理, 02:定期类, 03:净值型)
     */
    private String patternCode;
    /**
     * 销售机构代码 唐小僧 TXS|企业理财QYLIC|摇旺YW
     */
    private String saleChannel;
    /**
     * 产品状态
     */
    private String productStatus;
    /**
     * 存续期
     */
    private Integer duration;
    /**
     * 存续期单位
     */
    private String durationUnit;
    /**
     * 产品规模
     */
    private BigDecimal productAmount;
    /**
     * 日历模式
     */
    private String calendarMode;
    /**
     * 起息日
     */
    private Date valueStartDate;
    /**
     * 到期日
     */
    private Date expireDate;
    /**
     * 结清日
     */
    private Date settlementDate;
    /**
     * 基础收益
     */
    private BigDecimal yield;
    /**
     * 计息天数(默认365)
     */
    private Integer interestDays;
    /**
     * 资产池编号
     */
    private String assetPoolCode;
}
