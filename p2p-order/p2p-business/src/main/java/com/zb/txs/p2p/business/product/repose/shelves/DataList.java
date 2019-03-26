package com.zb.txs.p2p.business.product.repose.shelves;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author nibaoshan
 * @create 2017-09-28 16:59
 * @desc
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataList {
    private String approvalRequireSign;

    private String approvalSign;

    private int approvalStatus;

    private String archiveTime;

    private String assetPoolCode;

    private String assetPoolName;

    private int assetPoolType;

    private int calendarMode;

    private int collectStatus;

    private String createBy;

    private String createTime;

    private String fundSettleParty;

    private int id;

    private String introduction;

    private int isOpenHMT;

    private String joinChannelCode;

    private String lastApprovalTime;

    private String modifyBy;

    private String modifyTime;

    private String patternCode;

    private String productApprovalModel;

    private String productCode;

    private List<ProductContractModelList> productContractModelList ;

    private String productDisplayName;

    private List<ProductLadderModelList> productLadderModelList ;

    private String productLineCode;

    private int productLineId;

    private String productLineName;

    private String productName;

    private ProductPeriodModel productPeriodModel;

    private ProductProfitModel productProfitModel;

    private ProductStockModel productStockModel;

    private String riskLevel;

    private String saleChannelCode;

    private int saleStatus;

    private BigDecimal totalAmount;

}
