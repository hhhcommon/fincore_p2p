package com.zb.fincore.pms.service.product.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.zb.fincore.pms.common.enums.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.zb.fincore.ams.facade.dto.p2p.req.CancelAssetProductRelationRequest;
import com.zb.fincore.ams.facade.dto.p2p.req.CreateAssetProductRelationRequest;
import com.zb.fincore.ams.facade.dto.p2p.req.SynAssetProductRelationRequest;
import com.zb.fincore.ams.facade.dto.resp.QueryProductRelatedAssetInfoResponse;
import com.zb.fincore.common.enums.ChannelEnum;
import com.zb.fincore.common.enums.product.DisplayStatusEnum;
import com.zb.fincore.common.enums.product.PatternCodeTypeEnum;
import com.zb.fincore.common.enums.product.ProductSaleStatusEnum;
import com.zb.fincore.common.utils.AesHttpClientUtils;
import com.zb.fincore.common.utils.BeanUtils;
import com.zb.fincore.common.utils.HttpClientUtil;
import com.zb.fincore.common.utils.JsonUtils;
import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.GlobalConfigConstants;
import com.zb.fincore.pms.common.RespObj;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.common.exception.BusinessException;
import com.zb.fincore.pms.common.model.Page;
import com.zb.fincore.pms.facade.product.dto.req.ApproveProductRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductApprovalListRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductInfoRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductListRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductListRequestForP2P;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductStockChangeFlowRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductBaseInfoRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductPeriodInfoRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductProfitInfoRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductRequest;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductCollectAmountRequest;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductCollectStatusRequest;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductDisplayStatusRequest;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductInfoRequest;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductSaleStatusRequest;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductSyncStatusRequest;
import com.zb.fincore.pms.facade.product.dto.resp.ProductStatisticsResponse;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductApprovalInfoResponse;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductInfoResponse;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;
import com.zb.fincore.pms.facade.product.model.ProductApprovalModel;
import com.zb.fincore.pms.facade.product.model.ProductContractModel;
import com.zb.fincore.pms.facade.product.model.ProductLadderModel;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zb.fincore.pms.facade.product.model.ProductPeriodModel;
import com.zb.fincore.pms.facade.product.model.ProductProfitModel;
import com.zb.fincore.pms.facade.product.model.ProductRelatedAssetInfoModel;
import com.zb.fincore.pms.facade.product.model.ProductStockChangeFlowModel;
import com.zb.fincore.pms.facade.product.model.ProductStockModel;
import com.zb.fincore.pms.service.SequenceService;
import com.zb.fincore.pms.service.ams.AMSService;
import com.zb.fincore.pms.service.dal.dao.GlobalConfigDao;
import com.zb.fincore.pms.service.dal.dao.ProductApprovalDao;
import com.zb.fincore.pms.service.dal.dao.ProductContractDao;
import com.zb.fincore.pms.service.dal.dao.ProductDao;
import com.zb.fincore.pms.service.dal.dao.ProductLadderDao;
import com.zb.fincore.pms.service.dal.dao.ProductPeriodDao;
import com.zb.fincore.pms.service.dal.dao.ProductProfitDao;
import com.zb.fincore.pms.service.dal.dao.ProductStockChangeFlowDao;
import com.zb.fincore.pms.service.dal.dao.ProductStockDao;
import com.zb.fincore.pms.service.dal.model.GlobalConfig;
import com.zb.fincore.pms.service.dal.model.Product;
import com.zb.fincore.pms.service.dal.model.ProductApproval;
import com.zb.fincore.pms.service.dal.model.ProductContract;
import com.zb.fincore.pms.service.dal.model.ProductLadder;
import com.zb.fincore.pms.service.dal.model.ProductPeriod;
import com.zb.fincore.pms.service.dal.model.ProductProfit;
import com.zb.fincore.pms.service.dal.model.ProductStock;
import com.zb.fincore.pms.service.dal.model.ProductStockChangeFlow;
import com.zb.fincore.pms.service.order.OrderService;
import com.zb.fincore.pms.service.product.BaseProductService;
import com.zb.fincore.pms.service.product.ProductCacheForP2PService;
import com.zb.fincore.pms.service.product.ProductCacheService;
import com.zb.fincore.pms.service.product.validate.ProductDbServiceParameterValidator;
import com.zb.fincore.pms.service.trade.TradeService;
import com.zb.fincore.pms.service.txs.TXSService;
import com.zb.p2p.match.api.req.AssetMatchReq;
import com.zb.p2p.match.api.req.ProductAssetMatchDTO;
//import com.zb.fincore.ta.ladder.facade.ProductFacade;

/**
 * 功能: 产品数据库接口类
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/6 0006 16:57
 * 版本: V1.0
 */
@Service
public abstract class AbstractProductServiceImpl implements BaseProductService {

    private static Logger logger = LoggerFactory.getLogger(AbstractProductServiceImpl.class);

    /**
     * 参数校验器
     */
    @Autowired
    private ProductDbServiceParameterValidator validator;

    /**
     * 序列服务
     */
    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductPeriodDao productPeriodDao;

    @Autowired
    private ProductProfitDao productProfitDao;

    @Autowired
    private ProductStockDao productStockDao;

    @Autowired
    private ProductContractDao productContractDao;

    @Autowired
    private ProductLadderDao productLadderDao;

    @Autowired
    private ProductApprovalDao productApprovalDao;

    @Autowired
    private ProductStockChangeFlowDao productStockChangeFlowDao;

    @Autowired
    private GlobalConfigDao globalConfigDao;

    @Autowired
    @Qualifier("productCacheService")
    private ProductCacheService productCacheService;

    @Autowired
    private AMSService amsService;

//    /**
//     * ta 提供的服务
//     */
//    @Autowired
//    private ProductFacade productFacade;

//    @Autowired
//    private ProducerService producerService;

//    @Autowired
//    private AssetProductRelationServiceFacade assetProductRelationServiceFacade;

//    // V1.0 产品注册时通知资管
//    @Value("${product_register_notify_asset_mng_url}")
//    private String productRegisterNotifyAssetMngUrl;

//    // V2.0 产品注册时通知资管
//    @Value("${p2p_create_product_loan_relation_url}")
//    private String p2pCreateProductLoanRelationUrl;

    // V1.0 审核不通过时，通知资管
//    @Value("${product_approval_failure_release_asset_url}")
//    private String productApprovalFailureReleaseAssetUrl;

//    // V2.0 审核通过时，通知资管，资管再通知订单匹配(通知交易)
//    @Value("${p2p_notice_product_loan_relation_url}")
//    private String p2pNoticeProductLoanRelationUrl;

//    @Value("${product_related_asset_info_query_url}")
//    private String productRelatedAssetInfoQueryUrl;

//    @Value("${product_related_asset_list_query_url}")
//    private String productRelatedAssetListQueryUrl;

//    @Value("${txs_update_product_stock_url}")
//    private String txsUpdateProductStockUrl;

    @Autowired
    protected AesHttpClientUtils aesHttpClientUtils;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private ProductCacheForP2PService productCacheForP2PService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TXSService txsService;



//    /**
//     * 更新产品募集金额
//     *
//     * @param req
//     * @return
//     */
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
//    @Override
//    public BaseResponse updateCollectAmount( @Valid UpdateProductCollectAmountRequest req) {
//
//        Product product = productDao.selectProductByCodeForUpdate(req.getProductCode());
//        if (null == product) {
//            return BaseResponse.build(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
//        }
//
//        Product productInfo = productDao.queryProductDetailByProductCode(req.getProductCode());
//        if (null == productInfo) {
//            return BaseResponse.build(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
//        }
//
//        BaseResponse resp = validator.checkUpdateCollectAmountParameter(req.getCollectAmount(), productInfo.getProductProfit().getMinInvestAmount());
//        if (null != resp) {
//            return resp;
//        }
//        BigDecimal currentStock = product.getTotalAmount().add(req.getCollectAmount());
//        BigDecimal addStock = req.getCollectAmount().subtract(product.getTotalAmount());
//
//        Product tempProduct=new Product();
//        tempProduct.setId(product.getId());
//
//        tempProduct.setTotalAmount(req.getCollectAmount());
//        tempProduct.setModifyTime(new Date());
//
//		try {
//			 resp = callTxsUpdateProductStock(product, currentStock, addStock);
//			if(resp!=null){
//				if(Constants.SUCCESS_RESP_CODE.equals(resp.getRespCode())){
//					ProductStock stock = productStockDao.selectProductStockByProductCode(product.getProductCode());
//					BigDecimal stockAmount=stock.getStockAmount().add(addStock).compareTo(BigDecimal.ZERO)==1?stock.getStockAmount().add(addStock):BigDecimal.ZERO;
//					ProductStock tempStock=new ProductStock();
//					tempStock.setId(stock.getId());
//					tempStock.setStockAmount(stockAmount);
//					tempStock.setModifyTime(new Date());
//					productDao.updateByPrimaryKeySelective(tempProduct);
//					productStockDao.updateByPrimaryKeySelective(tempStock);
//				}
//			}
//		} catch (Exception e) {
//			logger.error("调用唐小僧更新产品库存系统异常",e);
//			resp=new BaseResponse();
//			resp.setRespCode(Constants.FAIL_RESP_CODE);
//			resp.setRespMsg("调用唐小僧更新产品库存系统异常");
//		}
//
//        return resp;
//    }
//
//    /**
//     * 调用唐小僧接口通知更新产品库存
//     *
//     * @param product
//     * @param currentStock
//     * @param addStock
//     * @throws Exception
//     */
//	private BaseResponse callTxsUpdateProductStock(Product product,BigDecimal currentStock, BigDecimal addStock) throws Exception {
//		BaseResponse resp=new BaseResponse();
//		String serialNo = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//		Map map=new HashMap<String,String>();
//		map.put("addStock", addStock);
//		map.put("currentStock", currentStock);
//		map.put("serialNo", serialNo);
//		map.put("productCode", product.getProductCode());
//		String requestJson=JsonUtils.object2Json(map);
//		logger.info("调用唐小僧更新产品库存请求报文："+requestJson);
//		String responseStr=HttpClientUtil.sendPostRequest(txsUpdateProductStockUrl+"/"+product.getProductCode(), requestJson);
//		logger.info("调用唐小僧更新产品库存响应报文："+responseStr);
//		if(StringUtils.isNotEmpty(responseStr)){
//			RespObj respObj = JsonUtils.json2Object(responseStr, RespObj.class);
//			if(RespObj.SUCCESS.equals(respObj.getCode())){
//				resp.setRespCode(Constants.SUCCESS_RESP_CODE);
//				return resp;
//			}else{
//				resp.setRespCode(Constants.FAIL_RESP_CODE);
//				resp.setRespMsg(respObj.getMessage());
//			}
//		}else{
//			resp.setRespCode(Constants.FAIL_RESP_CODE);
//			resp.setRespMsg("调用唐小僧更新产品库存系统异常");
//		}
//
//		return resp;
//	}

//    /**
//     * 产品上线 调用TA系统做产品登记
//     *
//     * @param product
//     * @throws BusinessException
//     */
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
//    public void notifyTaRegisterProduct(Product product) throws BusinessException {
//        try {
//            //dubbo 获取与该产品关联的资产列表
//            /*com.zb.fincore.ams.common.dto.PageQueryResponse<AssetProductRelationModel> response = queryAssetProductRelationUseDubbo(product.getProductCode(),
//                    product.getAssetPoolCode());*/
//            //http 获取与该产品关联的资产列表
//            com.zb.fincore.ams.common.dto.PageQueryResponse<AssetProductRelationModel> response = queryAssetProductRelationUseHttp(product.getProductCode(),
//                    product.getAssetPoolCode());
//
//            //组装TA系统 产品登记 需要的数据
//            ProductDTO productDTO = new ProductDTO();
//            productDTO.setProductCode(product.getProductCode());
//            productDTO.setProductName(product.getProductDisplayName());
//            productDTO.setProductLineCode(product.getProductLineCode());
//            productDTO.setPatternCode(product.getPatternCode());
//            productDTO.setSaleChannel(product.getSaleChannelCode());
//            productDTO.setDuration(product.getProductPeriod().getInvestPeriod());
//            productDTO.setDurationUnit(String.valueOf(product.getProductPeriod().getInvestPeriodUnit()));
//            productDTO.setProductAmount(product.getTotalAmount());
//            productDTO.setCalendarMode(String.valueOf(product.getCalendarMode()));
//            productDTO.setInterestDate(product.getProductPeriod().getValueTime());
//            productDTO.setEstablishDate(product.getProductPeriod().getExpectEstablishTime());
//            productDTO.setExpireDate(product.getProductPeriod().getExpectExpireTime());
//            productDTO.setSettlementDate(product.getProductPeriod().getExpectClearTime());
//            productDTO.setStageNum(product.getProductPeriod().getInvestPeriodLoopUnit());
//            productDTO.setTotalInterestStageCount(product.getProductPeriod().getInvestPeriodLoopCount());
//            productDTO.setYield(product.getProductProfit().getMinYieldRate());
//            productDTO.setInterestDays(product.getProductProfit().getBasicInterestsPeriod());
//            //将浮动利率传给TA
//            productDTO.setFloatingYieldRate(product.getProductProfit().getFloatingYieldRate());
//            //产品关联的资产信息
//            List<ProductAssetDTO> assetDTOList = new ArrayList<ProductAssetDTO>();
//            if (!CollectionUtils.isNullOrEmpty(response.getDataList())) {
//                List<AssetProductRelationModel> assetProductRelationModels = response.getDataList();
//                for (AssetProductRelationModel assetProductRelationModel : assetProductRelationModels) {
//                    ProductAssetDTO productAssetDTO = new ProductAssetDTO();
//                    productAssetDTO.setAssetCode(assetProductRelationModel.getAssetCode());
//                    productAssetDTO.setAssetPoolCode(assetProductRelationModel.getPoolCode());
//                    productAssetDTO.setAssetDate(assetProductRelationModel.getAssetExpireTime());
//                    productAssetDTO.setAssetName(assetProductRelationModel.getAssetName());
//                    productAssetDTO.setAssetAssignAmount(assetProductRelationModel.getAssetAmount());
//                    productAssetDTO.setAssetType(String.valueOf(null == assetProductRelationModel.getAssetType() ? 0 : assetProductRelationModel.getAssetType()));
//                    assetDTOList.add(productAssetDTO);
//                }
//                productDTO.setAssetList(assetDTOList);
//            }
//            //产品关联的阶梯信息
//            List<ProductLadderDTO> productLadderDTOList = new ArrayList<ProductLadderDTO>();
//            List<ProductLadder> productLadderList = product.getProductLadderList();
//            if (!CollectionUtils.isNullOrEmpty(productLadderList)) {
//                for (ProductLadder productLadder : productLadderList) {
//                    ProductLadderDTO productLadderDTO = new ProductLadderDTO();
//                    productLadderDTO.setCurrentStageNum(productLadder.getInvestPeriodLoopIndex());
//                    productLadderDTO.setTotalStageCount(productLadder.getInvestPeriodLoopCount());
//                    productLadderDTO.setYield(productLadder.getYieldRate());
//                    productLadderDTO.setStageBeginDate(productLadder.getValueStartTime());
//                    productLadderDTO.setStageEndDate(productLadder.getValueEndTime());
//                    productLadderDTO.setRedeemDate(productLadder.getNextRepayTime());
//                    productLadderDTO.setPoundage(productLadder.getPoundage());
//                    productLadderDTOList.add(productLadderDTO);
//                }
//                productDTO.setProductLadderDTOList(productLadderDTOList);
//            }
//
//            logger.debug("调用TA系统做产品登记 请求参数：" + JSONObject.toJSONString(productDTO));
//            com.zb.fincore.common.rpc.dto.BaseResponse bs = productFacade.registerProduct(productDTO);
//            if (null == bs || !Constants.SUCCESS_RESP_CODE.equals(bs.getResultCode())) {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用TA系统做产品登记失败:" + bs.getResultMsg());
//            }
//        } catch (Exception e) {
//            if (e instanceof BusinessException) {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, ((BusinessException) e).getResultMsg());
//            } else {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用系统失败", e);
//            }
//        }
//    }
//
//    /**
//     * TA资产登记  查询产品关联的资产列表
//     *
//     * @param
//     * @throws BusinessException
//     */
//    public com.zb.fincore.ams.common.dto.PageQueryResponse<AssetProductRelationModel> queryAssetProductRelationUseDubbo(String productCode, String assetPoolCode)
//            throws BusinessException {
//        QueryAssetProductRelationRequest request = new QueryAssetProductRelationRequest();
//        request.setProductCode(productCode);
//        request.setPoolCode(assetPoolCode);
//        com.zb.fincore.ams.common.dto.PageQueryResponse<AssetProductRelationModel> response = assetProductRelationServiceFacade.queryAssetProductRelation(request);
//        if (null == response || !Constants.SUCCESS_RESP_CODE.equals(response.getRespCode())) {
//            throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统查询关联资产失败:" + response.getRespMsg());
//        }
//        return response;
//    }
//
//    /**
//     * TA资产登记  查询产品关联的资产列表
//     *
//     * @param
//     * @throws BusinessException
//     */
//    public com.zb.fincore.ams.common.dto.PageQueryResponse<AssetProductRelationModel> queryAssetProductRelationUseHttp(String productCode, String assetPoolCode)
//            throws BusinessException {
//        try {
//            //获取与该产品关联的资产信息
//            String respContent = null;
//            net.sf.json.JSONObject obj = null;
//            Map<String, Object> assetNotifyParamMap = new HashMap<String, Object>();
//            // 产品编号
//            assetNotifyParamMap.put("productCode", productCode);
//            // 资产池编号
//            assetNotifyParamMap.put("poolCode", assetPoolCode);
//            // 调用远程服务
//            logger.debug("产品注册 调用资管请求参数：" + JSONObject.toJSONString(assetNotifyParamMap));
//            respContent = aesHttpClientUtils.sendPostRequest(productRelatedAssetListQueryUrl, JSONObject.toJSONString(assetNotifyParamMap));
//            logger.debug("产品注册 调用资管响应参数：" + respContent);
//            // 将json字符创转换成json对象
//            obj = net.sf.json.JSONObject.fromObject(respContent);
//
//            String[] dateFormats = new String[]{"yyyy-MM-dd HH:mm:ss"};
//            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
//            Map<String, Class> classMap = new HashMap<String, Class>();
//            classMap.put("dataList", AssetProductRelationModel.class);
//            com.zb.fincore.ams.common.dto.PageQueryResponse<AssetProductRelationModel> response =
//                    (com.zb.fincore.ams.common.dto.PageQueryResponse<AssetProductRelationModel>) net.sf.json.JSONObject.toBean(obj,
//                            com.zb.fincore.ams.common.dto.PageQueryResponse.class, classMap);
//            if (null == response || !Constants.SUCCESS_RESP_CODE.equals(response.getRespCode())) {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统查询关联资产失败:" + response.getRespMsg());
//            }
//            return response;
//        } catch (Exception e) {
//            if (e instanceof BusinessException) {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, ((BusinessException) e).getResultMsg());
//            } else {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用系统失败", e);
//            }
//        }
//    }

