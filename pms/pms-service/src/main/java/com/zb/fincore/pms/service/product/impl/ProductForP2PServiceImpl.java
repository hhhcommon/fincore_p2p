package com.zb.fincore.pms.service.product.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.zb.fincore.pms.facade.product.dto.req.*;
import com.zb.fincore.pms.service.ams.AMSService;
import com.zb.fincore.pms.service.dal.dao.*;
import com.zb.fincore.pms.service.dal.model.ProductStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.zb.fincore.common.enums.ChannelEnum;
import com.zb.fincore.common.enums.product.PatternCodeTypeEnum;
import com.zb.fincore.common.enums.product.ProductSaleStatusEnum;
import com.zb.fincore.common.utils.BeanUtils;
import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.enums.BuyWaysEnum;
import com.zb.fincore.pms.common.enums.P2PProductCollectStatusEnum;
import com.zb.fincore.pms.common.enums.PayChannelEnum;
import com.zb.fincore.pms.common.enums.ProductInvestTypeEnum;
import com.zb.fincore.pms.common.exception.BusinessException;
import com.zb.fincore.pms.common.model.Page;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductInfoResponse;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;
import com.zb.fincore.pms.facade.product.model.ProductLoanInfoModel;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zb.fincore.pms.facade.product.model.ProductPeriodModel;
import com.zb.fincore.pms.facade.product.model.ProductProfitModel;
import com.zb.fincore.pms.facade.product.model.ProductStockModel;
import com.zb.fincore.pms.service.SequenceService;
import com.zb.fincore.pms.service.dal.model.Product;
import com.zb.fincore.pms.service.dal.model.ProductPeriod;
import com.zb.fincore.pms.service.product.ProductForP2PService;
import com.zb.fincore.pms.service.product.validate.ProductForP2PServiceParameterValidator;
import com.zb.fincore.pms.service.trade.TradeService;
import com.zb.p2p.match.api.req.AssetMatchReq;
import com.zb.p2p.match.api.req.ProductAssetMatchDTO;

/**
 * 功能: 产品数据库接口类
 * 日期: 2017/4/6 0006 16:57
 * 版本: V1.0
 */
@Service
public class ProductForP2PServiceImpl extends AbstractProductServiceImpl implements ProductForP2PService {

    private static Logger logger = LoggerFactory.getLogger(ProductForP2PServiceImpl.class);

    /**
     * 参数校验器
     */
    @Autowired
    private ProductForP2PServiceParameterValidator periodicProductValidator;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductPeriodDao productPeriodDao;

    @Autowired
    private ProductProfitDao productProfitDao;

    @Autowired
    private ProductApprovalDao productApprovalDao;

    @Autowired
    private ProductStockDao productStockDao;

//    @Value("${p2p_product_register_notify_asset_mng_url}")
//    private String p2pProductRegisterNotifyAssetMngUrl;

//    @Value("${p2p_create_product_loan_relation_url}")
//    private String p2pCreateProductLoanRelationUrl;

//    @Value("${p2p_query_product_loan_relation_list_url}")
//    private String p2pQueryProductLoanRelationListUrl;

//    @Value("${p2p_notice_product_loan_relation_url}")
//    private String p2pNoticeProductLoanRelationUrl;

//    @Autowired
//    private OrderFacade orderFacade;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private AMSService amsService;


