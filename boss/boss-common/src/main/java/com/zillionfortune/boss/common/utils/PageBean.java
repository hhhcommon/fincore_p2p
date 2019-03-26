/*
 * Copyright (c) 2016, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.common.utils;

/**
 * ClassName: PageBean <br/>
 * Function: 分页Bean. <br/>
 * Date: 2016年12月27日 下午5:11:45 <br/>
 *
 * @author zhengrunlong@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class PageBean {

	 /** 默认每页条数 */
    public static final int DEFAULT_PAGE_SIZE = 10;
    
    /** 默认当前页 */
    public static final int DEFAULT_CURRENT_PAGE = 1;

    /** 总页数 */
    private int pageCount;

    /** 当前页数 */
    private int currentPage;
    
    /** 开始记录位置 */
    private int pageStart;

    /** 每页记录数 */
    private int pageSize;
    
    /** 总记录数 */
    private int totalCount;

    public PageBean(){
    	
    }
    
    public PageBean(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.pageStart = (currentPage - 1) * pageSize;
    }

    public PageBean(int totalCount, int currentPage, int pageSize) {
        this(currentPage, pageSize);
        this.totalCount = totalCount;
        this.pageCount = this.countPageCount(this.totalCount, this.pageSize);
    }
    
    /**
     * 计算分页总数
     *
     * @param totalCount 总记录数
     * @param pageSize   每页记录数
     * @return
     */
    public int countPageCount(int totalCount, int pageSize) {
        int pageCount;
        if (totalCount % pageSize > 0) {
        	pageCount = totalCount / pageSize + 1;
        } else {
        	pageCount = totalCount / pageSize;
        }
        return pageCount;
    }

    public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageStart() {
        return pageStart;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	
    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
}
