/*
 * Copyright (c) 2016, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 *
 *
 */
package com.zillionfortune.boss.web.controller.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSException;
import com.zillionfortune.boss.biz.file.FileBiz;
import com.zillionfortune.boss.biz.file.dto.FileModifyRequest;
import com.zillionfortune.boss.biz.file.dto.FileQueryByPageRequest;
import com.zillionfortune.boss.biz.file.dto.FileUploadRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.common.utils.PageBean;
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;
import com.zillionfortune.boss.web.controller.file.check.FileParameterChecker;
import com.zillionfortune.boss.web.controller.file.vo.DeleteFileVo;
import com.zillionfortune.boss.web.controller.file.vo.FileModifyVo;
import com.zillionfortune.boss.web.controller.file.vo.FileQueryByPageVo;
import com.zillionfortune.boss.web.controller.file.vo.FileUploadVo;
import com.zillionfortune.boss.web.controller.file.vo.QueryFileInfoDetailVo;

/**
 * ClassName: FileController <br/>
 * Function: 文件处理. <br/>
 * Date: 2017年5月16日 下午4:30:27 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/disclosureService")
public class FileController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FileParameterChecker parameterChecker;
	
	@Autowired
	private FileBiz fileBiz;
	
	@Autowired
	private HttpSessionUtils httpSessionUtils;
	
	private static int BUFFER_SIZE = 8096;// 缓冲大小
	
	/**
	 * upload:文件上传. <br/>
	 * 
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public BaseWebResponse upload(FileUploadVo vo) {
		log.info("FileController.upload.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null;
		FileUploadRequest req = null;
		try {
			// step1: 参数校验
			parameterChecker.checkFileUploadRequest(vo);

			// step2: 参数对象封装
			req = new FileUploadRequest();
			PropertyUtils.copyProperties(req, vo);
			if (httpSessionUtils.getCuruser() != null) {
				req.setCreateBy(httpSessionUtils.getCuruser().getName());
			}

			// step3: 调用文件上传
			resp = fileBiz.upload(req);
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("FileController.upload.resp:" + JSON.toJSONString(resp));
		}

		return resp;
	}
	
	/**
	 * download:文件下载. <br/>
	 *
	 * @param fileName
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/download",method=RequestMethod.GET)
	public String download(String fileName, HttpServletResponse response) {
		log.info("FileController.download.req:fileName = " + fileName);
		OutputStream out = null;
		InputStream inputStream = null;
		try {
			// step1: 参数校验
			if (fileName == null) {
				return "文件名不能为空";
			}
			
			// step2: 调用文件下载
			// 获取文件下载输入流
			inputStream = fileBiz.getInputStream(fileName);
			if (inputStream == null) {
				return "没有找到相应文件";
			}
			// 将文件名的特殊英文字符反转码,字符包含        空格 () {} []
			fileName = URLEncoder.encode(fileName.substring(26), "UTF-8").replaceAll("\\+", "%20")
					.replaceAll("%28", "\\(").replaceAll("%29", "\\)").replaceAll("%7B", "\\{").replaceAll("%7D", "\\}")
					.replaceAll("%5B", "\\[").replaceAll("%5D", "\\]");
			response.setContentType("application/octet-stream;charset=UTF-8");//流的形式
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			out = response.getOutputStream();
			byte[] buf = new byte[BUFFER_SIZE];   
			int size = 0;  
			while ((size=inputStream.read(buf)) != -1) {
				out.write(buf, 0, size);
			}
		} catch (OSSException e) {
			log.error(e.getMessage(), e);
			return "没有找到相应文件";
		}catch (Exception e) {
			log.error("文件下载失败");
			log.error(e.getMessage(), e);
			return "文件下载失败";
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				log.error("文件下载失败");
				log.error(e.getMessage(), e);
				return "文件下载失败";
			}
		}
		
		return null;
	}
	
	/**
	 * querybypage:文件列表查询. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/querybypage",method=RequestMethod.POST)
	public BaseWebResponse queryByPage(@RequestBody FileQueryByPageVo vo) {
		log.info("FileController.queryByPage.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null;
		FileQueryByPageRequest req = null;
		try {
			// step1: 参数校验
			parameterChecker.checkFileQueryByPageRequest(vo);

			// step2: 参数对象封装
			req = new FileQueryByPageRequest();
			PropertyUtils.copyProperties(req, vo);
			PageBean pageBean = new PageBean(vo.getPageNo(), vo.getPageSize());
			req.setPageStart(pageBean.getPageStart());

			// step3: 调用文件列表查询
			resp = fileBiz.queryByPage(req);
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("FileController.queryByPage.resp:" + JSON.toJSONString(resp));
		}

		return resp;
	}
	
	/**
	 * queryFileInfoDetail:文件详细信息查询. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryfileinfodetail",method=RequestMethod.POST)
	public BaseWebResponse queryFileInfoDetail(@RequestBody QueryFileInfoDetailVo vo) {
		log.info("FileController.queryFileInfoDetail.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null;
		try {
			// step1: 参数校验
			parameterChecker.checkQueryFileInfoDetailRequest(vo);

			// step2: 调用文件详细信息查询
			resp = fileBiz.queryFileInfoDetail(vo.getId());
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("FileController.queryFileInfoDetail.resp:" + JSON.toJSONString(resp));
		}

		return resp;
	}
	
	/**
	 * modify:文件信息更新. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public BaseWebResponse modify(@RequestBody FileModifyVo vo) {
		log.info("FileController.modify.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null;
		FileModifyRequest req = null;
		try {
			// step1: 参数校验
			parameterChecker.checkFileModifyRequest(vo);

			// step2: 参数对象封装
			req = new FileModifyRequest();
			req.setId(vo.getId()); // 文件主键Id
			req.setShowName(vo.getShowName()); // 文件展示名
			req.setHookType(vo.getHookType()); // 勾选类型
			if (httpSessionUtils.getCuruser() != null) {
				req.setModifyBy(httpSessionUtils.getCuruser().getName());
			}

			// step3: 调用文件列表查询
			resp = fileBiz.modify(req);
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("FileController.modify.resp:" + JSON.toJSONString(resp));
		}

		return resp;
	}
	
	/**
	 * countUpFile:文件分类统计. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/countupfile", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse countUpFile() {
		log.info("ProductController.countUpFile.req:");

		BaseWebResponse resp = null;
		try {
			// 调用文件分类统计
			resp = fileBiz.countUpFile();
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("ProductController.countUpFile.resp:" + JSON.toJSONString(resp));
		}

		return resp;

	}
	
	/**
	 * delete:文件删除. <br/>
	 *
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public BaseWebResponse delete(@RequestBody DeleteFileVo vo) {
		log.info("FileController.delete.req:" + JSON.toJSONString(vo));
		
		BaseWebResponse resp = null;
		try {
			// step1: 参数校验
			parameterChecker.checkDeleteFileRequest(vo);

			// step2: 调用文件删除
			resp = fileBiz.delete(vo.getId(), vo.getFileName());
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			if (e instanceof BusinessException) {

				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());

			} else {

				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}

		} finally {
			log.info("FileController.delete.resp:" + JSON.toJSONString(resp));
		}

		return resp;
	}
	
}
