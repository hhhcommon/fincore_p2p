package com.zb.fincore.pms.service.ams.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zb.fincore.pms.common.enums.PayChannelEnum;
import com.zb.fincore.pms.facade.product.model.ProductLoanInfoModel;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zb.fincore.ams.common.dto.BaseResponse;
import com.zb.fincore.ams.common.dto.QueryResponse;
import com.zb.fincore.ams.facade.dto.p2p.req.CancelAssetProductRelationRequest;
import com.zb.fincore.ams.facade.dto.p2p.req.CreateAssetProductRelationRequest;
import com.zb.fincore.ams.facade.dto.p2p.req.SynAssetProductRelationRequest;
import com.zb.fincore.ams.facade.dto.p2p.resp.BaseP2P3Response;
import com.zb.fincore.ams.facade.dto.p2p.resp.CreateAssetProductRelationResponse;
import com.zb.fincore.ams.facade.dto.req.QueryPoolRequest;
import com.zb.fincore.ams.facade.model.PoolModel;
import com.zb.fincore.common.utils.AesHttpClientUtils;
import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.exception.BusinessException;
import com.zb.fincore.pms.common.utils.HttpClientUtil;
import com.zb.fincore.pms.service.ams.AMSService;

/**
 * 资产服务接口实现. <br/>
 * Date: 2018年4月23日 上午11:41:23 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version
 * @since JDK 1.7
 */
@Service
public class AMSServiceImpl implements AMSService {

	private static Logger logger = LoggerFactory.getLogger(AMSServiceImpl.class);

	/**
	 * V2.0 产品注册时通知资管URL
	 */
    @Value("${p2p_create_product_loan_relation_url}")
    private String p2pCreateProductLoanRelationUrl;

    /**
	 * V2.0 审核通过时，通知资管，资管再通知订单匹配(通知交易)URL
	 */
    @Value("${p2p_notice_product_loan_relation_url}")
    private String p2pNoticeProductLoanRelationUrl;

    /**
     * V2.0 审核不通过时，通知资管释放资产URL【唐小僧 V2.0版 新加】
     */
    @Value("${p2p_asset_cancel_product_loan_relation_url}")
    private String p2pAssetCancelProductLoanRelationUrl;

    /**
     * 设置借款信息列表（产品详情里要包含借款端信息）
     */
    @Value("${p2p_query_product_loan_relation_list_url}")
    private String p2pQueryProductLoanRelationListUrl;

    /**
     * V3.0 产品注册时通知资管URL
     */
    @Value("${p2p_asset_create_product_relation}")
    private String p2pAssetCreateProductRelationUrl;

    /**
	 * V3.0 审核不通过时，通知资管URL
	 */
    @Value("${p2p_asset_cancel_product_relation}")
    private String p2pAssetCancelProductRelationUrl;

    /**
	 * V3.0 审核通过时，通知资管URL
	 */
    @Value("${p2p_asset_syn_product_relation}")
    private String p2pAssetSynProductRelationUrl;

    /**
     * V3.0 查询资产池详情URL
     */
    @Value("${p2p_asset_query_pool_info}")
    private String p2pAssertQueryPoolInfoUrl;

	@Autowired
    protected AesHttpClientUtils aesHttpClientUtils;