    /**
     * 货架系统 P2P定期产品注册 —— 支持多借款单 V2.0
     * @param req
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public RegisterProductResponse registerProduct(@Valid RegisterProductRequest req) throws Exception {

    	return super.registerProduct(req);

//        RegisterProductResponse response = null;
//        // 如果同一资产池，同一起息日，同一产品期限，并且产品销售状态为上线的产品已存在，不能注册
//        QueryProductInfoForTradeRequest queryProductInfoForTradeRequest = new QueryProductInfoForTradeRequest();
//        queryProductInfoForTradeRequest.setAssetPoolCode(req.getAssetPoolCode());
//        queryProductInfoForTradeRequest.setInvestPeriod(req.getInvestPeriod());
//        queryProductInfoForTradeRequest.setValueTime(req.getValueTime());
//		List<Product> productList = getProductListForP2P(queryProductInfoForTradeRequest);
//		if (productList != null && productList.size() > 0) {
//			throw new BusinessException(Constants.PRODUCT_SAME_ASSETPOOL_VALUETIME_INVESTPERIOD_ONLINEEXIST_CODE,
//					Constants.PRODUCT_SAME_ASSETPOOL_VALUETIME_INVESTPERIOD_ONLINEEXIST_CODE_DESC);
//		}

//        //1:注册产品基本信息,并入库产品库存信息
//        RegisterProductBaseInfoRequest registerProductBaseInfoRequest = BeanUtils.copyAs(req, RegisterProductBaseInfoRequest.class);
//        response = registerProductBaseInfo(registerProductBaseInfoRequest);
//        if (!Constants.SUCCESS_RESP_CODE.equals(response.getRespCode())) {
//            throw new BusinessException(response.getRespCode(), response.getRespMsg());
//        }
//        req.setProductCode(response.getProductCode());
//        req.setProductId(response.getId());
//
//        //2:注册产品期限相关信息及阶梯信息
//        RegisterProductPeriodInfoRequest registerProductPeriodInfoRequest = BeanUtils.copyAs(req, RegisterProductPeriodInfoRequest.class);
//        response = registerProductPeriodInfo(registerProductPeriodInfoRequest);
//        if (!Constants.SUCCESS_RESP_CODE.equals(response.getRespCode())) {
//            throw new BusinessException(response.getRespCode(), response.getRespMsg());
//        }
//
//        //3:注册产品投资收益相关信息及阶梯信息
//        RegisterProductProfitInfoRequest registerProductProfitInfoRequest = BeanUtils.copyAs(req, RegisterProductProfitInfoRequest.class);
//        response = registerProductProfitInfo(registerProductProfitInfoRequest);
//        if (!Constants.SUCCESS_RESP_CODE.equals(response.getRespCode())) {
//            throw new BusinessException(response.getRespCode(), response.getRespMsg());
//        }
//
//        // 通知资管系统产品与借款订单匹配
//        List<String> loanNoList = new ArrayList<String>();
//        String[] loanNoAarry = null;
//        String loanOrderNoSet = req.getLoanOrderNoSet();
//        try {
//            if (StringUtils.isNotEmpty(loanOrderNoSet)) {
//                loanNoAarry = loanOrderNoSet.split(";");
//            }
//        } catch (Exception e) {
//            logger.error("【产品注册】借款订单编号集格式不正确：" + loanOrderNoSet);
//            throw new BusinessException(Constants.LOAN_ORDER_NO_SET_FORMAT_ERROR_CODE, Constants.LOAN_ORDER_NO_SET_FORMAT_ERROR_CODE_DESC);
//        }
//        if (loanNoAarry.length > 0) {
//            for (int i=0; i<loanNoAarry.length; i++) {
//                loanNoList.add(loanNoAarry[i]);
//            }
//        }
//        notifyAssetCreateProductLoanRelationHttp(req.getProductCode(), req.getProductName(), loanNoList);
//
//        return response;
    }

    /**
     * 货架系统 P2P定期产品注册 —— 散标类型，一个企业对应一个借款单 V2.0
     * @param req
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public RegisterProductResponse registerProductSB(@Valid RegisterProductRequestSB req) throws Exception {

    	RegisterProductRequest regReq = BeanUtils.copyAs(req, RegisterProductRequest.class);

    	//校验支付渠道与购买方式
    	if (PayChannelEnum.getEnumItem(req.getPayChannel()) == null) {
            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_PAY_CHANNEL_NOT_IN_ENUMS_RETURN_CODE,
                    Constants.PRODUCT_PAY_CHANNEL_NOT_IN_ENUMS_RETURN_CODE_DESC);
        }
    	if (req.getPayChannel().equals(PayChannelEnum.BF.getCode()) && !req.getBuyWays().equals(BuyWaysEnum.YHK.getCode())) {
    		throw new BusinessException(Constants.FAIL_RESP_CODE, "宝付支付渠道只支持银行卡购买方式！");
    	}

    	return super.registerProduct(regReq);

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public BaseResponse putProductCancel(@Valid CancelProductRequest req) throws Exception {

        List<String> productCodeList = req.getProductCodeList();
        if (null!=productCodeList && productCodeList.size()>0) {
            for (String productCode : req.getProductCodeList()) {
                //更新产品销售状态
                Product product = productDao.selectProductByCode(productCode);
                if (null == product) {
                    return BaseResponse.build(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
                }

                ProductStock productStock = productStockDao.selectProductStockByProductCode(productCode);
                if (null == productStock) {
                    return BaseResponse.build(Constants.PRODUCT_STOCK_NOT_EXIST_CODE, Constants.PRODUCT_STOCK_NOT_EXIST_CODE_DESC);
                }

                if (ProductSaleStatusEnum.PRODUCT_SALE_STATUS_OFF_LINE.getCode() == product.getSaleStatus()) {
                    return BaseResponse.build(Constants.PRODUCT_ALREADY_OFF_LINE_RESULT_CODE,
                            Constants.PRODUCT_ALREADY_OFF_LINE_RESULT_CODE_DESC);
                }

                if ( -1 == productStock.getStockAmount().compareTo(product.getTotalAmount())) {
                    return BaseResponse.build(Constants.FAIL_RESP_CODE, "该产品有部分售出【productCode = 】" + "productCode");
                }

                Map<String, Object> params = new HashMap<String, Object>();
                params.put("id", product.getId());
                params.put("originalStatus", product.getSaleStatus());
                params.put("saleStatus", ProductSaleStatusEnum.PRODUCT_SALE_STATUS_OFF_LINE.getCode());
                productDao.updateProductSaleStatusById(params);

                ProductPeriod productPeriod = new ProductPeriod();
                productPeriod.setProductCode(product.getProductCode());
                productPeriod.setOfflineTime(new Date());
                productPeriodDao.updateActualTimeByProductCode(productPeriod);

                //通知资管释放库存(不需要通知)
                amsService.notifyAssetCancelProductLoanRelationHttp(productCode);
            }
        } else {
            BaseResponse.build(Constants.FAIL_RESP_CODE, "产品编号列表为空");
        }

        return BaseResponse.build();
    }
//    /**
//     * 货架系统 产品注册  投资期限信息注册
//     *
//     * @param req
//     * @return
//     */
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
//    @Override
//    public RegisterProductResponse registerProductPeriodInfo(@Valid RegisterProductPeriodInfoRequest req) throws Exception {
//        //1: 执行请求参数校验
//        Product product = productDao.selectProductByCode(req.getProductCode());
//        if (null == product) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
//        }
//        RegisterProductResponse resp = periodicProductValidator.checkRegisterProductPeriodInfoParameter(req, product.getPatternCode());
//        if (null != resp) {
//            return resp;
//        }
//
//        //2: 入库产品期限信息
//        ProductPeriod productPeriod = new ProductPeriod();
//        BeanUtils.copy(req, productPeriod);
//        productPeriod.setProductCode(product.getProductCode());
//        productPeriod.setProductId(product.getId());
//        productPeriod.setInvestPeriodUnit(ProductLockPeriodUnitEnum.DAY.getCode());
////        productPeriod.setInvestPeriod(DateUtils.getBetweenDays(req.getValueTime(), req.getExpectExpireTime()));
//
//        if (null == req.getExpectClearTime()) {
//            //预期结清时间 = 到期日+1
//            Calendar cl = Calendar.getInstance();
//            cl.setTime(req.getExpectExpireTime());
//            cl.add(Calendar.DATE, 1);
//            productPeriod.setExpectClearTime(cl.getTime());
//        } else {
//            productPeriod.setExpectClearTime(req.getExpectClearTime());
//        }
//
//        productPeriodDao.insertSelective(productPeriod);
//
//        //3: 返回结果
//        resp = BaseResponse.build(RegisterProductResponse.class);
//        resp.setId(productPeriod.getProductId());
//        resp.setProductCode(productPeriod.getProductCode());
//        return resp;
//    }

//    /**
//     * 货架系统 产品注册 投资收益信息注册
//     *
//     * @param req
//     * @return
//     */
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
//    @Override
//    public RegisterProductResponse registerProductProfitInfo(@Valid RegisterProductProfitInfoRequest req) throws Exception {
//        //1: 执行请求参数校验
//        Product product = productDao.selectProductByCode(req.getProductCode());
//        if (null == product) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
//        }
//        RegisterProductResponse resp = periodicProductValidator.checkRegisterProductProfitInfoParameter(req);
//        if (null != resp) {
//            return resp;
//        }
//
//        //2: 入库产品期限信息
//        ProductProfit productProfit = new ProductProfit();
//        BeanUtils.copy(req, productProfit);
//        productProfit.setProductCode(product.getProductCode());
//        productProfit.setProductId(product.getId());
//        productProfit.setBasicInterestsPeriod(ProductBasicInterestsPeriodEnum.ProductBasicInterestsPeriod_365.getCode());
//        productProfit.setUnit(ProductUnitEnum.RMB.getCode());
//        productProfit.setProfitType(req.getProfitType()==null ? ProductProfitTypeEnum.PERIODIC_VALUE.getCode() : req.getProfitType());//收益方式=固定起息日
//        productProfit.setCalculateInvestType(req.getCalculateInvestType()==null ? ProductInvestTypeEnum.ONCE_PAY_ALL.getCode() : req.getCalculateInvestType());//计息方式=一次性还本付息
//
//        productProfitDao.insertSelective(productProfit);
//
//        //3: 返回结果
//        resp = BaseResponse.build(RegisterProductResponse.class);
//        resp.setId(productProfit.getId());
//        resp.setProductCode(productProfit.getProductCode());
//        return resp;
//    }

