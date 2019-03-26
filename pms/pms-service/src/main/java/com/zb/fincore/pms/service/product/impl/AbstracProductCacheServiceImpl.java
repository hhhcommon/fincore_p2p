package com.zb.fincore.pms.service.product.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.zb.fincore.common.redis.RedisLock;
import com.zb.fincore.common.utils.DecimalUtils;
import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.QueryResponse;
import com.zb.fincore.pms.common.enums.ChangeProductStockStatusEnum;
import com.zb.fincore.pms.common.enums.ChangeProductStockTypeEnum;
import com.zb.fincore.pms.common.enums.ProductApprovalStatusEnum;
import com.zb.fincore.pms.common.enums.ProductDisplayStatusEnum;
import com.zb.fincore.pms.common.exception.BusinessException;
import com.zb.fincore.pms.facade.line.dto.req.QueryCacheProductRequest;
import com.zb.fincore.pms.facade.line.dto.req.QueryCacheProductStockRequest;
import com.zb.fincore.pms.facade.product.dto.req.ChangeProductStockRequest;
import com.zb.fincore.pms.facade.product.dto.req.FreezeProductStockRequest;
import com.zb.fincore.pms.facade.product.dto.resp.ChangeProductStockResponse;
import com.zb.fincore.pms.facade.product.dto.resp.FreezeProductStockResponse;
import com.zb.fincore.pms.facade.product.model.ProductContractModel;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zb.fincore.pms.facade.product.model.ProductPeriodModel;
import com.zb.fincore.pms.facade.product.model.ProductProfitModel;
import com.zb.fincore.pms.facade.product.model.ProductStockModel;
import com.zb.fincore.pms.service.dal.dao.ProductDao;
import com.zb.fincore.pms.service.dal.dao.ProductStockChangeFlowDao;
import com.zb.fincore.pms.service.dal.dao.ProductStockChangeReqDao;
import com.zb.fincore.pms.service.dal.dao.ProductStockDao;
import com.zb.fincore.pms.service.dal.model.Product;
import com.zb.fincore.pms.service.dal.model.ProductContract;
import com.zb.fincore.pms.service.dal.model.ProductStock;
import com.zb.fincore.pms.service.dal.model.ProductStockChangeFlow;
import com.zb.fincore.pms.service.dal.model.ProductStockChangeReq;
import com.zb.fincore.pms.service.product.ProductCacheService;
import com.zb.fincore.pms.service.product.validate.ProductCacheServiceParameterValidator;

/**
 * 功能: 产品缓存接口类
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/6 0006 16:58
 * 版本: V1.0
 */
public abstract class AbstracProductCacheServiceImpl extends BaseCacheServiceImpl implements ProductCacheService {

    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(AbstracProductCacheServiceImpl.class);

    @Autowired
    protected ProductCacheServiceParameterValidator productCacheParameterValidate;

    /**
     * 产品库存变更请求数据访问对象
     */
    @Autowired
    protected ProductStockChangeReqDao productStockChangeReqDao;

    /**
     * 产品库存数据访问对象
     */
    @Autowired
    protected ProductStockDao productStockDao;

    /**
     * 产品库存变更流水数据访问对象
     */
    @Autowired
    protected ProductStockChangeFlowDao productStockChangeFlowDao;

    /**
     * 产品信息数据访问对象
     */
    @Autowired
    protected ProductDao productDao;

    /**
     * 产品缓存集合
     */
    protected static LinkedHashMap<String, ProductModel> PRODUCT_CACHE_MAP = new LinkedHashMap<String, ProductModel>();

