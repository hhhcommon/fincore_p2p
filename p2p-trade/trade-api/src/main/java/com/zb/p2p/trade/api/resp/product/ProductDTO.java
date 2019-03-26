package com.zb.p2p.trade.api.resp.product;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangxin on 2017/9/5.
 */
@Data
public class ProductDTO implements Serializable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 3183206847036893899L;

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 产品编码
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
     * 产品线主键
     */
    private Long productLineId;

    /**
     * 产品线编号
     */
    private String productLineCode;

    /**
     * 产品线名称
     */
    private String productLineName;

    /**
     * 资产池类型
     */
    private Integer assetPoolType;

    /**
     * 资产池编号
     */
    private String assetPoolCode;

    /**
     * 资产池名称
     */
    private String assetPoolName;

    /**
     * 产品形态代码
     */
    private String patternCode;

    /**
     * 销售渠道代码
     */
    private String saleChannelCode;

    /**
     * 接入渠道
     */
    private String joinChannelCode;

    /**
     * 产品总规模
     */
    private BigDecimal totalAmount;

    /**
     * 销售状态
     */
    private Integer saleStatus;

    /**
     * 募集状态
     */
    private Integer collectStatus;

    /**
     * 产品风险等级
     */
    private String riskLevel;

    /**
     * 资金结算方
     */
    private String fundSettleParty;

    /**
     * 是否对港澳台客户开放
     */
    private Integer isOpenHMT;

    /**
     * 日历模式
     */
    private Integer calendarMode;

    /**
     * 产品介绍
     */
    private String introduction;

    /**
     * 审核需要签名
     */
    private String approvalRequireSign;

    /**
     * 已审核签名
     */
    private String approvalSign;

    /**
     * 审核状态
     */
    private Integer approvalStatus;

    /**
     * 最终审核时间
     */
    private Date lastApprovalTime;

    /**
     * 归档时间
     */
    private Date archiveTime;

    /**
     * 产品期限信息
     */
    private ProductPeriodDTO productPeriodDTO;

    /**
     * 产品投资限制及收益信息
     */
    private ProductProfitDTO productProfitDTO;

}
