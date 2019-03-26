/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.pms.vo;

/**
 * ClassName: ApproveProductVo <br/>
 * Function: 产品审核用Vo. <br/>
 * Date: 2017年5月10日 下午2:16:29 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class ApproveProductVo {
	/** productCode:产品编号 **/
	private String productCode;

	/** approvalStatus:审核状态 **/
	private String approvalStatus;

	/** approvalSuggestion:审核意见 **/
	private String approvalSuggestion;

	/** approvalBy:审核人 **/
	private String approvalBy;

	/** sign:授权级别 **/
	private String sign;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getApprovalSuggestion() {
		return approvalSuggestion;
	}

	public void setApprovalSuggestion(String approvalSuggestion) {
		this.approvalSuggestion = approvalSuggestion;
	}

	public String getApprovalBy() {
		return approvalBy;
	}

	public void setApprovalBy(String approvalBy) {
		this.approvalBy = approvalBy;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
