/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.file.vo;

/**
 * ClassName: DeleteFileVo <br/>
 * Function: 文件删除用Vo. <br/>
 * Date: 2017年5月16日 下午5:42:25 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class DeleteFileVo {
	/** id:文件信息Id **/
    private Long id;
    
    /** fileName:文件名 **/
    private String fileName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