    /**
     * 产品信息审核
     *
     * @param req
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public BaseResponse approveProduct(@Valid ApproveProductRequest req) throws Exception {

    	return super.approveProduct(req);
//        Product product = productDao.queryProductDetailByProductCode(req.getProductCode());
//        if (null == product) {
//            return BaseResponse.build(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
//        }
//
//        BaseResponse resp = periodicProductValidator.checkApproveProductRequestParameter(req, product);
//        if (null != resp) {
//            return resp;
//        }
//
//        String curRequireApprovalSign = product.getApprovalRequireSign();
//        String restNeedApprovalSign = "";
//        int index = curRequireApprovalSign.indexOf(",");
//        if (index != -1) {
//            curRequireApprovalSign = curRequireApprovalSign.substring(0, index);
//            restNeedApprovalSign = product.getApprovalRequireSign().substring(index + 1);
//        }
//        if (!curRequireApprovalSign.equals(req.getSign())) {
//            return BaseResponse.build(Constants.PRODUCT_APPROVAL_NO_PRIVILEGE_CODE, Constants.PRODUCT_APPROVAL_NO_PRIVILEGE_CODE_DESC);
//        }
//
//        ProductApproval productApproval = new ProductApproval();
//        BeanUtils.copy(req, productApproval);
//        productApproval.setProductId(product.getId());
//        productApproval.setProductCode(product.getProductCode());
//        productApprovalDao.insertSelective(productApproval);
//
//        int productApprovalStatus = req.getApprovalStatus();
//        Map<String, Object> params = new HashMap<String, Object>();
//        //产品审核不通过，通知资管释放注册时关联的资产池
//        if (ProductApprovalStatusEnum.APPROVAL_FAILURE.getCode() == req.getApprovalStatus()) {
//            //审核不通过调用资管系统释放资产
////            notifyAmsReleaseAssetPoolUseHttp(product.getProductCode(), product.getAssetPoolCode());
//        } else {
//            //当前审核通过，如果不存在下一个审核人，设置为审核通过，否则，状态为待审核
//            if (!StringUtils.isBlank(restNeedApprovalSign)) {
//                productApprovalStatus = ProductApprovalStatusEnum.WAIT_APPROVAL.getCode();
//            } else {
//                params.put("saleStatus", ProductSaleStatusEnum.PRODUCT_SALE_STATUS_DEPLOYED.getCode());
//            }
//        }
//
//        params.put("id", product.getId());
//        params.put("approvalStatus", productApprovalStatus);
//        params.put("originalStatus", product.getApprovalStatus());
//        params.put("approvalSign", StringUtils.isBlank(product.getApprovalSign()) ? curRequireApprovalSign : (product.getApprovalSign() + "," + curRequireApprovalSign));
//        params.put("approvalRequireSign", restNeedApprovalSign);
//        params.put("lastApprovalTime", new Date());
//        productDao.updateProductApprovalStatusById(params);
//
//        // 通知资管通知订单匹配(通知交易)
//        notifyAssetProductLoanRelationHttp(product.getProductCode(), product.getProductName(), productApprovalStatus);
//
//        return BaseResponse.build();
    }

//  /**
//  * 产品审核，通知资管系统通知订单匹配  V2.0
//  *
//  * @param productCode
//  * @param productName
//  * @param productStatus
//  * @throws BusinessException
//  */
// private void notifyAssetProductLoanRelationHttp(String productCode, String productName, Integer productStatus) throws BusinessException {
//     try {
//         String respContent = null;
//         net.sf.json.JSONObject obj = null;
//         Map<String, Object> assetNotifyParamMap = new HashMap<String, Object>();
//         // 产品编号
//         assetNotifyParamMap.put("productCode", productCode);
//         assetNotifyParamMap.put("productName", productName);
//         assetNotifyParamMap.put("productStatus", productStatus);
//         // 调用远程服务
//         logger.debug("【产品审核】 通知资管系统通知订单匹配请求参数：" + JSONObject.toJSONString(assetNotifyParamMap));
//         respContent = aesHttpClientUtils.sendPostRequest(p2pNoticeProductLoanRelationUrl, JSONObject.toJSONString(assetNotifyParamMap));
//         logger.debug("【产品审核】 通知资管系统通知订单匹配响应参数：" + respContent);
//         // 将json字符创转换成json对象
//         obj = net.sf.json.JSONObject.fromObject(respContent);
//         // 判断远程URl调用是否成功
//         String notifyRespCode = obj.getString("respCode");
//         if (!Constants.SUCCESS_RESP_CODE.equals(notifyRespCode)) {
//             throw new BusinessException(Constants.FAIL_RESP_CODE, "调用【资管系统-通知订单匹配】错误:" + obj.getString("respMsg"));
//         }
//     } catch (Exception e) {
//         logger.error("【产品审核】 调用资管系统资管系统通知订单匹配失败:productCode={} \n {}", productCode, e );
//         if (e instanceof BusinessException) {
//             throw new BusinessException(Constants.FAIL_RESP_CODE, ((BusinessException) e).getResultMsg());
//         } else {
//             throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统失败", e);
//         }
//     }
// }

