package com.zb.fincore.pms.service.product.impl;


import com.alibaba.fastjson.JSONArray;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.io.netty.util.internal.StringUtil;
import com.zb.fincore.common.enums.ChannelEnum;
import com.zb.fincore.common.enums.product.ProductSaleStatusEnum;
import com.zb.fincore.common.utils.BeanUtils;
import com.zb.fincore.common.utils.DecimalUtils;
import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.common.enums.ChangeProductStockStatusEnum;
import com.zb.fincore.pms.common.enums.ChangeProductStockTypeEnum;
import com.zb.fincore.pms.common.enums.P2PProductCollectStatusEnum;
import com.zb.fincore.pms.common.exception.ExceptionHandler;
import com.zb.fincore.pms.common.model.Page;
import com.zb.fincore.pms.facade.product.dto.req.ChangeProductStockForP2PRequest;
import com.zb.fincore.pms.facade.product.dto.req.ChangeProductStockRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductListRequestForP2P;
import com.zb.fincore.pms.facade.product.dto.req.UnFreezeProductStockRequest;
import com.zb.fincore.pms.facade.product.dto.resp.ChangeProductStockResponse;
import com.zb.fincore.pms.facade.product.dto.resp.UnFreezeProductStockResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zb.fincore.pms.facade.product.model.ProductStockModel;
import com.zb.fincore.pms.service.dal.model.Product;
import com.zb.fincore.pms.service.dal.model.ProductStockChangeReq;
import com.zb.fincore.pms.service.product.ProductCacheForP2PService;
import com.zb.fincore.pms.service.product.ProductForP2PService;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 功能: 产品缓存接口类
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/6 0006 16:58
 * 版本: V1.0
 */
@Service
public class ProductCacheForP2PServiceImpl extends AbstracProductCacheServiceImpl implements ProductCacheForP2PService {

    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(ProductCacheForP2PServiceImpl.class);
    @Autowired
    private ProductForP2PService productForP2PService;

    @Value("${p2p_app_page_size}")
    private Integer p2pAppPageSize;

    @Value("${product_list_cache_expire_time}")
    private Integer productListCacheExpireTime;

    @Autowired
    private ExceptionHandler exceptionHandler;

    /**
     * 产品列表缓存集合
     */
    protected static LinkedHashMap<String, List<ProductModel>> PRODUCT_LIST_CACHE_MAP = new LinkedHashMap<String, List<ProductModel>>();

