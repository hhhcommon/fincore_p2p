package com.zillionfortune.boss.common.dto;

/**
 * ClassName: BasePageResponse <br/>
 * Function: 相应对象基础类封装. <br/>
 * Date: 2016年11月8日 上午9:48:40 <br/>
 *
 * @author Administrators
 * @version 
 * @since JDK 1.7
 */
public class BasePageResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;
	
	/** 结果集总数 */
    private Integer totalCount;

    /** 总页数 */
    private Integer totalPage;
    
    /** 每页条数 */
    private Integer pageSize;
    
    /** 当前页 */
    private Integer pageNo;
  
    public BasePageResponse() {

    }

    public BasePageResponse(String respCode) {
		super(respCode);
	}
    
    public BasePageResponse(String respCode, String resultMsg) {
		super(respCode, resultMsg);
	}
    
	public BasePageResponse(String respCode, String resultCode, String resultMsg) {
		super(respCode, resultCode, resultMsg);
	}

	public BasePageResponse(String respCode, String resultCode, String resultMsg,
			Integer totalCount, Integer totalPage,
			Integer pageSize, Integer pageNo) {
		
		super(respCode, resultCode, resultMsg);
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.pageSize = pageSize;
		this.pageNo = pageNo;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}