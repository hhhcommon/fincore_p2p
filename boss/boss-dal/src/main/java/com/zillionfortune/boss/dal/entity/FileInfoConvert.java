package com.zillionfortune.boss.dal.entity;

public class FileInfoConvert {
    /** id:主键Id **/
    private Long id;

    /** fileName:文件名 **/
    private String fileName;
    
    /** fileNameForDownLoad:下载用文件名 **/
    private String fileNameForDownLoad;

    /** showName:文件展示名 **/
    private String showName;

    /** downloadUrl:下载地址 **/
    private String downloadUrl;

    /** fileType:文件类型 **/
    private String fileType;

    /** hookType:勾选类型 **/
    private String hookType;

    /** createTime:创建时间 **/
    private String createTime;

    /** modifyTime:修改时间 **/
    private String modifyTime;

    /** createBy:创建人 **/
    private String createBy;

    /** modifyBy:修改人 **/
    private String modifyBy;

    /** extInfo:扩展信息 **/
    private String extInfo;
    
    /** relationProduct:披露文件关联的所有产品 **/
    private String relationProduct;
    
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
        this.fileName = fileName == null ? null : fileName.trim();
    }
    
    public String getFileNameForDownLoad() {
		return fileNameForDownLoad;
	}

	public void setFileNameForDownLoad(String fileNameForDownLoad) {
		this.fileNameForDownLoad = fileNameForDownLoad;
	}

	public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName == null ? null : showName.trim();
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl == null ? null : downloadUrl.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public String getHookType() {
        return hookType;
    }

    public void setHookType(String hookType) {
        this.hookType = hookType == null ? null : hookType.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo == null ? null : extInfo.trim();
    }

	public String getRelationProduct() {
		return relationProduct;
	}

	public void setRelationProduct(String relationProduct) {
		this.relationProduct = relationProduct;
	}
}