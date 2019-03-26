/*
 * Copyright (c) 2016, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.biz.file.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.zillionfortune.boss.biz.file.FileBiz;
import com.zillionfortune.boss.biz.file.dto.FileModifyRequest;
import com.zillionfortune.boss.biz.file.dto.FileQueryByPageRequest;
import com.zillionfortune.boss.biz.file.dto.FileUploadRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.FileTypeEnum;
import com.zillionfortune.boss.common.enums.HookTypeEnum;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.utils.DateUtil;
import com.zillionfortune.boss.common.utils.OssFileUtil;
import com.zillionfortune.boss.common.utils.PageBean;
import com.zillionfortune.boss.dal.entity.FileInfo;
import com.zillionfortune.boss.dal.entity.FileInfoConvert;
import com.zillionfortune.boss.service.file.FileService;

/**
 * ClassName: FileBizImpl <br/>
 * Function: 文件处理实现. <br/>
 * Date: 2016年12月20日 下午7:39:02 <br/>
 *
 * @author pengting
 * @version 
 * @since JDK 1.7
 */
@Component
public class FileBizImpl implements FileBiz {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private FileService fileService;
	
	/** accessId:阿里云ACCESS_ID **/
	@Value("${ACCESS_KEY_ID}")
	private String accessId;
	
	/** accessKey:阿里云ACCESS_KEY **/
	@Value("${ACCESS_KEY_SECRET}")
	private String accessKey;
	
	/** bucketName:阿里云BUCKET_NAME **/
	@Value("${BUCKET_NAME}")
	private String bucketName;
	
	/** ossEndpoint:阿里云OSS_ENDPOINT **/
	@Value("${OSS_ENDPOINT}")
	private String ossEndpoint;
	
	/** showFileUrl:阿里云文件访问url **/
	@Value("${SHOW_FILE_URL}")
	private String showFileUrl;
	