    /**
     * 刷新产品缓存
     *
     * @return 通用结果
     */
    @Override
    public synchronized BaseResponse refreshProductCache() throws Exception {
        LinkedHashMap<String, ProductModel> productModelMap = new LinkedHashMap<String, ProductModel>();
        Product queryProduct = new Product();
        queryProduct.setApprovalStatus(ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode());
        queryProduct.setDisplayStatus(ProductDisplayStatusEnum.VISIBLE.getCode());
        List<Product> productList = productDao.queryProductListByBean(queryProduct);
        if (!CollectionUtils.isEmpty(productList)) {
            logger.info("刷新产品缓存完毕,缓存中产品数量:" + productList.size());
            for (Product product : productList) {
                ProductModel model = new ProductModel();
                PropertyUtils.copyProperties(model, product);

                //产品期限信息
                ProductPeriodModel productPeriodModel = new ProductPeriodModel();
                PropertyUtils.copyProperties(productPeriodModel, product.getProductPeriod());
                model.setProductPeriodModel(productPeriodModel);

                //产品投资限制及收益信息
                ProductProfitModel productProfitModel = new ProductProfitModel();
                PropertyUtils.copyProperties(productProfitModel, product.getProductProfit());
                model.setProductProfitModel(productProfitModel);

                //产品合同
                if (!CollectionUtils.isEmpty(product.getProductContractList())) {
                    List<ProductContractModel> productContractModels = new ArrayList<ProductContractModel>();
                    for (ProductContract contract : product.getProductContractList()) {
                        ProductContractModel contractModel = new ProductContractModel();
                        PropertyUtils.copyProperties(contractModel, contract);
                        productContractModels.add(contractModel);
                    }
                    model.setProductContractModelList(productContractModels);
                }

                super.updateRedisProductStock(product.getProductStock());
                productModelMap.put(model.getProductCode(), model);

                logger.info("产品编码:" + model.getProductCode());
            }
        }
        PRODUCT_CACHE_MAP.clear();
        PRODUCT_CACHE_MAP = null;
        PRODUCT_CACHE_MAP = productModelMap;
        return BaseResponse.build();
    }

    /**
     * 查询缓存中产品详情
     *
     * @param req 产品详情查询请求对象
     * @return 产品详情询响应对象
     */
    @Override
    public QueryResponse<ProductModel> queryProduct(@Valid QueryCacheProductRequest req) throws Exception {
        QueryResponse<ProductModel> queryResponse = BaseResponse.build(QueryResponse.class);
        if (PRODUCT_CACHE_MAP.containsKey(req.getProductCode())) {
            ProductModel productModel = PRODUCT_CACHE_MAP.get(req.getProductCode());
            ProductStockModel stockModel = super.getRedisProductStock(req.getProductCode());
            productModel.setProductStockModel(stockModel);
            queryResponse.setData(productModel);
        } else {
            queryResponse.setRespCode(Constants.PARAM_RESULTBLANK_CODE);
            queryResponse.setRespMsg(Constants.PARAM_RESULTBLANK_DESC);
        }
        return queryResponse;
    }

    /**
     * 查询缓存中产品库存信息
     *
     * @param req 查询请求对象
     * @return 响应对象
     */
    @Override
    public QueryResponse<ProductStockModel> queryProductStock(@Valid QueryCacheProductStockRequest req) throws Exception {
        QueryResponse<ProductStockModel> queryResponse = BaseResponse.build(QueryResponse.class);
        ProductStockModel stockModel = super.getRedisProductStock(req.getProductCode());
        if (stockModel == null) {
            queryResponse.setRespCode(Constants.PARAM_RESULTBLANK_CODE);
            queryResponse.setRespMsg(Constants.PARAM_RESULTBLANK_DESC);
        } else {
            queryResponse.setData(stockModel);
        }
        return queryResponse;
    }