	@Override
	public void notifyAssetCreateProductLoanRelationHttp(String productCode,String productName, List<String> loanNoList, String payChannel) throws BusinessException {
		try {
            String respContent = null;
            JSONObject obj = null;
            Map<String, Object> assetNotifyParamMap = new HashMap<String, Object>();
            int payChannelInt = 1;//新浪

            if (payChannel.equals(PayChannelEnum.BF.getCode())) {
                payChannelInt = 2;
            }
            // 产品编号
            assetNotifyParamMap.put("productCode", productCode);
            assetNotifyParamMap.put("productName", productName);
            assetNotifyParamMap.put("loanNoList", loanNoList);
            assetNotifyParamMap.put("payChannel", payChannelInt);
            // 调用远程服务(V2.0 需加密)
            logger.info("【产品注册】 通知资管系统产品与借款订单匹配请求参数：" + JSONObject.toJSONString(assetNotifyParamMap));
            respContent = aesHttpClientUtils.sendPostRequest(p2pCreateProductLoanRelationUrl, JSONObject.toJSONString(assetNotifyParamMap));
            logger.info("【产品注册】 通知资管系统产品与借款订单匹配响应参数：" + respContent);
            // 将json字符创转换成json对象
            obj = JSONObject.parseObject(respContent);
            // 判断远程URl调用是否成功
            String notifyRespCode = obj.getString("respCode");
            if (!Constants.SUCCESS_RESP_CODE.equals(notifyRespCode)) {
                throw new BusinessException(notifyRespCode, "调用【资管系统-产品与借款订单】错误:" + obj.getString("respMsg"));
            }
        } catch (Exception e) {
        	logger.error("【产品注册】 调用资管系统资管系统产品与借款订单失败:productCode={} \n {}", productCode, e );
            if (e instanceof BusinessException) {
            	String resultCode = ((BusinessException) e).getResultCode();
                throw new BusinessException(resultCode, ((BusinessException) e).getResultMsg());
            } else {
                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统服务失败", e);
            }
        }
	}

	@Override
	public void notifyAssetProductLoanRelationHttp(String productCode, String productName, Integer productStatus) throws BusinessException {
		try {
            String respContent = null;
            JSONObject obj = null;
            Map<String, Object> assetNotifyParamMap = new HashMap<String, Object>();
            // 产品编号
            assetNotifyParamMap.put("productCode", productCode);
            assetNotifyParamMap.put("productName", productName);
            assetNotifyParamMap.put("productStatus", productStatus);
            // 调用远程服务(V2.0 需加密)
            logger.info("【产品审核】 通知资管系统通知订单匹配请求参数：" + JSONObject.toJSONString(assetNotifyParamMap));
            respContent = aesHttpClientUtils.sendPostRequest(p2pNoticeProductLoanRelationUrl, JSONObject.toJSONString(assetNotifyParamMap));
            logger.info("【产品审核】 通知资管系统通知订单匹配响应参数：" + respContent);
            // 将json字符创转换成json对象
            obj = JSON.parseObject(respContent);
            // 判断远程URl调用是否成功
            String notifyRespCode = obj.getString("respCode");
            if (!Constants.SUCCESS_RESP_CODE.equals(notifyRespCode)) {
                throw new BusinessException(notifyRespCode, "调用【资管系统-通知订单匹配】错误:" + obj.getString("respMsg"));
            }
        } catch (Exception e) {
            logger.error("【产品审核】 调用资管系统资管系统通知订单匹配失败:productCode={} \n {}", productCode, e );
            if (e instanceof BusinessException) {
            	String resultCode = ((BusinessException) e).getResultCode();
                throw new BusinessException(resultCode, ((BusinessException) e).getResultMsg());
            } else {
                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统服务失败", e);
            }
        }
	}

    @Override
    public void notifyAssetCancelProductLoanRelationHttp(String productCode) throws BusinessException {
        try {
            String respContent = null;
            JSONObject obj = null;
            Map<String, Object> assetNotifyParamMap = new HashMap<String, Object>();
            // 产品编号
            assetNotifyParamMap.put("productCode", productCode);
            // 调用远程服务(V2.0 需加密)
            logger.info("【产品审核】 通知【资管系统-审核不通过】请求参数：" + JSONObject.toJSONString(assetNotifyParamMap));
            respContent = HttpClientUtil.sendPostRequest(p2pAssetCancelProductLoanRelationUrl, JSONObject.toJSONString(assetNotifyParamMap));
            logger.info("【产品审核】 通知【资管系统-审核不通过】响应参数：" + respContent);
            // 将json字符创转换成json对象
            obj = JSON.parseObject(respContent);
            // 判断远程URl调用是否成功
            String notifyRespCode = obj.getString("respCode");
            if (!Constants.SUCCESS_RESP_CODE.equals(notifyRespCode)) {
                throw new BusinessException(notifyRespCode, "调用【资管系统-审核不通过】错误:" + obj.getString("respMsg"));
            }
        } catch (Exception e) {
            logger.error("【产品审核】 调用【资管系统-审核不通过】失败:productCode={} \n {}", productCode, e );
            if (e instanceof BusinessException) {
                String resultCode = ((BusinessException) e).getResultCode();
                throw new BusinessException(resultCode, ((BusinessException) e).getResultMsg());
            } else {
                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用【资管系统-审核不通过】服务失败", e);
            }
        }
    }

