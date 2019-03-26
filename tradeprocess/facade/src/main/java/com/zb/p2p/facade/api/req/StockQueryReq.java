package com.zb.p2p.facade.api.req;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 库存查询请求参数
 * Created by limingxin on 2017/8/31.
 */
@Data
public class StockQueryReq implements Serializable {

    /**
     * 资产起息日期
     */
    private Date valueStartTime;

    /**
     * 借款期限
     */
    private Integer lockDate;

    /**
     * 产品编号
     */
    private String productCode;

    /**
     * 查询来源 （BOSS: MSD）
     */
    private String source;
}
