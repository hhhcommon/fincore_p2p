/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.member;

import com.zillionfortune.boss.common.dto.BaseWebResponse;

/**
 * ClassName: ProductBiz <br/>
 * Function: 产品管理Biz. <br/>
 * Date: 2017年5月9日 上午11:27:40 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public interface MemberBiz {
	
	/**
	 * 更新产品募集金额
	 * 
	 * @param req
	 * @return
	 */
	public BaseWebResponse queryMemberInfo(String memberId,String mobile);
	/**
	 * 查询用户绑卡信息
	 * 
	 * @param sourceId
	 * @param memberId
	 * @return
	 */
	public BaseWebResponse queryMemberCards(String memberId);
}