    /**
     * 冻结产品库存
     *
     * @param req 冻结产品库存请求对象
     * @return 响应对象
     */
    @Override
    public FreezeProductStockResponse freezeProductStock(@Valid FreezeProductStockRequest req) throws Exception {
        StringBuffer errorMsg = new StringBuffer().append("【refNo=").append(req.getRefNo()).append(", productCode=").append(req.getProductCode()).append("】");
        FreezeProductStockResponse resp = BaseResponse.build(FreezeProductStockResponse.class);
        PropertyUtils.copyProperties(resp, req);

        //校验
        Product product = productDao.selectProductByCode(req.getProductCode());
        BaseResponse  baseResponse = productCacheParameterValidate.productStockRequestParameter(product, ChangeProductStockTypeEnum.FREEZE.getCode());
        if (baseResponse!=null && baseResponse.getRespCode().equals(Constants.FAIL_RESP_CODE) ) {
        	PropertyUtils.copyProperties(resp, baseResponse);
        	return resp;
        }

        //查询流水是否存在
        ProductStockChangeReq changeReq = new ProductStockChangeReq();
        changeReq.setRefNo(req.getRefNo());
        changeReq.setChangeType(ChangeProductStockTypeEnum.FREEZE.getCode());
        changeReq = productStockChangeReqDao.select(changeReq);
        if (changeReq != null) {
            int status = changeReq.getStatus();
            if (status == ChangeProductStockStatusEnum.PROCESSING.getCode()) {
                errorMsg.append("相同流水号正在处理中");
                resp.setStatus(ChangeProductStockStatusEnum.FAIL.getCode());
                resp.setAddition(errorMsg.toString());
                resp.setRespMsg(resp.getAddition());
            } else {
                errorMsg.append("幂等结果");
                resp.setStatus(changeReq.getStatus());
                resp.setAddition(errorMsg.toString());
                resp.setRespMsg(resp.getAddition());
            }
            return resp;
        }

        //流水不存在,则插入申请记录
        changeReq = new ProductStockChangeReq();
        PropertyUtils.copyProperties(changeReq, req);
        changeReq.setChangeType(ChangeProductStockTypeEnum.FREEZE.getCode());
        changeReq.setStatus(ChangeProductStockStatusEnum.PROCESSING.getCode());
        productStockChangeReqDao.insertSelective(changeReq);
        try {
            //真正冻结逻辑处理
            internalFreezeProductStock(req, resp);
        } catch (Exception e) {
            logger.error("冻结库存异常", e);
        }
        //根据冻结结果更新申请记录
        changeReq.setStatus(resp.getStatus());
        changeReq.setMemo(resp.getAddition());
        productStockChangeReqDao.updateByPrimaryKeySelective(changeReq);
        return resp;
    }

