package com.zillionfortune.boss.dal.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zillionfortune.boss.dal.entity.Template;
import com.zillionfortune.boss.support.mybatis.annotation.MyBatisRepository;
@MyBatisRepository
@Component
public interface TemplateDao {
    int deleteByPrimaryKey(Long id);

    int insert(Template record);

    int insertSelective(Template record);

    Template selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Template record);

    int updateByPrimaryKey(Template record);

	List<Template> selectBySelective(Map<String, Object> paramMap);
}