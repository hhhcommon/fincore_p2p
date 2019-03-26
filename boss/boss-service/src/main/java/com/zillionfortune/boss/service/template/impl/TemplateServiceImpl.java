/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.service.template.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zillionfortune.boss.dal.dao.TemplateDao;
import com.zillionfortune.boss.dal.entity.Template;
import com.zillionfortune.boss.service.template.TemplateService;

/**
 * ClassName: FileServiceImpl <br/>
 * Function: 文件操作实现. <br/>
 * Date: 2017年5月17日 上午10:45:30 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Service
public class TemplateServiceImpl implements TemplateService {

	@Autowired
	private TemplateDao templateDao;

	@Override
	public void add(Template template) {
		templateDao.insertSelective(template);
		
	}

	@Override
	public void delete(Long id) {
		templateDao.deleteByPrimaryKey(id);
		
	}

	@Override
	public void update(Template template) {
		templateDao.updateByPrimaryKeySelective(template);
		
	}

	@Override
	public Template selectByPrimaryKey(Long id) {
		return templateDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Template> selectBySelective(Map<String, Object> paramMap) {
		return templateDao.selectBySelective(paramMap);
	}

}