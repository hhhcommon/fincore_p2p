package com.zb.p2p.trade.api.resp.contract;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mengkai on 2017/8/30.
 */
@Data
public class ViewLoanContractResp implements Serializable {
    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 8285007923632630377L;

    /**
     * 默认分页大小
     */
    public static final Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * 页码(当前页)
     */
    private Integer pageNo = 1;

    /**
     * 分页大小(每页数量)
     */
    private Integer pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 总记录数
     */
    private Integer totalCount = 0;


    private List<ViewLoanContractDTO> list;

}
