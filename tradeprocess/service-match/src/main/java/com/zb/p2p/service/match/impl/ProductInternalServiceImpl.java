package com.zb.p2p.service.match.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.zb.fincore.common.utils.BeanUtils;
import com.zb.fincore.common.utils.DateUtils;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.facade.product.ProductServiceForP2PFacade;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductInfoForTradeRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductInfoRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductListRequest;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductInfoResponse;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zb.p2p.enums.ResponseCodeEnum;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.api.resp.product.ProductDTO;
import com.zb.p2p.facade.api.resp.product.ProductPeriodDTO;
import com.zb.p2p.facade.api.resp.product.ProductProfitDTO;
import com.zb.p2p.facade.service.internal.ProductInternalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 资产预匹配查询产品服务
 * Created by zhangxin on 2017/9/5.
 */
@Service
public class ProductInternalServiceImpl implements ProductInternalService {

    private static Logger logger = LoggerFactory.getLogger(ProductInternalServiceImpl.class);

    @Autowired
    private ProductServiceForP2PFacade productServiceForP2PFacade;

    private static Map<String, ProductDTO> productMap = new ConcurrentHashMap<String, ProductDTO>();
    private static Map<String, ProductDTO> productAssetPoolMap = new ConcurrentHashMap<String, ProductDTO>();
    private static final Cache<String, ProductDTO> productAssetPoolCache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.DAYS).maximumSize(1000).build();


    /**
     * 根据资产池编号，起息时间，期限查询产品
     * @param assetPoolCode
     * @param valueTime
     * @param investPeriod
     * @return
     */
    public CommonResp<ProductDTO> loadProductInfoFromCacheForAssetMatch(final String assetPoolCode, final Date valueTime, final Integer investPeriod){
        CommonResp<ProductDTO> commonResp = new CommonResp<ProductDTO>();
        try {
            String valueTimeStr = DateUtils.format(valueTime, DateUtils.DATE_FORMAT_YYMMDD);
            String matchPattern = assetPoolCode + "_" + valueTimeStr + "_" + String.valueOf(investPeriod);
            ProductDTO productDTO = productAssetPoolCache.get(matchPattern, new Callable<ProductDTO>() {
                @Override
                public ProductDTO call() throws Exception {
                    //未命中，则执行业务方法
                    return loadProductInfoByAssetPoolCodeAndInvestPeriod(assetPoolCode, valueTime, investPeriod);
                }
            });

            if(null != productDTO){
                commonResp.setData(productDTO);
            }
        }catch (Exception e){
            logger.info("加载产品信息失败：",e);
        }
        return commonResp;
    }

    /**
     * 根据产品编号查询产品
     * @param productCode
     * @return
     */
    @Override
    public CommonResp<ProductDTO> queryProductInfoByProductCode(String productCode) {
        CommonResp<ProductDTO> commonResp = new CommonResp<ProductDTO>();
        if(null == productMap.get(productCode)){
            ProductDTO productDTO = loadProductInfoFromPms(productCode);
            if(null != productDTO) {
                commonResp.setData(productDTO);
                productMap.put(productDTO.getProductCode(), productDTO);

                Integer investPeriod = productDTO.getProductPeriodDTO().getInvestPeriod();
                Date valueTime = productDTO.getProductPeriodDTO().getValueTime();
                String valueTimeStr = DateUtils.format(valueTime, DateUtils.DATE_FORMAT_YYMMDD);
                String productAssetPoolMapKey = productDTO.getAssetPoolCode() + "_" + valueTimeStr + "_" + String.valueOf(investPeriod);
                productAssetPoolMap.put(productAssetPoolMapKey, productDTO);
            }
        }else{
            commonResp.setData(productMap.get(productCode));
        }
        return commonResp;
    }

    /**
     * 根据资产池编号，起息时间，期限查询产品  放入本地缓存
     * @param assetPoolCode
     * @param valueTime
     * @param investPeriod
     * @return
     */
    @Override
    public CommonResp<ProductDTO> queryProductInfoForAssetMatch(String assetPoolCode, Date valueTime, Integer investPeriod) {
        CommonResp<ProductDTO> commonResp = new CommonResp<ProductDTO>();

        String valueTimeStr = DateUtils.format(valueTime, DateUtils.DATE_FORMAT_YYMMDD);
        String matchPattern = assetPoolCode + "_" + valueTimeStr + "_" + String.valueOf(investPeriod);

        if(null == productAssetPoolMap.get(matchPattern)){
            ProductDTO productDTO = loadProductInfoByAssetPoolCodeAndInvestPeriod(assetPoolCode, valueTime, investPeriod);
            if(null != productDTO) {
                commonResp.setData(productDTO);
                productMap.put(productDTO.getProductCode(), productDTO);
                productAssetPoolMap.put(matchPattern, productDTO);
            }
        }else{
            commonResp.setData(productAssetPoolMap.get(matchPattern));
        }
        return commonResp;
    }

    /**
     * 查询产品列表
     * @param date
     * @return
     */
    @Override
    public List<ProductDTO> queryProductListByDate(Date date) {
        if (date == null) {
            date = DateUtils.addDay(new Date(), -1);
        }
        List<ProductDTO> productDTOList = new ArrayList<>();
        QueryProductListRequest queryProductListRequest = new QueryProductListRequest();
        queryProductListRequest.setSaleEndTimeForDailyCut(date);
        logger.info("日切查询产品列表请求参数：{}", queryProductListRequest.getSaleEndTimeForDailyCut());
        PageQueryResponse<ProductModel> productRsp = productServiceForP2PFacade.queryProductListForTrade(queryProductListRequest);
        if (ResponseCodeEnum.RESPONSE_SUCCESS.getCode().equals(productRsp.getRespCode())) {
            if(productRsp.getDataList()!=null&& productRsp.getDataList().size()>0){
                for(ProductModel productModel:productRsp.getDataList()){
                    ProductDTO productDTO = convertModel2DTO(productModel);
                    productDTOList.add(productDTO);
                }
            }
        }
        return productDTOList;
    }

    /**
     * 调用pms服务 根据产品编号查询产品信息
     * @param productCode
     * @return
     */
    public ProductDTO loadProductInfoFromPms(String productCode){
        logger.info("call pms load product info by productCode service start, productCode :{}", productCode);
        QueryProductInfoRequest request = new QueryProductInfoRequest();
        request.setProductCode(productCode);
        QueryProductInfoResponse response = productServiceForP2PFacade.queryProductInfo(request);
        if(null == response || null == response.getProductModel()){
            return null;
        }
        logger.info("call pms load product info by productCode result :{}", response);
        return convertModel2DTO(response.getProductModel());
    }

    /**
     * 调用pms服务 根据资产池编号，起息时间，期限查询产品信息
     * @param assetPoolCode
     * @param valueTime
     * @param investPeriod
     * @return
     */
    private ProductDTO loadProductInfoByAssetPoolCodeAndInvestPeriod(String assetPoolCode, Date valueTime, Integer investPeriod){
        logger.info("call pms load product info service start, assetPoolCode :{}, valueTime :{}, investPeriod : {}", assetPoolCode, valueTime, investPeriod);
        QueryProductInfoForTradeRequest request = new QueryProductInfoForTradeRequest();
        request.setAssetPoolCode(assetPoolCode);
        request.setValueTime(valueTime);
        request.setInvestPeriod(investPeriod);
        QueryProductInfoResponse response = productServiceForP2PFacade.queryProductInfoForTrade(request);
        logger.info("call pms load product info result :{}", response);
        if(null == response || null == response.getProductModel()){
            logger.info("call pms load product info result2 :{}", response);
            return null;
        }
        logger.info("call pms load product info convert product :{}", response);
        return convertModel2DTO(response.getProductModel());
    }

    /**
     * 将productModel转换为ProductDTO
     * @param productModel
     * @return
     */
    public  ProductDTO convertModel2DTO(ProductModel productModel){
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copy(productModel, productDTO);
        ProductProfitDTO productProfitDTO = new ProductProfitDTO();
        BeanUtils.copy(productModel.getProductProfitModel(), productProfitDTO);
        ProductPeriodDTO productPeriodDTO = new ProductPeriodDTO();
        BeanUtils.copy(productModel.getProductPeriodModel(), productPeriodDTO);
        productDTO.setProductPeriodDTO(productPeriodDTO);
        productDTO.setProductProfitDTO(productProfitDTO);
        return productDTO;
    }
}
