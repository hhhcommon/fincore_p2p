/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.file.vo;

/**
 * ClassName: FileModifyVo <br/>
 * Function: 文件信息更新用Vo. <br/>
 * Date: 2017年5月16日 下午5:42:25 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class FileModifyVo {
	/** id:主键Id **/
	private Long id;
	
	/** showName:文件展示名 **/
	private String showName;

	/** hookType:勾选类型 **/
    private String hookType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