    /**
     * 解冻产品库存（供直销系统调用）
     *
     * @param req 冻结产品库存请求对象
     * @return 响应对象
     */
    @Override
    public UnFreezeProductStockResponse unFreezeProductStock(UnFreezeProductStockRequest req) throws Exception {
        ChangeProductStockRequest changeProductStockReq = new ChangeProductStockRequest();
        changeProductStockReq.setChangeType(ChangeProductStockTypeEnum.RELEASE.getCode());
        changeProductStockReq.setProductCode(req.getProductCode());
        changeProductStockReq.setRefNo(req.getRefNo());
        ChangeProductStockResponse changeProductStockResponse = super.changeProductStock(changeProductStockReq);
        UnFreezeProductStockResponse unFreezeProductStockResponse = BeanUtils.copyAs(changeProductStockResponse, UnFreezeProductStockResponse.class);
        return unFreezeProductStockResponse;
    }

//    /**
//     * 产品库存变更
//     *
//     * @param req
//     * @return
//     */
//    public ChangeProductStockResponse changeProductStock(@Valid ChangeProductStockForP2PRequest reqForP2P) throws Exception {
//        ChangeProductStockResponse resp = BaseResponse.build(ChangeProductStockResponse.class);
//        try {
//            PropertyUtils.copyProperties(resp, reqForP2P);
//            resp.setStatus(ChangeProductStockStatusEnum.PROCESSING.getCode());
//
//            ProductStockChangeReq changeReq = new ProductStockChangeReq();
//            changeReq.setRefNo(reqForP2P.getRefNo());
//            changeReq.setChangeType(reqForP2P.getChangeType());
//
//            //查询原冻结记录
//            ProductStockChangeReq freezeReq = new ProductStockChangeReq();
//            freezeReq.setRefNo(reqForP2P.getRefNo());
//            freezeReq.setChangeType(ChangeProductStockTypeEnum.FREEZE.getCode());
//            freezeReq = super.productStockChangeReqDao.select(freezeReq);
//            if (null == freezeReq ) {
//                resp.setStatus(ChangeProductStockStatusEnum.FAIL.getCode());
//                resp.setAddition("未查询到冻结记录");
//            } else {
//                if (freezeReq.getStatus() != ChangeProductStockStatusEnum.SUCCESS.getCode()) {
//                	resp.setStatus(ChangeProductStockStatusEnum.FAIL.getCode());
//                    resp.setAddition("冻结记录未成功冻结");
//                } else {
//                	BigDecimal totalReleaseAmount = this.doGetStockAmount(ChangeProductStockTypeEnum.RELEASE.getCode(), reqForP2P.getRefNo());
//            		BigDecimal totalOccupyAmount = this.doGetStockAmount(ChangeProductStockTypeEnum.OCCUPY.getCode(), reqForP2P.getRefNo());
//            		BigDecimal remainingAmount = freezeReq.getChangeAmount().subtract(totalReleaseAmount.add(totalOccupyAmount));//【库存】剩余金额 = 冻结总金额 - （ 释放总金额 + 取消总金额 ）
//
//                	resp.setStatus(ChangeProductStockStatusEnum.PROCESSING.getCode());//添加，req没传值 插入申请记录 报错
//                    changeReq.setProductCode(freezeReq.getProductCode());
//                    changeReq.setChangeAmount(reqForP2P.getChangeAmount() != null ? reqForP2P.getChangeAmount() : BigDecimal.ZERO);
//
//                    if (DecimalUtils.lt(remainingAmount, reqForP2P.getChangeAmount())) {
//                        resp.setStatus(ChangeProductStockStatusEnum.FAIL.getCode());
//                        resp.setAddition("冻结金额不足:" + reqForP2P.getProductCode());
//                        logger.info("冻结金额不足:product={},refNo={}", reqForP2P.getProductCode(), reqForP2P.getRefNo());
//                    }
//
////            		if (reqForP2P.getChangeType() == ChangeProductStockTypeEnum.OCCUPY.getCode()) {
////            			if (DecimalUtils.lt(remainingAmount, reqForP2P.getChangeAmount())) {
////                            resp.setStatus(ChangeProductStockStatusEnum.FAIL.getCode());
////                            resp.setAddition("冻结金额不足:" + reqForP2P.getProductCode());
////                            logger.info("冻结金额不足:product={},refNo={}", reqForP2P.getProductCode(), reqForP2P.getRefNo());
////                        }
////            		}
//                }
//            }
//
//            //插入申请记录
//            if (resp.getStatus() == ChangeProductStockStatusEnum.FAIL.getCode()
//                    || resp.getStatus() == ChangeProductStockStatusEnum.ERROR.getCode()) {
//                changeReq.setStatus(resp.getStatus());
//                changeReq.setMemo(resp.getAddition());
//            } else {
//                changeReq.setStatus(ChangeProductStockStatusEnum.PROCESSING.getCode());
//            }
//            super.productStockChangeReqDao.insertSelective(changeReq);
//
//            //根据上面逻辑，如果resp 为失败，不需要进行下面的  库存逻辑变更处理；
//            if (resp.getStatus() == ChangeProductStockStatusEnum.FAIL.getCode()
//                    || resp.getStatus() == ChangeProductStockStatusEnum.ERROR.getCode()) {
//                return resp;
//            }
//
//            try {
//                //真正变更逻辑处理
//            	ChangeProductStockRequest req = new ChangeProductStockRequest();
//            	PropertyUtils.copyProperties(req, reqForP2P);
//                super.internalChangeProductStock(req, resp);
//            } catch (Exception e) {
//                logger.error("变更库存异常", e);
//            }
//            //根据冻结结果更新申请记录
//            changeReq.setStatus(resp.getStatus());
//            changeReq.setMemo(resp.getAddition());
//            super.productStockChangeReqDao.updateByPrimaryKeySelective(changeReq);
//        } catch (Exception e) {
//            logger.error("库存变更异常", e);
//            resp.setStatus(ChangeProductStockStatusEnum.ERROR.getCode());
//        }
//        return resp;
//    }
//
//
//    private BigDecimal doGetStockAmount(Integer changeType, String refNo) throws Exception {
//
//    	BigDecimal totalAmount = BigDecimal.ZERO;
//    	List<ProductStockChangeReq> productStockReleaseReqList = null;
//
//    	//查询
//    	ProductStockChangeReq releaseStockReq = new ProductStockChangeReq();
//        releaseStockReq.setRefNo(refNo);
//        releaseStockReq.setChangeType(changeType);
//
//    	try {
//            productStockReleaseReqList = super.productStockChangeReqDao.queryProductStockChangeReqList(releaseStockReq);
//            if (null != productStockReleaseReqList && productStockReleaseReqList.size() > 0) {
//            	for (ProductStockChangeReq stockChangeReq : productStockReleaseReqList) {
//            		if (stockChangeReq.getStatus() != ChangeProductStockStatusEnum.FAIL.getCode()) {
//            			totalAmount = totalAmount.add(stockChangeReq.getChangeAmount());
//            		}
//            	}
//            }
//        } catch (Exception e) {
//        	throw e;
//        }
//
//		return totalAmount;
//    }

