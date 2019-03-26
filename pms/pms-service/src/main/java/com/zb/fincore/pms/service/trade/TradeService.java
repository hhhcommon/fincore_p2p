package com.zb.fincore.pms.service.trade;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.p2p.match.api.req.AssetMatchReq;


/**
 * 功能: 产品缓存接口类
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/6 0006 16:58
 * 版本: V1.0
 */
public interface TradeService {
	
	/**
     * 资产预匹配
     * @return
     */
	BaseResponse assetMatchHttp(AssetMatchReq assetMatchReq) throws Exception;


}
