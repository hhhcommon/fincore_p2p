package com.zb.fincore.pms.service.order;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.zb.fincore.pms.common.dto.BaseResponse;

/**
 * 【p2p】 订单系统服务接口 <br/>
 * Date: 2018年3月6日 下午5:40:44 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version
 * @since JDK 1.7
 */
public interface OrderService {

//	/**
//     * 日切产品售罄通知接口 V2.0
//     * 目的：但产品下线、上线、募集结束时，同步产品状态，已达到页面显示
//     *
//     * @return
//     */
//	void tradeNotifyOrderHttp(List<String> productCodes) throws Exception;

	/**
	 * 查询昨日可用复投金额 V3.0. <br/>
	 *
	 * @param reqDate 获取金额时间：YYYY-MM-dd HH:mm:ss
	 * @return
	 * @throws Exception
	 */
	BigDecimal queryAutoInvestAmtHttp(String reqDate) throws Exception;

	/**
	 * 自动产品开放时通知订单 V3.0 <br/>
	 *
	 * @param productCode
	 * @throws Exception
	 */
	BaseResponse newProductHttp(String productCode) throws Exception;


}
