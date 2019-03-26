/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.pms;

import com.zb.fincore.pms.facade.line.dto.req.QueryProductLineListRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

/**
 * ClassName: ProductLineBiz <br/>
 * Function: 产品线服务Biz. <br/>
 * Date: 2017年5月31日 上午11:03:12 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public interface ProductLineBiz {
	/**
	 * queryProductLineList:产品线列表查询. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse queryProductLineList(QueryProductLineListRequest req,String productType);
}