    /**
     * 设置借款信息列表（产品详情里要包含借款端信息）. <br/>
     *
     * @param productModel
     * @return
     * @throws BusinessException
     */
    public ProductModel setProductLoanInfoList(ProductModel productModel) throws BusinessException {
        try {
            String respContent = null;
            net.sf.json.JSONObject obj = null;
            Map<String, Object> assetNotifyParamMap = new HashMap<String, Object>();
            // 产品编号
            assetNotifyParamMap.put("productCode", productModel.getProductCode());
            // 调用远程服务
            logger.info("调用资管系统  查询借款信息列表请求参数：" + JSONObject.toJSONString(assetNotifyParamMap));
            respContent = aesHttpClientUtils.sendPostRequest(p2pQueryProductLoanRelationListUrl, JSONObject.toJSONString(assetNotifyParamMap));
            logger.info("调用资管系统  查询借款信息列表响应参数：" + respContent);
            // 将json字符创转换成json对象
            obj = net.sf.json.JSONObject.fromObject(respContent);
            // 判断远程URl调用是否成功
            String notifyRespCode = obj.getString("respCode");

            if (Constants.SUCCESS_RESP_CODE.equals(notifyRespCode)) {
                List<ProductLoanInfoModel> loanInfoList = com.alibaba.fastjson.JSONArray.parseArray(obj.getString("dataList"), ProductLoanInfoModel.class);
                productModel.setProductLoanInfoList(loanInfoList);
                return productModel;
            } else {
                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统查询借款信息列表失败:" + obj.getString("respMsg"));
            }
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                throw new BusinessException(Constants.FAIL_RESP_CODE, ((BusinessException) e).getResultMsg());
            } else {
                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统失败", e);
            }
        }
    }

	@Override
	public CreateAssetProductRelationResponse createAssetProductRelationHttp(CreateAssetProductRelationRequest req) throws BusinessException {
		CreateAssetProductRelationResponse resp = null;
		try {
            // 调用远程服务s
            logger.info("调用【资管系统-建立资产池与产品的关系】请求参数：" + JSONObject.toJSONString(req));
            String respContent = HttpClientUtil.sendPostRequest(p2pAssetCreateProductRelationUrl, JSONObject.toJSONString(req));
            logger.info("调用【资管系统-建立资产池与产品的关系】响应参数：" + respContent);
            // 将json字符创转换成json对象
            resp = JSON.parseObject(respContent, new CreateAssetProductRelationResponse().getClass());
            // 判断远程URl调用是否成功
            String respCode = resp.getRespCode();
            if (!Constants.SUCCESS_RESP_CODE.equals(respCode)) {
                throw new BusinessException(respCode, "调用【资管系统-建立资产池与产品的关系】错误:" + resp.getRespMsg());
            }
        } catch (Exception e) {
            logger.error("调用【资管系统-建立资产池与产品的关系】失败:req={} \n {}", JSONObject.toJSONString(req), e );
            if (e instanceof BusinessException) {
            	String resultCode = ((BusinessException) e).getResultCode();
                throw new BusinessException(resultCode, ((BusinessException) e).getResultMsg());
            } else {
                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统服务失败", e);
            }
        }
		return resp;
	}

	@Override
	public BaseResponse cancelAssetProductRelationHttp(CancelAssetProductRelationRequest req) throws BusinessException {
		BaseResponse resp = null;
		try {
            // 调用远程服务
            logger.info("调用【资管系统-取消资产池与产品的关系】请求参数：" + JSONObject.toJSONString(req));
            String respContent = HttpClientUtil.sendPostRequest(p2pAssetCancelProductRelationUrl, JSONObject.toJSONString(req));
            logger.info("调用【资管系统-取消资产池与产品的关系】响应参数：" + respContent);
            // 将json字符创转换成json对象
            resp = JSON.parseObject(respContent, BaseResponse.class);
            // 判断远程URl调用是否成功
            String respCode = resp.getRespCode();
            if (!Constants.SUCCESS_RESP_CODE.equals(respCode)) {
                throw new BusinessException(respCode, "调用【资管系统-取消资产池与产品的关系】错误:" + resp.getRespMsg());
            }
        } catch (Exception e) {
            logger.error("调用【资管系统-取消资产池与产品的关系】失败:req={} \n {}", req, e );
            if (e instanceof BusinessException) {
            	String resultCode = ((BusinessException) e).getResultCode();
                throw new BusinessException(resultCode, ((BusinessException) e).getResultMsg());
            } else {
                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统服务失败", e);
            }
        }
		return resp;
	}

	@Override
	public BaseResponse synAssetProductRelation(SynAssetProductRelationRequest req) throws BusinessException {
		BaseResponse resp = null;
		try {
            // 调用远程服务
            logger.info("调用【资管系统-同步资产产品关系给交易匹配】请求参数：" + JSONObject.toJSONString(req));
            String respContent = HttpClientUtil.sendPostRequest(p2pAssetSynProductRelationUrl, JSONObject.toJSONString(req));
            logger.info("调用【资管系统-同步资产产品关系给交易匹配】响应参数：" + respContent);
            // 将json字符创转换成json对象
            resp = JSON.parseObject(respContent, BaseResponse.class);
            // 判断远程URl调用是否成功
            String respCode = resp.getRespCode();
            if (!Constants.SUCCESS_RESP_CODE.equals(respCode)) {
                throw new BusinessException(respCode, "调用【资管系统-同步资产产品关系给交易匹配】错误:" + resp.getRespMsg());
            }
        } catch (Exception e) {
            logger.error("调用【资管系统-同步资产产品关系给交易匹配】失败:req={} \n {}", req, e );
            if (e instanceof BusinessException) {
            	String resultCode = ((BusinessException) e).getResultCode();
                throw new BusinessException(resultCode, ((BusinessException) e).getResultMsg());
            } else {
                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统服务失败", e);
            }
        }
		return resp;
	}

	@Override
	public QueryResponse<PoolModel> queryPoolInfoHttp(QueryPoolRequest queryPoolRequest) throws BusinessException {
        QueryResponse resp = null;
		try {
            // 调用远程服务
            logger.info("【获取计划产品库存】 查询资产池详情请求参数：" + JSONObject.toJSONString(queryPoolRequest));
            String respContent = HttpClientUtil.sendPostRequest(p2pAssertQueryPoolInfoUrl, JSONObject.toJSONString(queryPoolRequest));
            logger.info("【获取计划产品库存】 查询资产池详情响应参数：" + respContent);
            // 将json字符创转换成json对象
            resp = JSON.parseObject(respContent, new QueryResponse().getClass());
            PoolModel poolModel = ((JSONObject)resp.getData()).toJavaObject(PoolModel.class);
            resp.setData(poolModel);
            // 判断远程URl调用是否成功
            String notifyRespCode = resp.getRespCode();
            if (!Constants.SUCCESS_RESP_CODE.equals(notifyRespCode)) {
                throw new BusinessException(notifyRespCode, "调用【资管系统-通知订单匹配】错误:" + resp.getRespMsg());
            }
        } catch (Exception e) {
            logger.error("【获取计划产品库存】 查询资产池详情失败:req={} \n {}", queryPoolRequest, e );
            if (e instanceof BusinessException) {
            	String resultCode = ((BusinessException) e).getResultCode();
                throw new BusinessException(resultCode, ((BusinessException) e).getResultMsg());
            } else {
                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统服务失败", e);
            }
        }
		return resp;
	}



}