    /**
     * 货架系统 产品注册  基本信息注册
     *
     * @param req
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public RegisterProductResponse registerProductBaseInfo(@Valid RegisterProductBaseInfoRequest req) throws Exception {
        //1: 执行请求参数校验
        RegisterProductResponse resp = validator.checkRegisterProductBaseInfoParameter(req);
        if (null != resp) {
            return resp;
        }

        //2: 按照规则生成产品编号
        String productCode = null;
        if (req.getRegisterType().equals(RegisterTypeEnum.AUTO.getCode())) {
        	productCode = req.getProductCode();
        } else {
        	productCode = sequenceService.generateProductCode(Constants.SEQUENCE_NAME_PREFIX_PRODUCT,req.getPatternCode(),4);
            req.setProductCode(productCode);
        }

        //3: 入库产品信息
        Product product = new Product();
        BeanUtils.copy(req, product);
        product.setProductCode(productCode);
        product.setSaleStatus(ProductSaleStatusEnum.PRODUCT_SALE_STATUS_WAIT_DEPLOYED.getCode());
        product.setCollectStatus(ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_WAIT_COLLECT.getCode());
        product.setDisplayStatus(DisplayStatusEnum.INVISIBLE.getCode());
        product.setApprovalStatus(ProductApprovalStatusEnum.WAIT_APPROVAL.getCode());
        product.setSyncStatus(ProductSyncStatusEnum.UN_SYNC.getCode());

        GlobalConfig globalConfig = globalConfigDao.selectByPropertyName(Constants.PRODUCT_APPROVAL_SIGN_PROP_NAME);
        if (null == globalConfig || StringUtils.isBlank(globalConfig.getPropertyValue())) {
            product.setApprovalStatus(ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode());
            product.setSaleStatus(ProductSaleStatusEnum.PRODUCT_SALE_STATUS_DEPLOYED.getCode());
        }
        product.setApprovalRequireSign(null == globalConfig ? null : globalConfig.getPropertyValue());

        productDao.insertSelective(product);

        //入库产品库存信息
        registerProductStockInfo(req, product);

        //入库产品审核信息
        registerProductApprovalInfo(req, product);

        //4: 返回结果
        resp = BaseResponse.build(RegisterProductResponse.class);
        resp.setId(product.getId());
        resp.setProductCode(productCode);
        return resp;
    }

    /**
     * 货架系统 产品注册  投资期限信息注册
     *
     * @param req
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public RegisterProductResponse registerProductPeriodInfo(@Valid RegisterProductPeriodInfoRequest req) throws Exception {
        //1: 执行请求参数校验
        Product product = productDao.selectProductByCode(req.getProductCode());
        if (null == product) {
            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
        }
        RegisterProductResponse resp = validator.checkRegisterProductPeriodInfoParameter(req, product.getPatternCode());
        if (null != resp) {
            return resp;
        }

        //2: 入库产品期限信息
        ProductPeriod productPeriod = new ProductPeriod();
        BeanUtils.copy(req, productPeriod);
        productPeriod.setProductCode(product.getProductCode());
        productPeriod.setProductId(product.getId());

        switch (product.getPatternCode())
        {
            case "02":
            	//定期
            	productPeriod.setInvestPeriodUnit(ProductLockPeriodUnitEnum.DAY.getCode());
	        	if (null == req.getExpectClearTime()) {
	                //预期结清时间 = 到期日+1
	                Calendar cl = Calendar.getInstance();
	                cl.setTime(req.getExpectExpireTime());
	                cl.add(Calendar.DATE, 1);
	                productPeriod.setExpectClearTime(cl.getTime());
	            } else {
	                productPeriod.setExpectClearTime(req.getExpectClearTime());
	            }
	        	break;
            case "05":
            	//N 复投
            	productPeriod.setLockPeriod(productPeriod.getLockPeriod());
            	productPeriod.setLockPeriodUnit(ProductLockPeriodUnitEnum.DAY.getCode());
            	break;
        }

//        //如果是阶梯收益 产品
//        if (PatternCodeTypeEnum.LADDER.getCode().equals(product.getPatternCode())) {
//            int loop = productPeriod.getInvestPeriod() % productPeriod.getInvestPeriodLoopUnit();
//
//            /* if (loop == 0) {
//                productPeriod.setInvestPeriodLoopCount(productPeriod.getInvestPeriod() / productPeriod.getInvestPeriodLoopUnit());
//            }else{
//                productPeriod.setInvestPeriodLoopCount(productPeriod.getInvestPeriod() / productPeriod.getInvestPeriodLoopUnit() +1);
//            }*/
//
//            if (loop != 0) {
//                return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_LOOP_PERIOD_COUNT_NOT_CORRECT_RETURN_CODE,
//                        Constants.PRODUCT_LOOP_PERIOD_COUNT_NOT_CORRECT_RETURN_CODE_DESC);
//            }
//
//            productPeriod.setInvestPeriodLoopCount(productPeriod.getInvestPeriod() / productPeriod.getInvestPeriodLoopUnit());
//
//            productPeriod.setInvestPeriodLoopIndex(1);
//            //初始化 下一开放赎回时间
//            Date valueTime = productPeriod.getValueTime();
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(valueTime);
//            calendar.add(Calendar.DATE, productPeriod.getInvestPeriodLoopUnit() - 1);
//            productPeriod.setNextRedeemTime(calendar.getTime());
//            //初始化 下一回款时间
//            calendar.setTime(valueTime);
//            calendar.add(Calendar.DATE, productPeriod.getInvestPeriodLoopUnit());
//            productPeriod.setNextRepayTime(calendar.getTime());
//        }

        productPeriodDao.insertSelective(productPeriod);

