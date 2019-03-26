/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.file.dto;

/**
 * ClassName: FileQueryByPageRequest <br/>
 * Function: 文件列表查询用Request. <br/>
 * Date: 2017年5月16日 下午5:42:25 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public class FileQueryByPageRequest {
	/** fileName:文件名 **/
    private String fileName;
	
	/** showName:文件展示名 **/
	private String showName;

	/** fileType:文件类型 **/
	private String fileType;

	/** uploadStartTime:上传开始时间 **/
	private String uploadStartTime;
	
	/** uploadEndTime:上传结束时间 **/
	private String uploadEndTime;
	
	/** pageNo:页码(当前页) **/
	private int pageNo;
	
	/** 起始序号  */
    private int pageStart;
	
	/** pageSize:分页大小(每页数量) **/
	private int pageSize;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getUploadStartTime() {
		return uploadStartTime;
	}

	public void setUploadStartTime(String uploadStartTime) {
		this.uploadStartTime = uploadStartTime;
	}

	public String getUploadEndTime() {
		return uploadEndTime;
	}

	public void setUploadEndTime(String uploadEndTime) {
		this.uploadEndTime = uploadEndTime;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageStart() {
		return pageStart;
	}

	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
