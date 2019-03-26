package com.zb.p2p.facade.api.resp;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 功能: 产品期限信息数据模型
 * 日期: 2017/4/1 0001 10:17
 * 版本: V1.0
 */
@Data
public class ProductStockDTO implements Serializable{

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = -5410424256635488285L;

    /**
     * 预约总金额
     */
    private BigDecimal reservationTotalAmount;

    /**
     * 正式交易总金额
     */
    private BigDecimal actualTotalAmount;


}
