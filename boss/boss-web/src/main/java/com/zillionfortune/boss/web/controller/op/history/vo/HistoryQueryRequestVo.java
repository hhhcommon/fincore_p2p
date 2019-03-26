package com.zillionfortune.boss.web.controller.op.history.vo;

import com.zillionfortune.boss.common.dto.BasePageRequest;

/**
 * ClassName: HistoryQueryRequestVo <br/>
 * Function: 查询日志Request. <br/>
 * Date: 2017年2月27日 下午2:28:28 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class HistoryQueryRequestVo extends BasePageRequest {
	
	private static final long serialVersionUID = 1L;

	/**
	 * operationHistoryId:日志Id.
	 */
	private Integer operationHistoryId;

	public Integer getOperationHistoryId() {
		return operationHistoryId;
	}

	public void setOperationHistoryId(Integer operationHistoryId) {
		this.operationHistoryId = operationHistoryId;
	}
	
}
