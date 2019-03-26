/*
 * Copyright (c) 2016, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.biz.template;

import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.dal.entity.Template;

/**
 * ClassName: FileBiz <br/>
 * Function: 文件处理. <br/>
 * Date: 2017年5月16日 下午4:33:22 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public interface TemplateBiz {

	
	
	/**
	 * 模板信息查询. <br/>
	 *
	 * @param id
	 * @return
	 */
	public BaseWebResponse queryTemplateDetail(Integer templateNum,Long templateId);


	BaseWebResponse queryTemplateList(Integer templateType);


	BaseWebResponse saveOrUpdate(Template template);
	
	
	
}
