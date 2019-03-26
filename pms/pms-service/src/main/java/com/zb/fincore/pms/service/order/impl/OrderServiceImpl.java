package com.zb.fincore.pms.service.order.impl;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zb.fincore.common.redis.RedisLock;
import com.zb.fincore.common.redis.RedisManager;
import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.enums.InterfaceRetryBusinessTypeEnum;
import com.zb.fincore.pms.common.enums.InterfaceRetryStatusEnum;
import com.zb.fincore.pms.common.exception.BusinessException;
import com.zb.fincore.pms.common.utils.HttpClientUtil;
import com.zb.fincore.pms.common.utils.RandomUtil;
import com.zb.fincore.pms.service.SequenceService;
import com.zb.fincore.pms.service.dal.model.InterfaceRetry;
import com.zb.fincore.pms.service.order.OrderService;
import com.zb.fincore.pms.service.product.InterfaceRetryService;

/**
 * 【p2p】 订单系统服务接口实现类 <br/>
 * Date: 2018年3月6日 下午5:40:44 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version
 * @since JDK 1.7
 */
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

//    /** V2.0 日切产品售罄url */
//    @Value("${p2p_order_sell_out_notify}")
//	private String p2pOrderSellOutNotifyUrl;

    /** V3.0 获取自动投资金额（昨日可用的复投金额）url */
    @Value("${p2p_order_query_auto_invest_amt}")
    private String p2pOrderQueryAutoInvestAmtUrl;

    /** V3.0 自动产品开放时，通知订单url */
    @Value("${p2p_order_new_product}")
    private String p2pOrderNewProductUrl;

    @Autowired
    protected RedisManager redisManager;

    @Autowired
    private InterfaceRetryService interfaceRetryService;

    @Autowired
    private SequenceService sequenceService;