    /**
     * 产品详情
     *
     * @param req
     * @return
     */
    @Override
    public QueryProductInfoResponse queryProductInfo(QueryProductInfoRequest req) throws Exception {
        Product product = productDao.queryProductDetailByProductCode(req.getProductCode());
        if (null == product) {
            return new QueryProductInfoResponse(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
        }

        QueryProductInfoResponse response = new QueryProductInfoResponse();
        ProductModel productModel = new ProductModel();
        BeanUtils.copy(product, productModel);

        //产品期限信息
        ProductPeriodModel productPeriodModel = new ProductPeriodModel();
        BeanUtils.copy(product.getProductPeriod(), productPeriodModel);
        productModel.setProductPeriodModel(productPeriodModel);

        //产品投资限制及收益信息
        ProductProfitModel productProfitModel = new ProductProfitModel();
        BeanUtils.copy(product.getProductProfit(), productProfitModel);
        if (null != productProfitModel.getCalculateInvestType()) {
            productProfitModel.setCalculateInvestTypeDesc(ProductInvestTypeEnum.getEnumCodeDesc(productProfitModel.getCalculateInvestType()));
        }
        productModel.setProductProfitModel(productProfitModel);

        //产品库存信息
        ProductStockModel productStockModel = new ProductStockModel();
        BeanUtils.copy(product.getProductStock(), productStockModel);
        productModel.setProductStockModel(productStockModel);

//        // 产品合同信息列表
//        List<ProductContractModel> contractModelList = null;
//        List<ProductContract> contractList = product.getProductContractList();
//        if (null != contractList && contractList.size() > 0) {
//            contractModelList = BeanUtils.copyAs(contractList, ProductContractModel.class);
//            productModel.setProductContractModelList(contractModelList);
//        }

//        // 调用交易系统查询交易库存信息并设置交易库存信息--------一期p2p内容
//        try {
//        	productModel = setTradeStock(productModel);
//		} catch (Exception e) {
//			BusinessException be = (BusinessException)e;
//			logger.info(be.getMessage());
//		}

        // 调用资管系统获取借款信息列表
        try {
//        	productModel = setProductLoanInfoList(productModel);
            productModel = amsService.setProductLoanInfoList(productModel);
		} catch (Exception e) {
			BusinessException be = (BusinessException)e;
			logger.info(be.getMessage());
		}

        response.setProductModel(productModel);
        return response;
    }

//    /**
//     * setTradeStock:设置产品交易库存信息. <br/>
//     *
//     * @param productModel
//     * @return
//     * @throws BusinessException
//     */
//    private ProductModel setTradeStock(ProductModel productModel) throws BusinessException {
//		try {
//			StockQueryReq stockQueryReq = new StockQueryReq();
//			stockQueryReq.setProductCode(productModel.getProductCode());
//			stockQueryReq.setSource("BOSS");
//			logger.debug("调用交易系统  查询交易库存请求参数:{}" + stockQueryReq);
//			CommonResp<ProductStockDTO> response = orderFacade.queryStock(stockQueryReq);
//			logger.debug("调用交易系统  查询交易库存响应参数:{}" + response);
//			if (response == null || !Constants.SUCCESS_RESP_CODE.equals(response.getCode())) {
//				throw new BusinessException(Constants.FAIL_RESP_CODE, "调用交易系统查询交易库存失败:" + response.getMessage());
//			}
//			productModel.setReservationTotalAmount(response.getData().getReservationTotalAmount()); // 预约募集金额
//			productModel.setActualTotalAmount(response.getData().getActualTotalAmount()); // 已募集金额
//
//			return productModel;
//		} catch (Exception e) {
//			if (e instanceof BusinessException) {
//				throw new BusinessException(Constants.FAIL_RESP_CODE, ((BusinessException) e).getResultMsg());
//			} else {
//				throw new BusinessException(Constants.FAIL_RESP_CODE, "调用交易系统失败", e);
//			}
//		}
//    }

//    /**
//     * setProductLoanInfoList:设置借款信息列表（产品详情里要包含借款端信息）. <br/>
//     *
//     * @param productModel
//     * @return
//     * @throws BusinessException
//     */
//    private ProductModel setProductLoanInfoList(ProductModel productModel) throws BusinessException {
//        try {
//            String respContent = null;
//            net.sf.json.JSONObject obj = null;
//            Map<String, Object> assetNotifyParamMap = new HashMap<String, Object>();
//            // 产品编号
//            assetNotifyParamMap.put("productCode", productModel.getProductCode());
//            // 调用远程服务
//            logger.debug("调用资管系统  查询借款信息列表请求参数：" + JSONObject.toJSONString(assetNotifyParamMap));
//            respContent = aesHttpClientUtils.sendPostRequest(p2pQueryProductLoanRelationListUrl, JSONObject.toJSONString(assetNotifyParamMap));
//            logger.debug("调用资管系统  查询借款信息列表响应参数：" + respContent);
//            // 将json字符创转换成json对象
//            obj = net.sf.json.JSONObject.fromObject(respContent);
//            // 判断远程URl调用是否成功
//            String notifyRespCode = obj.getString("respCode");
//
//            if (Constants.SUCCESS_RESP_CODE.equals(notifyRespCode)) {
//                List<ProductLoanInfoModel> loanInfoList = com.alibaba.fastjson.JSONArray.parseArray(obj.getString("dataList"), ProductLoanInfoModel.class);
//                productModel.setProductLoanInfoList(loanInfoList);
//                return productModel;
//            } else {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统查询借款信息列表失败:" + obj.getString("respMsg"));
//            }
//        } catch (Exception e) {
//            if (e instanceof BusinessException) {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, ((BusinessException) e).getResultMsg());
//            } else {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统失败", e);
//            }
//        }
//    }

    /**
     * 供交易系统调用  产品详情查询
     *
     * @param req
     * @return
     */
	@Override
	public QueryProductInfoResponse queryProductInfoForTrade(QueryProductInfoForTradeRequest req) throws Exception {
		List<Product> productList = getProductListForP2P(req);

		Product product = new Product();
        if (productList == null || productList.size() == 0) {
            return new QueryProductInfoResponse(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
        }
        product = productList.get(0);

        QueryProductInfoResponse response = new QueryProductInfoResponse();
        ProductModel productModel = new ProductModel();
        BeanUtils.copy(product, productModel);

        //产品期限信息
        ProductPeriodModel productPeriodModel = new ProductPeriodModel();
        BeanUtils.copy(product.getProductPeriod(), productPeriodModel);
        productModel.setProductPeriodModel(productPeriodModel);

        //产品投资限制及收益信息
        ProductProfitModel productProfitModel = new ProductProfitModel();
        BeanUtils.copy(product.getProductProfit(), productProfitModel);
        if (null != productProfitModel.getCalculateInvestType()) {
            productProfitModel.setCalculateInvestTypeDesc(ProductInvestTypeEnum.getEnumCodeDesc(productProfitModel.getCalculateInvestType()));
        }
        productModel.setProductProfitModel(productProfitModel);

        //产品库存信息
        ProductStockModel productStockModel = new ProductStockModel();
        BeanUtils.copy(product.getProductStock(), productStockModel);
        productModel.setProductStockModel(productStockModel);

        response.setProductModel(productModel);
        return response;
	}

  /**
	 * getProductListForP2P:获取同一资产池、同一起息时间、同一投资期限，已上线，销售渠道为马上贷的产品列表. <br/>
	 *
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public List<Product> getProductListForP2P(QueryProductInfoForTradeRequest req) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("assetPoolCode", req.getAssetPoolCode());// 资产池编号
		params.put("valueTime", req.getValueTime());// 起息时间
		params.put("investPeriod", req.getInvestPeriod());// 投资期限
		params.put("saleChannelCode", ChannelEnum.MSD.getCode());// 销售渠道
		params.put("saleStatus", ProductSaleStatusEnum.PRODUCT_SALE_STATUS_ON_LINE.getCode());// 销售状态
		List<Product> productList = productDao.queryProductListByCondition(params, new Page());
		return productList;
	}

    /**
     * 供订单系统调用 库存售完通知产品
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	@Override
	public BaseResponse noticeProductStockSellout(@Valid NoticeProductStockSelloutRequest req) throws Exception {
        List<String> productCodeList = req.getProductCodeList();
        if (null!=productCodeList && productCodeList.size()>0) {
            for (String productCode : req.getProductCodeList() ) {
                //查询产品
                Product product = productDao.selectProductByCode(productCode);
                if (null == product) {
                    return BaseResponse.build(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
                }

                //更新募集状态为“募集完成”
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("id", product.getId());
                params.put("collectStatus", P2PProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_RAISE_COMPLETE.getCode());
                params.put("saleStatus", ProductSaleStatusEnum.PRODUCT_SALE_STATUS_OFF_LINE.getCode());
                productDao.updateProductCollectStatusById(params);

                ProductPeriod productPeriod = new ProductPeriod();
                productPeriod.setProductCode(product.getProductCode());
                productPeriod.setSaleOutTime(new Date());
                productPeriod.setOfflineTime(new Date());
                productPeriodDao.updateActualTimeByProductCode(productPeriod);

                //通知交易匹配资产
                List<ProductAssetMatchDTO> productAssetMatchDTOList = new ArrayList<ProductAssetMatchDTO>();
                ProductAssetMatchDTO productAssetMatchDTO = new ProductAssetMatchDTO();
                productAssetMatchDTO.setProductCode(product.getProductCode());
                productAssetMatchDTO.setProductSource(product.getPatternCode());
                productAssetMatchDTOList.add(productAssetMatchDTO);
                AssetMatchReq assetMatchReq = new AssetMatchReq();
                assetMatchReq.setProductAssetMatchDTOList(productAssetMatchDTOList);
                tradeService.assetMatchHttp(assetMatchReq);
            }
        } else {
            BaseResponse.build(Constants.FAIL_RESP_CODE, "产品编号列表为空");
        }
        return BaseResponse.build();
	}

//    /**
//     * 产品注册时，通知资管系统产品与借款订单匹配
//     *
//     * @param productCode
//     * @param productName
//     * @param loanNoList
//     * @throws BusinessException
//     */
//    private void notifyAssetCreateProductLoanRelationHttp(String productCode, String productName, List<String> loanNoList) throws BusinessException {
//        try {
//            String respContent = null;
//            net.sf.json.JSONObject obj = null;
//            Map<String, Object> assetNotifyParamMap = new HashMap<String, Object>();
//            // 产品编号
//            assetNotifyParamMap.put("productCode", productCode);
//            assetNotifyParamMap.put("productName", productName);
//            assetNotifyParamMap.put("loanNoList", loanNoList);
//            // 调用远程服务
//            logger.debug("【产品注册】 通知资管系统产品与借款订单匹配请求参数：" + JSONObject.toJSONString(assetNotifyParamMap));
//            respContent = aesHttpClientUtils.sendPostRequest(p2pCreateProductLoanRelationUrl, JSONObject.toJSONString(assetNotifyParamMap));
//            logger.debug("【产品注册】 通知资管系统产品与借款订单匹配响应参数：" + respContent);
//            // 将json字符创转换成json对象
//            obj = net.sf.json.JSONObject.fromObject(respContent);
//            // 判断远程URl调用是否成功
//            String notifyRespCode = obj.getString("respCode");
//            if (!Constants.SUCCESS_RESP_CODE.equals(notifyRespCode)) {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用【资管系统-产品与借款订单】错误:" + obj.getString("respMsg"));
//            }
//        } catch (Exception e) {
//        	logger.error("【产品注册】 调用资管系统资管系统产品与借款订单失败:productCode={} \n {}", productCode, e );
//            if (e instanceof BusinessException) {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, ((BusinessException) e).getResultMsg());
//            } else {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统失败", e);
//            }
//        }
//    }
}
