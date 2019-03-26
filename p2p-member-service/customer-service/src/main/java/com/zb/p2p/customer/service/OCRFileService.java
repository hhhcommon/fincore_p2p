/**
 * 
 */
package com.zb.p2p.customer.service;

import com.zb.p2p.customer.api.entity.orc.ORCInfo;
import com.zb.p2p.customer.api.entity.orc.ORCReq;

/**
 * 上传文件
 * @author guolitao
 *
 */
public interface OCRFileService {

	/**
	 * 上传文件
	 * @param file
	 * @param customerId
	 * @param fileType
	 * @return
	 */
	ORCReq uploadFile(String file,String customerId,String fileType);
	
	/**
	 * 文件扫描
	 * @param frontFile
	 * @param backFile
	 * @param customerId
	 * @return
	 */
	ORCInfo certificateScanning(String frontFile,String backFile,String customerId);
}
