package com.zillionfortune.boss.biz.ams;

import com.zb.fincore.ams.facade.dto.req.FileInfoRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

public interface FileInfoBiz {
	/**
	 * 保存文件上传信息
	 * 
	 * @param req
	 * @return
	 */
	BaseWebResponse saveFileInfo(FileInfoRequest req);

	/**
	 * 查询上传文件列
	 * 
	 * @param req
	 * @return
	 */
	BaseWebResponse queryFileInfoList(FileInfoRequest req);

	/**
	 * delFileInfoById
	 * 
	 * @param id
	 * @return
	 */
	BaseWebResponse delFileInfoById(Long id);
	
	BaseWebResponse startSigned(FileInfoRequest req);

}
