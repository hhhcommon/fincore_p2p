/*
 * Copyright (c) 2016, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.biz.file;

import java.io.File;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.zillionfortune.boss.biz.file.dto.FileModifyRequest;
import com.zillionfortune.boss.biz.file.dto.FileQueryByPageRequest;
import com.zillionfortune.boss.biz.file.dto.FileUploadRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

/**
 * ClassName: FileBiz <br/>
 * Function: 文件处理. <br/>
 * Date: 2017年5月16日 下午4:33:22 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public interface FileBiz {
	
	/**
	 * upload:文件上传. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse upload(File file);

	/**
	 * upload:文件上传. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse upload(FileUploadRequest req);
	
	/**
	 * getInputStream:获取文件下载输入流. <br/>
	 *
	 * @param fileName
	 * @return
	 */
	public InputStream getInputStream(String fileName);
	
	/**
	 * queryByPage:文件列表查询. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse queryByPage(FileQueryByPageRequest req);
	
	/**
	 * queryFileInfoDetail:文件详细信息查询. <br/>
	 *
	 * @param id
	 * @return
	 */
	public BaseWebResponse queryFileInfoDetail(Long id);
	
	/**
	 * modify:文件修改. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse modify(FileModifyRequest req);
	
	/**
	 * delete:文件删除. <br/>
	 *
	 * @param id
	 * @param fileName
	 * @return
	 */
	public BaseWebResponse delete(Long id, String fileName);
	
	/**
     * countUpFile:文件分类统计. <br/>
     *
     * @return
     */
    public BaseWebResponse countUpFile();

    public BaseWebResponse uploadAssetFile(MultipartFile file);

	BaseWebResponse onlyUpload(FileUploadRequest req);

	BaseWebResponse delete(String fileName);
	
}
