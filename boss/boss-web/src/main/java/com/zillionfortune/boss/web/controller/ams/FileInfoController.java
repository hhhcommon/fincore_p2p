package com.zillionfortune.boss.web.controller.ams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
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
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSException;
import com.zb.fincore.ams.facade.dto.req.FileInfoRequest;
import com.zb.fincore.ams.facade.model.SignInfoObj;
import com.zillionfortune.boss.biz.ams.FileInfoBiz;
import com.zillionfortune.boss.biz.file.FileBiz;
import com.zillionfortune.boss.biz.file.dto.FileUploadRequest;
import com.zillionfortune.boss.biz.role.RoleBiz;
import com.zillionfortune.boss.biz.role.RolePowerBiz;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.DeleteFlag;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.dal.entity.Role;
import com.zillionfortune.boss.service.role.RoleService;
import com.zillionfortune.boss.service.user.UserRoleService;
import com.zillionfortune.boss.web.controller.ams.vo.FileInfoVO;
import com.zillionfortune.boss.web.controller.ams.vo.QueryFileInfoRequestVO;
import com.zillionfortune.boss.web.controller.ams.vo.StartSignListRequestVO;
import com.zillionfortune.boss.web.controller.common.HttpSessionUtils;

/**
 * FileInfoController
 * 
 * @author litaiping
 * 
 */
@Controller
@RequestMapping(value = "/fileinfoservice")
public class FileInfoController {

	private final Logger log = LoggerFactory.getLogger(FileInfoController.class);

	@Autowired
	private FileInfoBiz fileInfoBiz;

	@Autowired
	private FileBiz fileBiz;

	@Autowired
	private HttpSessionUtils httpSessionUtils;

	private final static String tempPah = "../temp";

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private RoleBiz roleBiz;

	@Autowired
	private RolePowerBiz rolePowerBiz;

	private static int BUFFER_SIZE = 8096;// 缓冲大小

	@Autowired
	private RoleService roleService;
	
