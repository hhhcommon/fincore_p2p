/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.service.file.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zillionfortune.boss.dal.dao.FileInfoDao;
import com.zillionfortune.boss.dal.entity.FileInfo;
import com.zillionfortune.boss.service.file.FileService;

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
public class FileServiceImpl implements FileService {

	@Autowired
	private FileInfoDao fileInfoDao;

	@Override
	public void add(FileInfo fileInfo) {
		fileInfoDao.insertSelective(fileInfo);
		
	}

	@Override
	public void delete(Long id) {
		fileInfoDao.deleteByPrimaryKey(id);
		
	}

	@Override
	public void update(FileInfo fileInfo) {
		fileInfoDao.updateByPrimaryKeySelective(fileInfo);
		
	}

	@Override
	public FileInfo selectByPrimaryKey(Long id) {
		return fileInfoDao.selectByPrimaryKey(id);
	}

	@Override
	public List<FileInfo> selectBySelective(Map<String, Object> paramMap) {
		return fileInfoDao.selectBySelective(paramMap);
	}

	@Override
	public int selectBySelectiveCount(Map<String, Object> paramMap) {
		return fileInfoDao.selectBySelectiveCount(paramMap);
	}

	@Override
	public FileInfo selectByShowName(String showName) {
		return fileInfoDao.selectByShowName(showName);
	}

	@Override
	public void updateByShowNameSelective(FileInfo fileInfo) {
		fileInfoDao.updateByShowNameSelective(fileInfo);
	}

}