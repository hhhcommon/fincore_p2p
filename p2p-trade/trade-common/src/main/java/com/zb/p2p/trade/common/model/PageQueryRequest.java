package com.zb.p2p.trade.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 功能: RPC分页查询请求基类
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/3/28 0028 13:30
 * 版本: V1.0
 */
@Data
public class PageQueryRequest implements Serializable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 116506480140504486L;

    /**
     * 页码(当前页)
     */
    private Integer pageNo=1;

    /**
     * 分页大小(每页数量)
     */
    private Integer pageSize=10;

    /**
     * 排序字段
     */
    private Integer sortField;

    /**
     * 排序方式,1.升序,2.降序
     */
    private Integer sortType;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getSortField() {
        return sortField;
    }

    public void setSortField(Integer sortField) {
        this.sortField = sortField;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }
}