	/**
	 * 保存文件上传信息
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/savefileinfo", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse saveFileInfo(FileInfoVO vo) {
		log.info("FileInfoController.saveFileInfo.req:" + vo.toString());
		FileInfoRequest req = null;
		BaseWebResponse resp = null;
		try {
			if (vo == null) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.ILLEGAL_PARAMETER.code(), ResultCode.ILLEGAL_PARAMETER.desc());
			}

			FileUploadRequest uploadFileReq = new FileUploadRequest();
			try {
				uploadFileReq.setCreateBy(httpSessionUtils.getCuruser().getName());
			} catch (Exception e) {
				log.error("获取当前登录用户系统异常", e);
			}
			uploadFileReq.setFile(vo.getFile());
			uploadFileReq.setShowName(vo.getShowName());
			try {
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
				ServletContext servletContext = webApplicationContext.getServletContext();
				// 得到文件绝对路径
				String realPath = servletContext.getRealPath(tempPah);
				FileOutputStream fos = new FileOutputStream(new File(realPath + "/" + vo.getFile().getOriginalFilename()));
				// 获取输出流
				InputStream is = vo.getFile().getInputStream();
				int temp;
				while ((temp = is.read()) != (-1)) {
					fos.write(temp);
				}
				fos.flush();
				fos.close();
				is.close();

			} catch (FileNotFoundException e) {
				log.error("文件上传到服务器系统异常", e);
			}
			resp = fileBiz.onlyUpload(uploadFileReq);
			if (resp != null && RespCode.SUCCESS.code().equals(resp.getRespCode()) && ResultCode.SUCCESS.code().equals(resp.getResultCode())) {
				req = new FileInfoRequest();
				PropertyUtils.copyProperties(req, vo);
				try {
					req.setOriginator(String.valueOf(httpSessionUtils.getCuruser().getId()));
				} catch (Exception e) {
					log.error("获取当前登录用户系统异常", e);
					resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
				}
				req.setFileName(vo.getFile().getOriginalFilename());
				PropertyUtils.copyProperties(req, vo);
				Map<String, Object> respMap = (Map<String, Object>) resp.getData();
				req.setSourceDownloadUrl((String) respMap.get("downloadUrl"));

				resp = fileInfoBiz.saveFileInfo(req);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("FileInfoController.saveFileInfo.resp:" + JSON.toJSONString(resp));
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
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String download(String fileName, String showName,HttpServletResponse response) {
		log.info("FileInfoController.download.req:fileName = " + fileName);
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
			// 将文件名的特殊英文字符反转码,字符包含 空格 () {} []
	/*		fileName = URLEncoder.encode(showName, "UTF-8").replaceAll("\\+", "%20").replaceAll("%28", "\\(").replaceAll("%29", "\\)").replaceAll("%7B", "\\{")
					.replaceAll("%7D", "\\}").replaceAll("%5B", "\\[").replaceAll("%5D", "\\]");*/
			response.setContentType("application/octet-stream;charset=UTF-8");// 流的形式
			response.setCharacterEncoding("UTF-8");  
			showName=new String(showName.getBytes("UTF-8"),"ISO-8859-1");
			response.addHeader("Content-Disposition", "attachment;filename=" + showName);
			out = response.getOutputStream();
			byte[] buf = new byte[BUFFER_SIZE];
			int size = 0;
			while ((size = inputStream.read(buf)) != -1) {
				out.write(buf, 0, size);
			}
		} catch (OSSException e) {
			log.error(e.getMessage(), e);
			return "没有找到相应文件";
		} catch (Exception e) {
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

	private String getPath(String originalFilename) {
		String path = this.getClass().getClassLoader().getResource(tempPah).getPath();
		String fileName = originalFilename.substring(0, originalFilename.indexOf("."));
		return path + "/" + fileName + ".pdf";
	}

	/**
	 * 查询上传文件列
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/queryfileinfolist", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse queryFileInfoList(@RequestBody QueryFileInfoRequestVO vo) {

		log.info("FileInfoController.queryFileInfoList.req:" + JSON.toJSONString(vo));

		FileInfoRequest req = null;
		BaseWebResponse resp = null;

		try {
			if (vo == null) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.ILLEGAL_PARAMETER.code(), ResultCode.ILLEGAL_PARAMETER.desc());
			}
			req = new FileInfoRequest();
			PropertyUtils.copyProperties(req, vo);
			try {
				req.setOriginator(String.valueOf(httpSessionUtils.getCuruser().getId()));
			} catch (Exception e) {
				log.error("获取当前登录用户系统异常", e);
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			}
			resp = fileInfoBiz.queryFileInfoList(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("FileInfoController.queryFileInfoList.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}

	/**
	 * 删除上传文件信息
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/delfileinfobyid", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse delFileInfoById(@RequestBody FileInfoVO vo) {

		log.info("FileInfoController.delFileInfoById.req:" + JSON.toJSONString(vo));

		BaseWebResponse resp = null;

		try {
			if (vo == null || vo.getId() == null) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.ILLEGAL_PARAMETER.code(), ResultCode.ILLEGAL_PARAMETER.desc());
			}
			resp = fileInfoBiz.delFileInfoById(vo.getId());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("FileInfoController.delFileInfoById.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}

	/**
	 * 发起签章
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/startsigned", method = RequestMethod.POST)
	@ResponseBody
	public BaseWebResponse startSigned(@RequestBody StartSignListRequestVO vo) {
		log.info("FileInfoController.startSigned.req:" + JSON.toJSONString(vo));
		FileInfoRequest req = null;
		BaseWebResponse resp = null;
		try {
			if (vo == null) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.ILLEGAL_PARAMETER.code(), ResultCode.ILLEGAL_PARAMETER.desc());
			}
			List<String> list = new ArrayList<String>();
			for (SignInfoObj signInfoObj : vo.getPartyAList()) {
				list.add(signInfoObj.getRoleId());
			}
			for (SignInfoObj signInfoObj : vo.getPartyBList()) {
				list.add(signInfoObj.getRoleId());
			}
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("userId", httpSessionUtils.getCuruser().getId());
			paraMap.put("deleteFlag", DeleteFlag.EXISTS.code());
			List<Role> roleList = userRoleService.selectRoleByUserId(paraMap);
			boolean hasRole = false;
			for (int i = 0; i < roleList.size(); i++) {
				Role role = roleList.get(i);
				for (int j = 0; j < list.size(); j++) {
					if (role.getId().intValue() == Integer.valueOf(list.get(j)).intValue()) {
						hasRole = true;
						break;
					}
				}
			}
			if (!hasRole) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), "该用户没有发起签章权限");
				return resp;
			}
			req = new FileInfoRequest();
			PropertyUtils.copyProperties(req, vo);
			resp = fileInfoBiz.startSigned(req);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof BusinessException) {
				resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), e.getMessage());
			} else {
				resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
			}
		} finally {
			log.info("FileInfoController.startSigned.resp:" + JSON.toJSONString(resp));
		}
		return resp;
	}
}
