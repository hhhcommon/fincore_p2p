package com.zb.fincore.pms.service.txs.impl;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.enums.InterfaceRetryBusinessTypeEnum;
import com.zb.fincore.pms.common.enums.InterfaceRetryStatusEnum;
import com.zb.fincore.pms.common.exception.BusinessException;
import com.zb.fincore.pms.common.utils.HttpClientUtil;
import com.zb.fincore.pms.service.SequenceService;
import com.zb.fincore.pms.service.dal.dao.ProductDao;
import com.zb.fincore.pms.service.dal.model.InterfaceRetry;
import com.zb.fincore.pms.service.dal.model.Product;
import com.zb.fincore.pms.service.product.InterfaceRetryService;
import com.zb.fincore.pms.service.txs.TXSService;

/**
 * 【p2p】 订单系统服务接口实现类 <br/>
 * Date: 2018年3月6日 下午5:40:44 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version
 * @since JDK 1.7
 */
@Service
public class TXSServiceImpl implements TXSService {

    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(TXSServiceImpl.class);

    /** 产品募集期结束或下线通知订单 */
    @Value("${p2p_txs_sync_status_notify}")
	private String p2pTXSSynctStatusNotifyUrl;

    @Autowired
    private InterfaceRetryService interfaceRetryService;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private ProductDao productDao;


	@Override
	public BaseResponse syncStatusNoticeHttp(String productCodes) throws Exception {
        BaseResponse baseResp = BaseResponse.build();
        String errorMsg = null;
	    try {
	    	Map<String, String> paramMap = new HashMap<String, String>();
	    	paramMap.put("productcode", productCodes);
	    	logger.info("调用【唐小僧系统-产品售罄通知】接口请求参数：{}", JSONObject.toJSONString(paramMap));
	    	String respContent = HttpClientUtil.sendPostRequest(p2pTXSSynctStatusNotifyUrl, JSONObject.toJSONString(paramMap));
	        logger.info("调用【订单系统-产品售罄通知】接口响应参数：{}", respContent);
	        // 将json字符创转换成json对象
	        JSONObject obj = JSON.parseObject(respContent);
	        // 判断远程URl调用是否成功
	        baseResp.setRespCode(obj.getString("response_code"));
	        baseResp.setRespMsg(obj.getString("response_message"));
	        if (StringUtils.isNotEmpty(baseResp.getRespCode()) && !"success".equals(baseResp.getRespCode())) {
	        	errorMsg= "调用【订单系统-产品售罄通知】接口错误:" + baseResp.getRespMsg();
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
            String productCodeJson = JSONObject.toJSONString(productCodes);
            if (null == interfaceRetryService.selectByProductCodes(productCodeJson)) {
            	InterfaceRetry interfaceRetry = new InterfaceRetry();
            	interfaceRetry.setBusinessType(InterfaceRetryBusinessTypeEnum.TXS_SYNC_STATUS_NOTIFY.getCode());
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
