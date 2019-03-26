/**
 * 
 */
package com.zb.p2p.customer.api.entity.orc;

/**
 * ORC证件扫描请求
 * @author guolitao
 *
 */

public class ORCReq {
	
	private String bucketName;//bucketName，oss使用
	private  String objectKey;//对象key，oss使用
	private String customerId;//会员ID
	private String fileType;//front-正面，back-反面
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getObjectKey() {
		return objectKey;
	}
	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
		
}