        //3: 返回结果
        resp = BaseResponse.build(RegisterProductResponse.class);
        resp.setId(productPeriod.getProductId());
        resp.setProductCode(productPeriod.getProductCode());
        return resp;
    }

    /**
     * 货架系统 产品注册 投资收益信息注册
     *
     * @param req
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public RegisterProductResponse registerProductProfitInfo(@Valid RegisterProductProfitInfoRequest req) throws Exception {
        //1: 执行请求参数校验
        Product product = productDao.selectProductByCode(req.getProductCode());
        if (null == product) {
            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
        }
        RegisterProductResponse resp = validator.checkRegisterProductProfitInfoParameter(req, product.getRegisterType());
        if (null != resp) {
            return resp;
        }

        //2: 入库产品期限信息
        ProductProfit productProfit = new ProductProfit();
        BeanUtils.copy(req, productProfit);
        productProfit.setProductCode(product.getProductCode());
        productProfit.setProductId(product.getId());
        productProfit.setBasicInterestsPeriod(ProductBasicInterestsPeriodEnum.ProductBasicInterestsPeriod_365.getCode());
        productProfit.setUnit(ProductUnitEnum.RMB.getCode());

        switch (product.getPatternCode())
        {
            case "02":
            	//定期
                productProfit.setProfitType(req.getProfitType()==null ? ProductProfitTypeEnum.PERIODIC_VALUE.getCode() : req.getProfitType());//收益方式=固定起息日
                productProfit.setCalculateInvestType(req.getCalculateInvestType()==null ? ProductInvestTypeEnum.ONCE_PAY_ALL.getCode() : req.getCalculateInvestType());//计息方式=一次性还本付息
	        	break;
            case "05":
            	//N 复投
            	productProfit.setProfitType(req.getProfitType()==null ? ProductProfitTypeEnum.AVERAGE_CAPITAL_PLUS_INTEREST.getCode() : req.getProfitType());//收益方式=等额本息
                productProfit.setCalculateInvestType(req.getCalculateInvestType()==null ? ProductInvestTypeEnum.AVERAGE_CAPITAL_PLUS_INTEREST.getCode() : req.getCalculateInvestType());//计息方式=等额本息
            	break;
        }

//        //如果是阶梯收益 产品
//        if (PatternCodeTypeEnum.LADDER.getCode().equals(product.getPatternCode())) {
//            productProfit.setCurrentYieldRate(productProfit.getMinYieldRate());
//        }

        productProfitDao.insertSelective(productProfit);

        //3: 返回结果
        resp = BaseResponse.build(RegisterProductResponse.class);
        resp.setId(productProfit.getId());
        resp.setProductCode(productProfit.getProductCode());
        return resp;
    }

    /**
     * 货架系统 产品注册 产品库存信息注册
     *
     * @param req
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void registerProductStockInfo(@Valid RegisterProductBaseInfoRequest req, Product product) throws Exception {
        //入库产品产品库存信息
        ProductStock productStock = new ProductStock();
        productStock.setProductCode(product.getProductCode());
        productStock.setProductId(product.getId());
        productStock.setFrozenAmount(BigDecimal.ZERO);
        productStock.setSaleAmount(BigDecimal.ZERO);
        productStock.setStockAmount(product.getTotalAmount());
        productStock.setRedeemAmount(BigDecimal.ZERO);
        productStockDao.insertSelective(productStock);
    }

    /**
     * 货架系统 产品注册 入库 产品审核信息
     *
     * @param req
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void registerProductApprovalInfo(RegisterProductBaseInfoRequest req, Product product) throws Exception {
        //入库产品审核信息
        ProductApproval productApproval = new ProductApproval();
        productApproval.setProductCode(product.getProductCode());
        productApproval.setProductId(product.getId());
        productApproval.setApprovalStatus(ProductApprovalStatusEnum.WAIT_APPROVAL.getCode());
        productApproval.setApprovalBy(product.getCreateBy());
        productApproval.setCreateBy(product.getCreateBy());
        productApprovalDao.insertSelective(productApproval);
    }

//    /**
//     * 货架系统 产品注册 更新产品介绍信息
//     *
//     * @param req
//     * @param returnMap
//     * @return
//     * @throws Exception
//     */
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
//    public RegisterProductResponse registerProductInstruction(RegisterProductRequest req, Map<String, Object> returnMap) throws Exception {
//        //将参数放进产品介绍中
//        if (StringUtils.isNotBlank(req.getIntroduction())) {
//            net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject(req.getIntroduction());
//            if (obj.size() > 0) {
//                Iterator it = obj.keys();
//                // 遍历jsonObject数据，添加到Map对象
//                while (it.hasNext()) {
//                    String key = String.valueOf(it.next());
//                    String value = (String) obj.get(key);
//                    returnMap.put(key, value);
//                }
//            }
//        }
//        net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(returnMap);
//        String introduction = jsonObject.toString();
//        Product product = new Product();
//        product.setId(req.getProductId());
//        product.setIntroduction(req.getIntroduction());
//        productDao.updateByPrimaryKeySelective(product);
//
//        //3: 返回结果
//        RegisterProductResponse resp = BaseResponse.build(RegisterProductResponse.class);
//        resp.setId(req.getProductId());
//        resp.setProductCode(req.getProductCode());
//        return resp;
//    }

//    /**
//     * 货架系统 产品注册 产品合同信息注册
//     *
//     * @param req
//     * @return
//     */
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
//    @Override
//    public RegisterProductResponse registerProductContractInfo(RegisterProductContractInfoRequest req) throws Exception {
//        //1: 执行请求参数校验
//        Product product = productDao.selectProductByCode(req.getProductCode());
//        if (null == product) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
//        }
//        /*RegisterProductResponse resp = validator.checkRegisterProductContractInfoParameter(req, product);
//        if (null != resp) {
//            return resp;
//        }*/
//
//        if(!CollectionUtils.isNullOrEmpty(req.getProductContractList())){
//            //2: 入库产品合同信息
//            List<ProductContract> productContractList = BeanUtils.copyAs(req.getProductContractList(), ProductContract.class);
//            for (ProductContract productContract : productContractList) {
//                productContract.setProductId(product.getId());
//                productContract.setProductCode(product.getProductCode());
//                productContractDao.insertSelective(productContract);
//            }
//        }
//
//        //3: 返回结果
//        RegisterProductResponse resp = BaseResponse.build(RegisterProductResponse.class);
//        resp.setId(product.getId());
//        resp.setProductCode(product.getProductCode());
//        return resp;
//    }

//    /**
//     * 货架系统 产品注册 产品阶梯信息落地
//     *
//     * @param req
//     * @return
//     */
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
//    public RegisterProductResponse registerProductLadderInfo(RegisterProductLadderInfoRequest req) throws Exception {
//        //1: 执行请求参数校验
//        Product product = productDao.selectProductByCode(req.getProductCode());
//        if (null == product) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
//        }
//
//        ProductPeriod productPeriod = productPeriodDao.selectProductPeriodInfoByProductCode(req.getProductCode());
//        if (null == productPeriod) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_PERIOD_INFO_UN_EXISTS_CODE, Constants.PRODUCT_PERIOD_INFO_UN_EXISTS_CODE_DESC);
//        }
//
//        ProductProfit productProfit = productProfitDao.selectProductProfitInfoByProductCode(req.getProductCode());
//        if (null == productProfit) {
//            return BaseResponse.build(RegisterProductResponse.class, Constants.PRODUCT_PROFIT_INFO_UN_EXISTS_CODE, Constants.PRODUCT_PROFIT_INFO_UN_EXISTS_CODE_DESC);
//        }
//
//        RegisterProductResponse resp = validator.checkRegisterProductLadderInfoParameter(req);
//        if (null != resp) {
//            return resp;
//        }
//
//        //2: 入库阶梯产品 信息
//        ProductLadder productLadder = new ProductLadder();
//        productLadder.setProductId(product.getId());
//        productLadder.setProductCode(product.getProductCode());
//        productLadder.setInvestPeriod(productPeriod.getInvestPeriod());
//        productLadder.setInvestPeriodLoopUnit(productPeriod.getInvestPeriodLoopUnit());
//        productLadder.setInvestPeriodLoopCount(productPeriod.getInvestPeriodLoopCount());
//
//        Date valueTime = productPeriod.getValueTime();
//
//        List<ProductLadderModel> productLadderModels = req.getProductLadderList();
//        Collections.sort(productLadderModels, new Comparator<ProductLadderModel>() {
//            /*
//             * int compare(ProductLadderModel o1, ProductLadderModel o2) 返回一个基本类型的整型，
//             * 返回负数表示：o1 小于o2，
//             * 返回0 表示：o1和o2相等，
//             * 返回正数表示：o1大于o2。
//             */
//            public int compare(ProductLadderModel o1, ProductLadderModel o2) {
//                //按照轮次进行升序排列
//                if (o1.getInvestPeriodLoopIndex() > o2.getInvestPeriodLoopIndex()) {
//                    return 1;
//                }
//                if (o1.getInvestPeriodLoopIndex() == o2.getInvestPeriodLoopIndex()) {
//                    return 0;
//                }
//                return -1;
//            }
//        });
//
//        //产品期限%循环周期 余数  可判断是否为固定周期产品;
//        int loop = productPeriod.getInvestPeriod() % productPeriod.getInvestPeriodLoopUnit();
//
//        for (int i = 0; i < productLadderModels.size(); i++) {
//            ProductLadderModel productLadderModel = productLadderModels.get(i);
//
//            productLadder.setId(null);
//            productLadder.setInvestPeriodLoopIndex(productLadderModel.getInvestPeriodLoopIndex());
//            productLadder.setPoundage(productLadderModel.getPoundage());
//            productLadder.setYieldRate(productLadderModel.getYieldRate());
//
//            Integer currentLoopIncDate = productPeriod.getInvestPeriodLoopUnit() * i;
//
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(valueTime);
//            calendar.add(Calendar.DATE, currentLoopIncDate);
//            //阶段起息时间
//            productLadder.setValueStartTime(calendar.getTime());
//
//            //非固定周期的 阶梯产品
//            if (loop != 0 && productLadder.getInvestPeriodLoopIndex() == productPeriod.getInvestPeriodLoopCount()) {
//                calendar.add(Calendar.DATE, loop - 1);
//            }else{
//                //固定周期的 阶梯产品
//                calendar.add(Calendar.DATE, productPeriod.getInvestPeriodLoopUnit() - 1);
//            }
//
//            //阶段止息时间
//            productLadder.setValueEndTime(calendar.getTime());
//            //下一开放赎回时间
//            productLadder.setNextRedeemTime(calendar.getTime());
//
//            calendar.add(Calendar.DATE, 1);
//            //下一回款时间
//            productLadder.setNextRepayTime(calendar.getTime());
//
//            //最后一个周期的回款时间 等于结清日
//            if (productLadder.getInvestPeriodLoopIndex() == productPeriod.getInvestPeriodLoopCount()) {
//                productLadder.setNextRepayTime(productPeriod.getExpectClearTime());
//            }
//            productLadderDao.insertSelective(productLadder);
//        }
//
//        //3: 返回结果
//        resp = BaseResponse.build(RegisterProductResponse.class);
//        resp.setId(product.getId());
//        resp.setProductCode(product.getProductCode());
//        return resp;
//    }

    /**
     * 货架系统 产品注册
     *
     * @param req
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public RegisterProductResponse registerProduct(@Valid RegisterProductRequest req) throws Exception {
        RegisterProductResponse response = null;

        //生成产品期数
        String numberPeriod = sequenceService.generateProductNameCode(Constants.SEQUENCE_NAME_PREFIX_PRODUCT_NAME + req.getProductLineId(), req.getPatternCode());
        req.setNumberPeriod(numberPeriod);

        //1:注册产品基本信息,并入库产品库存信息
        RegisterProductBaseInfoRequest registerProductBaseInfoRequest = BeanUtils.copyAs(req, RegisterProductBaseInfoRequest.class);
        response = registerProductBaseInfo(registerProductBaseInfoRequest);
        if (!Constants.SUCCESS_RESP_CODE.equals(response.getRespCode())) {
            throw new BusinessException(response.getRespCode(), response.getRespMsg());
        }
        req.setProductCode(response.getProductCode());
        req.setProductId(response.getId());

        //2:注册产品期限相关信息及阶梯信息
        RegisterProductPeriodInfoRequest registerProductPeriodInfoRequest = BeanUtils.copyAs(req, RegisterProductPeriodInfoRequest.class);
        response = registerProductPeriodInfo(registerProductPeriodInfoRequest);
        if (!Constants.SUCCESS_RESP_CODE.equals(response.getRespCode())) {
            throw new BusinessException(response.getRespCode(), response.getRespMsg());
        }

        //3:注册产品投资收益相关信息及阶梯信息
        RegisterProductProfitInfoRequest registerProductProfitInfoRequest = BeanUtils.copyAs(req, RegisterProductProfitInfoRequest.class);
        response = registerProductProfitInfo(registerProductProfitInfoRequest);
        if (!Constants.SUCCESS_RESP_CODE.equals(response.getRespCode())) {
            throw new BusinessException(response.getRespCode(), response.getRespMsg());
        }

        if (req.getPatternCode().equals(PatternCodeTypeEnum.PERIODIC_REGULAR.getCode()) ) {// 定期 V2.0
        	// 通知资管系统产品与借款订单匹配
            List<String> loanNoList = new ArrayList<String>();
            String[] loanNoAarry = null;
            String loanOrderNoSet = req.getLoanOrderNoSet();
            try {
                if (StringUtils.isNotEmpty(loanOrderNoSet)) {
                    loanNoAarry = loanOrderNoSet.split(";");
                }
            } catch (Exception e) {
                logger.error("【产品注册】借款订单编号集格式不正确：" + loanOrderNoSet);
                throw new BusinessException(Constants.LOAN_ORDER_NO_SET_FORMAT_ERROR_CODE, Constants.LOAN_ORDER_NO_SET_FORMAT_ERROR_CODE_DESC);
            }
            if (loanNoAarry.length > 0) {
                for (int i=0; i<loanNoAarry.length; i++) {
                    loanNoList.add(loanNoAarry[i]);
                }
            }
//            this.notifyAssetCreateProductLoanRelationHttp(req.getProductCode(), req.getProductName(), loanNoList);
            amsService.notifyAssetCreateProductLoanRelationHttp(req.getProductCode(), req.getProductName(), loanNoList, req.getPayChannel());
        } else if (req.getPatternCode().equals(PatternCodeTypeEnum.N_LOOP_PLAN.getCode()) ) {// N复投计划 V3.0
        	if (req.getRegisterType().equals(RegisterTypeEnum.UNAUTO.getCode())) {
        		CreateAssetProductRelationRequest createReq = new CreateAssetProductRelationRequest();
            	createReq.setProductCode(req.getProductCode());
            	createReq.setPoolCode(req.getAssetPoolCode());
            	createReq.setProductAmount(req.getTotalAmount());
            	createReq.setProductType(req.getOpenType());
            	amsService.createAssetProductRelationHttp(createReq);
        	}
        }

//        //4:注册产品合同相关信息
//        RegisterProductContractInfoRequest registerProductContractInfoRequest = BeanUtils.copyAs(req, RegisterProductContractInfoRequest.class);
//        response = registerProductContractInfo(registerProductContractInfoRequest);
//        if (!Constants.SUCCESS_RESP_CODE.equals(response.getRespCode())) {
//            throw new BusinessException(response.getRespCode(), response.getRespMsg());
//        }
//
//        //5:注册产品阶梯相关信息
//        RegisterProductLadderInfoRequest registerProductLadderInfoRequest = BeanUtils.copyAs(req, RegisterProductLadderInfoRequest.class);
//        response = registerProductLadderInfo(registerProductLadderInfoRequest);
//        if (!Constants.SUCCESS_RESP_CODE.equals(response.getRespCode())) {
//            throw new BusinessException(response.getRespCode(), response.getRespMsg());
//        }
//
//        //dubbo方式 调用资管系统
//        //notifyAmsAssociateAssetPoolUseDubbo(req.getProductCode(), req.getAssetPoolCode(), req.getTotalAmount(), req.getValueTime(), req.getExpectExpireTime());
//        //http方式 调用资管系统
//        Map<String, Object> returnMap = notifyAmsAssociateAssetPoolUseHttp(req.getProductCode(), req.getAssetPoolCode(), req.getTotalAmount(), req.getValueTime(), req.getExpectExpireTime());
//
//        //处理产品介绍信息；
//        response = registerProductInstruction(req, returnMap);
//        if (!Constants.SUCCESS_RESP_CODE.equals(response.getRespCode())) {
//            throw new BusinessException(response.getRespCode(), response.getRespMsg());
//        }

        return response;
    }

//    /**
//     * 产品注册时，通知资管系统产品与借款订单匹配 V2.0
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

//  /**
//  * 产品注册 产品部署，通知资管产品编码
//  *
//  * @param productCode
//  * @param assetPoolCode
//  * @param totalAmount
//  * @throws BusinessException
//  */
// private void notifyAmsAssociateAssetPoolUseDubbo(String productCode, String assetPoolCode, BigDecimal totalAmount, Date productValueStartTime, Date expectExpireTime) throws BusinessException {
//     try {
//         CreateAssetProductRelationRequest request = new CreateAssetProductRelationRequest();
//         request.setProductCode(productCode);
//         request.setPoolCode(assetPoolCode);
//         request.setProductAmount(totalAmount);
//         request.setProductValueStartTime(productValueStartTime);
//         request.setProductExpireTime(expectExpireTime);
//         logger.debug("产品注册 调用资管请求参数：" + JSONObject.toJSONString(request));
//         CreateAssetProductRelationResponse response = assetProductRelationServiceFacade.createAssetProductRelation(request);
//         if (!Constants.SUCCESS_RESP_CODE.equals(response.getRespCode())) {
//             throw new BusinessException(Constants.FAIL_RESP_CODE, "资管系统调用失败:" + response.getRespMsg());
//         }
//     } catch (Exception e) {
//         if (e instanceof BusinessException) {
//             throw new BusinessException(Constants.FAIL_RESP_CODE, ((BusinessException) e).getResultMsg());
//         } else {
//             throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统失败", e);
//         }
//     }
// }

