package com.zillionfortune.boss.dal.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zillionfortune.boss.dal.entity.FileInfo;
import com.zillionfortune.boss.support.mybatis.annotation.MyBatisRepository;

@MyBatisRepository
@Component
public interface FileInfoDao {
	int deleteByPrimaryKey(Long id);

	int insert(FileInfo record);

	int insertSelective(FileInfo record);

	FileInfo selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(FileInfo record);

	int updateByPrimaryKey(FileInfo record);

	List<FileInfo> selectBySelective(Map<String, Object> paramMap);

	int selectBySelectiveCount(Map<String, Object> paramMap);
	
	FileInfo selectByShowName(String showName);
	
	int updateByShowNameSelective(FileInfo record);
}