    /**
     * 冻结产品库存内部处理
     *
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private synchronized void internalFreezeProductStock(FreezeProductStockRequest req, FreezeProductStockResponse resp) throws Exception {
        //加Redis锁
        RedisLock redisLock = new RedisLock(redisManager, Constants.PRODUCT_STOCK_CHANGE_LOCK_PREFIX + req.getProductCode());
//        boolean locked = redisLock.tryLock(3000);
        boolean locked = redisLock.tryLock(3, TimeUnit.SECONDS);//3秒转成毫微秒
        if (!locked) {
            resp.setRespCode(Constants.FAIL_RESP_CODE);
            resp.setStatus(ChangeProductStockStatusEnum.FAIL.getCode());
            resp.setAddition("获取分布式锁失败");
            resp.setRespMsg(resp.getAddition());
            return;
        }
        try {
            //查询产品库存
            ProductStock productStock = productStockDao.selectProductStockByProductCode(req.getProductCode());
            if (productStock == null) {
                resp.setStatus(ChangeProductStockStatusEnum.FAIL.getCode());
                resp.setAddition("未查询到产品库存信息:" + req.getProductCode());
                resp.setRespCode(Constants.FAIL_RESP_CODE);
                resp.setRespMsg(resp.getAddition());
                return;
            }
            //比较库存
            if (DecimalUtils.lt(productStock.getStockAmount(), req.getChangeAmount())) {
                resp.setStatus(ChangeProductStockStatusEnum.NOT_ENOUGH_STOCK.getCode());
                resp.setAddition("产品库存不足:" + req.getProductCode());
                resp.setRespCode(Constants.FAIL_RESP_CODE);
                resp.setRespMsg(resp.getAddition());
                return;
            }
            //记录变更流水
            ProductStockChangeFlow changeFlow = new ProductStockChangeFlow();
            PropertyUtils.copyProperties(changeFlow, req);
            changeFlow.setProductId(productStock.getProductId());
            changeFlow.setChangeType(ChangeProductStockTypeEnum.FREEZE.getCode());
            changeFlow.setStockAmountBefore(productStock.getStockAmount());
            changeFlow.setFrozenAmountBefore(productStock.getFrozenAmount());
            changeFlow.setSaleAmountBefore(productStock.getSaleAmount());
            changeFlow.setSaleAmountAfter(productStock.getSaleAmount());
            changeFlow.setRedeemAmountBefore(productStock.getRedeemAmount());
            changeFlow.setRedeemAmountAfter(productStock.getRedeemAmount());
            BigDecimal stockAmount = productStock.getStockAmount().subtract(req.getChangeAmount());
            BigDecimal frozenAmount = productStock.getFrozenAmount().add(req.getChangeAmount());
            changeFlow.setStockAmountAfter(stockAmount);
            changeFlow.setFrozenAmountAfter(frozenAmount);
            productStockChangeFlowDao.insertSelective(changeFlow);

            //变更库存
            productStock.setStockAmount(stockAmount);
            productStock.setFrozenAmount(frozenAmount);
            int row = productStockDao.updateStockWithLock(productStock);
            if (row <= 0) {
                resp.setStatus(ChangeProductStockStatusEnum.FAIL.getCode());
                resp.setAddition("乐观锁更新产品库存失败:" + req.getProductCode());
                resp.setRespCode(Constants.FAIL_RESP_CODE);
                resp.setRespMsg(resp.getAddition());
                throw new BusinessException(Constants.FAIL_RESP_CODE, "乐观锁更新产品库存失败:" + req.getProductCode());
            }
            super.updateRedisProductStock(productStock);
            resp.setStatus(ChangeProductStockStatusEnum.SUCCESS.getCode());
            resp.setAddition("冻结成功");
        } catch (Exception e) {
            throw e;
        } finally {
            redisLock.unlock();
        }
    }

    /**
     * 产品库存变更
     *
     * @param req
     * @return
     */
    public ChangeProductStockResponse changeProductStock(ChangeProductStockRequest req) throws Exception {
        ChangeProductStockResponse resp = BaseResponse.build(ChangeProductStockResponse.class);
        StringBuffer errorMsg = new StringBuffer();
        try {
            PropertyUtils.copyProperties(resp, req);
            resp.setStatus(ChangeProductStockStatusEnum.PROCESSING.getCode());

            //校验
            Product product = productDao.selectProductByCode(req.getProductCode());
            BaseResponse  baseResponse = productCacheParameterValidate.productStockRequestParameter(product, req.getChangeType() );
            if (baseResponse!=null && baseResponse.getRespCode().equals(Constants.FAIL_RESP_CODE) ) {
            	PropertyUtils.copyProperties(resp, baseResponse);
            	return resp;
            }

            //查询流水是否存在
            ProductStockChangeReq changeReq = new ProductStockChangeReq();
            changeReq.setRefNo(req.getRefNo());
            changeReq.setChangeType(req.getChangeType());
            changeReq = productStockChangeReqDao.select(changeReq);
            if (changeReq != null) {
                //如果流水已经存在,且处理异常,则重新处理,否则不处理
                if (changeReq.getStatus() != ChangeProductStockStatusEnum.FAIL.getCode()) {
                    errorMsg.append("幂等结果");
                    resp.setStatus(changeReq.getStatus());
                    resp.setAddition(errorMsg.toString());
                    return resp;
                }
            } else {//添加，changeReq 为null报错
                changeReq = new ProductStockChangeReq();
                changeReq.setRefNo(req.getRefNo());
                changeReq.setChangeType(req.getChangeType());
            }

            //根据原纪录设置本次变更的值
            if (req.getChangeType() != ChangeProductStockTypeEnum.REDEEM.getCode()) {
                //不是赎回则查询原冻结记录
                ProductStockChangeReq freezeReq = new ProductStockChangeReq();
                freezeReq.setRefNo(req.getRefNo());
                freezeReq.setChangeType(ChangeProductStockTypeEnum.FREEZE.getCode());
                //freezeReq = productStockChangeReqDao.select(freezeReq);


                List<ProductStockChangeReq> productStockChangeReqList = productStockChangeReqDao.queryProductStockChangeReqList(freezeReq);
                if (null == productStockChangeReqList || productStockChangeReqList.size() <= 0) {
                    resp.setStatus(ChangeProductStockStatusEnum.FAIL.getCode());
                    resp.setAddition("未查询到冻结记录");
                } else {
                    boolean freezeSuccessRecord = false;
                    for (ProductStockChangeReq stockChangeReq : productStockChangeReqList) {
                        if (stockChangeReq.getStatus() == ChangeProductStockStatusEnum.SUCCESS.getCode()) {
                            freezeSuccessRecord = true;
                            freezeReq = stockChangeReq;
                            break;
                        }
                    }

                    if (!freezeSuccessRecord) {
                        resp.setStatus(ChangeProductStockStatusEnum.FAIL.getCode());
                        resp.setAddition("冻结记录未成功冻结");
                    } else {
                        resp.setStatus(ChangeProductStockStatusEnum.PROCESSING.getCode());//添加，req没传值 插入申请记录 报错
                        req.setChangeAmount(freezeReq.getChangeAmount());//添加，req没传值 internalChangeProductStock 报错
                        changeReq.setProductCode(freezeReq.getProductCode());
                        changeReq.setChangeAmount(freezeReq.getChangeAmount());

                        //若是解冻，则已占用后的库存订单不可以再解冻(20171203处理超卖)
                        if (req.getChangeType() == ChangeProductStockTypeEnum.RELEASE.getCode()) {
                            ProductStockChangeReq releaseStockReq = new ProductStockChangeReq();
                            releaseStockReq.setRefNo(req.getRefNo());
                            releaseStockReq.setChangeType(ChangeProductStockTypeEnum.OCCUPY.getCode());
                            releaseStockReq.setStatus(ChangeProductStockStatusEnum.SUCCESS.getCode());
                            releaseStockReq = productStockChangeReqDao.select(releaseStockReq);
                            if (null != releaseStockReq) {
                                resp.setStatus(ChangeProductStockStatusEnum.FAIL.getCode());
                                resp.setAddition("库存已占用，不可以解冻");
                            }
                        }

                        //若是占用，看库存是否已经释放
                        if (req.getChangeType() == ChangeProductStockTypeEnum.OCCUPY.getCode()) {
                            ProductStockChangeReq releaseStockReq = new ProductStockChangeReq();
                            releaseStockReq.setRefNo(req.getRefNo());
                            releaseStockReq.setChangeType(ChangeProductStockTypeEnum.RELEASE.getCode());
                            releaseStockReq = productStockChangeReqDao.select(releaseStockReq);
                            if (null != releaseStockReq) {
                                resp.setStatus(ChangeProductStockStatusEnum.FAIL.getCode());
                                resp.setAddition("库存已经释放，不可以占用");
                            }
                        }
                    }
                }
            } else {
                changeReq.setProductCode(req.getProductCode());
                changeReq.setChangeAmount(req.getChangeAmount() != null ? req.getChangeAmount() : BigDecimal.ZERO);
            }

            //插入申请记录
            if (resp.getStatus() == ChangeProductStockStatusEnum.FAIL.getCode()
                    || resp.getStatus() == ChangeProductStockStatusEnum.ERROR.getCode()) {
                changeReq.setStatus(resp.getStatus());
                changeReq.setMemo(resp.getAddition());
            } else {
                changeReq.setStatus(ChangeProductStockStatusEnum.PROCESSING.getCode());
            }
            productStockChangeReqDao.insertSelective(changeReq);

            //根据上面逻辑，如果resp 为失败，不需要进行下面的  库存逻辑变更处理；
            if (resp.getStatus() == ChangeProductStockStatusEnum.FAIL.getCode()
                    || resp.getStatus() == ChangeProductStockStatusEnum.ERROR.getCode()) {
                return resp;
            }

            try {
                //真正变更逻辑处理
                internalChangeProductStock(req, resp);
            } catch (Exception e) {
                logger.error("变更库存异常", e);
            }
            //根据冻结结果更新申请记录
            changeReq.setStatus(resp.getStatus());
            changeReq.setMemo(resp.getAddition());
            productStockChangeReqDao.updateByPrimaryKeySelective(changeReq);
        } catch (Exception e) {
            logger.error("库存变更异常", e);
            resp.setStatus(ChangeProductStockStatusEnum.ERROR.getCode());
        }
        return resp;
    }