// /**
//  * 产品注册 产品部署，通知资管产品编码 V1.0
//  *
//  * @param productCode
//  * @param assetPoolCode
//  * @param totalAmount
//  * @throws BusinessException
//  */
// public Map notifyAmsAssociateAssetPoolUseHttp(String productCode, String assetPoolCode, BigDecimal totalAmount, Date productValueStartTime, Date expectExpireTime) throws BusinessException {
//     try {
//         String respContent = null;
//         net.sf.json.JSONObject obj = null;
//         Map<String, Object> assetNotifyParamMap = new HashMap<String, Object>();
//         // 产品编号
//         assetNotifyParamMap.put("productCode", productCode);
//         // 资产池编号
//         assetNotifyParamMap.put("poolCode", assetPoolCode);
//         // 产品规模
//         assetNotifyParamMap.put("productAmount", totalAmount);
//         // 产品起息时间
//         assetNotifyParamMap.put("productValueStartTime", productValueStartTime);
//         // 产品到期时间
//         assetNotifyParamMap.put("productExpireTime", expectExpireTime);
//
//         // 调用远程服务
//         logger.debug("产品注册 调用资管请求参数：" + JSONObject.toJSONString(assetNotifyParamMap));
//         respContent = aesHttpClientUtils.sendPostRequest(productRegisterNotifyAssetMngUrl, JSONObject.toJSONString(assetNotifyParamMap));
//         logger.debug("产品注册 调用资管响应参数：" + respContent);
//         // 将json字符创转换成json对象
//         obj = net.sf.json.JSONObject.fromObject(respContent);
//         // 判断远程URl调用是否成功
//         String notifyRespCode = obj.getString("respCode");
//         if (!Constants.SUCCESS_RESP_CODE.equals(notifyRespCode)) {
//             throw new BusinessException(Constants.FAIL_RESP_CODE, "资管系统调用失败:" + obj.getString("respMsg"));
//         }
//
//         /**
//          * 返回资产池的 受托方和 合作机构信息
//          */
//         String infoModel = obj.getString("infoModel");
//         net.sf.json.JSONObject jsonInfoModel = net.sf.json.JSONObject.fromObject(infoModel);
//         String trusteeName = jsonInfoModel.getString("trusteeName");
//         String cooperationOrgName = jsonInfoModel.getString("cooperationOrgName");
//         String investTargetIntroduction = jsonInfoModel.getString("investTargetIntroduction");
//         Map<String, Object> assetReturnParamMap = new HashMap<String, Object>();
//         assetReturnParamMap.put("trusteeName", null == trusteeName ? "" : trusteeName);
//         assetReturnParamMap.put("cooperationOrgName", null == cooperationOrgName ? "" : cooperationOrgName);
//         assetReturnParamMap.put("investTargetIntroduction", null == investTargetIntroduction ? "" : investTargetIntroduction);
//         return assetReturnParamMap;
//
//     } catch (Exception e) {
//         if (e instanceof BusinessException) {
//             throw new BusinessException(Constants.FAIL_RESP_CODE, ((BusinessException) e).getResultMsg());
//         } else {
//             throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统失败", e);
//         }
//     }
// }

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
        Product product = productDao.queryProductDetailByProductCode(req.getProductCode());
        if (null == product) {
            return BaseResponse.build(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
        }

        BaseResponse resp = validator.checkApproveProductRequestParameter(req, product);
        if (null != resp) {
            return resp;
        }

        String curRequireApprovalSign = product.getApprovalRequireSign();
        String restNeedApprovalSign = "";
        int index = curRequireApprovalSign.indexOf(",");
        if (index != -1) {
            curRequireApprovalSign = curRequireApprovalSign.substring(0, index);
            restNeedApprovalSign = product.getApprovalRequireSign().substring(index + 1);
        }
        if (!curRequireApprovalSign.equals(req.getSign())) {
            return BaseResponse.build(Constants.PRODUCT_APPROVAL_NO_PRIVILEGE_CODE, Constants.PRODUCT_APPROVAL_NO_PRIVILEGE_CODE_DESC);
        }

        ProductApproval productApproval = new ProductApproval();
        BeanUtils.copy(req, productApproval);
        productApproval.setProductId(product.getId());
        productApproval.setProductCode(product.getProductCode());
        productApprovalDao.insertSelective(productApproval);

        int productApprovalStatus = req.getApprovalStatus();
        Map<String, Object> params = new HashMap<String, Object>();
        //产品审核不通过，通知资管释放注册时关联的资产池
        if (ProductApprovalStatusEnum.APPROVAL_FAILURE.getCode() == req.getApprovalStatus()) {
        	if (product.getPatternCode().equals(PatternCodeTypeEnum.N_LOOP_PLAN.getCode())) {//N复投计划3.0，新增
        		CancelAssetProductRelationRequest cancelReq = new CancelAssetProductRelationRequest();
        		cancelReq.setProductCode(product.getProductCode());
            	amsService.cancelAssetProductRelationHttp(cancelReq);
        	}
            if (product.getPatternCode().equals(PatternCodeTypeEnum.PERIODIC_REGULAR.getCode())) {//定期类，唐小僧定期V2.0版，新增
                amsService.notifyAssetCancelProductLoanRelationHttp(product.getProductCode());
            }

        	//----- V1.0 用的 start ----------
//            //审核不通过调用资管系统释放资产
//            notifyAmsReleaseAssetPoolUseHttp(product.getProductCode(), product.getAssetPoolCode());
            //----- V1.0 用的 end ----------
        } else {
            //当前审核通过，如果不存在下一个审核人，设置为审核通过，否则，状态为待审核
            if (!StringUtils.isBlank(restNeedApprovalSign)) {
                productApprovalStatus = ProductApprovalStatusEnum.WAIT_APPROVAL.getCode();
            } else {
                params.put("saleStatus", ProductSaleStatusEnum.PRODUCT_SALE_STATUS_DEPLOYED.getCode());
            }
        }

        params.put("id", product.getId());
        params.put("approvalStatus", productApprovalStatus);
        params.put("originalStatus", product.getApprovalStatus());
        params.put("approvalSign", StringUtils.isBlank(product.getApprovalSign()) ? curRequireApprovalSign : (product.getApprovalSign() + "," + curRequireApprovalSign));
        params.put("approvalRequireSign", restNeedApprovalSign);
        params.put("lastApprovalTime", new Date());
        productDao.updateProductApprovalStatusById(params);

//        if (ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode() == productApprovalStatus) {
//            //审核通过 调用TA系统 做产品登记
//            notifyTaRegisterProduct(product);
//        }
        if (ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode() == productApprovalStatus) {
            switch (product.getPatternCode())
            {
                case "02"://定期 2.0
                    amsService.notifyAssetProductLoanRelationHttp(product.getProductCode(), product.getProductName(), productApprovalStatus);
                    break;
                case "05"://N复投计划 3.0
                    //N 复投
                    SynAssetProductRelationRequest synReq = new SynAssetProductRelationRequest();
                    synReq.setProductCode(product.getProductCode());
                    synReq.setProductName(product.getProductName());
                    amsService.synAssetProductRelation(synReq);
                    break;
            }
        }

//        //定期 V2.0
//        if (product.getPatternCode().equals(PatternCodeTypeEnum.PERIODIC_REGULAR.getCode())) {
//        	// 通知资管通知订单匹配(通知交易)
////            this.notifyAssetProductLoanRelationHttp(product.getProductCode(), product.getProductName(), productApprovalStatus);
//        	amsService.notifyAssetProductLoanRelationHttp(product.getProductCode(), product.getProductName(), productApprovalStatus);
//        }

        return BaseResponse.build();
    }

//    /**
//     * 产品审核不通过 通知资管释放资产
//     *
//     * @param productCode
//     * @param assetPoolCode
//     * @throws BusinessException
//     */
//    private void notifyAmsReleaseAssetPool(String productCode, String assetPoolCode) throws BusinessException {
//        try {
//            CancelAssetProductRelationRequest request = new CancelAssetProductRelationRequest();
//            request.setProductCode(productCode);
//            com.zb.fincore.ams.common.dto.BaseResponse response = assetProductRelationServiceFacade.cancelAssetProductRelation(request);
//            if (!Constants.SUCCESS_RESP_CODE.equals(response.getRespCode())) {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统释放资产失败:" + response.getRespMsg());
//            }
//        } catch (Exception e) {
//            if (e instanceof BusinessException) {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, ((BusinessException) e).getResultMsg());
//            } else {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统失败", e);
//            }
//        }
//    }

//    /**
//     * 产品审核，通知资管系统通知订单匹配 V2.0
//     *
//     * @param productCode
//     * @param productName
//     * @param productStatus
//     * @throws BusinessException
//     */
//    private void notifyAssetProductLoanRelationHttp(String productCode, String productName, Integer productStatus) throws BusinessException {
//        try {
//            String respContent = null;
//            net.sf.json.JSONObject obj = null;
//            Map<String, Object> assetNotifyParamMap = new HashMap<String, Object>();
//            // 产品编号
//            assetNotifyParamMap.put("productCode", productCode);
//            assetNotifyParamMap.put("productName", productName);
//            assetNotifyParamMap.put("productStatus", productStatus);
//            // 调用远程服务
//            logger.debug("【产品审核】 通知资管系统通知订单匹配请求参数：" + JSONObject.toJSONString(assetNotifyParamMap));
//            respContent = aesHttpClientUtils.sendPostRequest(p2pNoticeProductLoanRelationUrl, JSONObject.toJSONString(assetNotifyParamMap));
//            logger.debug("【产品审核】 通知资管系统通知订单匹配响应参数：" + respContent);
//            // 将json字符创转换成json对象
//            obj = net.sf.json.JSONObject.fromObject(respContent);
//            // 判断远程URl调用是否成功
//            String notifyRespCode = obj.getString("respCode");
//            if (!Constants.SUCCESS_RESP_CODE.equals(notifyRespCode)) {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用【资管系统-通知订单匹配】错误:" + obj.getString("respMsg"));
//            }
//        } catch (Exception e) {
//            logger.error("【产品审核】 调用资管系统资管系统通知订单匹配失败:productCode={} \n {}", productCode, e );
//            if (e instanceof BusinessException) {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, ((BusinessException) e).getResultMsg());
//            } else {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统失败", e);
//            }
//        }
//    }

