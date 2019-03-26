package com.zb.fincore.pms.service.product.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zb.fincore.ams.common.dto.QueryResponse;
import com.zb.fincore.ams.facade.dto.p2p.req.CreateAssetProductRelationRequest;
import com.zb.fincore.ams.facade.dto.req.QueryPoolRequest;
import com.zb.fincore.ams.facade.model.PoolModel;
import com.zb.fincore.common.enums.product.PatternCodeTypeEnum;
import com.zb.fincore.common.utils.BeanUtils;
import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.GlobalConfigConstants;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.enums.CountStockStatusEnum;
import com.zb.fincore.pms.common.enums.OpenProductStatusEnum;
import com.zb.fincore.pms.common.enums.OpenTypeEnum;
import com.zb.fincore.pms.common.enums.P2PProductCollectStatusEnum;
import com.zb.fincore.pms.common.enums.ProductApprovalStatusEnum;
import com.zb.fincore.pms.common.enums.ProductCollectStatusEnum;
import com.zb.fincore.pms.common.enums.RegisterTypeEnum;
import com.zb.fincore.pms.common.exception.BusinessException;
import com.zb.fincore.pms.common.exception.ExceptionHandler;
import com.zb.fincore.pms.common.utils.DataFormatUtil;
import com.zb.fincore.pms.facade.product.dto.req.ApproveProductRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductRequestNFT;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductSaleStatusRequest;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;
import com.zb.fincore.pms.service.SequenceService;
import com.zb.fincore.pms.service.ams.AMSService;
import com.zb.fincore.pms.service.dal.dao.GlobalConfigDao;
import com.zb.fincore.pms.service.dal.dao.ProductCreatePlanDao;
import com.zb.fincore.pms.service.dal.dao.ProductDao;
import com.zb.fincore.pms.service.dal.dao.ProductLineDao;
import com.zb.fincore.pms.service.dal.dao.ProductPeriodDao;
import com.zb.fincore.pms.service.dal.dao.ProductProfitDao;
import com.zb.fincore.pms.service.dal.dao.ProductStockDao;
import com.zb.fincore.pms.service.dal.model.Product;
import com.zb.fincore.pms.service.dal.model.ProductCreatePlan;
import com.zb.fincore.pms.service.dal.model.ProductLine;
import com.zb.fincore.pms.service.dal.model.ProductPeriod;
import com.zb.fincore.pms.service.dal.model.ProductProfit;
import com.zb.fincore.pms.service.dal.model.ProductStock;
import com.zb.fincore.pms.service.order.OrderService;
import com.zb.fincore.pms.service.product.ProductCacheForP2PService;
import com.zb.fincore.pms.service.product.ProductCreatePlanService;
import com.zb.fincore.pms.service.product.ProductForP2PService;
import com.zb.fincore.pms.service.product.ProductNFTService;

