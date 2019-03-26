package com.zb.txs.p2p.business.product.repose.shelves;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author nibaoshan
 * @create 2017-09-28 17:00
 * @desc
 **/
@Data
public class ProductStockModel {
    private String createBy;

    private String createTime;

    private BigDecimal frozenAmount;

    private int id;

    private String modifyBy;

    private String modifyTime;

    private String productCode;

    private int productId;

    private BigDecimal redeemAmount;

    private BigDecimal saleAmount;

    private BigDecimal stockAmount;


}