//    /**
//     * 产品审核不通过 通知资管释放资产 V1.0
//     *
//     * @param productCode
//     * @param assetPoolCode
//     * @throws BusinessException
//     */
//    private void notifyAmsReleaseAssetPoolUseHttp(String productCode, String assetPoolCode) throws BusinessException {
//        try {
//            String respContent = null;
//            net.sf.json.JSONObject obj = null;
//            Map<String, Object> assetNotifyParamMap = new HashMap<String, Object>();
//            // 产品编号
//            assetNotifyParamMap.put("productCode", productCode);
//            // 调用远程服务
//            logger.debug("产品审核不通过 通知资管释放资产请求参数：" + JSONObject.toJSONString(assetNotifyParamMap));
//            respContent = aesHttpClientUtils.sendPostRequest(productApprovalFailureReleaseAssetUrl, JSONObject.toJSONString(assetNotifyParamMap));
//            logger.debug("产品审核不通过 通知资管释放资产响应参数：" + respContent);
//            // 将json字符创转换成json对象
//            obj = net.sf.json.JSONObject.fromObject(respContent);
//            // 判断远程URl调用是否成功
//            String notifyRespCode = obj.getString("respCode");
//            if (!Constants.SUCCESS_RESP_CODE.equals(notifyRespCode)) {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统释放资产失败:" + obj.getString("respMsg"));
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
     * 产品上线
     *
     * @param req
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public BaseResponse putProductOnLine(@Valid UpdateProductSaleStatusRequest req) throws Exception {
        Product product = productDao.selectProductByCode(req.getProductCode());
        if (null == product) {
            return BaseResponse.build(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
        }

        BaseResponse resp = validator.checkPutProductOnLineRequestParameter(product);
        if (null != resp) {
            return resp;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", product.getId());
        params.put("originalStatus", product.getSaleStatus());
        params.put("saleStatus", ProductSaleStatusEnum.PRODUCT_SALE_STATUS_ON_LINE.getCode());

        productDao.updateProductSaleStatusById(params);

        ProductPeriod productPeriod = new ProductPeriod();
        productPeriod.setProductCode(product.getProductCode());
        productPeriod.setOnlineTime(new Date());
        productPeriodDao.updateActualTimeByProductCode(productPeriod);

        return BaseResponse.build();
    }

    /**
     * 产品下线
     *
     * @param req
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public BaseResponse putProductOffLine(@Valid UpdateProductSaleStatusRequest req) throws Exception {

        Product product = productDao.selectProductByCode(req.getProductCode());
        if (null == product) {
            return BaseResponse.build(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
        }

        if (ProductSaleStatusEnum.PRODUCT_SALE_STATUS_OFF_LINE.getCode() == product.getSaleStatus()) {
            return BaseResponse.build(Constants.PRODUCT_ALREADY_OFF_LINE_RESULT_CODE,
                    Constants.PRODUCT_ALREADY_OFF_LINE_RESULT_CODE_DESC);
        }

        if (ProductSaleStatusEnum.PRODUCT_SALE_STATUS_ON_LINE.getCode() != product.getSaleStatus()) {
            return BaseResponse.build(Constants.PRODUCT_UN_ON_LINE_OFF_LINE_RETURN_CODE,
                    Constants.PRODUCT_UN_ON_LINE_OFF_LINE_RETURN_CODE_DESC);
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

//        //通知订单系统产品售罄（目的：但产品下线、募集结束时，同步产品状态，已达到页面是否显示）
//        List<String> productCodeList = new ArrayList<>();
//        productCodeList.add(req.getProductCode());
//        orderService.tradeNotifyOrderHttp(productCodeList);
        txsService.syncStatusNoticeHttp(req.getProductCode());


        return BaseResponse.build();
    }

    /**
     * 产品上架接口，用于C端显示
     *
     * @param req
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public BaseResponse putProductDisplay(@Valid UpdateProductDisplayStatusRequest req) throws Exception {
        Product product = productDao.selectProductByCode(req.getProductCode());
        if (null == product) {
            return BaseResponse.build(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
        }

        if (ProductDisplayStatusEnum.VISIBLE.getCode() == product.getDisplayStatus()) {
            return BaseResponse.build(Constants.PRODUCT_ALREADY_ON_SHELF_RESULT_CODE,
                    Constants.PRODUCT_ALREADY_ON_SHELF_RESULT_CODE_DESC);
        }

        //产品审核状态已通过，且销售状态为 上线才可以 上架操作
        if (product.getApprovalStatus() != ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode() ||
                product.getSaleStatus() != ProductSaleStatusEnum.PRODUCT_SALE_STATUS_ON_LINE.getCode()) {
            return BaseResponse.build(Constants.PRODUCT_UN_APPROVALED_OR_UN_ONLINE_RESULT_CODE,
                    Constants.PRODUCT_UN_APPROVALED_OR_UN_ONLINE_RESULT_CODE_DESC);
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", product.getId());
        params.put("originalStatus", product.getDisplayStatus());
        params.put("displayStatus", ProductDisplayStatusEnum.VISIBLE.getCode());//上架

        productDao.updateProductDisplayStatusById(params);

        //刷新缓存
        productCacheService.refreshProductCache();

        return BaseResponse.build();
    }

    /**
     * 产品下架接口，用于C端显示
     *
     * @param req
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public BaseResponse putProductUnDisplay(@Valid UpdateProductDisplayStatusRequest req) throws Exception {
        Product product = productDao.selectProductByCode(req.getProductCode());
        if (null == product) {
            return BaseResponse.build(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
        }

        if (ProductDisplayStatusEnum.INVISIBLE.getCode() == product.getDisplayStatus()) {
            return BaseResponse.build(Constants.PRODUCT_ALREADY_UN_DISPLAY_RESULT_CODE,
                    Constants.PRODUCT_ALREADY_UN_DISPLAY_RESULT_CODE_DESC);
        }

        //产品审核状态已通过，且销售状态不为未部署和已归档
        if (product.getApprovalStatus() != ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode() ||
                product.getSaleStatus() == ProductSaleStatusEnum.PRODUCT_SALE_STATUS_WAIT_DEPLOYED.getCode() ||
                product.getSaleStatus() == ProductSaleStatusEnum.PRODUCT_SALE_STATUS_FILED.getCode()) {
            return BaseResponse.build(Constants.PRODUCT_UNKNOWN_COLLECT_STATUS_CHANGED_RESULT_CODE,
                    Constants.PRODUCT_UNKNOWN_COLLECT_STATUS_CHANGED_RESULT_CODE_DESC);
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", product.getId());
        params.put("originalStatus", product.getDisplayStatus());
        params.put("displayStatus", ProductDisplayStatusEnum.INVISIBLE.getCode());//下架

        productDao.updateProductDisplayStatusById(params);
        //刷新缓存
        productCacheService.refreshProductCache();

        return BaseResponse.build();
    }

    /**
     * 产品详情
     *
     * @param req
     * @return
     */
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

        // 产品合同信息列表
        List<ProductContractModel> contractModelList = null;
        List<ProductContract> contractList = product.getProductContractList();
        if (null != contractList && contractList.size() > 0) {
            contractModelList = BeanUtils.copyAs(contractList, ProductContractModel.class);
            productModel.setProductContractModelList(contractModelList);
        }

        // 产品合同信息列表
        List<ProductLadderModel> productLadderModelList = null;
        List<ProductLadder> ladderList = product.getProductLadderList();
        if (null != ladderList && ladderList.size() > 0) {
            productLadderModelList = BeanUtils.copyAs(ladderList, ProductLadderModel.class);
            productModel.setProductLadderModelList(productLadderModelList);
        }

        response.setProductModel(productModel);
        return response;
    }

//    /**
//     * 查询产品详情时  查询产品关联的资产相关信息
//     *
//     * @param productModel
//     * @throws BusinessException
//     */
//    @Deprecated
//    public void queryProductRelatedAssetInfoUseDubbo(ProductModel productModel) throws BusinessException {
//        try {
//            //获取与该产品关联的资产信息
//            QueryProductRelatedAssetInfoRequest request = new QueryProductRelatedAssetInfoRequest();
//            request.setProductCode(productModel.getProductCode());
//            request.setPoolCode(productModel.getAssetPoolCode());
//            QueryProductRelatedAssetInfoResponse response = assetProductRelationServiceFacade.queryProductRelatedAssetInfo(request);
//            if (null == response || !Constants.SUCCESS_RESP_CODE.equals(response.getRespCode())) {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统查询资产信息失败:" + response.getRespMsg());
//            }
//
//            com.zb.fincore.ams.facade.model.ProductRelatedAssetInfoModel infoModel = response.getInfoModel();
//            if (null != infoModel) {
//                ProductRelatedAssetInfoModel productRelatedAssetInfoModel = BeanUtils.copyAs(infoModel, ProductRelatedAssetInfoModel.class);
//                //productModel.setProductRelatedAssetInfoModel(productRelatedAssetInfoModel);
//            }
//        } catch (Exception e) {
//            if (e instanceof BusinessException) {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, ((BusinessException) e).getResultMsg());
//            } else {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用系统失败", e);
//            }
//        }
//    }

//    /**
//     * 查询产品详情时  查询产品关联的资产相关信息
//     *
//     * @param productModel
//     * @throws BusinessException
//     */
//    @Deprecated
//    public void queryProductRelatedAssetInfoUseHttp(ProductModel productModel) throws BusinessException {
//        try {
//            //获取与该产品关联的资产信息
//            String respContent = null;
//            net.sf.json.JSONObject obj = null;
//            Map<String, Object> assetNotifyParamMap = new HashMap<String, Object>();
//            // 产品编号
//            assetNotifyParamMap.put("productCode", productModel.getProductCode());
//            // 资产池编号
//            assetNotifyParamMap.put("poolCode", productModel.getAssetPoolCode());
//            // 调用远程服务
//            logger.debug("产品注册 调用资管请求参数：" + JSONObject.toJSONString(assetNotifyParamMap));
//            respContent = aesHttpClientUtils.sendPostRequest(productRelatedAssetInfoQueryUrl, JSONObject.toJSONString(assetNotifyParamMap));
//            logger.debug("产品注册 调用资管响应参数：" + respContent);
//            // 将json字符创转换成json对象
//            obj = net.sf.json.JSONObject.fromObject(respContent);
//            QueryProductRelatedAssetInfoResponse response = (QueryProductRelatedAssetInfoResponse) net.sf.json.JSONObject.toBean(obj, QueryProductRelatedAssetInfoResponse.class);
//            if (null == response || !Constants.SUCCESS_RESP_CODE.equals(response.getRespCode())) {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用资管系统查询资产信息失败:" + response.getRespMsg());
//            }
//
//            com.zb.fincore.ams.facade.model.ProductRelatedAssetInfoModel infoModel = response.getInfoModel();
//            if (null != infoModel) {
//                ProductRelatedAssetInfoModel productRelatedAssetInfoModel = BeanUtils.copyAs(infoModel, ProductRelatedAssetInfoModel.class);
//                //productModel.setProductRelatedAssetInfoModel(productRelatedAssetInfoModel);
//            }
//        } catch (Exception e) {
//            if (e instanceof BusinessException) {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, ((BusinessException) e).getResultMsg());
//            } else {
//                throw new BusinessException(Constants.FAIL_RESP_CODE, "调用系统失败", e);
//            }
//        }
//    }

    /**
     * 产品列表
     *
     * @param req
     * @return
     */
    public PageQueryResponse<ProductModel> queryProductList(QueryProductListRequest req) throws Exception {
        PageQueryResponse<ProductModel> response = BaseResponse.build(PageQueryResponse.class);

        PageQueryResponse<ProductModel> resp = validator.checkQueryProductListParameter(req);
        if (null != resp) {
            return resp;
        }

        Page page = new Page();
        BeanUtils.copy(req, page);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("productCode", req.getProductCode());//产品编号
        params.put("productName", req.getProductName());//产品名称
        params.put("productDisplayName", req.getProductDisplayName());//产品显示名
        params.put("productLineId", req.getProductLineId());//产品线ID
        params.put("productLineCode", req.getProductLineCode());//产品线编号
        params.put("assetPoolCode", req.getAssetPoolCode());//资产编号
//        params.put("patternCode", StringUtils.isBlank(req.getPatternCode())?PatternCodeTypeEnum.PERIODIC_REGULAR.getCode():req.getPatternCode());//形态代码,默认是定期
        params.put("patternCode", req.getPatternCode());//形态代码,默认是定期
        params.put("saleChannelCode", req.getSaleChannelCode());//销售渠道
        params.put("joinChannelCode", req.getJoinChannelCode());//接入渠道
        params.put("totalAmount", req.getTotalAmount());//产品总规模
        params.put("saleStatus", req.getSaleStatus());//销售状态
        params.put("collectStatus", req.getCollectStatus());//募集状态
        params.put("displayStatus", req.getDisplayStatus());//显示状态
        params.put("riskLevel", req.getRiskLevel());//风险等级
        params.put("approvalStatus", req.getApprovalStatus());//产品审核状态
        params.put("beginCreateTime", req.getBeginCreateTime());//查询创建开始时间
        params.put("endCreateTime", req.getEndCreateTime());//查询创建结束时间
        params.put("approvalStartTime", req.getApprovalStartTime());//查询创建开始时间
        params.put("approvalEndTime", req.getApprovalEndTime());//查询创建结束时间
        params.put("syncStatus", req.getSyncStatus());//同步状态
        params.put("saleEndTime", req.getSaleEndTime());//募集截止时间
        params.put("saleEndTimeForDailyCut", req.getSaleEndTimeForDailyCut());//交易日切用募集截止时间（P2P）
        params.put("loanOrderNo", req.getLoanOrderNo());//借款订单编号
        params.put("collectMode", req.getCollectMode());//募集方式
        params.put("orderByOnlineTime", req.getOrderByOnlineTime());//ASC：按照上线时间升序,DESC：按照上线时间降序
        params.put("openType", req.getOpenType());//产品开放类型

        //列表总数
        int totalCount = productDao.queryProductListCount(params);

        List<ProductModel> modelList = null;
        if (totalCount > 0) {
            modelList = new ArrayList<ProductModel>();
            // 不分页的场合
			if (req.getPageNo() == null && req.getPageSize() == null) {
				page = Page.getNullPage();
			}
            List<Product> productList = productDao.queryProductListByCondition(params, page);
            for (Product product : productList) {
                ProductModel productModel = new ProductModel();
                BeanUtils.copy(product, productModel);//产品基本信息

                //产品期限信息
                ProductPeriodModel productPeriodModel = new ProductPeriodModel();
                BeanUtils.copy(product.getProductPeriod(), productPeriodModel);
                productModel.setProductPeriodModel(productPeriodModel);

                //产品投资限制及收益信息
                ProductProfitModel productProfitModel = new ProductProfitModel();
                BeanUtils.copy(product.getProductProfit(), productProfitModel);
                productModel.setProductProfitModel(productProfitModel);

                //产品库存信息
                ProductStockModel productStockModel = new ProductStockModel();
                BeanUtils.copy(product.getProductStock(), productStockModel);
                productModel.setProductStockModel(productStockModel);

                //产品合同
                /*List<ProductContractModel> contractModelList = null;
                List<ProductContract> contractList = product.getProductContractList();
                if (null != contractList && contractList.size() > 0) {
                    contractModelList = BeanUtils.copyAs(contractList, ProductContractModel.class);
                    productModel.setProductContractModelList(contractModelList);
                }*/

                //产品审核信息
//                ProductApprovalModel productApprovalModel = new ProductApprovalModel();
//                BeanUtils.copy(product.getProductApproval(), productApprovalModel);
//                productModel.setProductApprovalModel(productApprovalModel);

                modelList.add(productModel);
            }
        }

        response.setPageSize(page.getPageSize());
        response.setPageNo(page.getPageNo());
        response.setTotalCount(totalCount);
        response.setDataList(modelList);
        return response;
    }

    @Override
    public PageQueryResponse<ProductModel> queryProductListForYw(@Valid QueryProductListRequest req) throws Exception {
        //req.setCollectStatus(ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_COLLECTING.getCode());
        req.setSaleChannelCode(ChannelEnum.YW.getCode());
        req.setSaleStatus(ProductSaleStatusEnum.PRODUCT_SALE_STATUS_ON_LINE.getCode());
        req.setSyncStatus(ProductSyncStatusEnum.UN_SYNC.getCode());
        req.setSaleEndTime(new Date());
        return queryProductList(req);
    }

    @Override
    public PageQueryResponse<ProductModel> queryProductListForP2P(@Valid QueryProductListRequest req) throws Exception {
        //req.setCollectStatus(ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_COLLECTING.getCode());
        req.setSaleChannelCode(ChannelEnum.TXS.getCode());
        req.setSaleStatus(ProductSaleStatusEnum.PRODUCT_SALE_STATUS_ON_LINE.getCode());
        req.setSyncStatus(ProductSyncStatusEnum.UN_SYNC.getCode());
        req.setSaleEndTime(new Date());
        req.setOpenType(OpenTypeEnum.OUT.getCode());
        return queryProductList(req);
    }

    @Override
    public PageQueryResponse<ProductModel> queryProductListForTrade(@Valid QueryProductListRequest req) throws Exception {
        req.setSaleChannelCode(ChannelEnum.TXS.getCode());
        return queryProductList(req);
    }

    @Override
    public PageQueryResponse<ProductModel> queryProductListForP2PApp(@Valid QueryProductListRequestForP2P req) throws Exception {
        if (Constants.ON_SALE.equals(req.getSaleFlag())) { // 在售产品列表
            return queryOnSaleProductListForP2P(req);

        } else if (Constants.SOLD_OUT.equals(req.getSaleFlag())) { // 售罄产品列表
            return querySoldOutProductListForP2P(req);
        } else {
            return BaseResponse.build(PageQueryResponse.class, Constants.SALE_FLAG_NOT_IN_ENUMS_CODE,
                    Constants.SALE_FLAG_NOT_IN_ENUMS_CODE_DESC);
        }

    }

    @Override
    public PageQueryResponse<ProductModel> queryProductListForMSD(@Valid QueryProductListRequestForP2P req) throws Exception {
        if (Constants.ON_SALE.equals(req.getSaleFlag())) { // 在售产品列表
            if (req.getPageNo() != null && req.getPageNo() == 1) { // 第一页读缓存
                return productCacheForP2PService.queryProductListForP2PApp(req);
            }
            // 其他查库
            return queryOnSaleProductListForP2P(req);


        } else if (Constants.SOLD_OUT.equals(req.getSaleFlag())) { // 售罄产品列表
            if (req.getPageNo() != null && req.getPageNo() == 1) {// 第一页开始读库
                return productCacheForP2PService.queryProductListForP2PApp(req);
            }
            // 其他查库
            return querySoldOutProductListForP2P(req);

        } else {
            return BaseResponse.build(PageQueryResponse.class, Constants.SALE_FLAG_NOT_IN_ENUMS_CODE,
                    Constants.SALE_FLAG_NOT_IN_ENUMS_CODE_DESC);
        }

    }

    /**
     * 查询在售产品列表列表(P2P)
     * @return
     * @throws Exception
     */
    private PageQueryResponse<ProductModel> queryOnSaleProductListForP2P(QueryProductListRequestForP2P req) throws Exception {
        PageQueryResponse<ProductModel> response = BaseResponse.build(PageQueryResponse.class);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("saleChannelCode", ChannelEnum.TXS.getCode());// 销售渠道
        params.put("patternCode", req.getPatternCode());// 产品类型
        params.put("curTime", new Date());// 当前时间
        params.put("saleStatus", ProductSaleStatusEnum.PRODUCT_SALE_STATUS_ON_LINE.getCode()); // 上线
        params.put("collectStatus", P2PProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_COLLECTING.getCode()); // 募集状态：募集期
        params.put("orderByYieldRate", req.getOrderByYieldRate()); // 按照预期年化收益率（最低预期收益率）排序  (ASC,DESC)
        params.put("orderByInvestPeriod", req.getOrderByInvestPeriod()); // 按照产品期限排序  (ASC,DESC)
        params.put("orderByMinInvestAmount", req.getOrderByMinInvestAmount()); // 按照起投金额排序  (ASC,DESC)
        params.put("openType", OpenTypeEnum.OUT.getCode());
        // 获取全局配置信息
        GlobalConfig globalConfig = globalConfigDao.selectByPropertyName(GlobalConfigConstants.TEST_PRODUCT_SWITCH);
        String propertyValue = "off";
        if (null!=globalConfig) {
            propertyValue = StringUtils.isBlank(globalConfig.getPropertyValue())?"off":globalConfig.getPropertyValue();
        }
        params.put("testProductSwitch",propertyValue ); // 是否开放测试产品

        Page page = new Page();
        BeanUtils.copy(req, page);
        //列表总数
        int totalCount = 0;
        List<ProductModel> modelList = null;
        List<Product> tempProductList = productDao.queryOnSaleProductListForP2P(params, Page.getNullPage());
        if (tempProductList != null && tempProductList.size() > 0) {
            totalCount = tempProductList.size();
            modelList = new ArrayList<ProductModel>();
            // 不分页的场合
            if (req.getPageNo() == null && req.getPageSize() == null) {
                page = Page.getNullPage();
            }
            List<Product> productList = productDao.queryOnSaleProductListForP2P(params, page);
            setProductModelList(productList, modelList);
        }

        response.setPageSize(page.getPageSize());
        response.setPageNo(page.getPageNo());
        response.setTotalCount(totalCount);
        response.setDataList(modelList);
        return response;
    }

    /**
     * 查询售罄产品列表列表(P2P)
     * @return
     * @throws Exception
     */
    private PageQueryResponse<ProductModel> querySoldOutProductListForP2P(QueryProductListRequestForP2P req) throws Exception {
        PageQueryResponse<ProductModel> response = BaseResponse.build(PageQueryResponse.class);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("saleChannelCode", ChannelEnum.MSD.getCode());// 销售渠道
        params.put("curTime", new Date());// 当前时间
        params.put("patternCode", req.getPatternCode());// 产品类型
        params.put("openType", OpenTypeEnum.OUT.getCode());

        List<Integer> saleStatusList = new ArrayList<Integer>();
        saleStatusList.add(ProductSaleStatusEnum.PRODUCT_SALE_STATUS_OFF_LINE.getCode()); // 下线
        saleStatusList.add(ProductSaleStatusEnum.PRODUCT_SALE_STATUS_FILED.getCode()); // 归档
        params.put("saleStatusList", saleStatusList);//销售状态List

        List<Integer> notEstablishCollectStatusList = new ArrayList<Integer>(); // 未成立的产品募集状态List
        notEstablishCollectStatusList.add(P2PProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_WAIT_COLLECT.getCode()); // 待募集
//        notEstablishCollectStatusList.add(P2PProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_COLLECTING.getCode()); // 募集期
        params.put("notEstablishCollectStatusList", notEstablishCollectStatusList);// 未成立产品募集状态List
        // 获取全局配置信息
        GlobalConfig globalConfig = globalConfigDao.selectByPropertyName(GlobalConfigConstants.TEST_PRODUCT_SWITCH);
        String propertyValue = StringUtils.isBlank(globalConfig.getPropertyValue())?"off":globalConfig.getPropertyValue();
        params.put("testProductSwitch",propertyValue ); // 是否开放测试产品

        Page page = new Page();
        BeanUtils.copy(req, page);
        //列表总数
        int totalCount = 0;
        List<ProductModel> modelList = null;
        List<Product> tempProductList = productDao.querySoldOutProductListForP2P(params, Page.getNullPage());
        if (tempProductList != null && tempProductList.size() > 0) {
            totalCount = tempProductList.size();
            modelList = new ArrayList<ProductModel>();
            // 不分页的场合
            if (req.getPageNo() == null && req.getPageSize() == null) {
                page = Page.getNullPage();
            }
            List<Product> productList = productDao.querySoldOutProductListForP2P(params, page);
            setProductModelList(productList, modelList);
        }

        response.setPageSize(page.getPageSize());
        response.setPageNo(page.getPageNo());
        response.setTotalCount(totalCount);
        response.setDataList(modelList);
        return response;
    }

    /**
     * 查询产品库存变更记录流水
     *
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public PageQueryResponse<ProductStockChangeFlowModel> queryProductStockChangeFlowList(QueryProductStockChangeFlowRequest req) throws Exception {
        PageQueryResponse<ProductStockChangeFlowModel> response = BaseResponse.build(PageQueryResponse.class);

        Page page = new Page();
        BeanUtils.copy(req, page);
        ProductStockChangeFlow productStockChangeFlow = new ProductStockChangeFlow();
        BeanUtils.copy(req, productStockChangeFlow);

        int totalCount = productStockChangeFlowDao.queryProductStockChangeFlowCount(productStockChangeFlow);
        List<ProductStockChangeFlowModel> flowModelList = null;
        if (totalCount > 0) {
            List<ProductStockChangeFlow> flowList = productStockChangeFlowDao.queryProductStockChangeFlowList(productStockChangeFlow, page);
            flowModelList = BeanUtils.copyAs(flowList, ProductStockChangeFlowModel.class);
        }

        response.setPageSize(page.getPageSize());
        response.setPageNo(page.getPageNo());
        response.setTotalCount(totalCount);
        response.setDataList(flowModelList);
        return response;
    }

    /**
     * 查询产品审核信息列表
     *
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public QueryProductApprovalInfoResponse queryProductApprovalList(@Valid QueryProductApprovalListRequest req) throws Exception {
        Product product = productDao.selectProductByCode(req.getProductCode());
        if (null == product) {
            return BaseResponse.build(QueryProductApprovalInfoResponse.class, Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
        }

        QueryProductApprovalInfoResponse response = BaseResponse.build(QueryProductApprovalInfoResponse.class);

        ProductApproval productApproval = new ProductApproval();
        BeanUtils.copy(req, productApproval);

        int totalCount = productApprovalDao.queryProductApprovalListCount(productApproval);
        List<ProductApprovalModel> productApprovalModels = null;
        if (totalCount > 0) {
            List<ProductApproval> productApprovals = productApprovalDao.queryProductApprovalList(productApproval);
            productApprovalModels = BeanUtils.copyAs(productApprovals, ProductApprovalModel.class);
        }

        response.setProductDisplayName(product.getProductDisplayName());
        response.setProductName(product.getProductName());
        response.setSaleChannelCode(product.getSaleChannelCode());
        response.setDataList(productApprovalModels);
        return response;
    }

    /**
     * 产品信息更新
     *
     * @param req
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public BaseResponse updateProductInfo(@Valid UpdateProductInfoRequest req) throws Exception {

        //校验产品是否存在
        Product product = productDao.selectByPrimaryKey(req.getProductId());
        if (null == product) {
            return BaseResponse.build(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
        }

        //校验产品展示名修改前后是否一致
        if (req.getProductDisplayName().equals(product.getProductDisplayName())) {
            return BaseResponse.build();
        }
        //校验产品展示名是否重复
        int count = productDao.selectProductCountByDisplayName(req.getProductDisplayName());
        if (count > 0) {
            return BaseResponse.build(Constants.PRODUCT_DISPLAY_NAME_EXIST_RESULT_CODE, Constants.PRODUCT_DISPLAY_NAME_EXIST_RESULT_CODE_DESC);
        }

        product.setProductDisplayName(req.getProductDisplayName());
        product.setModifyBy(req.getModifyBy());
        productDao.updateByPrimaryKeySelective(product);

        return BaseResponse.build();
    }

    /**
     * 消息 更新产品募集状态
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public BaseResponse updateProductCollectStatus(@Valid UpdateProductCollectStatusRequest req) throws Exception {
        Product product = productDao.selectProductByCode(req.getProductCode());
        if (null == product) {
            return BaseResponse.build(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
        }

        Map<String, Object> params = new HashMap<String, Object>();
        //请求状态为 待兑付
        if (req.getCollectStatus() == ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_WAIT_REDEEM.getCode()) {
            //产品募集状态需为到期且已下线
            if (!(product.getCollectStatus() == ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_VALUE_EXPIRE.getCode()
                    && product.getSaleStatus() == ProductSaleStatusEnum.PRODUCT_SALE_STATUS_OFF_LINE.getCode())) {
                return BaseResponse.build(Constants.PRODUCT_UNKNOWN_COLLECT_STATUS_CHANGED_RESULT_CODE,
                        Constants.PRODUCT_UNKNOWN_COLLECT_STATUS_CHANGED_RESULT_CODE_DESC);
            }
            params.put("id", product.getId());
            params.put("originalStatus", product.getCollectStatus());
            params.put("collectStatus", ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_WAIT_REDEEM.getCode());//待兑付
        } else if (req.getCollectStatus() == ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_REDEEMED.getCode()) {//请求状态为 兑付完成
            //产品募集状态需为待兑付且已下线
            if (!(product.getCollectStatus() == ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_WAIT_REDEEM.getCode()
                    && product.getSaleStatus() == ProductSaleStatusEnum.PRODUCT_SALE_STATUS_OFF_LINE.getCode())) {
                return BaseResponse.build(Constants.PRODUCT_UNKNOWN_COLLECT_STATUS_CHANGED_RESULT_CODE,
                        Constants.PRODUCT_UNKNOWN_COLLECT_STATUS_CHANGED_RESULT_CODE_DESC);
            }

            params.put("id", product.getId());
            params.put("originalStatus", product.getCollectStatus());
            params.put("collectStatus", ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_REDEEMED.getCode());//兑付完成
            params.put("saleOriginalStatus", product.getSaleStatus());
            params.put("saleStatus", ProductSaleStatusEnum.PRODUCT_SALE_STATUS_FILED.getCode());//归档

            //兑付完成更新  实际结清时间
            ProductPeriod productPeriod = new ProductPeriod();
            productPeriod.setProductCode(req.getProductCode());
            productPeriod.setClearTime(new Date());
            productPeriodDao.updateActualTimeByProductCode(productPeriod);
            //更新  归档时间
            params.put("archiveTime", new Date());
            productDao.updateProductArchiveTimeById(params);
        } else if (req.getCollectStatus() == ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_FAILURE_SALE.getCode()) {//请求状态为 流标
            // 产品上线后 无人购买流程  募集期结束后  直接变成 ”流标“ “归档”状态
            params.put("id", product.getId());
            //params.put("originalStatus", product.getCollectStatus());
            params.put("collectStatus", ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_FAILURE_SALE.getCode());//流标
            //params.put("saleOriginalStatus", product.getSaleStatus());
            params.put("saleStatus", ProductSaleStatusEnum.PRODUCT_SALE_STATUS_FILED.getCode());//归档

            //更新  归档时间
            params.put("archiveTime", new Date());
            productDao.updateProductArchiveTimeById(params);
        } else {
            return BaseResponse.build(Constants.PRODUCT_UNKNOWN_COLLECT_STATUS_CHANGED_RESULT_CODE,
                    Constants.PRODUCT_UNKNOWN_COLLECT_STATUS_CHANGED_RESULT_CODE_DESC);
        }
        productDao.updateProductCollectStatusById(params);

        return BaseResponse.build();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public BaseResponse updateProductSynStatus(@Valid UpdateProductSyncStatusRequest req) throws Exception {
        Product product = productDao.selectProductByCode(req.getProductCode());
        if (null == product) {
            return BaseResponse.build(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
        }

        if (ProductSyncStatusEnum.SYNCED.getCode() == product.getSyncStatus()) {
            return BaseResponse.build(Constants.PRODUCT_ALREADY_ON_SHELF_RESULT_CODE,
                    Constants.PRODUCT_ALREADY_ON_SHELF_RESULT_CODE_DESC);
        }

        //产品审核状态已通过，且销售状态为 上线才可以 上架操作
        if (product.getApprovalStatus() != ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode() ||
                product.getSaleStatus() != ProductSaleStatusEnum.PRODUCT_SALE_STATUS_ON_LINE.getCode()) {
            return BaseResponse.build(Constants.PRODUCT_UN_APPROVALED_OR_UN_ONLINE_RESULT_CODE,
                    Constants.PRODUCT_UN_APPROVALED_OR_UN_ONLINE_RESULT_CODE_DESC);
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", product.getId());
        params.put("originalStatus", product.getSyncStatus());
        params.put("syncStatus", ProductSyncStatusEnum.SYNCED.getCode());//已同步

        productDao.updateProductSyncStatusById(params);
        return BaseResponse.build();
    }

    /**
     * 产品确认成立
     *
     * @param req
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public BaseResponse confirmProductEstablished(@Valid UpdateProductCollectStatusRequest req) throws Exception {

        Product product = productDao.selectProductByCode(req.getProductCode());
        if (null == product) {
            return BaseResponse.build(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
        }

        //产品募集状态需为待成立
        if (product.getCollectStatus() != ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_WAIT_ESTABLISH.getCode()) {
            return BaseResponse.build(Constants.PRODUCT_UNKNOWN_COLLECT_STATUS_CHANGED_RESULT_CODE,
                    Constants.PRODUCT_UNKNOWN_COLLECT_STATUS_CHANGED_RESULT_CODE_DESC);
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", product.getId());
        params.put("originalStatus", product.getCollectStatus());
        params.put("collectStatus", ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_ESTABLISHED.getCode());//成立

        productDao.updateProductCollectStatusById(params);

        ProductPeriod productPeriod = new ProductPeriod();
        productPeriod.setProductCode(product.getProductCode());
        productPeriod.setEstablishTime(new Date());
        productPeriodDao.updateActualTimeByProductCode(productPeriod);

        return BaseResponse.build();
    }

    /**
     * 产品募集期开始 JOB调用
     *
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public BaseResponse startProductRaising() throws Exception {

        Map<String, Object> params = new HashMap<String, Object>();
        List<Integer> saleStatusList = new ArrayList<Integer>();
//        saleStatusList.add(ProductSaleStatusEnum.PRODUCT_SALE_STATUS_DEPLOYED.getCode());
        saleStatusList.add(ProductSaleStatusEnum.PRODUCT_SALE_STATUS_ON_LINE.getCode());
        saleStatusList.add(ProductSaleStatusEnum.PRODUCT_SALE_STATUS_OFF_LINE.getCode());
        params.put("saleStatusList", saleStatusList);
//        params.put("patternCode", PatternCodeTypeEnum.PERIODIC_REGULAR.getCode());//不区分定期类、N日计划类
        params.put("registerType", RegisterTypeEnum.UNAUTO.getCode());//不管是定期类还是N计划类，人工注册时对应的注册类型都是unauto,而自动注册时则是auto
        params.put("approvalStatus", ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode());//审核通过
        params.put("collectStatus", ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_WAIT_COLLECT.getCode());//待募集
        params.put("querySaleUnStartProduct", Constants.CONSTANT_VALUE_1);
        params.put("curTime", new Date());//当前时间
        List<Product> infoList = productDao.queryProductListForUpdateStatus(params);
        for (Product p : infoList) {
            params.put("id", p.getId());
            params.put("originalStatus", p.getCollectStatus());
            params.put("collectStatus", ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_COLLECTING.getCode());
            productDao.updateProductCollectStatusById(params);
        }
        return BaseResponse.build();
    }

//    /**
//     * 募集期结束 --》产品待成立    JOB调用
//     *
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public BaseResponse putProductWaitingEstablish() throws Exception {
//        Map<String, Object> params = new HashMap<String, Object>();
//        List<Integer> saleStatusList = new ArrayList<Integer>();
//        saleStatusList.add(ProductSaleStatusEnum.PRODUCT_SALE_STATUS_DEPLOYED.getCode());
//        saleStatusList.add(ProductSaleStatusEnum.PRODUCT_SALE_STATUS_ON_LINE.getCode());
//        saleStatusList.add(ProductSaleStatusEnum.PRODUCT_SALE_STATUS_OFF_LINE.getCode());
//        params.put("saleChannelCode", ChannelEnum.YW.getCode());
//        params.put("saleStatusList", saleStatusList);
//        params.put("approvalStatus", ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode());//审核通过
//        params.put("collectStatus", ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_COLLECTING.getCode());//募集期
//        params.put("querySaleTimeOutProduct", Constants.CONSTANT_VALUE_1);//查询募集期结束产品列表
//        params.put("curTime", new Date());//当前时间
//
//        List<Product> infoList = productDao.queryProductListForUpdateStatus(params);
//        for (Product p : infoList) {
//            try{
//                doProductWaitingEstablish(p);
//            }catch (Exception e) {
//                logger.error("募集期结束产品状态更新失败 id：{}" ,p.getId());
//            }
//        }
//        return BaseResponse.build();
//    }
//
//    /**
//     * 募集期结束 --》产品待成立    JOB调用 实际处理逻辑
//     *
//     * @return
//     * @throws Exception
//     */
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
//    public void doProductWaitingEstablish(Product p) throws Exception {
//        Map<String, Object> params = new HashMap<String, Object>();
//        //更新产品募集状态为 待成立
//        params.put("id", p.getId());
//        params.put("originalStatus", p.getCollectStatus());
//        params.put("collectStatus", ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_WAIT_ESTABLISH.getCode());
//        productDao.updateProductCollectStatusById(params);
//        //更新产品销售状态为 下线
//        params.put("originalStatus", p.getSaleStatus());
//        params.put("saleStatus", ProductSaleStatusEnum.PRODUCT_SALE_STATUS_OFF_LINE.getCode());//下线状态
//        productDao.updateProductSaleStatusById(params);
//
//        ProductPeriod productPeriod = new ProductPeriod();
//        productPeriod.setProductCode(p.getProductCode());
//        productPeriod.setSaleOutTime(new Date());
//        productPeriod.setOfflineTime(new Date());
//        productPeriodDao.updateActualTimeByProductCode(productPeriod);
//
////        ProductStock stock = productStockDao.selectProductStockByProductCode(p.getProductCode());
////        WaitingEstablishRequest request = new WaitingEstablishRequest();
////        request.setProductCode(p.getProductCode());
////        request.setCollectAmount(stock.getSaleAmount());
////        //调用消息
////        producerService.publishMessage(request, TopicConstants.FINCORE_TOPIC_PMS_PRODUCT_COLLECT_END_DATE);
//    }

    /**
     * 募集期结束 --》募集完成   JOB调用
     *
     * @return
     * @throws Exception
     */
    @Override
    public BaseResponse putProductValuing() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        List<Integer> saleStatusList = new ArrayList<Integer>();
        saleStatusList.add(ProductSaleStatusEnum.PRODUCT_SALE_STATUS_DEPLOYED.getCode());
        saleStatusList.add(ProductSaleStatusEnum.PRODUCT_SALE_STATUS_ON_LINE.getCode());
        saleStatusList.add(ProductSaleStatusEnum.PRODUCT_SALE_STATUS_OFF_LINE.getCode());
//        params.put("saleChannelCode", ChannelEnum.MSD.getCode());
        params.put("saleStatusList", saleStatusList);
//        params.put("patternCode", PatternCodeTypeEnum.PERIODIC_REGULAR.getCode());//不区分定期类、N日计划类
        params.put("approvalStatus", ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode());//审核通过
        params.put("collectStatus", P2PProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_COLLECTING.getCode());//募集期
        params.put("querySaleTimeOutProduct", Constants.CONSTANT_VALUE_1);//查询募集期结束产品列表
        params.put("curTime", new Date());//当前时间

        List<Product> infoList = productDao.queryProductListForUpdateStatus(params);
        List<ProductAssetMatchDTO> productAssetMatchDTOList = new ArrayList<ProductAssetMatchDTO>();
        StringBuffer productCodes = new StringBuffer();

        if (CollectionUtils.isNotEmpty(infoList)) {
        	for (Product p : infoList) {
                try{
                	((BaseProductService)(AopContext.currentProxy())).doProductValuing(p);
                }catch (Exception e) {
                    logger.error("【募集期结束job】募集期结束产品状态更新失败! productCode：{}"+"\n"+"{}" ,p.getProductCode(), e);
                }
                ProductAssetMatchDTO productAssetMatchDTO = new ProductAssetMatchDTO();
                productAssetMatchDTO.setProductCode(p.getProductCode());
                productAssetMatchDTO.setProductSource(p.getPatternCode());
                productAssetMatchDTOList.add(productAssetMatchDTO);

                productCodes.append(p.getProductCode()).append(",");
            }

            if (CollectionUtils.isNotEmpty(productAssetMatchDTOList)) {
            	//通知交易匹配资产
            	AssetMatchReq assetMatchReq = new AssetMatchReq();
                assetMatchReq.setProductAssetMatchDTOList(productAssetMatchDTOList);
                tradeService.assetMatchHttp(assetMatchReq);
                //通知唐小僧同步产品状态
                txsService.syncStatusNoticeHttp(productCodes.toString().substring(0,productCodes.toString().length()-1));
            }
        }

        return BaseResponse.build();
    }

    /**
     * 募集期结束  --》募集完成     JOB调用 实际处理逻辑
     *
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void doProductValuing(Product p) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        //更新产品募集状态为 募集完成
        params.put("id", p.getId());
        params.put("originalStatus", p.getCollectStatus());
//        params.put("collectStatus", ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_VALUING.getCode());
        params.put("collectStatus", P2PProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_RAISE_COMPLETE.getCode());
        productDao.updateProductCollectStatusById(params);
        //更新产品销售状态为 下线
        params.put("originalStatus", p.getSaleStatus());
        params.put("saleStatus", ProductSaleStatusEnum.PRODUCT_SALE_STATUS_OFF_LINE.getCode());//下线状态
        productDao.updateProductSaleStatusById(params);

        ProductPeriod productPeriod = new ProductPeriod();
        productPeriod.setProductCode(p.getProductCode());
        productPeriod.setSaleOutTime(new Date());
        productPeriod.setOfflineTime(new Date());
        productPeriodDao.updateActualTimeByProductCode(productPeriod);

//        ProductStock stock = productStockDao.selectProductStockByProductCode(p.getProductCode());
//        WaitingEstablishRequest request = new WaitingEstablishRequest();
//        request.setProductCode(p.getProductCode());
//        request.setCollectAmount(stock.getSaleAmount());
//        //调用消息（交易自己跑）
//        producerService.publishMessage(request, TopicConstants.FINCORE_TOPIC_PMS_PRODUCT_COLLECT_END_DATE);
    }

//    /**
//     * 产品起息开始--存续期
//     *
//     * @param req
//     * @return
//     */
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
//    @Override
//    public BaseResponse putProductInValue(UpdateProductCollectStatusRequest req) throws Exception {
//        Product product = productDao.selectProductByCode(req.getProductCode());
//        if (null == product) {
//            return BaseResponse.build(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
//        }
//
//        //产品募集状态需为已成立且已下线
//        if (!(product.getCollectStatus() == ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_ESTABLISHED.getCode()
//                && product.getSaleStatus() == ProductSaleStatusEnum.PRODUCT_SALE_STATUS_OFF_LINE.getCode())) {
//            return BaseResponse.build(Constants.PRODUCT_UNKNOWN_COLLECT_STATUS_CHANGED_RESULT_CODE,
//                    Constants.PRODUCT_UNKNOWN_COLLECT_STATUS_CHANGED_RESULT_CODE_DESC);
//        }
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("id", product.getId());
//        params.put("originalStatus", product.getCollectStatus());
//        params.put("collectStatus", ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_VALUING.getCode());//存续期
//
//        productDao.updateProductCollectStatusById(params);
//        return BaseResponse.build();
//    }
//
//    /**
//     * 产品存续期结束 --》产品到期  产品到期JOB调用
//     *
//     * @return
//     * @throws Exception
//     */
//    @Override
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
//    public BaseResponse putProductOutValue() throws Exception {
//        Map<String, Object> params = new HashMap<String, Object>();
//        List<Integer> saleStatusList = new ArrayList<Integer>();
//        saleStatusList.add(ProductSaleStatusEnum.PRODUCT_SALE_STATUS_DEPLOYED.getCode());
//        saleStatusList.add(ProductSaleStatusEnum.PRODUCT_SALE_STATUS_ON_LINE.getCode());
//        saleStatusList.add(ProductSaleStatusEnum.PRODUCT_SALE_STATUS_OFF_LINE.getCode());
//        params.put("saleStatusList", saleStatusList);
//        params.put("approvalStatus", ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode());//审核通过
//        params.put("collectStatus", ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_VALUING.getCode());//存续期
//        params.put("queryOutValueProduct", Constants.CONSTANT_VALUE_1);//查询到期产品列表
//        params.put("curTime", new Date());//当前时间
//
//        List<Product> infoList = productDao.queryProductListForUpdateStatus(params);
//        for (Product p : infoList) {
//            params.put("id", p.getId());
//            params.put("originalStatus", p.getCollectStatus());
//            params.put("collectStatus", ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_VALUE_EXPIRE.getCode());//到期
//            productDao.updateProductCollectStatusById(params);
//
//            ProductPeriod productPeriod = new ProductPeriod();
//            productPeriod.setProductCode(p.getProductCode());
//            productPeriod.setExpireTime(new Date());
//            productPeriodDao.updateActualTimeByProductCode(productPeriod);
//        }
//        return BaseResponse.build();
//    }

