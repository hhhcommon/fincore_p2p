/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.file.vo;

import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName: FileUploadVo <br/>
 * Function: 文件上传用Vo. <br/>
 * Date: 2017年5月16日 下午5:42:25 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class FileUploadVo {
	/** file:待上传文件 **/
	private MultipartFile file;

	/** showName:文件展示名 **/
	private String showName;

	/** hookType:勾选类型 **/
	private String hookType;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getHookType() {
		return hookType;
	}

	public void setHookType(String hookType) {
		this.hookType = hookType;
	}
}
