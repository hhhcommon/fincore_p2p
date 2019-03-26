/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.service.template;

import java.util.List;
import java.util.Map;

import com.zillionfortune.boss.dal.entity.Template;

/**
 * ClassName: FileService <br/>
 * Function: 模板Service. <br/>
 * Date: 2017年5月17日 上午10:35:09 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public interface TemplateService {

	/**
	 * add:新增模板. <br/>
	 *
	 * @param fileInfo
	 * @return
	 */
	public void add(Template template);
	
	/**
	 * delete:根据主键删除模板. <br/>
	 *
	 * @param id
	 * @return
	 */
	public void delete(Long id);
	
	/**
	 * update:更新模板. <br/>
	 *
	 * @param fileInfo
	 * @return
	 */
	public void update(Template template);
	
	
	/**
	 * selectByPrimaryKey:根据主键查询模板. <br/>
	 *
	 * @param id
	 * @return
	 */
	public Template selectByPrimaryKey(Long id);
	
	/**
	 * selectBySelective:根据条件查询 <br/>
	 *
	 * @param FileInfo
	 * @return
	 */
	public List<Template> selectBySelective(Map<String, Object> paramMap);
	
	
}