//    /**
//     * 产品存续期 开始起息 JOB调用
//     *
//     * @return
//     */
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
//    @Override
//    public BaseResponse putProductInValueOfJob() throws Exception {
//        Map<String, Object> params = new HashMap<String, Object>();
//        List<Integer> saleStatusList = new ArrayList<Integer>();
//        saleStatusList.add(ProductSaleStatusEnum.PRODUCT_SALE_STATUS_DEPLOYED.getCode());
//        saleStatusList.add(ProductSaleStatusEnum.PRODUCT_SALE_STATUS_ON_LINE.getCode());
//        saleStatusList.add(ProductSaleStatusEnum.PRODUCT_SALE_STATUS_OFF_LINE.getCode());
//        params.put("saleStatusList", saleStatusList);
//        params.put("approvalStatus", ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode());//审核通过
//        params.put("collectStatus", ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_ESTABLISHED.getCode());//已成立
//        params.put("queryUnValueStartProduct", Constants.CONSTANT_VALUE_1);//起息时间
//        params.put("curTime", new Date());//当前时间
//        List<Product> infoList = productDao.queryProductListForUpdateStatus(params);
//        for (Product p : infoList) {
//            params.put("id", p.getId());
//            params.put("originalStatus", p.getCollectStatus());
//            params.put("collectStatus", ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_VALUING.getCode());
//            productDao.updateProductCollectStatusById(params);
//        }
//        return BaseResponse.build();
//    }

