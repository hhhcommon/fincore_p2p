package com.zb.fincore.pms.service.txs;


import com.zb.fincore.pms.common.dto.BaseResponse;

/**
 * 【p2p】 唐小僧系统服务接口 <br/>
 * Date: 2018年3月6日 下午5:40:44 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version
 * @since JDK 1.7
 */
public interface TXSService {

	/**
	 * 通知唐小僧同步产品状态 <br/>
	 *
	 * @param productCodes
	 * @throws Exception
	 */
	BaseResponse syncStatusNoticeHttp(String productCodes) throws Exception;

}
