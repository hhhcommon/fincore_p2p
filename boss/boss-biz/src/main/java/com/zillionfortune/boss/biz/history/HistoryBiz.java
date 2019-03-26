/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.history;

import com.zillionfortune.boss.biz.history.dto.HistoryQueryByPageRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

/**
 * ClassName: HistoryBiz <br/>
 * Function: 运营平台日志管理biz层接口定义. <br/>
 * Date: 2017年2月27日 下午2:51:27 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public interface HistoryBiz {
	
	/**
	 * query:根据主键查询日志. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse query(Integer operationHistoryId);
	
	/**
	 * queryByPage:查询日志(分页). <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse queryByPage(HistoryQueryByPageRequest req);
	
}
