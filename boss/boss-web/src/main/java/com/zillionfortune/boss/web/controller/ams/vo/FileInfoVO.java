package com.zillionfortune.boss.web.controller.ams.vo;

import org.springframework.web.multipart.MultipartFile;

public class FileInfoVO {

	
	private MultipartFile file;

	/** 主键 */
	private Long id;

	/** 文件名称 */
	private String fileName;

	/** 文件类型 */
	private String fileType;

	/** 文件展示名 */
	private String showName;

	/** 下载地址 */
	private String sourceDownloadUrl;

	
	public MultipartFile getFile() {
		return file;
	}


	public void setFile(MultipartFile file) {
		this.file = file;
	}


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


	public String getFileType() {
		return fileType;
	}


	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	public String getShowName() {
		return showName;
	}


	public void setShowName(String showName) {
		this.showName = showName;
	}


	public String getSourceDownloadUrl() {
		return sourceDownloadUrl;
	}


	public void setSourceDownloadUrl(String sourceDownloadUrl) {
		this.sourceDownloadUrl = sourceDownloadUrl;
	}


	@Override
	public String toString() {
		return "fileName=" + this.getFile().getOriginalFilename()  + " fileType=" + this.fileType ;
	}

}