    /**
     * 刷新产品在售列表（第一页）缓存
     *
     * @return 通用结果
     */
    @Override
    public synchronized BaseResponse refreshOnSaleProductListForP2PCache() throws Exception {
        LinkedHashMap<String, List<ProductModel>> productModelListMap = new LinkedHashMap<String, List<ProductModel>>();
        QueryProductListRequestForP2P req = new QueryProductListRequestForP2P();
        req.setSaleFlag(Constants.ON_SALE);
        req.setPageNo(1);
        req.setPageSize(p2pAppPageSize);
        // 1、更新默认排序的在售产品列表
        req.setOrderByYieldRate(null);
        req.setOrderByInvestPeriod(null);
        req.setOrderByMinInvestAmount(null);
        PageQueryResponse<ProductModel> onSaleListOrderByDefault = productForP2PService.queryProductListForP2PApp(req);
        if (onSaleListOrderByDefault != null
            && Constants.SUCCESS_RESP_CODE.equals(onSaleListOrderByDefault.getRespCode())) {
            logger.debug("刷新默认排序产品在售列表开始");
            productModelListMap.put(Constants.ON_SALE_PRODUCT_LIST_PREFIX + Constants.ORDER_BY_DEFAULT, onSaleListOrderByDefault.getDataList());
            updateRedisProductList((List<ProductModel>) onSaleListOrderByDefault.getDataList(), Constants.ON_SALE_PRODUCT_LIST_PREFIX + Constants.ORDER_BY_DEFAULT);
            logger.debug("刷新默认排序产品在售列表结束");
        }

        // 2、更新按【最低预期收益率升序】排序的在售产品列表
        req.setOrderByYieldRate("ASC");
        PageQueryResponse<ProductModel> onSaleListOrderByYieldRateASC = productForP2PService.queryProductListForP2PApp(req);
        if (onSaleListOrderByYieldRateASC != null
                && Constants.SUCCESS_RESP_CODE.equals(onSaleListOrderByYieldRateASC.getRespCode())) {
            logger.debug("刷新按【最低预期收益率升序】排序产品在售列表开始");
            productModelListMap.put(Constants.ON_SALE_PRODUCT_LIST_PREFIX + Constants.ORDER_BY_YIELD_RATE_ASC, onSaleListOrderByYieldRateASC.getDataList());
            updateRedisProductList((List<ProductModel>) onSaleListOrderByYieldRateASC.getDataList(), Constants.ON_SALE_PRODUCT_LIST_PREFIX + Constants.ORDER_BY_YIELD_RATE_ASC);
            logger.debug("刷新按【最低预期收益率升序】排序产品在售列表结束");
        }

        // 3、更新按【最低预期收益率降序】排序的在售产品列表
        req.setOrderByYieldRate("DESC");
        PageQueryResponse<ProductModel> onSaleListOrderByYieldRateDESC = productForP2PService.queryProductListForP2PApp(req);
        if (onSaleListOrderByYieldRateDESC != null
                && Constants.SUCCESS_RESP_CODE.equals(onSaleListOrderByYieldRateDESC.getRespCode())) {
            logger.debug("刷新按【最低预期收益率降序】排序产品在售列表开始");
            productModelListMap.put(Constants.ON_SALE_PRODUCT_LIST_PREFIX + Constants.ORDER_BY_YIELD_RATE_DESC, onSaleListOrderByYieldRateDESC.getDataList());
            updateRedisProductList((List<ProductModel>) onSaleListOrderByYieldRateDESC.getDataList(), Constants.ON_SALE_PRODUCT_LIST_PREFIX + Constants.ORDER_BY_YIELD_RATE_DESC);
            logger.debug("刷新按【最低预期收益率降序】排序产品在售列表结束");
        }

        // 4、更新按【产品期限升序】排序的在售产品列表
        req.setOrderByYieldRate(null);
        req.setOrderByInvestPeriod("ASC");
        PageQueryResponse<ProductModel> onSaleListOrderByInvestPeriodASC = productForP2PService.queryProductListForP2PApp(req);
        if (onSaleListOrderByInvestPeriodASC != null
                && Constants.SUCCESS_RESP_CODE.equals(onSaleListOrderByInvestPeriodASC.getRespCode())) {
            logger.debug("刷新按【产品期限升序】排序产品在售列表开始");
            productModelListMap.put(Constants.ON_SALE_PRODUCT_LIST_PREFIX + Constants.ORDER_BY_INVEST_PERIOD_ASC, onSaleListOrderByInvestPeriodASC.getDataList());
            updateRedisProductList((List<ProductModel>) onSaleListOrderByInvestPeriodASC.getDataList(), Constants.ON_SALE_PRODUCT_LIST_PREFIX + Constants.ORDER_BY_INVEST_PERIOD_ASC);
            logger.debug("刷新按【产品期限升序】排序产品在售列表结束");
        }

        // 5、更新按【产品期限降序】排序的在售产品列表
        req.setOrderByYieldRate(null);
        req.setOrderByInvestPeriod("DESC");
        PageQueryResponse<ProductModel> onSaleListOrderByInvestPeriodDESC = productForP2PService.queryProductListForP2PApp(req);
        if (onSaleListOrderByInvestPeriodDESC != null
                && Constants.SUCCESS_RESP_CODE.equals(onSaleListOrderByInvestPeriodDESC.getRespCode())) {
            logger.debug("刷新按【产品期限降序】排序产品在售列表开始");
            productModelListMap.put(Constants.ON_SALE_PRODUCT_LIST_PREFIX + Constants.ORDER_BY_INVEST_PERIOD_DESC, onSaleListOrderByInvestPeriodDESC.getDataList());
            updateRedisProductList((List<ProductModel>) onSaleListOrderByInvestPeriodDESC.getDataList(), Constants.ON_SALE_PRODUCT_LIST_PREFIX + Constants.ORDER_BY_INVEST_PERIOD_DESC);
            logger.debug("刷新按【产品期限降序】排序产品在售列表结束");
        }

        // 6、更新按【起投金额升序】排序的在售产品列表
        req.setOrderByYieldRate(null);
        req.setOrderByInvestPeriod(null);
        req.setOrderByMinInvestAmount("ASC");
        PageQueryResponse<ProductModel> onSaleListOrderByMinInvestAmountASC = productForP2PService.queryProductListForP2PApp(req);
        if (onSaleListOrderByMinInvestAmountASC != null
                && Constants.SUCCESS_RESP_CODE.equals(onSaleListOrderByMinInvestAmountASC.getRespCode())) {
            logger.debug("刷新按【起投金额升序】排序产品在售列表开始");
            productModelListMap.put(Constants.ON_SALE_PRODUCT_LIST_PREFIX + Constants.ORDER_BY_MIN_INVEST_AMOUNT_ASC, onSaleListOrderByMinInvestAmountASC.getDataList());
            updateRedisProductList((List<ProductModel>) onSaleListOrderByMinInvestAmountASC.getDataList(), Constants.ON_SALE_PRODUCT_LIST_PREFIX + Constants.ORDER_BY_MIN_INVEST_AMOUNT_ASC);
            logger.debug("刷新按【起投金额升序】排序产品在售列表结束");
        }

        // 7、更新按【起投金额降序】排序的在售产品列表
        req.setOrderByYieldRate(null);
        req.setOrderByInvestPeriod(null);
        req.setOrderByMinInvestAmount("DESC");
        PageQueryResponse<ProductModel> onSaleListOrderByMinInvestAmountDESC = productForP2PService.queryProductListForP2PApp(req);
        if (onSaleListOrderByMinInvestAmountDESC != null
                && Constants.SUCCESS_RESP_CODE.equals(onSaleListOrderByMinInvestAmountDESC.getRespCode())) {
            logger.debug("刷新按【起投金额降序】排序产品在售列表开始");
            productModelListMap.put(Constants.ON_SALE_PRODUCT_LIST_PREFIX + Constants.ORDER_BY_MIN_INVEST_AMOUNT_DESC, onSaleListOrderByMinInvestAmountDESC.getDataList());
            updateRedisProductList(onSaleListOrderByMinInvestAmountDESC.getDataList(), Constants.ON_SALE_PRODUCT_LIST_PREFIX + Constants.ORDER_BY_MIN_INVEST_AMOUNT_DESC);
            logger.debug("刷新按【起投金额降序】排序产品在售列表结束");
        }

        PRODUCT_LIST_CACHE_MAP.clear();
        PRODUCT_LIST_CACHE_MAP = null;
        PRODUCT_LIST_CACHE_MAP = productModelListMap;
        return BaseResponse.build();
    }

