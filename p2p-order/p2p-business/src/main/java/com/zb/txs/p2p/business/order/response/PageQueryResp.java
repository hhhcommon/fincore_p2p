package com.zb.txs.p2p.business.order.response;

import com.zb.txs.p2p.business.invest.repose.InvestPageResp;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页列表页返回
 * Created by liguoliang on 2017/9/26.
 */
@Data
public class PageQueryResp implements Serializable {
    public static final Integer DEFAULT_PAGE_SIZE = Integer.valueOf(10);
    private Integer pageNo = Integer.valueOf(1);
    private Integer pageSize;
    private Integer totalCount;
    private List<InvestPageResp> dataList;

    public List<InvestPageResp> getDataList() {
        return dataList;
    }

    public void setDataList(List<InvestPageResp> dataList) {
        this.dataList = dataList;
    }

    public Integer getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(Integer pageNo) {
        if (pageNo == null) {
            this.pageNo = pageNo;
        } else {
            this.pageNo = Integer.valueOf(pageNo.intValue() < 1 ? 1 : pageNo.intValue());
        }

    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null) {
            this.pageSize = pageSize;
        } else {
            this.pageSize = pageSize.intValue() < 1 ? DEFAULT_PAGE_SIZE : pageSize;
        }

    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = Integer.valueOf(totalCount.intValue() < 0 ? 0 : totalCount.intValue());
    }
}