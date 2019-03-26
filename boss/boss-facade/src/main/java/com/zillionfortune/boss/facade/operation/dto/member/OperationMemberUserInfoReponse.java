package com.zillionfortune.boss.facade.operation.dto.member;

import java.util.List;

import com.zillionfortune.boss.facade.common.dto.BaseResponse;


public class OperationMemberUserInfoReponse extends BaseResponse {

	private static final long serialVersionUID = 1L;

	private Integer totalPage;
	private Integer totalCount;
	private Integer pageSize;
	private Integer pageNo;
	
	private List<OperationMemberUserInfoForList> data;
	
	public OperationMemberUserInfoReponse(String respCode,String resultCode,String resultMsg){
		super(respCode,resultCode,resultMsg);
	}
	
	public OperationMemberUserInfoReponse(String respCode,String resultMsg){
		super(respCode,resultMsg);
	}
	
	public OperationMemberUserInfoReponse(){
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
	public List<OperationMemberUserInfoForList> getData() {
		return data;
	}

	public void setData(List<OperationMemberUserInfoForList> data) {
		this.data = data;
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

}