	/**
	 * 文件上传.
	 * @see com.zillionfortune.boss.biz.file.FileBiz#upload(com.zillionfortune.boss.biz.file.dto.FileUploadRequest)
	 */
	@Override
	public BaseWebResponse upload(FileUploadRequest req){
		BaseWebResponse resp = null;
		try {
			MultipartFile file = req.getFile();
			String fileName = generateFileName(file.getOriginalFilename());
			// 文件展示名不能重复
			if (fileService.selectByShowName(req.getShowName()) != null) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),
						ResultCode.DISCLOSURE_FILE_SHOW_NAME_IS_EXIST.code(),
						ResultCode.DISCLOSURE_FILE_SHOW_NAME_IS_EXIST.desc());
				return resp;
			}
			
			// 上传
			OssFileUtil fileUtil = new OssFileUtil(ossEndpoint, accessId, accessKey);
			fileUtil.uploadFile(bucketName, file.getInputStream(), fileName);
			
			// 将文件上传信息入库
			FileInfo fileInfo = new FileInfo();
			fileInfo.setFileName(fileName); // 文件名
			fileInfo.setShowName(req.getShowName()); // 文件展示名
			fileInfo.setDownloadUrl(showFileUrl+fileName); // 下载地址
			fileInfo.setFileType(getFileType(fileName)); // 文件类型
			fileInfo.setHookType(req.getHookType()); // 勾选类型
			fileInfo.setCreateTime(new Date()); // 创建时间
			fileInfo.setCreateBy(req.getCreateBy()); // 创建人
			fileService.add(fileInfo);
			
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("fileName", fileName);
			respMap.put("downloadUrl", fileInfo.getDownloadUrl());
			resp.setData(respMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resp = new BaseWebResponse(RespCode.FAIL.code(),"上传文件处理异常");
		} finally {
			log.info("FileBizImpl.upload.resp:" +JSONObject.toJSONString(resp) );
		}
		
		return resp;
	}
	
	/**
	 * 文件上传.
	 * @see com.zillionfortune.boss.biz.file.FileBiz#upload(com.zillionfortune.boss.biz.file.dto.FileUploadRequest)
	 */
	@Override
	public BaseWebResponse onlyUpload(FileUploadRequest req){
		log.info("FileBizImpl.upload.req："+ JSONObject.toJSONString(req.getFile().getOriginalFilename()) );
		BaseWebResponse resp = null;
		try {
			MultipartFile file = req.getFile();
			String fileName = generateFileName(file.getOriginalFilename());
			// 文件展示名不能重复
			if (fileService.selectByShowName(req.getShowName()) != null) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),
						ResultCode.DISCLOSURE_FILE_SHOW_NAME_IS_EXIST.code(),
						ResultCode.DISCLOSURE_FILE_SHOW_NAME_IS_EXIST.desc());
				return resp;
			}
			
			// 上传
			OssFileUtil fileUtil = new OssFileUtil(ossEndpoint, accessId, accessKey);
			fileUtil.uploadFile(bucketName, file.getInputStream(), fileName);
			
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("fileName", fileName);
			respMap.put("downloadUrl", showFileUrl+fileName);
			resp.setData(respMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resp = new BaseWebResponse(RespCode.FAIL.code(),"上传文件处理异常");
		} finally {
			log.info("FileBizImpl.upload.resp:" +JSONObject.toJSONString(resp) );
		}
		
		return resp;
	}
	
	@Override
	public BaseWebResponse upload(File file){
		log.info("FileBizImpl.upload.req："+ JSONObject.toJSONString(file.getName()) );
		BaseWebResponse resp = null;
		try {
			String fileName = file.getName();
			// 上传
			OssFileUtil fileUtil = new OssFileUtil(ossEndpoint, accessId, accessKey);
			fileUtil.uploadFile(bucketName, new FileInputStream(file), fileName);
			
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("fileName", fileName);
			respMap.put("downloadUrl", showFileUrl + fileName);
			resp.setData(respMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resp = new BaseWebResponse(RespCode.FAIL.code(),"上传文件处理异常");
		} finally {
			log.info("FileBizImpl.upload.resp:" +JSONObject.toJSONString(resp) );
		}
		
		return resp;
	}
	
	/**
	 * 获取Object的输入流.
	 * @see com.zillionfortune.boss.biz.file.FileBiz#download(java.lang.String)
	 */
	@Override
	public InputStream getInputStream(String fileName) {
		
		if(fileName.contains(showFileUrl)){
			fileName=fileName.substring(showFileUrl.length());
		}
		
		InputStream inputStream = null;
		try {
			OssFileUtil fileUtil = new OssFileUtil(ossEndpoint, accessId, accessKey);
			OSSClient client = fileUtil.getClient();
			// 获取Object，返回结果为OSSObject对象
			OSSObject object = client.getObject(bucketName, fileName);
			inputStream = object.getObjectContent();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
		return inputStream;
	}
	
	/**
	 * 文件信息列表查询.
	 * @see com.zillionfortune.boss.biz.file.FileBiz#queryByPage(com.zillionfortune.boss.biz.file.dto.FileQueryByPageRequest)
	 */
	@Override
	public BaseWebResponse queryByPage(FileQueryByPageRequest req) {
		log.info("FileBizImpl.queryByPage.req:" +JSONObject.toJSONString(req) );
		BaseWebResponse resp = null;
		try {
			// 设置参数
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("showName", req.getShowName()); // 文件展示名
			params.put("fileType", req.getFileType()); // 文件类型
			params.put("uploadStartTime", req.getUploadStartTime()); // 上传开始时间
			params.put("uploadEndTime", req.getUploadEndTime()); // 上传结束时间
			params.put("pageStart", req.getPageStart()); // 起始序号
			params.put("pageSize", req.getPageSize()); // 分页大小
			
			// 查询
			int totalCount = fileService.selectBySelectiveCount(params);
			List<FileInfo> list = null;
			if (totalCount > 0) {
				list = fileService.selectBySelective(params);
			}
			
			//处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("totalCount", totalCount);
			respMap.put("pageSize", req.getPageSize());
			respMap.put("pageNo", req.getPageNo());
			
			List<FileInfoConvert> fileInfoList = new ArrayList<FileInfoConvert>();
			if(totalCount > 0){
				respMap.put("totalPage", new PageBean().countPageCount(totalCount, req.getPageSize()));
				FileInfoConvert fileInfoCovert = null;
				
				for (FileInfo currentFileInfo : list) {
					fileInfoCovert = new FileInfoConvert();
					covertFileInfo(currentFileInfo, fileInfoCovert);
					fileInfoList.add(fileInfoCovert);
				}
			}
			
			respMap.put("dataList",fileInfoList);
			
			resp.setData(respMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		}
		
		log.info("FileBizImpl.queryByPage.resp:" +JSONObject.toJSONString(resp) );
		return resp;
	}
	
	/**
	 * 文件详细信息查询.
	 * @see com.zillionfortune.boss.biz.file.FileBiz#queryFileInfoDetail(java.lang.Long)
	 */
	@Override
	public BaseWebResponse queryFileInfoDetail(Long id) {
		log.info("FileBizImpl.queryFileInfoDetail.req: id= " +JSONObject.toJSONString(Long.valueOf(id)) );
		BaseWebResponse resp = null;
		try {
			// 校验文件信息是否存在
			FileInfo fileInfo = fileService.selectByPrimaryKey(id);
			if(fileInfo == null){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.CAN_NOT_QUERY_FILE_INFO.code(),
						ResultCode.CAN_NOT_QUERY_FILE_INFO.desc());
				return resp;
			}
			
			// 处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			FileInfoConvert info = new FileInfoConvert();
			covertFileInfo(fileInfo, info);
			resp.setData(info);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		} finally {
			log.info("FileBizImpl.modify.resp:" +JSONObject.toJSONString(resp) );
		}
		
		return resp;
	}
	
	/**
	 * 文件信息更新.
	 * @see com.zillionfortune.boss.biz.file.FileBiz#modify(com.zillionfortune.boss.biz.file.dto.FileModifyRequest)
	 */
	@Override
	public BaseWebResponse modify(FileModifyRequest req) {
		log.info("FileBizImpl.modify.req:" +JSONObject.toJSONString(req) );
		BaseWebResponse resp = null;
		try {
			// 校验文件信息是否存在
			FileInfo info = fileService.selectByPrimaryKey(req.getId());
			if (info == null) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.CAN_NOT_QUERY_FILE_INFO.code(),
						ResultCode.CAN_NOT_QUERY_FILE_INFO.desc());
				return resp;
			}
			
			// 更新文件信息
			FileInfo tempInfo = new FileInfo();
			tempInfo.setId(req.getId());
			tempInfo.setShowName(req.getShowName()); // 文件展示名
			tempInfo.setHookType(req.getHookType()); // 勾选类型
			tempInfo.setModifyBy(req.getModifyBy()); // 修改人
			tempInfo.setModifyTime(new Date()); // 修改时间
			fileService.update(tempInfo);
			
			// 处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		} finally {
			log.info("FileBizImpl.modify.resp:" +JSONObject.toJSONString(resp) );
		}
		
		return resp;
	}
	
	/**
	 * 文件分类统计.
	 * @see com.zillionfortune.boss.biz.file.FileBiz#countUpFile()
	 */
	@Override
	public BaseWebResponse countUpFile() {
		log.info("FileBizImpl.countUpFile.req:");
		BaseWebResponse resp = null;
		try {
			// 查询文件信息列表
			Map<String, Object> params = new HashMap<String, Object>();
			List<FileInfo> fileInfoList = fileService.selectBySelective(params);
			
			int fileTotal = 0; // 累计上传文件总数
			int pureDisclosureFileTotal = 0; // 纯披露文件总数
			int dynamicGenerationTotal = 0; // 动态生成文件总数
			int internalInformationTotal = 0; // 内部信息文件总数
			
			if (fileInfoList != null && fileInfoList.size() > 0) {
				for (FileInfo fileInfo : fileInfoList) {
					fileTotal ++;
					if (HookTypeEnum.PURE_DISCLOSURE.getCode().equals(fileInfo.getHookType())) {
						pureDisclosureFileTotal ++;
						continue;
					}
					if (HookTypeEnum.DYNAMIC_GENERATION.getCode().equals(fileInfo.getHookType())) {
						dynamicGenerationTotal ++;
						continue;
					}
					if (HookTypeEnum.INTERNAL_INFORMATION.getCode().equals(fileInfo.getHookType())) {
						internalInformationTotal ++;
					}
				}
			}
			
			resp =new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("fileTotal", fileTotal); // 累计上传文件总数
			respMap.put("pureDisclosureFileTotal", pureDisclosureFileTotal); // 纯披露文件总数
			respMap.put("dynamicGenerationTotal", dynamicGenerationTotal); // 动态生成文件总数
			respMap.put("internalInformationTotal", internalInformationTotal); // 内部信息文件总数
			resp.setData(respMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
            resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		}
		
		log.info("FileBizImpl.countUpFile.resp:" + JSON.toJSONString(resp));
		return resp;
	}
	
	/**
	 * 文件删除.
	 * @see com.zillionfortune.boss.biz.file.FileBiz#delete(java.lang.Long)
	 */
	@Override
	public BaseWebResponse delete(Long id, String fileName) {
		log.info("FileBizImpl.delete.req: id= " +JSONObject.toJSONString(Long.valueOf(id)) );
		BaseWebResponse resp = null;
		try {
			// 校验文件信息是否存在
			FileInfo info = fileService.selectByPrimaryKey(id);
			if(info == null){
				resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.CAN_NOT_QUERY_FILE_INFO.code(),
						ResultCode.CAN_NOT_QUERY_FILE_INFO.desc());
				return resp;
			}
			
			// 从数据库删除文件信息
			fileService.delete(id);
			
			// 从阿里云删除上传的文件
			try {
				OssFileUtil fileUtil = new OssFileUtil(ossEndpoint, accessId, accessKey);
				fileUtil.deleteFile(bucketName, fileName);
			} catch (FileNotFoundException e) {
				;
			}
			
			// 处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		} finally {
			log.info("FileBizImpl.delete.resp:" +JSONObject.toJSONString(resp) );
		}
		
		return resp;
	}
	
	/**
	 * 文件删除.
	 * @see com.zillionfortune.boss.biz.file.FileBiz#delete(java.lang.Long)
	 */
	@Override
	public BaseWebResponse delete(String fileName) {
		log.info("FileBizImpl.delete.req: fileName= " +fileName );
		BaseWebResponse resp = null;
		try {
			// 从阿里云删除上传的文件
			OssFileUtil fileUtil = new OssFileUtil(ossEndpoint, accessId, accessKey);
			fileUtil.deleteFile(bucketName, fileName);
			// 处理反馈结果
			resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			resp = new BaseWebResponse(RespCode.FAIL.code(),RespCode.FAIL.desc());
		} finally {
			log.info("FileBizImpl.delete.resp:" +JSONObject.toJSONString(resp) );
		}
		
		return resp;
	}
	
	
	/**
	 * generateFileName:生成上传用的文件名. <br/>
	 *
	 * @param fileName
	 * @return
	 */
	private String generateFileName(String fileName) {
		String uuId = UUID.randomUUID().toString().trim().replaceAll("-", "");
		SimpleDateFormat yyyyMMddHHmmsss = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		StringBuffer buffer = new StringBuffer();
		buffer.append(uuId.substring(2, 9));
		buffer.append("-");
        buffer.append(yyyyMMddHHmmsss.format(new Date()));
        buffer.append("-");
        buffer.append(fileName);
        return buffer.toString();
	}
	
	/**
	 * getFileType:获取文件类型. <br/>
	 *
	 * @param fileName
	 * @return
	 */
	private String getFileType(String fileName) {
		String fileType = "";
		if (fileName.endsWith(".pdf")) {
			fileType = FileTypeEnum.PDF.getCode();
			return fileType;
		}
		
		if (fileName.endsWith(".doc")
				|| fileName.endsWith(".docx")) {
			fileType = FileTypeEnum.WORD.getCode();
			return fileType;
		}
		return fileType;
	}
	
	/**
	 * covertFileInfo:文件信息结果转换. <br/>
	 *
	 * @param info
	 * @param infoConvert
	 */
	private void covertFileInfo (FileInfo info, FileInfoConvert infoConvert) {
		infoConvert.setCreateBy(info.getCreateBy());
		infoConvert.setCreateTime(DateUtil.dateToDateString(info.getCreateTime()));
		infoConvert.setDownloadUrl(info.getDownloadUrl());
		infoConvert.setExtInfo(info.getExtInfo());
		infoConvert.setFileName(info.getFileName().substring(26));
		infoConvert.setFileNameForDownLoad(info.getFileName());
		infoConvert.setFileType(info.getFileType());
		infoConvert.setHookType(info.getHookType());
		infoConvert.setId(info.getId());
		infoConvert.setModifyBy(info.getModifyBy());
		infoConvert.setModifyTime(DateUtil.dateToDateString(info.getModifyTime()));
		infoConvert.setShowName(info.getShowName());
		infoConvert.setRelationProduct(info.getRelationProduct());
	}


    /**
     * 文件上传.
     * @see com.zillionfortune.boss.biz.file.FileBiz#upload(com.zillionfortune.boss.biz.file.dto.FileUploadRequest)
     */
    @Override
    public BaseWebResponse uploadAssetFile(MultipartFile file){
        BaseWebResponse resp = null;
        try {
            log.info("FileBizImpl.uploadAssetFile.fileName:" +JSONObject.toJSONString(file.getOriginalFilename()));

            String fileName = file.getOriginalFilename();
            // 文件名

            // 上传
            OssFileUtil fileUtil = new OssFileUtil(ossEndpoint, accessId, accessKey);
            String result = fileUtil.uploadFile(bucketName, file.getInputStream(), fileName);

            if(!result.equals("")){
                resp = new BaseWebResponse(RespCode.SUCCESS.code(),ResultCode.SUCCESS.code(),ResultCode.SUCCESS.desc());
                Map<String, Object> respMap = new HashMap<String, Object>();
                respMap.put("fileName", fileName);
                resp.setData(respMap);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            resp = new BaseWebResponse(RespCode.FAIL.code(),"上传文件处理异常");
        } finally {
            log.info("FileBizImpl.upload.resp:" +JSONObject.toJSONString(resp) );
        }

        return resp;
    }
	
	public static void main(String[] args) {
		String uuId = UUID.randomUUID().toString().trim().replaceAll("-", "");
		System.out.println(uuId);
		SimpleDateFormat yyyyMMddHHmmsss = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		StringBuffer buffer = new StringBuffer();
		buffer.append(uuId.substring(2, 9));
		buffer.append("-");
        buffer.append(yyyyMMddHHmmsss.format(new Date()));
        buffer.append("-");
        buffer.append("文件.doc");
        String str = buffer.toString();
        System.out.println(str);
        System.out.println(str.substring(26));
	}

}
