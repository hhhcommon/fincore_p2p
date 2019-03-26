/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.web.controller.file.check;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.zillionfortune.boss.common.enums.FileTypeEnum;
import com.zillionfortune.boss.common.enums.HookTypeEnum;
import com.zillionfortune.boss.common.exception.BusinessException;
import com.zillionfortune.boss.web.controller.file.vo.DeleteFileVo;
import com.zillionfortune.boss.web.controller.file.vo.FileModifyVo;
import com.zillionfortune.boss.web.controller.file.vo.FileQueryByPageVo;
import com.zillionfortune.boss.web.controller.file.vo.FileUploadVo;
import com.zillionfortune.boss.web.controller.file.vo.QueryFileInfoDetailVo;

/**
 * ClassName: FileParameterChecker <br/>
 * Function: 文件处理参数校验. <br/>
 * Date: 2017年5月16日 下午5:51:33 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Component
public class FileParameterChecker {

	/**
	 * checkFileUpLoadRequest:文件上传相关参数校验. <br/>
	 *
	 * @param req
	 * @throws Exception
	 */
	public void checkFileUploadRequest(FileUploadVo req) throws Exception {

		if (req == null) {
			throw new BusinessException("请求对象不能为空");
		}
		
		if (req.getFile() == null) {
			throw new BusinessException("待上传的文件不能为空");
		}
		
		if (StringUtils.isBlank(req.getHookType())) {
			throw new BusinessException("勾选类型不能为空");
		}
			
		if (StringUtils.isBlank(req.getShowName())) {
			throw new BusinessException("文件展示名不能为空");
		}
		
		// 判断勾选类型是否在枚举之列
		if (!(HookTypeEnum.PURE_DISCLOSURE.getCode().equals(req.getHookType())
				|| HookTypeEnum.DYNAMIC_GENERATION.getCode().equals(req.getHookType())
				|| HookTypeEnum.INTERNAL_INFORMATION.getCode().equals(req.getHookType()))) {
			throw new BusinessException("勾选类型值不在枚举之列");
		}
	}
	
	/**
	 * checkFileQueryByPageRequest:文件列表查询相关参数校验. <br/>
	 *
	 * @param req
	 * @throws Exception
	 */
	public void checkFileQueryByPageRequest(FileQueryByPageVo req) throws Exception {

		if (req == null) {
			throw new BusinessException("请求对象不能为空");
		}		
		
		// 判断文件类型是否在枚举之列
		if (StringUtils.isNotBlank(req.getFileType())) {
			if (!(FileTypeEnum.PDF.getCode().equals(req.getFileType())
					|| FileTypeEnum.WORD.getCode().equals(req.getFileType()))) {
				throw new BusinessException("文件类型值不在枚举之列");
			}
		}
	}
	
	/**
	 * checkQueryFileInfoDetailRequest:文件列表查询相关参数校验. <br/>
	 *
	 * @param req
	 * @throws Exception
	 */
	public void checkQueryFileInfoDetailRequest(QueryFileInfoDetailVo req) throws Exception {

		if (req == null) {
			throw new BusinessException("请求对象不能为空");	
		}		
		
		if (req.getId() == null) {
				throw new BusinessException("文件信息Id不能为空");
		}
	}
	
	/**
	 * checkFileModifyRequest:文件信息更新相关参数校验. <br/>
	 *
	 * @param req
	 * @throws Exception
	 */
	public void checkFileModifyRequest(FileModifyVo req) throws Exception {

		if (req == null) {
			throw new BusinessException("请求对象不能为空");
		}

		if (req.getId() == null) {
			throw new BusinessException("文件信息Id不能为空");
		}
	}
	
	/**
	 * checkDeleteFileRequest:文件删除相关参数校验. <br/>
	 *
	 * @param req
	 * @throws Exception
	 */
	public void checkDeleteFileRequest(DeleteFileVo req) throws Exception {

		if (req == null) {
			throw new BusinessException("请求对象不能为空");
		}

		if (req.getId() == null) {
			throw new BusinessException("文件信息Id不能为空");
		}
		
		if (StringUtils.isBlank(req.getFileName())) {
			throw new BusinessException("文件名不能为空");
		}
	}
}