    /**
     * 刷新产品售罄列表（第一页）缓存
     *
     * @return 通用结果
     */
    @Override
    public synchronized BaseResponse refreshSoldOutProductListForP2PCache() throws Exception {
        LinkedHashMap<String, List<ProductModel>> productModelListMap = new LinkedHashMap<String, List<ProductModel>>();
        QueryProductListRequestForP2P req = new QueryProductListRequestForP2P();
        req.setSaleFlag(Constants.SOLD_OUT);
        req.setPageNo(1);
        req.setPageSize(p2pAppPageSize);
        PageQueryResponse<ProductModel> soldOutList = productForP2PService.queryProductListForP2PApp(req);
        if (soldOutList != null
                && Constants.SUCCESS_RESP_CODE.equals(soldOutList.getRespCode())) {
            logger.debug("刷新产品售罄列表开始");
            productModelListMap.put(Constants.SOLD_OUT_PRODUCT_LIST_PREFIX, soldOutList.getDataList());
            updateRedisProductList(soldOutList.getDataList(), Constants.SOLD_OUT_PRODUCT_LIST_PREFIX);
            logger.debug("刷新产品售罄列表结束");
        }

        PRODUCT_LIST_CACHE_MAP.clear();
        PRODUCT_LIST_CACHE_MAP = null;
        PRODUCT_LIST_CACHE_MAP = productModelListMap;
        return BaseResponse.build();
    }

