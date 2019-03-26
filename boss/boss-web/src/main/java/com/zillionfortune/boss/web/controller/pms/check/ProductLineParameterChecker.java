/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.pms.check;

import org.springframework.stereotype.Component;

import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.pms.vo.QueryProductLineListVO;

/**
 * ClassName: ProductLineParameterChecker <br/>
 * Function: 产品线服务参数校验. <br/>
 * Date: 2017年8月24日 下午2:56:39 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Component
public class ProductLineParameterChecker {

	/**
	 * checkQueryProductLineListRequest:查询产品线列表请求参数校验. <br/>
	 *
	 * @param vo
	 * @throws Exception
	 */
	public void checkQueryProductLineListRequest(QueryProductLineListVO vo) throws Exception {

		if ( vo == null) {
			throw new BusinessException("请求对象不能为空");
		}
	}
}