//	@Override
//	public void tradeNotifyOrderHttp(List<String> productCodes) throws Exception {
//	    try {
//	    	logger.info("调用【订单系统-日切产品售罄】接口请求参数：{}", JSONObject.toJSONString(productCodes));
//	    	String respContent = HttpClientUtil.sendPostRequest(p2pOrderSellOutNotifyUrl, JSONObject.toJSONString(productCodes));
//	        logger.info("调用【订单系统-日切产品售罄】接口响应参数：{}", respContent);
//	        // 将json字符创转换成json对象
//	        JSONObject obj = JSON.parseObject(respContent);
//	        // 判断远程URl调用是否成功
//	        String notifyRespCode = obj.getString("code");
//	        String notifyMessage = obj.getString("msg");
//	        if (StringUtils.isNotEmpty(notifyRespCode) && !Constants.SUCCESS_RESP_CODE.equals(notifyRespCode)) {
//	            logger.error("调用【订单系统-日切产品售罄】接口响应报文：{}", notifyMessage);
//	            throw new BusinessException(Constants.FAIL_RESP_CODE, "调用【订单系统-日切产品售罄】接口错误:" + notifyMessage);
//	        }
//
//	    } catch (Exception e) {
//            if (e instanceof BusinessException) {
//                BusinessException be = (BusinessException) e;
//                throw new BusinessException(be.getResultCode(), be.getResultMsg());
//            } else {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用【订单系统-日切产品售罄】接口失败", e);
//            }
//        }
//	}

    public String getSerialNumber () throws Exception{
        String result = null;
        //加Redis锁
        RedisLock redisLock = new RedisLock(redisManager,  "product_notic_trade_sale_out");//Constants.PRODUCT_STOCK_CHANGE_LOCK_PREFIX
        boolean locked = redisLock.tryLock(3000);
        if (!locked) {
            String errorMsg = "【调用交易日切售罄接口】获取redis分布式锁失败!";
            logger.error(errorMsg);
            throw new BusinessException(Constants.FAIL_RESP_CODE, errorMsg);
        }
        try {
            result = RandomUtil.getRandomFileName();
        } catch (Exception e) {
            throw e;
        } finally {
            redisLock.unlock();
        }
        return result;
    }

	@Override
	public BigDecimal queryAutoInvestAmtHttp(String reqDate) throws Exception {
        BigDecimal result = BigDecimal.ZERO;
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
	    	paramMap.put("reqDate", reqDate);
	    	logger.info("调用【订单系统-获取自动投资金额】接口请求参数：{}", JSONObject.toJSONString(paramMap));
	    	String respContent = HttpClientUtil.sendPostRequest(p2pOrderQueryAutoInvestAmtUrl, JSONObject.toJSONString(paramMap));
	        logger.info("调用【订单系统-获取自动投资金额】接口响应参数：{}", respContent);
	        // 将json字符创转换成json对象
	        JSONObject obj = JSON.parseObject(respContent);
	        // 判断远程URl调用是否成功
	        String notifyRespCode = obj.getString("code");
	        String notifyMessage = obj.getString("msg");
	        if (StringUtils.isNotEmpty(notifyRespCode) && !Constants.SUCCESS_RESP_CODE.equals(notifyRespCode)) {
	            logger.error("调用【订单系统-获取自动投资金额】接口响应报文：{}", notifyMessage);
	            throw new BusinessException(Constants.FAIL_RESP_CODE, "调用【订单系统-获取自动投资金额】接口错误:" + notifyMessage);
	        }
	        String data = obj.getString("data");
	        obj= JSON.parseObject(data);
	        String investAmt = obj.getString("investAmt");
	        result = StringUtils.isBlank(investAmt)?result:new BigDecimal(investAmt);
	    } catch (Exception e) {
            if (e instanceof BusinessException) {
                BusinessException be = (BusinessException) e;
                throw new BusinessException(be.getResultCode(), be.getResultMsg());
            } else {
                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用【订单系统-获取自动投资金额】接口失败", e);
            }
        }
		return result;
	}

	@Override
	public BaseResponse newProductHttp(String productCode) throws Exception {
        BaseResponse baseResp = BaseResponse.build();
        String errorMsg = null;
	    try {
	    	Map<String, String> paramMap = new HashMap<String, String>();
	    	paramMap.put("productCode", productCode);
	    	logger.info("调用【订单系统-产品创建通知】接口请求参数：{}", JSONObject.toJSONString(paramMap));
	    	String respContent = HttpClientUtil.sendPostRequest(p2pOrderNewProductUrl, JSONObject.toJSONString(paramMap));
	        logger.info("调用【订单系统-产品创建通知】接口响应参数：{}", respContent);
	        // 将json字符创转换成json对象
	        JSONObject obj = JSON.parseObject(respContent);
	        // 判断远程URl调用是否成功
	        baseResp.setRespCode(obj.getString("code"));
	        baseResp.setRespMsg(obj.getString("msg"));
	        if (StringUtils.isNotEmpty(baseResp.getRespCode()) && !Constants.SUCCESS_RESP_CODE.equals(baseResp.getRespCode())) {
	        	errorMsg= "调用【订单系统-产品创建通知】接口错误:" + baseResp.getRespMsg();
	            throw new BusinessException(Constants.FAIL_RESP_CODE, errorMsg);
	        }
	    } catch (Exception e) {
	    	logger.error(errorMsg + "\n", e  );
	    	if (e instanceof BusinessException) {
                BusinessException be = (BusinessException) e;
                baseResp = BaseResponse.build(be.getResultCode(), be.getResultMsg());
            } else {
            	baseResp = BaseResponse.build(Constants.FAIL_RESP_CODE, e.getMessage());
            }
            String productCodeJson = JSONObject.toJSONString(productCode);
            if (null == interfaceRetryService.selectByProductCodes(productCodeJson)) {
            	InterfaceRetry interfaceRetry = new InterfaceRetry();
            	interfaceRetry.setBusinessType(InterfaceRetryBusinessTypeEnum.OPEN_PRODUCT_PLAN_NOTIFY_ORDER.getCode());
            	interfaceRetry.setBusinessNo(sequenceService.generateRetryCode(Constants.SEQUENCE_NAME_PREFIX_RETRY, 4));
            	interfaceRetry.setRequestParam(productCodeJson);
            	interfaceRetry.setResponseParam(baseResp!=null?JSONObject.toJSONString(baseResp):null);
            	interfaceRetry.setStatus(InterfaceRetryStatusEnum.INIT.getCode());
            	interfaceRetry.setRetryTimes(0);
            	interfaceRetry.setMemo("");
            	interfaceRetry.setProductCode(productCodeJson);
            	interfaceRetryService.saveInterfaceRetryRecord(interfaceRetry);
            }
        }
	    return baseResp;
	}
}
