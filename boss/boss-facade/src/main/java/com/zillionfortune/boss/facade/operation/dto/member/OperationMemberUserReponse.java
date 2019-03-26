package com.zillionfortune.boss.facade.operation.dto.member;

import java.util.List;

import com.zillionfortune.boss.facade.common.dto.BaseResponse;


public class OperationMemberUserReponse extends BaseResponse {

	private static final long serialVersionUID = 1L;

	private Integer total;
	private Integer totalPage;
	private Integer pageSize;
	private Integer currentPage;
	
	private List<OperationMemberUserInfo> data;
	
	public OperationMemberUserReponse(String respCode,String resultCode,String resultMsg){
		super(respCode,resultCode,resultMsg);
	}
	
	public OperationMemberUserReponse(String respCode,String resultMsg){
		super(respCode,resultMsg);
	}
	
	public OperationMemberUserReponse(){
		super();
	}
	
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
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
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public List<OperationMemberUserInfo> getData() {
		return data;
	}

	public void setData(List<OperationMemberUserInfo> data) {
		this.data = data;
	}

}
