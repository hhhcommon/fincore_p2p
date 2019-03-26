package com.zillionfortune.boss.dal.dao;

import org.springframework.stereotype.Component;

import com.zillionfortune.boss.dal.entity.AssetFile;
import com.zillionfortune.boss.support.mybatis.annotation.MyBatisRepository;

import java.util.List;

@MyBatisRepository
@Component
public interface AssetFileDao {
    int deleteByPrimaryKey(Long id);

    int insert(AssetFile record);

    int insertSelective(AssetFile record);

    AssetFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AssetFile record);

    int updateByPrimaryKey(AssetFile record);

    List<AssetFile> selectByFileName(AssetFile record);
}