/**
 * Function: 自动创建产品计划接口类实现类. <br/>
 * Date: 2018年4月20日 下午5:05:17 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Service
public class ProductCreatePlanServiceImpl implements ProductCreatePlanService {
	private static Logger logger = LoggerFactory.getLogger(ProductCreatePlanServiceImpl.class);
	
	@Autowired
	private ProductCreatePlanDao productCreatePlanDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ProductStockDao productStockDao;
	
	@Autowired
	private ProductLineDao productLineDao;
	
	@Autowired
	private ProductPeriodDao productPeriodDao;
	
	@Autowired
	private ProductProfitDao productProfitDao;
	
	@Autowired
	private GlobalConfigDao globalConfigDao;
	
	/**
     * 序列服务
     */
    @Autowired
    private SequenceService sequenceService;
    
    @Autowired
    private ProductNFTService productNFTService;
    
    @Autowired
    private AMSService amsService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private ProductForP2PService productForP2PService;
    
    @Autowired
    private ProductCacheForP2PService productCacheForP2PService;
    
    @Autowired
    private ExceptionHandler exceptionHandler;
	
	

	
	@Override
	public int insertSelective(ProductCreatePlan productCreatePlan) {
		return productCreatePlanDao.insertSelective(productCreatePlan);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	@Override
	public ProductCreatePlan queryProductPlanByCode(String productCode) throws Exception {
		return productCreatePlanDao.queryProductPlanByCode(productCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	@Override
	public List<ProductCreatePlan> queryProductPlanListByBean(ProductCreatePlan productCreatePlan) throws Exception {
		return productCreatePlanDao.queryProductPlanListByBean(productCreatePlan);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	@Override
	public List<ProductCreatePlan> queryProductPlanListByMap(Map<String, Object> params) throws Exception {
		return productCreatePlanDao.queryProductPlanListByMap(params);
	}
	
	@Override
	public BaseResponse createProductPlan() throws Exception {
		//获取N计划的产品线列表
		ProductLine pl = new ProductLine();
		pl.setPatternCode(PatternCodeTypeEnum.N_LOOP_PLAN.getCode());
		List<ProductLine> productLineList = productLineDao.selectList(pl);
		if (CollectionUtils.isNotEmpty(productLineList) ) {
			for (ProductLine productLine : productLineList) {
				 try{
	             	((ProductCreatePlanService)(AopContext.currentProxy())).doCreateProductPlan(productLine);
	             } catch (BusinessException be) {
	                 logger.error("【创建产品计划job】失败: {}, productLineCode={}", be.getResultMsg() ,productLine.getLineCode());
	             } catch (Exception e) {
	                 logger.error("【创建产品计划job】失败: productLineCode={}"+"\n"+"{}" ,productLine.getLineCode(), e);
//	                 exceptionHandler.handleException(e);
	             }
			}
		}
		return BaseResponse.build();
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	@Override
	public void doCreateProductPlan(ProductLine productLine) throws Exception {
		String productCode = null;
		
		String currentDate = DataFormatUtil.getDateFormat(new Date());//当前日期
		String currentTime = DataFormatUtil.getDateTimeFormat(new Date());//当前时间
        
        //判断当天的产品计划是否已生成
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("planTime", currentDate);
		params.put("productLineCode", productLine.getLineCode());
        if (CollectionUtils.isNotEmpty(productCreatePlanDao.queryProductPlanListByMap(params))) {
        	throw new BusinessException(Constants.FAIL_RESP_CODE, "【" + currentDate + "】当天产品计划已存在，不可重复创建！");
        }
        
        //获取全局配置的时间节点
        String nPlanOpenTimeNodes = globalConfigDao.selectByPropertyName(GlobalConfigConstants.N_PLAN_OPEN_TIME_NODES).getPropertyValue();
        nPlanOpenTimeNodes = (StringUtils.isBlank(nPlanOpenTimeNodes)?"10#15#18#21":nPlanOpenTimeNodes).replace(" ", "");
        String[] nPlanOpenTimeNodesArray = nPlanOpenTimeNodes.split("#");
        String nPlanCountStockIntervalOut = globalConfigDao.selectByPropertyName(GlobalConfigConstants.N_PLAN_COUNT_STOCK_INTERVAL_OUT).getPropertyValue();
        nPlanCountStockIntervalOut = (StringUtils.isBlank(nPlanCountStockIntervalOut)?"30":nPlanCountStockIntervalOut).replace(" ", "");
        String nPlanProductCloseTime = globalConfigDao.selectByPropertyName(GlobalConfigConstants.N_PLAN_PRODUCT_CLOSE_TIME).getPropertyValue();
        nPlanProductCloseTime = (StringUtils.isBlank(nPlanProductCloseTime)?"23:00:00":nPlanProductCloseTime).replace(" ", "");
        String nPlanCountStockIntervalIn = globalConfigDao.selectByPropertyName(GlobalConfigConstants.N_PLAN_COUNT_STOCK_INTERVAL_IN).getPropertyValue();
        nPlanCountStockIntervalIn = (StringUtils.isBlank(nPlanCountStockIntervalIn)?"30":nPlanCountStockIntervalIn).replace(" ", "");
        String nPlanOpenTimeIntervalIn = globalConfigDao.selectByPropertyName(GlobalConfigConstants.N_PLAN_OPEN_TIME_INTERVAL_IN).getPropertyValue();
        nPlanOpenTimeIntervalIn = (StringUtils.isBlank(nPlanOpenTimeIntervalIn)?"30":nPlanOpenTimeIntervalIn).replace(" ", "");
        
		//查询已募集完成的产品列表（创建时间DESC）
		Product product = new Product();
		product.setProductLineCode(productLine.getLineCode());
		product.setCollectStatus(P2PProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_RAISE_COMPLETE.getCode());
		List<Product> productList = productDao.queryProductListByBean(product);
		if (CollectionUtils.isEmpty(productList)) {
			throw new BusinessException(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
		}
		product = productList.get(0);//最后一条
		//组装对外产品的募集结束时间
		Date outSaleEndTime = DataFormatUtil.formatDate(currentDate+" " + nPlanProductCloseTime, "yyyy-MM-dd HH:mm:ss");
		//组装对内产品的募集结束时间
		String fristOpenProductTime = new StringBuffer().append(currentDate).append(" ").append(nPlanOpenTimeNodesArray[0]).append(":00:00").toString();
		Date inSaleEndTime = DataFormatUtil.formatDate(DataFormatUtil.getSpecifiedDayBefore(fristOpenProductTime, 60*2), "yyyy-MM-dd HH:mm:ss");//在第一个对外产品开放时间点提前2小时
		
		//创建对内产品计划信息
		productCode = sequenceService.generateProductCode(Constants.SEQUENCE_NAME_PREFIX_PRODUCT, product.getPatternCode(), 4);
		String countStcokTime = DataFormatUtil.getSpecifiedDayAfter(currentTime, Integer.parseInt(nPlanCountStockIntervalIn));
		String openProductTime = DataFormatUtil.getSpecifiedDayAfter(countStcokTime, Integer.parseInt(nPlanOpenTimeIntervalIn));
		ProductCreatePlan pcl = new ProductCreatePlan();
        pcl.setProductCode(productCode);
        pcl.setProductLineCode(product.getProductLineCode());
        pcl.setCountStockTime(countStcokTime);
        pcl.setOpenProductTime(openProductTime);
        pcl.setPlanTime(currentDate);
        pcl.setCountStockStatus(CountStockStatusEnum.INIT.getCode());
        pcl.setOpenProductStatus(OpenProductStatusEnum.INIT.getCode());
        pcl.setTotalAmount(BigDecimal.ZERO);
        pcl.setOpenType(OpenTypeEnum.IN.getCode());
        productCreatePlanDao.insertSelective(pcl);
        
        ProductProfit productProfit = productProfitDao.selectProductProfitInfoByProductCode(product.getProductCode());
        ProductPeriod productPeriod = productPeriodDao.selectProductPeriodInfoByProductCode(product.getProductCode());
        
        //创建对内产品信息
        String productName = product.getProductDisplayName();//产品名称
        RegisterProductRequestNFT regNFTINReq = BeanUtils.copyAs(product, RegisterProductRequestNFT.class);
        regNFTINReq.setProductCode(productCode);
        regNFTINReq.setProductName(productName);
        regNFTINReq.setProductDisplayName(productName);
        regNFTINReq.setTotalAmount(BigDecimal.ZERO);
        regNFTINReq.setRegisterType(RegisterTypeEnum.AUTO.getCode());
        regNFTINReq.setSaleStartTime(DataFormatUtil.formatDate(openProductTime, "yyyy-MM-dd HH:mm:ss"));
        regNFTINReq.setSaleEndTime(inSaleEndTime);
        BigDecimal increaseOrMinInvestAmt = new BigDecimal("0.01");
        regNFTINReq.setOpenType(OpenTypeEnum.IN.getCode());
        regNFTINReq.setLockPeriod(0);//锁定期为空        
        regNFTINReq.setIncreaseAmount(increaseOrMinInvestAmt);//步长
        regNFTINReq.setMinInvestAmount(increaseOrMinInvestAmt);//起头金额
        regNFTINReq.setMaxInvestAmount(productProfit.getMaxInvestAmount());
        regNFTINReq.setMinYieldRate(productProfit.getMinYieldRate());
        regNFTINReq.setMaxYieldRate(productProfit.getMaxYieldRate());
        regNFTINReq.setSingleMaxInvestAmount(productProfit.getSingleMaxInvestAmount());
        productNFTService.registerProductNFT(regNFTINReq);

        //创建对外产品计划信息（产品数量=配置的时间节点数）、对外产品信息
        for (int i=0;i<nPlanOpenTimeNodesArray.length;i++) {
        	String openProductPlanTime = new StringBuffer().append(DataFormatUtil.getDateFormat(new Date())).append(" ").append(nPlanOpenTimeNodesArray[i]).append(":00:00").toString();//对外开放的产品时间
        	String countProductPlanStockTime = DataFormatUtil.getSpecifiedDayBefore(openProductPlanTime, Integer.parseInt(nPlanCountStockIntervalOut));//产品的计算库存时间
        	productCode = sequenceService.generateProductCode(Constants.SEQUENCE_NAME_PREFIX_PRODUCT, product.getPatternCode(), 4);
        	//创建对外产品计划信息
        	pcl = new ProductCreatePlan();
	        pcl.setProductCode(productCode);
	        pcl.setProductLineCode(product.getProductLineCode());
	        pcl.setCountStockTime(countProductPlanStockTime);
	        pcl.setOpenProductTime(openProductPlanTime);
	        pcl.setPlanTime(currentDate);
	        pcl.setCountStockStatus(CountStockStatusEnum.INIT.getCode());
	        pcl.setOpenProductStatus(OpenProductStatusEnum.INIT.getCode());
	        pcl.setTotalAmount(BigDecimal.ZERO);
	        pcl.setOpenType(OpenTypeEnum.OUT.getCode());
	        productCreatePlanDao.insertSelective(pcl);
	        //创建对外产品信息
	        RegisterProductRequestNFT regNFTOutReq = BeanUtils.copyAs(product, RegisterProductRequestNFT.class);
	        regNFTOutReq.setProductCode(productCode);
	        regNFTOutReq.setProductName(productName);
	        regNFTOutReq.setProductDisplayName(productName);
	        regNFTOutReq.setTotalAmount(BigDecimal.ZERO);
	        regNFTOutReq.setRegisterType(RegisterTypeEnum.AUTO.getCode());
	        regNFTOutReq.setSaleStartTime(DataFormatUtil.formatDate(openProductPlanTime, "yyyy-MM-dd HH:mm:ss"));
	        regNFTOutReq.setSaleEndTime(outSaleEndTime);
	        regNFTOutReq.setLockPeriod(productPeriod.getLockPeriod());
	        regNFTOutReq.setLockPeriodUnit(productPeriod.getLockPeriodUnit());
	        regNFTOutReq.setIncreaseAmount(productProfit.getIncreaseAmount());//步长
	        regNFTOutReq.setMinInvestAmount(productProfit.getMinInvestAmount());//起头金额
	        regNFTOutReq.setMaxInvestAmount(productProfit.getMaxInvestAmount());
	        regNFTOutReq.setMinYieldRate(productProfit.getMinYieldRate());
	        regNFTOutReq.setMaxYieldRate(productProfit.getMaxYieldRate());
	        regNFTOutReq.setSingleMaxInvestAmount(productProfit.getSingleMaxInvestAmount());
	        productNFTService.registerProductNFT(regNFTOutReq);
        }
	}
	
	@Override
	public BaseResponse countProductPlanStock() throws Exception {
		//在规定时间段内查询待计算库存的计划
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("countStockStatus", CountStockStatusEnum.INIT.getCode());
		params.put("planTime", DataFormatUtil.getDateFormat(new Date()));//只获取当天的计划
		params.put("countStockFlag", Constants.CONSTANT_VALUE_1);
		params.put("curTime", new Date());//当前时间
		List<ProductCreatePlan> productCreatePlanList = this.queryProductPlanListByMap(params);
		if (CollectionUtils.isNotEmpty(productCreatePlanList)) {
			//若计划对内，则请求资管、交易获取昨日可用债转资产、昨日可用复投金额
			for (ProductCreatePlan productCreatePlan : productCreatePlanList) {
				try{
	             	((ProductCreatePlanService)(AopContext.currentProxy())).doCountProductPlanStock(productCreatePlan);
	             }catch (BusinessException be) {
	            	 logger.error("【计算计划产品库存job】失败: {}, productCreatePlanId={}", be.getResultMsg() ,productCreatePlan.getId());
	             }catch (Exception e) {
	                 logger.error("【计算计划产品库存job】失败: productCreatePlanId={}"+"\n"+"{}" ,productCreatePlan.getId(), e);
	             }
			}
		}
		
		return BaseResponse.build();
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	@Override
	public void doCountProductPlanStock(ProductCreatePlan productCreatePlan) throws Exception {
		ProductCreatePlan pcl = new ProductCreatePlan();
		BigDecimal totalAmount = BigDecimal.ZERO;//产品计划库存
		String countStockStatus = CountStockStatusEnum.SUCCESS.getCode();
		String errorMsg = null;
		
//		//根据产品线查产品列表（创建时间DESC）
//		Product product = new Product();
//		product.setProductLineCode(productCreatePlan.getProductLineCode());
//		product.setPatternCode(PatternCodeTypeEnum.N_LOOP_PLAN.getCode());
//		List<Product> productList = productDao.queryProductListByBean(product);
//		if (productList==null || productList.isEmpty() ) {
//			throw new BusinessException(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
//		}
//		//若在时间点有多条记录，则取最后一条，目的是计算最后一条，其他都是有原因导致逾期未计算的
//		product = productList.get(0);
		
		Product product = productDao.selectProductByCode(productCreatePlan.getProductCode());
		if (product==null) {
			throw new BusinessException(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
		}
        //获取可用资产
        QueryPoolRequest queryPoolRequest = new QueryPoolRequest();
		queryPoolRequest.setPoolCode(product.getAssetPoolCode());
		QueryResponse<PoolModel> queryPoolResp = amsService.queryPoolInfoHttp(queryPoolRequest);
		PoolModel poolModel = queryPoolResp.getData();
		
		BigDecimal stockAmt = poolModel.getStockAmount()==null?BigDecimal.ZERO:poolModel.getStockAmount();//资产池库存金额  = 债转资产 + 可用原始资产
		BigDecimal transferAmt = poolModel.getTransferAmount()==null?BigDecimal.ZERO:poolModel.getTransferAmount();//债转资产（包含利息）
		
		if (productCreatePlan.getOpenType().equals(OpenTypeEnum.IN.getCode())) {
			//获取昨日可用复投金额
			BigDecimal fuTouAmt = orderService.queryAutoInvestAmtHttp(DataFormatUtil.getDateTimeFormat(new Date()));
			//校验
			if (transferAmt.compareTo(BigDecimal.ZERO)<1 || fuTouAmt.compareTo(BigDecimal.ZERO)<1 ) {
				countStockStatus = CountStockStatusEnum.FAIL.getCode();
				errorMsg = "对内产品计算库存时，昨日可用债转金额和昨日可用复投金额必须同时大于0";
			}
			totalAmount = transferAmt;
		} else {
			String nPlanOutProductOpenAmtThreshold = globalConfigDao.selectByPropertyName(GlobalConfigConstants.N_PLAN_PRODUCT_OPEN_AMT_THRESHOLD_OUT).getPropertyValue();
			nPlanOutProductOpenAmtThreshold = StringUtils.isBlank(nPlanOutProductOpenAmtThreshold)?"100000":nPlanOutProductOpenAmtThreshold;
			//校验
			if (stockAmt.compareTo(new BigDecimal(nPlanOutProductOpenAmtThreshold))<0) {
				countStockStatus = CountStockStatusEnum.FAIL.getCode();
				errorMsg = new StringBuilder().append("对外产品计算库存时，库存金额必须大于").append(nPlanOutProductOpenAmtThreshold).toString();
			}
			totalAmount = stockAmt;
		}
		
		if (countStockStatus!=CountStockStatusEnum.SUCCESS.getCode()) {
			final long pclIdF = productCreatePlan.getId();
			final String countStockStatusF = countStockStatus;
			final String errorMsgF = errorMsg;
			new Thread(new Runnable(){
				@Override  
                public void run() {  
					ProductCreatePlan pcl = new ProductCreatePlan();
					pcl.setId(pclIdF);
					pcl.setCountStockStatus(countStockStatusF);
					pcl.setMemo(errorMsgF);
					productCreatePlanDao.updateByPrimaryKeySelective(pcl);
				}
			}).start();
			throw new BusinessException(Constants.FAIL_RESP_CODE, errorMsg);
		}
		
		//更新产品计划的库存、状态
		pcl.setId(productCreatePlan.getId());
		pcl.setCountStockStatus(countStockStatus);
		pcl.setTotalAmount(totalAmount);
		pcl.setRealCountStockTime(DataFormatUtil.getDateTimeFormat(new Date()));
		productCreatePlanDao.updateByPrimaryKeySelective(pcl);
		
		//更新产品表的产品总规模
		Product p = new Product();
		p.setId(product.getId());
		p.setTotalAmount(totalAmount);
		productDao.updateByPrimaryKeySelective(p);
		
		//更新库存表里剩余库存金额
		ProductStock productStock = new ProductStock();
		productStock.setProductCode(product.getProductCode());
		productStock.setStockAmount(totalAmount);
		productStockDao.updateByProductCode(productStock);
		
		//计算好库存后，通知资管，告知产品与资产池关系
		CreateAssetProductRelationRequest createReq = new CreateAssetProductRelationRequest();
    	createReq.setProductCode(product.getProductCode());
    	createReq.setPoolCode(product.getAssetPoolCode());
    	createReq.setProductType(productCreatePlan.getOpenType());
    	createReq.setProductAmount(totalAmount);
    	createReq.setTransferAmount(transferAmt);
    	amsService.createAssetProductRelationHttp(createReq);
    	
    	//产品审核
        ApproveProductRequest appReq = new ApproveProductRequest();
        appReq.setProductCode(product.getProductCode());
        appReq.setApprovalStatus(ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode());
        appReq.setApprovalBy("sys");
        appReq.setSign("A");
        productNFTService.approveProduct(appReq);
        
        //产品上线(对内产品不上线)
        if (productCreatePlan.getOpenType().equals(OpenTypeEnum.OUT.getCode())) {
        	UpdateProductSaleStatusRequest onLineReq = new UpdateProductSaleStatusRequest();
            onLineReq.setProductCode(product.getProductCode());
            BaseResponse baseResponse = productForP2PService.putProductOnLine(onLineReq);
            if (baseResponse.getRespCode().equals(Constants.SUCCESS_RESP_CODE)) {
				productCacheForP2PService.refreshOnSaleProductListForP2PCache();
	        	productCacheForP2PService.refreshSoldOutProductListForP2PCache();
			}
        }
	}

	@Override
	public BaseResponse openProductPaln() throws Exception {
		//在规定时间段内查询待开放的计划
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("countStockStatus", CountStockStatusEnum.SUCCESS.getCode());
		params.put("openProductStatus", OpenProductStatusEnum.INIT.getCode());
		params.put("planTime", DataFormatUtil.getDateFormat(new Date()));//只获取当天的计划
		params.put("openProductFlag", Constants.CONSTANT_VALUE_1);
		params.put("curTime", new Date());//当前时间
		List<ProductCreatePlan> productCreatePlanList = this.queryProductPlanListByMap(params);
		if (CollectionUtils.isNotEmpty(productCreatePlanList) ) {
			for (ProductCreatePlan productCreatePlan : productCreatePlanList) {
				try{
	             	((ProductCreatePlanService)(AopContext.currentProxy())).doOpenProductPaln(productCreatePlan);
	             }catch (BusinessException be) {
	                 logger.error("【开放对外产品计划job】失败:{}, productCreatePlanId={}", be.getResultMsg() ,productCreatePlan.getId());
	             }catch (Exception e) {
	                 logger.error("【开放对外产品计划job】失败: productCreatePlanId={}"+"\n"+"{}" ,productCreatePlan.getId(), e);
	             }
			}
		}
		
		return BaseResponse.build();
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	@Override
	public void doOpenProductPaln(ProductCreatePlan productCreatePlan) throws Exception {
		ProductCreatePlan pcl = new ProductCreatePlan();
		String openProductStatus = OpenProductStatusEnum.SUCCESS.getCode();
		String errorMsg = null;
		
		//根据产品线查产品列表（创建时间DESC）
		Product product = new Product();
		product.setProductLineCode(productCreatePlan.getProductLineCode());
		product.setPatternCode(PatternCodeTypeEnum.N_LOOP_PLAN.getCode());
		product.setCollectStatus(P2PProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_COLLECTING.getCode());
		List<Product> productList = productDao.queryProductListByBean(product);
		if (CollectionUtils.isNotEmpty(productList) ) {
			openProductStatus = OpenProductStatusEnum.FAIL.getCode();
			errorMsg = "开放产品失败，该产品线下有正在售卖的产品！";
		}
		
		//查询产品信息
		product = productDao.selectProductByCode(productCreatePlan.getProductCode());
        if (null == product) {
            throw new BusinessException(Constants.PRODUCT_NOT_EXIST_RETURN_CODE, Constants.PRODUCT_NOT_EXIST_RETURN_DESC);
        }
        
        if (openProductStatus!=OpenProductStatusEnum.SUCCESS.getCode()) {
        	final long pclIdF = productCreatePlan.getId();
			final String openProductStatusF = openProductStatus;
			final String errorMsgF = errorMsg;
			new Thread(new Runnable(){
				@Override  
                public void run() {  
					ProductCreatePlan pcl = new ProductCreatePlan();
					pcl.setId(pclIdF);
					pcl.setOpenProductStatus(openProductStatusF);
					pcl.setMemo(errorMsgF);
					productCreatePlanDao.updateByPrimaryKeySelective(pcl);
				}
			}).start();
			throw new BusinessException(Constants.FAIL_RESP_CODE, errorMsg);
        }
        
        //修改产品计划相应的状态
        pcl.setId(productCreatePlan.getId());
		pcl.setOpenProductStatus(OpenProductStatusEnum.SUCCESS.getCode());
		pcl.setRealOpenProductTime(DataFormatUtil.getDateTimeFormat(new Date()));
		pcl.setMemo(errorMsg);
		productCreatePlanDao.updateByPrimaryKeySelective(pcl);
        
        //修改产品的募集状态=募集期
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", product.getId());
        params.put("originalStatus", product.getCollectStatus());
        params.put("collectStatus", P2PProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_COLLECTING.getCode());
        if (productDao.updateProductCollectStatusById(params)>0) {
        	productCacheForP2PService.refreshOnSaleProductListForP2PCache();
        	productCacheForP2PService.refreshSoldOutProductListForP2PCache();
        }
        
		//通知订单
     	orderService.newProductHttp(product.getProductCode());
	}
	

}
