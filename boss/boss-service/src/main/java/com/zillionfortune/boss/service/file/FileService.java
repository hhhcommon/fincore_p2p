/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.service.file;

import java.util.List;
import java.util.Map;

import com.zillionfortune.boss.dal.entity.FileInfo;

/**
 * ClassName: FileService <br/>
 * Function: 文件信息Service. <br/>
 * Date: 2017年5月17日 上午10:35:09 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public interface FileService {

	/**
	 * add:新增文件信息. <br/>
	 *
	 * @param fileInfo
	 * @return
	 */
	public void add(FileInfo fileInfo);
	
	/**
	 * delete:根据主键删除文件信息. <br/>
	 *
	 * @param id
	 * @return
	 */
	public void delete(Long id);
	
	/**
	 * update:更新文件信息. <br/>
	 *
	 * @param fileInfo
	 * @return
	 */
	public void update(FileInfo fileInfo);
	
	
	/**
	 * selectByPrimaryKey:根据主键查询文件信息. <br/>
	 *
	 * @param id
	 * @return
	 */
	public FileInfo selectByPrimaryKey(Long id);
	
	/**
	 * selectBySelective:根据条件查询 <br/>
	 *
	 * @param FileInfo
	 * @return
	 */
	public List<FileInfo> selectBySelective(Map<String, Object> paramMap);
	
	/**
	 * selectBySelectiveCount:根据条件统计条数. <br/>
	 *
	 * @param paramMap
	 * @return
	 */
	public int selectBySelectiveCount(Map<String, Object> paramMap);
	
	/**
	 * selectByShowName:根据文件展示名查询. <br/>
	 *
	 * @param showName
	 * @return
	 */
	public FileInfo selectByShowName(String showName);
	
	/**
	 * updateByShowNameSelective:根据文件展示名更新文件信息. <br/>
	 *
	 * @param fileInfo
	 * @return
	 */
	public void updateByShowNameSelective(FileInfo fileInfo);
	
}
