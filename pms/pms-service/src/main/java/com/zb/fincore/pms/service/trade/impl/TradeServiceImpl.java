package com.zb.fincore.pms.service.trade.impl;


import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zb.fincore.common.utils.AesHttpClientUtils;
import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.enums.InterfaceRetryBusinessTypeEnum;
import com.zb.fincore.pms.common.enums.InterfaceRetryStatusEnum;
import com.zb.fincore.pms.common.exception.BusinessException;
import com.zb.fincore.pms.common.utils.HttpClientUtil;
import com.zb.fincore.pms.service.SequenceService;
import com.zb.fincore.pms.service.dal.model.InterfaceRetry;
import com.zb.fincore.pms.service.product.InterfaceRetryService;
import com.zb.fincore.pms.service.trade.TradeService;
import com.zb.p2p.match.api.common.CommonResp;
import com.zb.p2p.match.api.req.AssetMatchReq;
import com.zb.p2p.match.api.req.ProductAssetMatchDTO;

/**
 * 功能: 产品缓存接口类
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/6 0006 16:58
 * 版本: V1.0
 */
@Service
public class TradeServiceImpl implements TradeService {

    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(TradeServiceImpl.class);

//    @Autowired
//	private MatchRecordFacade matchRecordFacade;

    /** V2.0 资产匹配 */
    @Value("${p2p_match_asset_match}")
    private String p2pMatchAssetMatchUrl;

    @Autowired
    private InterfaceRetryService interfaceRetryService;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    protected AesHttpClientUtils aesHttpClientUtils;


	@Override
	public BaseResponse assetMatchHttp(AssetMatchReq assetMatchReq) throws Exception {
		BaseResponse baseResp = BaseResponse.build();
		String errorMsg = null;
		try {
            // 调用远程服务
            logger.info("调用【匹配系统-资产匹配】请求参数：" + JSONObject.toJSONString(assetMatchReq));
            String respContent = HttpClientUtil.sendPostRequest(p2pMatchAssetMatchUrl, JSONObject.toJSONString(assetMatchReq));
            logger.info("调用【匹配系统-资产匹配】响应参数：" + respContent);
            // 将json字符创转换成json对象
            CommonResp<String> resp = JSON.parseObject(respContent, new CommonResp<String>().getClass());
            // 判断远程URl调用是否成功
            String respCode = resp.getCode();
            String respMsg = resp.getMsg();
            baseResp.setRespCode(respCode);
            baseResp.setRespMsg(respMsg);
            if (!Constants.SUCCESS_RESP_CODE.equals(respCode)) {
            	errorMsg = "调用【匹配系统-资产匹配】接口错误:" + respMsg;
                throw new BusinessException(respCode, errorMsg);
            }
//			throw new BusinessException(Constants.FAIL_RESP_CODE, "调用【匹配系统-资产匹配】接口错误");
        } catch (Exception e) {
            logger.error(errorMsg + "\n", e);
            if (e instanceof BusinessException) {
                BusinessException be = (BusinessException) e;
                baseResp = BaseResponse.build(be.getResultCode(), be.getResultMsg());
            } else {
            	baseResp = BaseResponse.build(Constants.FAIL_RESP_CODE, e.getMessage());
            }
            List<String> productCodeList = new LinkedList<String>();
            for (ProductAssetMatchDTO dto : assetMatchReq.getProductAssetMatchDTOList()) {
            	productCodeList.add(dto.getProductCode());
            }
            String productCode = JSONObject.toJSONString(productCodeList);
            if (null == interfaceRetryService.selectByProductCodes(productCode)) {
              InterfaceRetry interfaceRetry = new InterfaceRetry();
              interfaceRetry.setBusinessType(InterfaceRetryBusinessTypeEnum.ASSET_MATCH_NOTIFY_TRADE.getCode());
              interfaceRetry.setBusinessNo(sequenceService.generateRetryCode(Constants.SEQUENCE_NAME_PREFIX_RETRY, 4));
              interfaceRetry.setRequestParam(null != assetMatchReq ? JSONObject.toJSONString(assetMatchReq) : null);
              interfaceRetry.setResponseParam(null != baseResp ? JSONObject.toJSONString(baseResp) : null);
              interfaceRetry.setStatus(InterfaceRetryStatusEnum.INIT.getCode());
              interfaceRetry.setRetryTimes(0);
              interfaceRetry.setMemo("");
              interfaceRetry.setProductCode(productCode);
              interfaceRetryService.saveInterfaceRetryRecord(interfaceRetry);
            }
        }
		return baseResp;
	}



}
