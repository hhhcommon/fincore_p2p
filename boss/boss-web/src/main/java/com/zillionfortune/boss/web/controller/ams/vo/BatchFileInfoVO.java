package com.zillionfortune.boss.web.controller.ams.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class BatchFileInfoVO {

	
	private List<MultipartFile> fileList;

	/** 主键 */
	private Long id;

	/** 业务代码 */
	private String bizCode;

	/** 业务类型 A 准入申请，L 挂牌申请 */
	private String bizType;

	/** 文件名称 */
	private String fileName;

	/** 文件类型 */
	private String fileType;

	/** 文件展示名 */
	private String showName;

	/** 01 一审，02 二审 */
	private String status;

	/** 下载地址 */
	private String downloadUrl;

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
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

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public List<MultipartFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<MultipartFile> fileList) {
		this.fileList = fileList;
	}

	@Override
	public String toString() {
		String fileNames="";
		for (int i=0;i<this.getFileList().size();i++){
			fileNames=fileNames+","+this.getFileList().get(i).getOriginalFilename();
		}
		return "fileName=" + fileNames + " bizCode=" + this.bizCode + " bizType=" + this.bizType + " fileType=" + this.fileType + " status="
				+ this.status;
	}

}
