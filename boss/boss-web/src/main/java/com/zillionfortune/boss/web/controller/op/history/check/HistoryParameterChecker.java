/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.op.history.check;

import org.springframework.stereotype.Component;

import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.op.history.vo.HistoryQueryByPageRequestVo;
import com.zillionfortune.boss.web.controller.op.history.vo.HistoryQueryRequestVo;

/**
 * ClassName: HistoryParameterChecker <br/>
 * Function: 日志管理参数校验. <br/>
 * Date: 2017年2月27日 下午2:35:00 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version
 * @since JDK 1.7
 */
@Component
public class HistoryParameterChecker {

	/**
	 * checkHistoryQueryRequest:校验查询日志请求参数. <br/>
	 *
	 * @param req
	 * @throws Exception
	 */
	public void checkHistoryQueryRequest(HistoryQueryRequestVo req) throws Exception {

		if (req == null) {
			throw new BusinessException("请求对象不能为空");
		}

		if (req.getOperationHistoryId() == null) {
			throw new BusinessException("operationHistoryId不能为空");
		}

	}

}
