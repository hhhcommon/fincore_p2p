/**
 * 
 */
package com.zb.p2p.customer.service.impl;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zb.qjs.common.util.OrderNoGenerator;
import com.zb.p2p.customer.api.entity.CustomerDetail;
import com.zb.p2p.customer.api.entity.orc.ORCInfo;
import com.zb.p2p.customer.api.entity.orc.ORCReq;
import com.zb.p2p.customer.common.enums.AppCodeEnum;
import com.zb.p2p.customer.common.exception.AppException;
import com.zb.p2p.customer.common.util.HttpClientUtils;
import com.zb.p2p.customer.common.util.ImageUtils;
import com.zb.p2p.customer.common.util.JsonUtil;
import com.zb.p2p.customer.common.util.MD5Util;
import com.zb.p2p.customer.common.util.OssFileUtil;
import com.zb.p2p.customer.dao.domain.CustomerInfo;
import com.zb.p2p.customer.service.InfoService;
import com.zb.p2p.customer.service.OCRFileService;
import com.zb.p2p.customer.service.bo.PaymentResponse;

/**
 * 上传文件
 * @author guolitao
 *
 */
@Service
public class OCRFileServiceImpl implements OCRFileService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private InfoService infoService;
	@Value("${env.paymentHost}")
	private String paymentHost;
	/** accessId:阿里云ACCESS_ID **/
	@Value("${env.accessKey}")
	private String accessId;
	
	/** accessKey:阿里云ACCESS_KEY **/
	@Value("${env.accessSecret}")
	private String accessKey;
	
	/** bucketName:阿里云BUCKET_NAME **/
	@Value("${env.ossBucketName}")
	private String bucketName;
	
	/** ossEndpoint:阿里云OSS_ENDPOINT **/
	@Value("${env.ossEndPoint}")
	private String ossEndpoint;
	
	/** ossEndpoint:阿里云OSS_ENDPOINT **/
	@Value("${env.ossDir}")
	private String ossDir;
	
	/* (non-Javadoc)
	 * @see com.zb.p2p.customer.service.OCRFileService#uploadFile(org.springframework.web.multipart.MultipartFile, java.lang.String)
	 */
	@Override
	public ORCReq uploadFile(String file, String customerId,String fileType) {
		log.info("上传"+customerId+" "+fileType+"图片开始...");
		try {
			String content = file.split(",")[1];
			long fileSize = content.length();
			if(fileSize > 4 * 1024 * 1024){
				throw new AppException(AppCodeEnum._A305_ORC_IMG_TOO_BIG);
			}
			String fileName = MD5Util.encrypt(customerId+fileType)+".jpg";
			String objKey = ossDir+"/"+fileName;
			// 上传
			OssFileUtil fileUtil = new OssFileUtil(ossEndpoint, accessId, accessKey);
			fileUtil.uploadFile(bucketName, ImageUtils.decodeBase64ToStream(content), objKey);
			log.info("上传文件成功结束");
			ORCReq req = new ORCReq();
			req.setCustomerId(customerId);
			req.setFileType(fileType);
			req.setBucketName(ossDir);
			req.setObjectKey(fileName);
			return req;
		} catch (Exception e) {
			log.error("上传文件失败！", e);
		}
		return null;
	}

	@Override
	public ORCInfo certificateScanning(String frontFile, String backFile, String customerId) {
		ORCReq frontReq = uploadFile(frontFile, customerId, "front");
		if(frontReq == null){
			throw new AppException(AppCodeEnum._A301_ORC_UPLOAD_FAIL);
		}
		ORCReq backReq = uploadFile(backFile, customerId, "back");
		if(backReq == null){
			throw new AppException(AppCodeEnum._A301_ORC_UPLOAD_FAIL);
		}
		ORCInfo front = certiScan(frontReq);
		if(front != null){
			ORCInfo back = certiScan(backReq);
			if(back != null){
				CustomerDetail cd = infoService.getPerDetail(Long.valueOf(customerId));
				if(StringUtils.isNotBlank(front.getIdCardNo()) && front.getIdCardNo().equals(cd.getIdCardNo())){
					CustomerInfo ci = new CustomerInfo();
					ci.setCustomerId(Long.valueOf(customerId));
					ci.setIsActiveEAccount(1);
					infoService.updatePer(ci);
				}else{
					throw new AppException(AppCodeEnum._A302_ORC_CERTIFY_FAIL);
				}
				return back;
			}
		}
		return null;
	}
	/**
	 * 调用接口
	 * @param req
	 * @return
	 */
	private ORCInfo certiScan(ORCReq req){
		String url = paymentHost + "/certificateScanningYST";
		JSONObject jsonReq = new JSONObject();
		String response = null;
		try {
			String orderNo = OrderNoGenerator.generate(Long.valueOf(req.getCustomerId()));//订单号
            Date orderDate = new Date();
            jsonReq.put("sourceId", "QJS_YST");//系统来源
            jsonReq.put("orderNo", orderNo);//订单号
            jsonReq.put("orderTime", orderDate);//订单时间
            jsonReq.put("memberId", req.getCustomerId());//会员id
            jsonReq.put("idCardSide", req.getFileType());//证件正反面
            jsonReq.put("imagePath", req.getBucketName());//订单号
            jsonReq.put("imageName", req.getObjectKey());//订单时间
			response = HttpClientUtils.doPost(url, jsonReq.toString());
			log.info(url+"请求为"+jsonReq.toString()+",响应为"+response);
		} catch (IOException e) {
			log.error("请求"+url+"报错!参数为"+jsonReq.toString(),e);
			throw new AppException(AppCodeEnum._A306_ORC_CALL_FAILED);
		}
		PaymentResponse pr = (PaymentResponse)JsonUtil.getObjectByJsonStr(response, PaymentResponse.class);
		if(pr != null){
			if(AppCodeEnum._0000_SUCCESS.getCode().equals(pr.getCode())){
				//提取属性
				ORCInfo ret = new ORCInfo();
				JSONObject job = (JSONObject)pr.getData();
				if(job != null){
					ret.setAddress(job.getString("address"));
					ret.setBirth(job.getString("birth"));
					ret.setExpiryDate(job.getString("expiryDate"));
					ret.setGender(job.getString("gender"));
					ret.setIdCardNo(job.getString("idCardNo"));
					//ret.setIdCardType(job.getString("idCardType"));
					ret.setIssuingAuthority(job.getString("issuingAuthority"));
					ret.setIssuingDate(job.getString("issuingDate"));
					ret.setNation(job.getString("nation"));
					ret.setRealName(job.getString("realName"));
				}
				return ret;
			}else{
				throw new AppException(AppCodeEnum._A302_ORC_CERTIFY_FAIL);
			}
		}else{
			throw new AppException(AppCodeEnum._A306_ORC_CALL_FAILED);
		}
	}

}