//    /**
//     * 产品到期 --》待兑付
//     */
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    @Override
//    public BaseResponse putProductWaitRedeem(@Valid UpdateProductCollectStatusRequest req) throws Exception {
//        Product product = productDao.selectProductByCode(req.getProductCode());
//        if (null == product) {
//            return BaseResponse.build(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
//        }
//
//        //产品募集状态需为到期且已下线
//        if (!(product.getCollectStatus() == ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_VALUE_EXPIRE.getCode()
//                && product.getSaleStatus() == ProductSaleStatusEnum.PRODUCT_SALE_STATUS_OFF_LINE.getCode())) {
//            return BaseResponse.build(Constants.PRODUCT_UNKNOWN_COLLECT_STATUS_CHANGED_RESULT_CODE,
//                    Constants.PRODUCT_UNKNOWN_COLLECT_STATUS_CHANGED_RESULT_CODE_DESC);
//        }
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("id", product.getId());
//        params.put("originalStatus", product.getCollectStatus());
//        params.put("collectStatus", ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_WAIT_REDEEM.getCode());//待兑付
//
//        productDao.updateProductCollectStatusById(params);
//        return BaseResponse.build();
//    }

//    /**
//     * JOB 阶梯信息更新
//     */
//    @Override
//    public BaseResponse updateProductLadderInfo() throws Exception {
//
//        ProductLadder productLadder = null;
//        List<ProductLadder> ladderList = productLadderDao.selectNextLadderList(productLadder);
//        for (ProductLadder ladder : ladderList) {
//            try {
//                doUpdateProductLadderInfo(ladder);
//            }catch (Exception e){
//                logger.error("阶梯信息更新失败productId：", ladder.getProductId());
//            }
//        }
//
//        //刷新缓存
//        productCacheService.refreshProductCache();
//
//        return BaseResponse.build();
//    }
//
//    /**
//     * JOB 阶梯信息更新 实际处理逻辑
//     */
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    public void doUpdateProductLadderInfo(ProductLadder ladder) throws Exception {
//        //更新投资期限信息
//        ProductPeriod period = productPeriodDao.selectProductPeriodInfoByProductCode(ladder.getProductCode());
//        if (null != period) {
//            ProductPeriod updatePeriod = new ProductPeriod();
//            updatePeriod.setId(period.getId());
//            updatePeriod.setInvestPeriodLoopIndex(ladder.getInvestPeriodLoopIndex());
//            updatePeriod.setNextRedeemTime(ladder.getNextRedeemTime());
//            updatePeriod.setNextRepayTime(ladder.getNextRepayTime());
//            productPeriodDao.updateByPrimaryKeySelective(updatePeriod);
//        }
//
//        //更新收益信息
//        ProductProfit profit = productProfitDao.selectProductProfitInfoByProductCode(ladder.getProductCode());
//        if (null != profit) {
//            ProductProfit updateProfit = new ProductProfit();
//            updateProfit.setId(profit.getId());
//            updateProfit.setCurrentYieldRate(ladder.getYieldRate());
//            productProfitDao.updateByPrimaryKeySelective(updateProfit);
//        }
//
//        ProductReceiveNotifyDTO request = new ProductReceiveNotifyDTO();
//        request.setProductCode(ladder.getProductCode());
//        request.setReceiveNotifyDate(ladder.getValueStartTime());//改阶段的起息时间，即上个阶段的回款日
//        logger.debug("发送TRADE消息:{}", JSONObject.toJSONString(request));
//        //调用消息
//        producerService.publishMessage(request, TopicConstants.FINCORE_TOPIC_PMS_PRODUCT_REPAY_DATE);
//    }


    /**
     * 产品统计接口
     *
     * @return
     * @throws Exception
     */
    @Override
    public ProductStatisticsResponse productStatistics(QueryProductListRequest req) throws Exception {
        ProductStatisticsResponse response = new ProductStatisticsResponse();
        //查询归档的产品数量
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("saleStatus", ProductSaleStatusEnum.PRODUCT_SALE_STATUS_FILED.getCode());
        int archiveProTotal = productDao.queryProductListCount(params);
        response.setArchiveProTotal(Long.valueOf(archiveProTotal));

        //查询审核通过产品总数
        params = new HashMap<String, Object>();
        params.put("approvalStatus", ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode());
        int approvalSuccessProTotal = productDao.queryProductListCount(params);
        response.setApprovalSuccessProTotal(Long.valueOf(approvalSuccessProTotal));

        //查询审核失败产品总数
        params = new HashMap<String, Object>();
        params.put("approvalStatus", ProductApprovalStatusEnum.APPROVAL_FAILURE.getCode());
        int approvalFailureProTotal = productDao.queryProductListCount(params);
        response.setApprovalFailureProTotal(Long.valueOf(approvalFailureProTotal));

        //查询待审核产品总数
        params = new HashMap<String, Object>();
        params.put("approvalStatus", ProductApprovalStatusEnum.WAIT_APPROVAL.getCode());
        int approvalWaitProTotal = productDao.queryProductListCount(params);
        response.setApprovalWaitProTotal(Long.valueOf(approvalWaitProTotal));

        return response;
    }

    /**
     * 根据SQL查询出来结果来设置ProductModelList值
     * @param productList
     * @param modelList
     * @return
     */
    private List<ProductModel> setProductModelList(List<Product> productList, List<ProductModel> modelList){
        for (Product product : productList) {
            ProductModel productModel = new ProductModel();
            BeanUtils.copy(product, productModel);//产品基本信息

            //产品期限信息
            ProductPeriodModel productPeriodModel = new ProductPeriodModel();
            BeanUtils.copy(product.getProductPeriod(), productPeriodModel);
            productModel.setProductPeriodModel(productPeriodModel);

            //产品投资限制及收益信息
            ProductProfitModel productProfitModel = new ProductProfitModel();
            BeanUtils.copy(product.getProductProfit(), productProfitModel);
            productModel.setProductProfitModel(productProfitModel);

            //产品库存信息
            ProductStockModel productStockModel = new ProductStockModel();
            BeanUtils.copy(product.getProductStock(), productStockModel);
            productModel.setProductStockModel(productStockModel);

            modelList.add(productModel);
        }
        return modelList;
    }

    public static void main(String[] args) {
		Map map=new HashMap<String,String>();
		map.put("addStock", "1000000.0000");
		map.put("currentStock", "10000000.0000");
		map.put("serialNo", "20171012114324");
		map.put("productCode", "0217090025");
		try {
			String responseStr=HttpClientUtil.sendPostRequest("http://139.196.10.149:9001/p2p/product/0217090025", JsonUtils.object2Json(map));
			System.out.println(responseStr);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