    @Override
    public ChangeProductStockResponse changeProductStockWithoutFreezeRecord(ChangeProductStockRequest req) throws Exception {
        ChangeProductStockResponse resp = BaseResponse.build(ChangeProductStockResponse.class);
        try {
            PropertyUtils.copyProperties(resp, req);
            resp.setStatus(ChangeProductStockStatusEnum.PROCESSING.getCode());

            //查询流水是否存在
            ProductStockChangeReq changeReq = new ProductStockChangeReq();
            changeReq.setRefNo(req.getRefNo());
            changeReq.setChangeType(req.getChangeType());
            changeReq = productStockChangeReqDao.select(changeReq);
            if (changeReq != null) {
                //如果流水已经存在,且处理异常,则重新处理,否则不处理
                if (changeReq.getStatus() != ChangeProductStockStatusEnum.FAIL.getCode()) {
                    resp.setStatus(changeReq.getStatus());
                    return resp;
                }
            } else {//添加，changeReq 为null报错
                changeReq = new ProductStockChangeReq();
                changeReq.setRefNo(req.getRefNo());
                changeReq.setChangeType(req.getChangeType());
            }

            //根据原纪录设置本次变更的值
            if (req.getChangeType() != ChangeProductStockTypeEnum.REDEEM.getCode()) {
                resp.setStatus(ChangeProductStockStatusEnum.PROCESSING.getCode());//添加，req没传值 插入申请记录 报错
                req.setChangeAmount(req.getChangeAmount());//添加，req没传值 internalChangeProductStock 报错
                changeReq.setProductCode(req.getProductCode());
                changeReq.setChangeAmount(req.getChangeAmount());

                if (req.getChangeType() == ChangeProductStockTypeEnum.OCCUPY.getCode()) {
                    ProductStockChangeReq releaseStockReq = new ProductStockChangeReq();
                    releaseStockReq.setRefNo(req.getRefNo());
                    releaseStockReq.setChangeType(ChangeProductStockTypeEnum.RELEASE.getCode());
                    releaseStockReq = productStockChangeReqDao.select(releaseStockReq);
                    if (null != releaseStockReq) {
                        resp.setStatus(ChangeProductStockStatusEnum.FAIL.getCode());
                        resp.setAddition("库存已经释放，不可以占用");
                    }
                }
            } else {
                changeReq.setProductCode(req.getProductCode());
                changeReq.setChangeAmount(req.getChangeAmount() != null ? req.getChangeAmount() : BigDecimal.ZERO);
            }

            //插入申请记录
            if (resp.getStatus() == ChangeProductStockStatusEnum.FAIL.getCode()
                    || resp.getStatus() == ChangeProductStockStatusEnum.ERROR.getCode()) {
                changeReq.setStatus(resp.getStatus());
                changeReq.setMemo(resp.getAddition());
            } else {
                changeReq.setStatus(ChangeProductStockStatusEnum.PROCESSING.getCode());
            }
            productStockChangeReqDao.insertSelective(changeReq);

            //根据上面逻辑，如果resp 为失败，不需要进行下面的  库存逻辑变更处理；
            if (resp.getStatus() == ChangeProductStockStatusEnum.FAIL.getCode()
                    || resp.getStatus() == ChangeProductStockStatusEnum.ERROR.getCode()) {
                return resp;
            }

            try {
                //真正变更逻辑处理
                internalChangeProductStockWithoutFreezeRecord(req, resp);
            } catch (Exception e) {
                logger.error("变更库存异常", e);
            }
            //根据冻结结果更新申请记录
            changeReq.setStatus(resp.getStatus());
            changeReq.setMemo(resp.getAddition());
            productStockChangeReqDao.updateByPrimaryKeySelective(changeReq);
        } catch (Exception e) {
            logger.error("库存变更异常", e);
            resp.setStatus(ChangeProductStockStatusEnum.ERROR.getCode());
        }
        return resp;
    }

