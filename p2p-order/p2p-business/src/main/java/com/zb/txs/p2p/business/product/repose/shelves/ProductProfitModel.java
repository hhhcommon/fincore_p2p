package com.zb.txs.p2p.business.product.repose.shelves;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author nibaoshan
 * @create 2017-09-28 17:00
 * @desc
 **/
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductProfitModel {
    private BigDecimal addYieldRate;

    private int basicInterestsPeriod;

    public void setCalculateInvestType(String calculateInvestType) {
        if (calculateInvestType == null) {
            this.calculateInvestType = "0";
        } else {
            this.calculateInvestType = calculateInvestType;
        }

    }

    private String calculateInvestType;


    private String calculateInvestTypeDesc;

    private String createBy;

    private String createTime;

    private BigDecimal currentYieldRate;

    private String establishCondition;

    private BigDecimal floatingYieldRate;

    private int id;

    private BigDecimal increaseAmount;

    private BigDecimal maxInvestAmount;

    private BigDecimal maxYieldRate;

    private BigDecimal minHoldAmount;

    private BigDecimal minInvestAmount;

    private BigDecimal minYieldRate;

    private String modifyBy;

    private String modifyTime;

    private String productCode;

    private int productId;

    private String profitType;

    private BigDecimal singleMaxInvestAmount;

    private int unit;

}