    @Override
    public PageQueryResponse<ProductModel> queryProductListForP2PApp(@Valid QueryProductListRequestForP2P req) throws Exception {
        if (Constants.ON_SALE.equals(req.getSaleFlag())) { // 在售产品列表
            return queryOnSaleProductListForP2P(req);

        } else if (Constants.SOLD_OUT.equals(req.getSaleFlag())) { // 售罄产品列表
            return getRedisProductList(Constants.SOLD_OUT_PRODUCT_LIST_PREFIX, req);
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
        // 默认排序的在售产品列表缓存信息
        if (StringUtils.isEmpty(req.getOrderByYieldRate())
                && StringUtils.isEmpty(req.getOrderByInvestPeriod())
                && StringUtils.isEmpty(req.getOrderByMinInvestAmount())) {
            return getRedisProductList(Constants.ON_SALE_PRODUCT_LIST_PREFIX + Constants.ORDER_BY_DEFAULT, req);

        // 按【最低预期收益率升序】排序的在售产品列表
        } else if (Constants.ASC.equals(req.getOrderByYieldRate())
                && StringUtils.isEmpty(req.getOrderByInvestPeriod())
                && StringUtils.isEmpty(req.getOrderByMinInvestAmount())) {
            return getRedisProductList(Constants.ON_SALE_PRODUCT_LIST_PREFIX + Constants.ORDER_BY_YIELD_RATE_ASC, req);

        // 按【最低预期收益率降序】排序的在售产品列表
        } else if (Constants.DESC.equals(req.getOrderByYieldRate())
                && StringUtils.isEmpty(req.getOrderByInvestPeriod())
                && StringUtils.isEmpty(req.getOrderByMinInvestAmount())) {
            return getRedisProductList(Constants.ON_SALE_PRODUCT_LIST_PREFIX + Constants.ORDER_BY_YIELD_RATE_DESC, req);

        // 按【产品期限升序】排序的在售产品列表
        } else if (StringUtils.isEmpty(req.getOrderByYieldRate())
                && Constants.ASC.equals(req.getOrderByInvestPeriod())
                && StringUtils.isEmpty(req.getOrderByMinInvestAmount())) {
            return getRedisProductList(Constants.ON_SALE_PRODUCT_LIST_PREFIX + Constants.ORDER_BY_INVEST_PERIOD_ASC, req);

        // 按【产品期限降序】排序的在售产品列表
        } else if (StringUtils.isEmpty(req.getOrderByYieldRate())
                && Constants.DESC.equals(req.getOrderByInvestPeriod())
                && StringUtils.isEmpty(req.getOrderByMinInvestAmount())) {
            return getRedisProductList(Constants.ON_SALE_PRODUCT_LIST_PREFIX + Constants.ORDER_BY_INVEST_PERIOD_DESC, req);

        // 按【起投金额升序】排序的在售产品列表
        } else if (StringUtils.isEmpty(req.getOrderByYieldRate())
                && StringUtils.isEmpty(req.getOrderByInvestPeriod())
                && Constants.ASC.equals(req.getOrderByMinInvestAmount())) {
            return getRedisProductList(Constants.ON_SALE_PRODUCT_LIST_PREFIX + Constants.ORDER_BY_MIN_INVEST_AMOUNT_ASC, req);

        // 按【起投金额降序】排序的在售产品列表
        } else if (StringUtils.isEmpty(req.getOrderByYieldRate())
                && StringUtils.isEmpty(req.getOrderByInvestPeriod())
                && Constants.DESC.equals(req.getOrderByMinInvestAmount())) {
            return getRedisProductList(Constants.ON_SALE_PRODUCT_LIST_PREFIX + Constants.ORDER_BY_MIN_INVEST_AMOUNT_DESC, req);

        }

        return response;
    }

    /**
     * 更新Redis产品列表信息
     *
     * @param productList 产品列表信息
     * @param key 缓存key
     */
    public synchronized void updateRedisProductList(List<ProductModel> productList, String key) {
        try {
            redisManager.set(key, JSONArray.toJSONString(productList));
            redisManager.expire(key, productListCacheExpireTime);
        } catch (Exception e) {
            logger.error("更新Redis产品列表失败", e);
        }
    }

    /**
     * 获取Redis中缓存产品列表信息
     *
     * @param key redis key值
     * @param req 请求信息
     * @return 产品列表信息
     */
    public PageQueryResponse<ProductModel> getRedisProductList(String key, QueryProductListRequestForP2P req) {
        PageQueryResponse<ProductModel> response = BaseResponse.build(PageQueryResponse.class);
        List<ProductModel> productModelList = null;
        try {
            String jsonStr = redisManager.get(key);
            if (org.apache.commons.lang3.StringUtils.isNotBlank(jsonStr)) {
                net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(jsonStr);
                productModelList = (List<ProductModel>) net.sf.json.JSONArray.toCollection(jsonArray, ProductModel.class);
                if (CollectionUtils.isEmpty(productModelList)) {
                    // key值过期后，查询数据库，然后再刷新缓存
                    try {
                        response = productForP2PService.queryProductListForP2PApp(req);
                        if (response != null && Constants.SUCCESS_RESP_CODE.equals(response.getRespCode())) {
                            refreshOnSaleProductListForP2PCache();
                            refreshSoldOutProductListForP2PCache();
                        }
                    } catch (Exception e) {
                        return exceptionHandler.handleException(e, PageQueryResponse.class);
                    }
                } else {
                    // 直接返回缓存内容
                    response.setPageSize(1);
                    response.setPageNo(p2pAppPageSize);
                    response.setTotalCount(productModelList.size());
                    response.setDataList(productModelList);
                }
            } else {
                // key值过期后，查询数据库，然后再刷新缓存
                try {
                    response = productForP2PService.queryProductListForP2PApp(req);
                    if (response != null && Constants.SUCCESS_RESP_CODE.equals(response.getRespCode())) {
                        refreshOnSaleProductListForP2PCache();
                        refreshSoldOutProductListForP2PCache();
                    }
                } catch (Exception e) {
                    return exceptionHandler.handleException(e, PageQueryResponse.class);
                }
            }
        } catch (Exception e) {
            logger.error("读取Redis库存信息异常:" + key, e);
            // redis异常，查库
            try {
                response = productForP2PService.queryProductListForP2PApp(req);
                if (response != null && Constants.SUCCESS_RESP_CODE.equals(response.getRespCode())) {
                    refreshOnSaleProductListForP2PCache();
                    refreshSoldOutProductListForP2PCache();
                }
            } catch (Exception ex) {
                return exceptionHandler.handleException(ex, PageQueryResponse.class);
            }
        }
        return response;
    }
}