    /**
     * 变更产品库存内部处理
     *
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    protected synchronized void internalChangeProductStock(ChangeProductStockRequest req, ChangeProductStockResponse resp) throws Exception {
        //加Redis锁
        RedisLock redisLock = new RedisLock(redisManager, Constants.PRODUCT_STOCK_CHANGE_LOCK_PREFIX + req.getProductCode());
//        boolean locked = redisLock.tryLock(3000);
        boolean locked = redisLock.tryLock(3, TimeUnit.SECONDS);//3秒转成毫微秒
        if (!locked) {
            resp.setStatus(ChangeProductStockStatusEnum.FAIL.getCode());
            resp.setAddition("获取分布式锁失败");
            return;
        }
        try {
            //查询产品库存
            ProductStock productStock = productStockDao.selectProductStockByProductCode(req.getProductCode());
            if (productStock == null) {
                resp.setStatus(ChangeProductStockStatusEnum.FAIL.getCode());
                resp.setAddition("未查询到产品库存信息:" + req.getProductCode());
                return;
            }
            //记录变更流水
            ProductStockChangeFlow changeFlow = new ProductStockChangeFlow();
            PropertyUtils.copyProperties(changeFlow, req);
            changeFlow.setProductId(productStock.getProductId());
            changeFlow.setChangeType(req.getChangeType());
            changeFlow.setStockAmountBefore(productStock.getStockAmount());
            changeFlow.setFrozenAmountBefore(productStock.getFrozenAmount());
            changeFlow.setSaleAmountBefore(productStock.getSaleAmount());
            changeFlow.setRedeemAmountBefore(productStock.getRedeemAmount());
            BigDecimal stockAmount, frozenAmount, saleAmount, redeemAmount;
            String addition = "";
            if (req.getChangeType() == ChangeProductStockTypeEnum.OCCUPY.getCode()) {
                stockAmount = productStock.getStockAmount();
                frozenAmount = productStock.getFrozenAmount().subtract(req.getChangeAmount());
                saleAmount = productStock.getSaleAmount().add(req.getChangeAmount());
                redeemAmount = productStock.getRedeemAmount();
                addition = ChangeProductStockTypeEnum.OCCUPY.getDesc();
            } else if (req.getChangeType() == ChangeProductStockTypeEnum.RELEASE.getCode()) {
                stockAmount = productStock.getStockAmount().add(req.getChangeAmount());
                frozenAmount = productStock.getFrozenAmount().subtract(req.getChangeAmount());
                saleAmount = productStock.getSaleAmount();
                redeemAmount = productStock.getRedeemAmount();
                addition = ChangeProductStockTypeEnum.RELEASE.getDesc();
            } else if (req.getChangeType() == ChangeProductStockTypeEnum.REDEEM.getCode()) {
                stockAmount = productStock.getStockAmount();
                frozenAmount = productStock.getFrozenAmount();
                saleAmount = productStock.getSaleAmount();
                redeemAmount = productStock.getRedeemAmount().add(req.getChangeAmount());
                addition = ChangeProductStockTypeEnum.REDEEM.getDesc();
            } else if (req.getChangeType() == ChangeProductStockTypeEnum.CANCEL.getCode()) {
                stockAmount = productStock.getStockAmount().add(req.getChangeAmount());
                frozenAmount = productStock.getFrozenAmount();
                saleAmount = productStock.getSaleAmount().subtract(req.getChangeAmount());
                redeemAmount = productStock.getRedeemAmount();
                addition = ChangeProductStockTypeEnum.CANCEL.getDesc();
            } else {
                resp.setStatus(ChangeProductStockStatusEnum.FAIL.getCode());
                addition = "未知产品库存变更类型:" + req.getProductCode();
                resp.setAddition(addition);
                throw new BusinessException(Constants.FAIL_RESP_CODE, addition);
            }
            changeFlow.setStockAmountAfter(stockAmount);
            changeFlow.setFrozenAmountAfter(frozenAmount);
            changeFlow.setSaleAmountAfter(saleAmount);
            changeFlow.setRedeemAmountAfter(redeemAmount);
            productStockChangeFlowDao.insertSelective(changeFlow);

            //变更库存
            productStock.setStockAmount(stockAmount);
            productStock.setFrozenAmount(frozenAmount);
            productStock.setSaleAmount(saleAmount);
            productStock.setRedeemAmount(redeemAmount);
            int row = productStockDao.updateStockWithLock(productStock);
            if (row <= 0) {
                resp.setStatus(ChangeProductStockStatusEnum.FAIL.getCode());
                addition = "乐观锁更新产品库存失败:" + req.getProductCode();
                resp.setAddition(addition);
                throw new BusinessException(Constants.FAIL_RESP_CODE, addition);
            }
            super.updateRedisProductStock(productStock);
            resp.setStatus(ChangeProductStockStatusEnum.SUCCESS.getCode());
            resp.setAddition(addition + "成功");
        } catch (Exception e) {
            throw e;
        } finally {
            redisLock.unlock();
        }
    }

    /**
     * 变更产品库存内部处理
     *
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private synchronized void internalChangeProductStockWithoutFreezeRecord(ChangeProductStockRequest req, ChangeProductStockResponse resp) throws Exception {
        //加Redis锁
        RedisLock redisLock = new RedisLock(redisManager, Constants.PRODUCT_STOCK_CHANGE_LOCK_PREFIX + req.getProductCode());
        boolean locked = redisLock.tryLock(3000);
        if (!locked) {
            resp.setStatus(ChangeProductStockStatusEnum.FAIL.getCode());
            resp.setAddition("获取分布式锁失败");
            return;
        }
        try {
            //查询产品库存
            ProductStock productStock = productStockDao.selectProductStockByProductCode(req.getProductCode());
            if (productStock == null) {
                resp.setStatus(ChangeProductStockStatusEnum.FAIL.getCode());
                resp.setAddition("未查询到产品库存信息:" + req.getProductCode());
                return;
            }
            //记录变更流水
            ProductStockChangeFlow changeFlow = new ProductStockChangeFlow();
            PropertyUtils.copyProperties(changeFlow, req);
            changeFlow.setProductId(productStock.getProductId());
            changeFlow.setChangeType(req.getChangeType());
            changeFlow.setStockAmountBefore(productStock.getStockAmount());
            changeFlow.setFrozenAmountBefore(productStock.getFrozenAmount());
            changeFlow.setSaleAmountBefore(productStock.getSaleAmount());
            changeFlow.setRedeemAmountBefore(productStock.getRedeemAmount());
            BigDecimal stockAmount, frozenAmount, saleAmount, redeemAmount;
            if (req.getChangeType() == ChangeProductStockTypeEnum.OCCUPY.getCode()) {
                stockAmount = productStock.getStockAmount().subtract(req.getChangeAmount());
                frozenAmount = BigDecimal.ZERO;
                saleAmount = productStock.getSaleAmount().add(req.getChangeAmount());
                redeemAmount = productStock.getRedeemAmount();
            } else if (req.getChangeType() == ChangeProductStockTypeEnum.RELEASE.getCode()) {
                stockAmount = productStock.getStockAmount().add(req.getChangeAmount());
                frozenAmount = BigDecimal.ZERO;
                saleAmount = productStock.getSaleAmount();
                redeemAmount = productStock.getRedeemAmount();
            } else if (req.getChangeType() == ChangeProductStockTypeEnum.REDEEM.getCode()) {
                stockAmount = productStock.getStockAmount();
                frozenAmount = productStock.getFrozenAmount();
                saleAmount = productStock.getSaleAmount();
                redeemAmount = productStock.getRedeemAmount().add(req.getChangeAmount());
            } else if (req.getChangeType() == ChangeProductStockTypeEnum.CANCEL.getCode()) {
                stockAmount = productStock.getStockAmount().add(req.getChangeAmount());
                frozenAmount = productStock.getFrozenAmount();
                saleAmount = productStock.getSaleAmount().subtract(req.getChangeAmount());
                redeemAmount = productStock.getRedeemAmount();
            } else {
                resp.setStatus(ChangeProductStockStatusEnum.FAIL.getCode());
                resp.setAddition("未知产品库存变更类型:" + req.getProductCode());
                throw new BusinessException(Constants.FAIL_RESP_CODE, "未知产品库存变更类型:" + req.getProductCode());
            }
            changeFlow.setStockAmountAfter(stockAmount);
            changeFlow.setFrozenAmountAfter(frozenAmount);
            changeFlow.setSaleAmountAfter(saleAmount);
            changeFlow.setRedeemAmountAfter(redeemAmount);
            productStockChangeFlowDao.insertSelective(changeFlow);

            //变更库存
            productStock.setStockAmount(stockAmount);
            productStock.setFrozenAmount(frozenAmount);
            productStock.setSaleAmount(saleAmount);
            productStock.setRedeemAmount(redeemAmount);
            int row = productStockDao.updateStockWithLock(productStock);
            if (row <= 0) {
                resp.setStatus(ChangeProductStockStatusEnum.FAIL.getCode());
                resp.setAddition("乐观锁更新产品库存失败:" + req.getProductCode());
                throw new BusinessException(Constants.FAIL_RESP_CODE, "乐观锁更新产品库存失败:" + req.getProductCode());
            }
            super.updateRedisProductStock(productStock);
            resp.setStatus(ChangeProductStockStatusEnum.SUCCESS.getCode());
        } catch (Exception e) {
            throw e;
        } finally {
            redisLock.unlock();
        }
    }
}
