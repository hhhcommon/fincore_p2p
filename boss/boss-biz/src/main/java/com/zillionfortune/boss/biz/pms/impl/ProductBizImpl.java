package com.zillionfortune.boss.biz.pms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ibm.icu.text.SimpleDateFormat;
import com.zb.fincore.common.utils.AesHttpClientUtils;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.common.enums.ProductApprovalStatusEnum;
import com.zb.fincore.pms.facade.product.dto.req.ApproveProductRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductApprovalListRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductInfoRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductListRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductRequest;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductCollectAmountRequest;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductSaleStatusRequest;
import com.zb.fincore.pms.facade.product.dto.resp.ProductStatisticsResponse;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductApprovalInfoResponse;
import com.zb.fincore.pms.facade.product.dto.resp.QueryProductInfoResponse;
import com.zb.fincore.pms.facade.product.dto.resp.RegisterProductResponse;
import com.zb.fincore.pms.facade.product.model.ProductApprovalModel;
import com.zb.fincore.pms.facade.product.model.ProductContractModel;
import com.zb.fincore.pms.facade.product.model.ProductModel;
import com.zillionfortune.boss.biz.pms.ProductBiz;
import com.zillionfortune.boss.biz.pms.dto.QueryProductApprovalModel;
import com.zillionfortune.boss.biz.pms.dto.QueryProductDetailModel;
import com.zillionfortune.boss.biz.pms.dto.QueryProductModel;
import com.zillionfortune.boss.common.dto.BaseWebResponse;
import com.zillionfortune.boss.common.enums.ProductTypeEnum;
import com.zillionfortune.boss.common.enums.RespCode;
import com.zillionfortune.boss.common.enums.ResultCode;
import com.zillionfortune.boss.dal.entity.FileInfo;
import com.zillionfortune.boss.dal.entity.OperationHistory;
import com.zillionfortune.boss.service.file.FileService;
import com.zillionfortune.boss.service.history.OperationHistoryService;

/**
 * ClassName: ProductIntegrationImpl <br/>
 * Function: 产品服务接口实现. <br/>
 * Date: 2017年5月8日 下午5:42:29 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @since JDK 1.7
 */
@Component
public class ProductBizImpl implements ProductBiz {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FileService fileService;

    @Autowired
    private OperationHistoryService operationHistoryService;

    /** 授权级别**/
    @Value("${SIGN_LEVEL}")
    private String sign;

    @Value("${product_register_url}")
    private String productRegisterUrl;
    
    @Value("${product_internal_register_url}")
    private String internalProductRegisterUrl;
    
    @Value("${product_internal_info_query_url}")
    private String internalProductInfoQueryUrl;

    @Value("${product_list_query_url}")
    private String productListQueryUrl;
    
    @Value("${internal_product_list_query_url}")
    private String internalProductListQueryUrl;
    
    @Value("${product_update_collectamount_url}")
    private String productCollectAmountUrl;
    
    @Value("${product_info_query_url}")
    private String productInfoQueryUrl;

    @Value("${product_on_line_url}")
    private String productOnLineUrl;

    @Value("${product_off_line_url}")
    private String productOffLineUrl;
    
    @Value("${internal_product_off_line_url}")
    private String internalProductOffLineUrl;

    @Value("${internal_product_approval_url}")
    private String internalProductApprovalUrl;
    
    @Value("${product_internal_on_line_url}")
    private String internalProductOnLineUrl;
    
    @Value("${product_approval_url}")
    private String productApprovalUrl;

    @Value("${product_approval_info_list_url}")
    private String productApprovalInfoListUrl;
    
    @Value("${internal_product_approval_info_list_url}")
    private String internalProductApprovalInfoListUrl;

    @Value("${product_statistics_url}")
    private String productStatisticsUrl;
    
    @Value("${internal_product_statistics_url}")
    private String internalProductStatisticsUrl;

    @Autowired
    protected AesHttpClientUtils aesHttpClientUtils;

    /**
     * 产品注册.
     *
     * @see com.zillionfortune.boss.biz.pms.ProductBiz# registerProduct(com.zb.fincore.pms.facade.product.dto.req.RegisterProductRequest)
     */
    @Override
    public BaseWebResponse registerProduct(RegisterProductRequest req, String createBy, Integer userId,String productType) {
        log.info("ProductBizImpl.registerProduct.req:" + JSON.toJSONString(req));
        BaseWebResponse resp = null;
        try {
            // 测试本地数据库是否连接正常,为了注册产品和披露文件关联幂等性
            fileService.selectBySelective(new HashMap<String, Object>());

            // 调用产品注册dubbo服务接口
            //RegisterProductResponse registerProductResponse = productServiceFacade.registerProduct(req);
            // 调用产品注册http服务接口
            RegisterProductResponse registerProductResponse = productRegisterUseHttp(req,productType);

            if (ResultCode.SUCCESS.code().equals(registerProductResponse.getRespCode())) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
                Map<String, Object> respMap = new HashMap<String, Object>();
                respMap.put("productId", registerProductResponse.getId());
                respMap.put("productCode", registerProductResponse.getProductCode());

                resp.setData(respMap);

                // 将已经注册的产品与披露文件建立关联
                List<ProductContractModel> contractModels = req.getProductContractList();
                if (contractModels != null && contractModels.size() > 0) {
                    for (ProductContractModel model : contractModels) {
                        FileInfo fileInfo = fileService.selectByShowName(model.getContractDisplayName());
						if (fileInfo != null) {
							String relationProduct = fileInfo.getRelationProduct();
							if (StringUtils.isNotBlank(relationProduct)) {
								relationProduct = relationProduct + "," + registerProductResponse.getProductCode();

							} else {
								relationProduct = registerProductResponse.getProductCode();
							}

							FileInfo tempInfo = new FileInfo();
							tempInfo.setRelationProduct(relationProduct);
							tempInfo.setModifyBy(createBy);
							tempInfo.setModifyTime(new Date());
							tempInfo.setShowName(model.getContractDisplayName());
							fileService.updateByShowNameSelective(tempInfo);
						}
                    }
                }

                //日志插入
                OperationHistory history = new OperationHistory();
                history.setUserId(userId);
                history.setOperationType("registerproduct");
                history.setContent("产品管理->产品注册");
                history.setCreateBy(createBy);
                history.setReferId(registerProductResponse.getProductCode());
                history.setCreateTime(new Date());
                operationHistoryService.insertOperationHistory(history);
            } else {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), registerProductResponse.getRespMsg());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
        }

        log.info("ProductBizImpl.registerProduct.resp:" + JSON.toJSONString(resp));
        return resp;
    }
    
    /**
     * 定期产品注册.
     *
     * @see com.zillionfortune.boss.biz.pms.ProductBiz# registerProduct(com.zb.fincore.pms.facade.product.dto.req.RegisterProductRequest)
     */
    @Override
    public BaseWebResponse registerInternalProduct(RegisterProductRequest req, String createBy, Integer userId) {
        log.info("ProductBizImpl.registerInternalProduct.req:" + JSON.toJSONString(req));
        BaseWebResponse resp = null;
        try {
            // 测试本地数据库是否连接正常,为了注册产品和披露文件关联幂等性
            fileService.selectBySelective(new HashMap<String, Object>());

            // 调用产品注册dubbo服务接口
            //RegisterProductResponse registerProductResponse = productServiceFacade.registerProduct(req);
            // 调用产品注册http服务接口
            RegisterProductResponse registerProductResponse = internalProductRegisterUseHttp(req);

            if (ResultCode.SUCCESS.code().equals(registerProductResponse.getRespCode())) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
                Map<String, Object> respMap = new HashMap<String, Object>();
                respMap.put("productId", registerProductResponse.getId());
                respMap.put("productCode", registerProductResponse.getProductCode());

                resp.setData(respMap);

                // 将已经注册的产品与披露文件建立关联
                List<ProductContractModel> contractModels = req.getProductContractList();
                if (contractModels != null && contractModels.size() > 0) {
                    for (ProductContractModel model : contractModels) {
                        FileInfo fileInfo = fileService.selectByShowName(model.getContractDisplayName());
						if (fileInfo != null) {
							String relationProduct = fileInfo.getRelationProduct();
							if (StringUtils.isNotBlank(relationProduct)) {
								relationProduct = relationProduct + "," + registerProductResponse.getProductCode();

							} else {
								relationProduct = registerProductResponse.getProductCode();
							}

							FileInfo tempInfo = new FileInfo();
							tempInfo.setRelationProduct(relationProduct);
							tempInfo.setModifyBy(createBy);
							tempInfo.setModifyTime(new Date());
							tempInfo.setShowName(model.getContractDisplayName());
							fileService.updateByShowNameSelective(tempInfo);
						}
                    }
                }

                //日志插入
                OperationHistory history = new OperationHistory();
                history.setUserId(userId);
                history.setOperationType("registerproduct");
                history.setContent("产品管理->产品注册");
                history.setCreateBy(createBy);
                history.setReferId(registerProductResponse.getProductCode());
                history.setCreateTime(new Date());
                operationHistoryService.insertOperationHistory(history);
            } else {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), registerProductResponse.getRespMsg());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
        }

        log.info("ProductBizImpl.registerProduct.resp:" + JSON.toJSONString(resp));
        return resp;
    }


    /**
     * 产品列表查询.
     *
     * @see com.zillionfortune.boss.biz.pms.ProductBiz#queryProductList(com.zb.fincore.pms.facade.product.dto.req.QueryProductListRequest)
     */
    @Override
    public BaseWebResponse queryProductList(QueryProductListRequest req,String productType) {
        log.info("ProductBizImpl.queryProductList.req:" + JSON.toJSONString(req));
        BaseWebResponse resp = null;
        try {
            // 调用产品列表查询dubbo服务接口
            //PageQueryResponse<ProductModel> pageQueryResponse = productServiceFacade.queryProductList(req);
            // 调用产品列表查询http服务接口
            PageQueryResponse<ProductModel> pageQueryResponse = productListQueryUseHttp(req,productType);

            if (ResultCode.SUCCESS.code().equals(pageQueryResponse.getRespCode())) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());

                Map<String, Object> respMap = new HashMap<String, Object>();
                List<QueryProductModel> list = null; // 产品列表返回结果集
                // 设置产品列表返回结果集
                if (pageQueryResponse.getDataList() != null && pageQueryResponse.getDataList().size() > 0) {
                    list = new ArrayList<QueryProductModel>();
                    setQueryProductModelList(list, pageQueryResponse.getDataList());
                }
                respMap.put("dataList", list);
                respMap.put("pageNo", pageQueryResponse.getPageNo());
                respMap.put("pageSize", pageQueryResponse.getPageSize());
                respMap.put("totalCount", pageQueryResponse.getTotalCount());
                respMap.put("totalPage", pageQueryResponse.getTotalPage());
                resp.setData(respMap);
            } else {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), pageQueryResponse.getRespMsg());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
        }

        log.info("ProductBizImpl.queryProductList.resp:" + JSON.toJSONString(resp));
        return resp;

    }

    /**
     * 产品详情查询.
     *
     * @see com.zillionfortune.boss.biz.pms.ProductBiz#queryProductDetail(java.lang.String)
     */
    @Override
    public BaseWebResponse queryProductDetail(String productCode,String productType) {
        log.info("ProductBizImpl.queryProductDetail.req:" + JSON.toJSONString(productCode));
        BaseWebResponse resp = null;
        try {
            // 调用产品详情查询dubbo服务接口
            QueryProductInfoRequest queryProductInfoRequest = new QueryProductInfoRequest();
            queryProductInfoRequest.setProductCode(productCode);
            //QueryProductInfoResponse queryProductInfoResponse = productServiceFacade.queryProductInfo(queryProductInfoRequest);
            // 调用产品详情查询http服务接口
            QueryProductInfoResponse queryProductInfoResponse = productInfoQueryUseHttp(queryProductInfoRequest, productType);

            if (ResultCode.SUCCESS.code().equals(queryProductInfoResponse.getRespCode())) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());

                Map<String, Object> respMap = new HashMap<String, Object>();
                QueryProductDetailModel productDetailModel = new QueryProductDetailModel();
                setQueryProductDetailModel(productDetailModel, queryProductInfoResponse);
                respMap.put("productDetail", productDetailModel);
                resp.setData(respMap);
            } else {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), queryProductInfoResponse.getRespMsg());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
        }

        log.info("ProductBizImpl.queryProductDetail.resp:" + JSON.toJSONString(resp));
        return resp;

    }

    /**
     * 产品上线.
     *
     * @see com.zillionfortune.boss.biz.pms.ProductBiz#putProductOnLine(com.zb.fincore.pms.facade.product.dto.req.UpdateProductSaleStatusRequest)
     */
    @Override
    public BaseWebResponse putProductOnLine(UpdateProductSaleStatusRequest req,String productType) {
        log.info("ProductBizImpl.putProductOnLine.req:" + JSON.toJSONString(req));
        BaseWebResponse resp = null;
        try {
            // 调用产品上线dubbo服务接口
            //BaseResponse baseResponse = productServiceFacade.putProductOnLine(req);
            // 调用产品上线http服务接口
            BaseResponse baseResponse = putProductOnLineUseHttp(req,productType);

            if (ResultCode.SUCCESS.code().equals(baseResponse.getRespCode())) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
            } else {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), baseResponse.getRespMsg());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
        }

        log.info("ProductBizImpl.putProductOnLine.resp:" + JSON.toJSONString(resp));
        return resp;
    }

    /**
     * 产品下线.
     *
     * @see com.zillionfortune.boss.biz.pms.ProductBiz#putProductOffLine(com.zb.fincore.pms.facade.product.dto.req.UpdateProductSaleStatusRequest)
     */
    @Override
    public BaseWebResponse putProductOffLine(UpdateProductSaleStatusRequest req,String productType) {
        log.info("ProductBizImpl.putProductOffLine.req:" + JSON.toJSONString(req));
        BaseWebResponse resp = null;
        try {
            // 调用产品下线dubbo服务接口
            //BaseResponse baseResponse = productServiceFacade.putProductOffLine(req);
            // 调用产品下线http服务接口
            BaseResponse baseResponse = putProductOffLineUseHttp(req,productType);

            if (ResultCode.SUCCESS.code().equals(baseResponse.getRespCode())) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());

            } else {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), baseResponse.getRespMsg());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
        }

        log.info("ProductBizImpl.putProductOffLine.resp:" + JSON.toJSONString(resp));
        return resp;
    }

    /**
     * 产品审核.
     *
     * @see com.zillionfortune.boss.biz.pms.ProductBiz#approveProduct(com.zb.fincore.pms.facade.product.dto.req.ApproveProductRequest)
     */
    @Override
    public BaseWebResponse approveProduct(ApproveProductRequest req,String productType) {
        log.info("ProductBizImpl.approveProduct.req:" + JSON.toJSONString(req));
        BaseWebResponse resp = null;
        try {
            // 测试本地数据库是否连接正常,为了注册产品和披露文件关联幂等性
            fileService.selectBySelective(new HashMap<String, Object>());

            // 调用产品审核dubbo服务接口
            req.setSign(sign); // 授权级别
            //BaseResponse baseResponse = productServiceFacade.approveProduct(req);
            // 调用产品审核http服务接口
            BaseResponse baseResponse = productApprovalUseHttp(req,productType);


            if (ResultCode.SUCCESS.code().equals(baseResponse.getRespCode())) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
                // 产品审核未通过的情况下，将披露文件和产品的关联解绑
                if (ProductApprovalStatusEnum.APPROVAL_FAILURE.getCode() == req.getApprovalStatus().intValue()) {
                    unBindContractFile(req.getProductCode(), req.getApprovalBy());
                }

            } else {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), baseResponse.getRespMsg());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
        }

        log.info("ProductBizImpl.approveProduct.resp:" + JSON.toJSONString(resp));
        return resp;
    }

    /**
     * 产品状态分类统计.
     *
     * @see com.zillionfortune.boss.biz.pms.ProductBiz#countUpProductStatus()
     */
    @Override
    public BaseWebResponse countUpProductStatus(String productType) {
        log.info("ProductBizImpl.countUpProductStatus.req:");
        BaseWebResponse resp = resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
        Map<String, Object> respMap = new HashMap<String, Object>();
        respMap.put("archiveProTotal", 0); // 归档产品总数
        respMap.put("approvalSuccessProTotal", 0); // 审核通过产品总数
        respMap.put("approvalFailureProTotal", 0); // 审核失败产品总数
        respMap.put("approvalWaitProTotal", 0); // 待审核产品总数
        resp.setData(respMap);
        try {
            QueryProductListRequest req = new QueryProductListRequest();
            // 调用产品列表查询http服务接口
            ProductStatisticsResponse response = productStatistics(req,productType);
            if (ResultCode.SUCCESS.code().equals(response.getRespCode())) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
                respMap = new HashMap<String, Object>();
                respMap.put("archiveProTotal", response.getArchiveProTotal()); // 归档产品总数
                respMap.put("approvalSuccessProTotal", response.getApprovalSuccessProTotal()); // 审核通过产品总数
                respMap.put("approvalFailureProTotal", response.getApprovalFailureProTotal()); // 审核失败产品总数
                respMap.put("approvalWaitProTotal", response.getApprovalWaitProTotal()); // 待审核产品总数
                resp.setData(respMap);
            } else {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), response.getRespMsg());
            }


//            // 调用产品列表查询dubbo服务接口
//            QueryProductListRequest req = new QueryProductListRequest();
//            //PageQueryResponse<ProductModel> pageQueryResponse = productServiceFacade.queryProductList(req);
//            // 调用产品列表查询http服务接口
//            PageQueryResponse<ProductModel> pageQueryResponse = productListQueryUseHttp(req);
//
//            if (ResultCode.SUCCESS.code().equals(pageQueryResponse.getRespCode())) {
//                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
//
//                Map<String, Object> respMap = new HashMap<String, Object>();
//                int archiveProTotal = 0; // 归档产品总数
//                int approvalSuccessProTotal = 0; // 审核通过产品总数
//                int approvalFailureProTotal = 0; // 审核失败产品总数
//                int approvalWaitProTotal = 0; // 待审核产品总数
//                if (pageQueryResponse.getDataList() != null && pageQueryResponse.getDataList().size() > 0) {
//                    for (ProductModel productModel : pageQueryResponse.getDataList()) {
//                        // 归档产品统计
//                        if (ProductSaleStatusEnum.PRODUCT_SALE_STATUS_FILED.getCode() == productModel.getSaleStatus()) {
//                            archiveProTotal++;
//                        }
//                        // 审核通过产品统计
//                        if (ProductApprovalStatusEnum.APPROVAL_SUCCESS.getCode() == productModel.getApprovalStatus()) {
//                            approvalSuccessProTotal++;
//                            continue;
//                        }
//                        // 审核失败产品统计
//                        if (ProductApprovalStatusEnum.APPROVAL_FAILURE.getCode() == productModel.getApprovalStatus()) {
//                            approvalFailureProTotal++;
//                            continue;
//                        }
//                        // 待审核产品统计
//                        if (ProductApprovalStatusEnum.WAIT_APPROVAL.getCode() == productModel.getApprovalStatus()) {
//                            approvalWaitProTotal++;
//                        }
//                    }
//                }
//                respMap.put("archiveProTotal", archiveProTotal); // 归档产品总数
//                respMap.put("approvalSuccessProTotal", approvalSuccessProTotal); // 审核通过产品总数
//                respMap.put("approvalFailureProTotal", approvalFailureProTotal); // 审核失败产品总数
//                respMap.put("approvalWaitProTotal", approvalWaitProTotal); // 待审核产品总数
//                resp.setData(respMap);
//            } else {
//                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), pageQueryResponse.getRespMsg());
//            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
        }
        log.info("ProductBizImpl.countUpProductStatus.resp:" + JSON.toJSONString(resp));
        return resp;
    }

    /**
     * 产品审核信息列表查询.
     *
     * @see com.zillionfortune.boss.biz.pms.ProductBiz# queryProductApprovalList(java.lang.String)
     */
    @Override
    public BaseWebResponse queryProductApprovalList(QueryProductApprovalListRequest req, String createBy,String productType) {
        log.info("ProductBizImpl.queryProductApprovalList.req:" + JSON.toJSONString(req));
        BaseWebResponse resp = null;
        try {
            // 调用产品审核信息列表查询dubbo服务接口
            //QueryProductApprovalInfoResponse productApprovalInfoResponse = productServiceFacade.queryProductApprovalList(req);
            // 调用产品审核信息列表查询http服务接口
            QueryProductApprovalInfoResponse productApprovalInfoResponse = queryProductApprovalListUseHttp(req,productType);

            if (ResultCode.SUCCESS.code().equals(productApprovalInfoResponse.getRespCode())) {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());

                Map<String, Object> respMap = new HashMap<String, Object>();
                List<QueryProductApprovalModel> list = null; // 产品审核信息列表返回结果集
                // 设置产品审核信息列表
                if (productApprovalInfoResponse.getDataList() != null && productApprovalInfoResponse.getDataList().size() > 0) {
                    list = new ArrayList<QueryProductApprovalModel>();
                    setQueryProductApprovalModelList(list, productApprovalInfoResponse.getDataList(), createBy);
                }
                respMap.put("productName", productApprovalInfoResponse.getProductName());
                respMap.put("productDisplayName", productApprovalInfoResponse.getProductDisplayName());
                respMap.put("saleChannelCode", productApprovalInfoResponse.getSaleChannelCode());
                respMap.put("dataList", list);
                resp.setData(respMap);
            } else {
                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), productApprovalInfoResponse.getRespMsg());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
        }

        log.info("ProductBizImpl.queryProductApprovalList.resp:" + JSON.toJSONString(resp));
        return resp;
    }

    /**
     * setQueryProductModelList:设置产品列表查询返回结果集. <br/>
     *
     * @param queryProductModelList
     * @param productModelList
     */
    private void setQueryProductModelList(List<QueryProductModel> queryProductModelList, List<ProductModel> productModelList) {
        for (ProductModel productModel : productModelList) {
            QueryProductModel model = new QueryProductModel();
            String productId = "";
            if (productModel.getProductPeriodModel() != null
                    && productModel.getProductPeriodModel().getProductId() != null) {
                productId = productModel.getProductPeriodModel().getProductId().toString();
            }
            model.setProductId(productId); // 产品Id
            model.setProductCode(productModel.getProductCode()); // 产品编号
            model.setProductName(productModel.getProductName()); // 产品名称
            model.setProductDisplayName(productModel.getProductDisplayName()); // 产品展示名
            String productLineId = "";
            if (productModel.getProductLineId() != null) {
                productLineId = productModel.getProductLineId().toString();
            }
            model.setProductLineId(productLineId); // 产品线Id
            model.setProductLineCode(productModel.getProductLineCode()); // 产品线编号
            model.setProductLineName(productModel.getProductLineName()); // 产品线名称
            model.setPatternCode(productModel.getPatternCode()); // 产品类型
            String maxYieldRate = "";
            String minYieldRate = "";
            if (productModel.getProductProfitModel() != null
                    && productModel.getProductProfitModel().getMaxYieldRate() != null) {
                maxYieldRate = productModel.getProductProfitModel().getMaxYieldRate().toString();
            }
            if (productModel.getProductProfitModel() != null
                    && productModel.getProductProfitModel().getMinYieldRate() != null) {
                minYieldRate = productModel.getProductProfitModel().getMinYieldRate().toString();
            }
            model.setMaxYieldRate(maxYieldRate); // 预期年化收益率上限
            model.setMinYieldRate(minYieldRate); // 预期年化收益率下限
            String totalAmount = "";
            if (productModel.getTotalAmount() != null) {
                totalAmount = productModel.getTotalAmount().toString();
            }
            model.setTotalAmount(totalAmount); // 产品规模
            String investPeriod = "";
            if (productModel.getProductPeriodModel() != null
                    && productModel.getProductPeriodModel().getInvestPeriod() != null) {
                investPeriod = productModel.getProductPeriodModel().getInvestPeriod().toString();
            }
            model.setInvestPeriod(investPeriod); // 产品期限
            model.setSaleChannelCode(productModel.getSaleChannelCode()); // 销售渠道
            model.setRiskLevel(productModel.getRiskLevel()); // 风险等级
            String saleStatus = "";
            if (productModel.getSaleStatus() != null) {
                saleStatus = productModel.getSaleStatus().toString();
            }
            model.setSaleStatus(saleStatus); // 销售状态
            String collectStatus = "";
            if (productModel.getCollectStatus() != null) {
                collectStatus = productModel.getCollectStatus().toString();
            }
            model.setCollectStatus(collectStatus); // 募集状态
            String approvalStatus = "";
            if (productModel.getApprovalStatus() != null) {
                approvalStatus = productModel.getApprovalStatus().toString();
            }
            model.setApprovalStatus(approvalStatus); // 审核状态
            String submitApprovalTime = "";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (productModel.getCreateTime() != null) {
                submitApprovalTime = formatter.format(productModel.getCreateTime());
            }
            model.setSubmitApprovalTime(submitApprovalTime); // 提交审核时间
            String approvalTime = "";
            if (productModel.getLastApprovalTime() != null) {
                approvalTime = formatter.format(productModel.getLastApprovalTime());
            }
            model.setApprovalTime(approvalTime); // 审核时间
            queryProductModelList.add(model);
        }
    }

    /**
     * setQueryProductDetailModel:设置产品详情查询返回结果集. <br/>
     *
     * @param model
     * @param resp
     */
    private void setQueryProductDetailModel(QueryProductDetailModel model, QueryProductInfoResponse resp) {
        ProductModel productModel = resp.getProductModel();
        // --------------产品基本信息-----------
        String productId = "";
        if (productModel.getProductPeriodModel() != null
                && productModel.getProductPeriodModel().getProductId() != null) {
            productId = productModel.getProductPeriodModel().getProductId().toString();
        }
        model.setProductId(productId); // 产品Id
        model.setProductCode(productModel.getProductCode()); // 产品编号
        model.setProductName(productModel.getProductName()); // 产品名称
        String productLineId = "";
        if (productModel.getProductLineId() != null) {
            productLineId = productModel.getProductLineId().toString();
        }
        if(productModel.getReservationTotalAmount()!=null){
        	model.setReservationTotalAmount(String.valueOf(productModel.getReservationTotalAmount()));
        }
        if(productModel.getActualTotalAmount()!=null){
        	model.setActualTotalAmount(String.valueOf(productModel.getActualTotalAmount()));
        }
        model.setProductLineId(productLineId); // 产品线Id
        model.setProductLineCode(productModel.getProductLineCode()); // 产品线编号
        model.setProductLineName(productModel.getProductLineName()); // 产品线名称
        model.setSaleChannelCode(productModel.getSaleChannelCode()); // 销售渠道
        model.setRiskLevel(productModel.getRiskLevel()); // 风险等级
        model.setProductDisplayName(productModel.getProductDisplayName()); // 产品展示名
        model.setPatternCode(productModel.getPatternCode()); // 产品类型
        model.setJoinChannelCode(productModel.getJoinChannelCode()); // 接入渠道
        String calendarMode = "";
        if (productModel.getCalendarMode() != null) {
            calendarMode = productModel.getCalendarMode().toString();
        }
        model.setCalendarMode(calendarMode); // 日历模式
        String saleStatus = "";
        if (productModel.getSaleStatus() != null) {
            saleStatus = productModel.getSaleStatus().toString();
        }
        model.setSaleStatus(saleStatus); // 销售状态
        String collectStatus = "";
        if (productModel.getCollectStatus() != null) {
            collectStatus = productModel.getCollectStatus().toString();
        }
        model.setCollectStatus(collectStatus); // 募集状态
        model.setInformationDisclosure(JSON.toJSONString(productModel.getProductContractModelList())); // 信息披露
        model.setIntroduction(productModel.getIntroduction()); // 产品介绍

        // ------------------产品交易信息-----------
        String totalAmount = "";
        if (productModel.getTotalAmount() != null) {
            totalAmount = productModel.getTotalAmount().toString();
        }
        model.setTotalAmount(totalAmount); // 募集总规模

        String increaseAmount = "";
        if (productModel.getProductProfitModel() != null
                && productModel.getProductProfitModel().getIncreaseAmount() != null) {
            increaseAmount = productModel.getProductProfitModel().getIncreaseAmount().toString();
        }
        model.setIncreaseAmount(increaseAmount); // 步长（递增金额）

        String assetPoolType = "";
        if (productModel.getAssetPoolType() != null) {
            assetPoolType = productModel.getAssetPoolType().toString();
        }
        model.setAssetPoolType(assetPoolType); // 资产池类型

        String maxInvestAmount = "";
        if (productModel.getProductProfitModel() != null
                && productModel.getProductProfitModel().getMaxInvestAmount() != null) {
            maxInvestAmount = productModel.getProductProfitModel().getMaxInvestAmount().toString();
        }
        model.setMaxInvestAmount(maxInvestAmount); // 个人限投

        String minInvestAmount = "";
        if (productModel.getProductProfitModel() != null
                && productModel.getProductProfitModel().getMinInvestAmount() != null) {
            minInvestAmount = productModel.getProductProfitModel().getMinInvestAmount().toString();
        }
        model.setMinInvestAmount(minInvestAmount); // 起购金额

        model.setAssetPoolCode(productModel.getAssetPoolCode()); // 资产池编号
        model.setAssetPoolName(productModel.getAssetPoolName()); // 资产池名称

        String minHoldAmount = "";
        if (productModel.getProductProfitModel() != null
                && productModel.getProductProfitModel().getMinHoldAmount() != null) {
            minHoldAmount = productModel.getProductProfitModel().getMinHoldAmount().toString();
        }
        model.setMinHoldAmount(minHoldAmount); // 起购金额
        model.setFundSettleParty(productModel.getFundSettleParty()); // 资金结算方

        String maxYieldRate = "";
        String minYieldRate = "";
        if (productModel.getProductProfitModel() != null
                && productModel.getProductProfitModel().getMaxYieldRate() != null) {
            maxYieldRate = productModel.getProductProfitModel().getMaxYieldRate().toString();
        }
        if (productModel.getProductProfitModel() != null
                && productModel.getProductProfitModel().getMinYieldRate() != null) {
            minYieldRate = productModel.getProductProfitModel().getMinYieldRate().toString();
        }
        model.setMaxYieldRate(maxYieldRate); // 预期年化收益率上限
        model.setMinYieldRate(minYieldRate); // 预期年化收益率下限

        // ---------产品库存----------------
        String saleAmount = "";
        if (productModel.getProductStockModel() != null
                && productModel.getProductStockModel().getSaleAmount() != null) {
            saleAmount = productModel.getProductStockModel().getSaleAmount().toString();
        }
        model.setSaleAmount(saleAmount); // 已募集金额

        String stockAmount = "";
        if (productModel.getProductStockModel() != null
                && productModel.getProductStockModel().getStockAmount() != null) {
            stockAmount = productModel.getProductStockModel().getStockAmount().toString();
        }
        model.setStockAmount(stockAmount); // 剩余可投金额

        String redeemAmount = "";
        if (productModel.getProductStockModel() != null
                && productModel.getProductStockModel().getRedeemAmount() != null) {
            redeemAmount = productModel.getProductStockModel().getRedeemAmount().toString();
        }
        model.setRedeemAmount(redeemAmount); //已赎回金额

        // -----------产品业务信息-------------
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String saleStartTime = "";
        if (productModel.getProductPeriodModel() != null
                && productModel.getProductPeriodModel().getSaleStartTime() != null) {
            saleStartTime = formatter.format(productModel.getProductPeriodModel().getSaleStartTime());
        }
        model.setSaleStartTime(saleStartTime); // 募集起始日

        String saleEndTime = "";
        if (productModel.getProductPeriodModel() != null
                && productModel.getProductPeriodModel().getSaleEndTime() != null) {
            saleEndTime = formatter.format(productModel.getProductPeriodModel().getSaleEndTime());
        }
        model.setSaleEndTime(saleEndTime); // 募集截止日

        String valueTime = "";
        if (productModel.getProductPeriodModel() != null
                && productModel.getProductPeriodModel().getValueTime() != null) {
            valueTime = formatter.format(productModel.getProductPeriodModel().getValueTime());
        }
        model.setValueTime(valueTime); // 起息日

        String investPeriodLoopUnit = "";
        if (productModel.getProductPeriodModel() != null
                && productModel.getProductPeriodModel().getInvestPeriodLoopUnit() != null) {
            investPeriodLoopUnit = productModel.getProductPeriodModel().getInvestPeriodLoopUnit().toString();
        }
        model.setInvestPeriodLoopUnit(investPeriodLoopUnit); // 循环周期

        String expireTime = "";
        if (productModel.getProductPeriodModel() != null
                && productModel.getProductPeriodModel().getExpectExpireTime() != null) {
            expireTime = formatter.format(productModel.getProductPeriodModel().getExpectExpireTime());
        }
        model.setExpireTime(expireTime); // 到期日(预期到期日)

        String expectClearTime = "";
        if (productModel.getProductPeriodModel() != null
                && productModel.getProductPeriodModel().getExpectClearTime() != null) {
            expectClearTime = formatter.format(productModel.getProductPeriodModel().getExpectClearTime());
        }
        model.setExpectClearTime(expectClearTime); // 到期回款日(预期结清日)


        String investPeriod = "";
        if (productModel.getProductPeriodModel() != null
                && productModel.getProductPeriodModel().getInvestPeriod() != null) {
            investPeriod = productModel.getProductPeriodModel().getInvestPeriod().toString();
        }
        model.setInvestPeriod(investPeriod); // 产品期限

        String clearTime = "";
        if (productModel.getProductPeriodModel() != null
                && productModel.getProductPeriodModel().getClearTime() != null) {
            clearTime = formatter.format(productModel.getProductPeriodModel().getClearTime());
        }
        model.setClearTime(clearTime); // 结清日

        String nextRedeemTime = "";
        if (productModel.getProductPeriodModel() != null
                && productModel.getProductPeriodModel().getNextRedeemTime() != null) {
            nextRedeemTime = formatter.format(productModel.getProductPeriodModel().getNextRedeemTime());
        }
        model.setNextRedeemTime(nextRedeemTime); // 下一开放赎回日

        String nextRepayTime = "";
        if (productModel.getProductPeriodModel() != null
                && productModel.getProductPeriodModel().getNextRepayTime() != null) {
            nextRepayTime = formatter.format(productModel.getProductPeriodModel().getNextRepayTime());
        }
        model.setNextRepayTime(nextRepayTime); // 下一回款日

        String currentYieldRate = "";
        if (productModel.getProductProfitModel() != null
                && productModel.getProductProfitModel().getCurrentYieldRate() != null) {
            currentYieldRate = productModel.getProductProfitModel().getCurrentYieldRate().toString();
        }
        model.setCurrentYieldRate(currentYieldRate); // 当期利率

        String floatingYieldRate = "";
        if (productModel.getProductProfitModel() != null
                && productModel.getProductProfitModel().getFloatingYieldRate() != null) {
            floatingYieldRate = productModel.getProductProfitModel().getFloatingYieldRate().toString();
        }
        model.setFloatingYieldRate(floatingYieldRate); // 浮动利率

        String investPeriodLoopCount = "";
        if (productModel.getProductPeriodModel() != null
                && productModel.getProductPeriodModel().getInvestPeriodLoopCount() != null) {
            investPeriodLoopCount = productModel.getProductPeriodModel().getInvestPeriodLoopCount().toString();
        }
        model.setInvestPeriodLoopCount(investPeriodLoopCount); // 总循环轮次

        String investPeriodLoopIndex = "";
        if (productModel.getProductPeriodModel() != null
                && productModel.getProductPeriodModel().getInvestPeriodLoopIndex() != null) {
            investPeriodLoopIndex = productModel.getProductPeriodModel().getInvestPeriodLoopIndex().toString();
        }
        model.setInvestPeriodLoopIndex(investPeriodLoopIndex); // 当前循环轮次

        model.setProductLadderList(JSON.toJSONString(productModel.getProductLadderModelList())); // 阶梯收益列表
    }

    /**
     * setQueryProductApprovalModelList:设置产品审核信息列表查询返回结果集. <br/>
     *
     * @param list
     * @param productApprovalModelList
     * @param createBy
     */
    private void setQueryProductApprovalModelList(List<QueryProductApprovalModel> list, List<ProductApprovalModel> productApprovalModelList, String createBy) {
        for (ProductApprovalModel productApprovalModel : productApprovalModelList) {
            QueryProductApprovalModel model = new QueryProductApprovalModel();
            if (productApprovalModel.getId() != null) {
                model.setProductId(productApprovalModel.getId().toString()); // 产品Id
            }
            model.setProductCode(productApprovalModel.getProductCode()); // 产品编号
            model.setSign(productApprovalModel.getSign()); // 授权级别
            if (productApprovalModel.getApprovalStatus() != null) {
                model.setApprovalStatus(productApprovalModel.getApprovalStatus().toString()); // 审核状态
            }
            model.setApprovalSuggestion(productApprovalModel.getApprovalSuggestion()); // 审核意见


            String approvalBy = productApprovalModel.getApprovalBy();
            if (StringUtils.isBlank(approvalBy)) {
                try {
                    OperationHistory operationHistory = new OperationHistory();
                    operationHistory.setOperationType("registerproduct");
                    operationHistory.setReferId(productApprovalModel.getProductCode());
                    List<OperationHistory> historyList = operationHistoryService.selectBySelective(operationHistory);
                    if (historyList != null && historyList.size() > 0) {
                        approvalBy = historyList.get(0).getCreateBy();
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    e.printStackTrace();
                }
            }
            model.setApprovalBy(approvalBy); // 审核人

            String aprovalTime = "";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (productApprovalModel.getApprovalTime() != null) {
                aprovalTime = formatter.format(productApprovalModel.getApprovalTime());
            }
            model.setApprovalTime(aprovalTime); // 审核时间
            list.add(model);
        }
    }

    /**
     * unBindContractFile:将披露文件和产品的关联解绑. <br/>
     *
     * @param productCode
     * @param modifyBy
     */
    private void unBindContractFile(String productCode, String modifyBy) throws Exception {
        QueryProductInfoRequest queryProductInfoRequest = new QueryProductInfoRequest();
        queryProductInfoRequest.setProductCode(productCode);
        //QueryProductInfoResponse queryProductInfoResponse = productServiceFacade.queryProductInfo(queryProductInfoRequest);
        QueryProductInfoResponse queryProductInfoResponse = productInfoQueryUseHttp(queryProductInfoRequest);

        if (ResultCode.SUCCESS.code().equals(queryProductInfoResponse.getRespCode())) {
            List<ProductContractModel> contractModels = queryProductInfoResponse.getProductModel().getProductContractModelList();
            if (contractModels != null && contractModels.size() > 0) {
                for (ProductContractModel model : contractModels) {
                    FileInfo fileInfo = fileService.selectByShowName(model.getContractDisplayName());
                    if (fileInfo != null && StringUtils.isNotBlank(fileInfo.getRelationProduct())) {
                        FileInfo tempInfo = new FileInfo();
                        String relationProduct = fileInfo.getRelationProduct();
                        if (relationProduct.contains(productCode)) {
                            int index = relationProduct.indexOf(productCode);
                            if (relationProduct.equals(productCode)) {
                                relationProduct = "";
                            } else if (index == 0) {
                                relationProduct = relationProduct.replace(productCode + ",", "");
                            } else {
                                relationProduct = relationProduct.replace("," + productCode, "");
                            }
                        }
                        tempInfo.setRelationProduct(relationProduct);
                        tempInfo.setModifyBy(StringUtils.isNotBlank(modifyBy) ? modifyBy : "SYS");
                        tempInfo.setModifyTime(new Date());
                        tempInfo.setShowName(model.getContractDisplayName());
                        fileService.updateByShowNameSelective(tempInfo);
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        String str = "aa,bb,cc";
        System.out.println(str.indexOf("aa"));
    }

    /**
     * 产品注册调用pms  http接口
     *
     * @param
     * @throws Exception
     */
    public RegisterProductResponse productRegisterUseHttp(RegisterProductRequest req,String productType) throws Exception {
        RegisterProductResponse registerProductResponse = null;
        String respContent = null;
        String reqeustUrl=productRegisterUrl;
		if(String.valueOf(ProductTypeEnum.INTERVAL.code()).equals(productType)){
			reqeustUrl=internalProductRegisterUrl;
        }else if(String.valueOf(ProductTypeEnum.CURRENT.code()).equals(productType)){
        	reqeustUrl=productRegisterUrl;
        }
        // 调用远程服务
        log.info("产品注册 调用pms请求地址"+reqeustUrl+" 请求参数：" + JSONObject.toJSONString(req));
        respContent = aesHttpClientUtils.sendPostRequest(reqeustUrl, JSONObject.toJSONString(req));
        log.info("产品注册 调用pms响应参数：：" + respContent);
        // 将json字符创转换成json对象
        if (StringUtils.isNotBlank(respContent)) {
            registerProductResponse = JSONObject.parseObject(respContent, RegisterProductResponse.class);
        }

        return registerProductResponse;
    }
    
    /**
     * 产品注册调用pms  http接口
     *
     * @param
     * @throws Exception
     */
    public RegisterProductResponse internalProductRegisterUseHttp(RegisterProductRequest req) throws Exception {
        RegisterProductResponse registerProductResponse = null;
        String respContent = null;
        String reqeustUrl=internalProductRegisterUrl;
        // 调用远程服务
        log.info("产品注册 调用pms请求地址"+reqeustUrl+" 请求参数：" + JSONObject.toJSONString(req));
        respContent = aesHttpClientUtils.sendPostRequest(reqeustUrl, JSONObject.toJSONString(req));
        //respContent=HttpClientUtil.sendPostRequest(internalProductRegisterUrl, JSONObject.toJSONString(req));
        log.info("产品注册 调用pms响应参数：：" + respContent);
        // 将json字符创转换成json对象
        if (StringUtils.isNotBlank(respContent)) {
            registerProductResponse = JSONObject.parseObject(respContent, RegisterProductResponse.class);
        }

        return registerProductResponse;
    }


    /**
     * 产品列表查询调用pms  http接口
     *
     * @param
     * @throws Exception
     */
    public PageQueryResponse<ProductModel> productListQueryUseHttp(QueryProductListRequest req,String productType) throws Exception {
        PageQueryResponse<ProductModel> productModelPageQueryResponse = null;
        String respContent = null;
        String reqeustUrl=productListQueryUrl;
        if(String.valueOf(ProductTypeEnum.INTERVAL.code()).equals(productType)){
        	reqeustUrl=internalProductListQueryUrl;
        }else if(String.valueOf(ProductTypeEnum.CURRENT.code()).equals(productType)){
        	reqeustUrl=productListQueryUrl;
        }
        // 调用远程服务
        log.info("产品列表查询 调用pms请求地址"+reqeustUrl+" 请求参数：" + JSONObject.toJSONString(req));
        respContent = aesHttpClientUtils.sendPostRequest(reqeustUrl, JSONObject.toJSONString(req));
        log.info("产品列表查询 调用pms响应参数：：" + respContent);

        if (StringUtils.isNotBlank(respContent)) {
            // 将json字符创转换成json对象
            productModelPageQueryResponse = JSONObject.parseObject(respContent, new TypeReference<PageQueryResponse<ProductModel>>() {
            });
        }
        return productModelPageQueryResponse;
    }

    /**
     * 产品信息查询调用pms  http接口
     *
     * @param
     * @throws Exception
     */
    public QueryProductInfoResponse productInfoQueryUseHttp(QueryProductInfoRequest req) throws Exception {
    	
    	 return productInfoQueryUseHttp(req,String.valueOf(ProductTypeEnum.CURRENT.code()));
        
    }
    
    /**
     * 产品信息查询调用pms  http接口
     *
     * @param
     * @throws Exception
     */
    public QueryProductInfoResponse productInfoQueryUseHttp(QueryProductInfoRequest req,String productType) throws Exception {
        QueryProductInfoResponse queryProductInfoResponse = null;
        String reqeustUrl=productInfoQueryUrl;
        if(String.valueOf(ProductTypeEnum.INTERVAL.code()).equals(productType)){
        	reqeustUrl=internalProductInfoQueryUrl;
        } else if(String.valueOf(ProductTypeEnum.CURRENT.code()).equals(productType)){
        	reqeustUrl=productInfoQueryUrl;
        }
        
        String respContent = null;
        
        // 调用远程服务
        log.info("产品详情查询 调用pms请求地址"+reqeustUrl+" 请求参数：" + JSONObject.toJSONString(req));
        respContent = aesHttpClientUtils.sendPostRequest(reqeustUrl, JSONObject.toJSONString(req));
        log.info("产品详情查询 调用pms响应参数：：" + respContent);
        // 将json字符创转换成json对象
        if (StringUtils.isNotBlank(respContent)) {
            queryProductInfoResponse = JSONObject.parseObject(respContent, QueryProductInfoResponse.class);
        }
        return queryProductInfoResponse;
    }

    /**
     * 产品上线调用pms  http接口
     *
     * @param
     * @throws Exception
     */
    public BaseResponse putProductOnLineUseHttp(UpdateProductSaleStatusRequest req,String productType) throws Exception {
        BaseResponse baseResponse = null;
        String reqeustUrl=productOnLineUrl;
        if(String.valueOf(ProductTypeEnum.INTERVAL.code()).equals(productType)){
        	reqeustUrl=internalProductOnLineUrl;
        } else if(String.valueOf(ProductTypeEnum.CURRENT.code()).equals(productType)){
        	reqeustUrl=productOnLineUrl;
        }
        String respContent = null;
        
        // 调用远程服务
        log.info("产品上线 调用pms请求地址"+reqeustUrl+" 请求参数：" + JSONObject.toJSONString(req));
        respContent = aesHttpClientUtils.sendPostRequest(reqeustUrl, JSONObject.toJSONString(req));
        log.info("产品上线 调用pms响应参数：：" + respContent);
        // 将json字符创转换成json对象
        if (StringUtils.isNotBlank(respContent)) {
            baseResponse = JSONObject.parseObject(respContent, BaseResponse.class);
        }
        return baseResponse;
    }
    
    /**
     * 更新产品募集金额调用pms  http接口
     *
     * @param
     * @throws Exception
     */
    private BaseResponse updateProductCollectAmountUseHttp(UpdateProductCollectAmountRequest req) throws Exception {
        BaseResponse baseResponse = null;
        String respContent = null;
        String reqeustUrl=productCollectAmountUrl;
        // 调用远程服务
        log.info("产品上线 调用pms请求地址"+reqeustUrl+" 请求参数：" + JSONObject.toJSONString(req));
        respContent = aesHttpClientUtils.sendPostRequest(reqeustUrl, JSONObject.toJSONString(req));
        log.info("产品上线 调用pms响应参数：：" + respContent);
        // 将json字符创转换成json对象
        if (StringUtils.isNotBlank(respContent)) {
            baseResponse = JSONObject.parseObject(respContent, BaseResponse.class);
        }
        return baseResponse;
    }

    /**
     * 产品下线调用pms  http接口
     *
     * @param
     * @throws Exception
     */
    public BaseResponse putProductOffLineUseHttp(UpdateProductSaleStatusRequest req,String productType) throws Exception {
        BaseResponse baseResponse = null;
        String respContent = null;
        String reqeustUrl=productOffLineUrl;
        if(String.valueOf(ProductTypeEnum.INTERVAL.code()).equals(productType)){
        	reqeustUrl=internalProductOffLineUrl;
        } else if(String.valueOf(ProductTypeEnum.CURRENT.code()).equals(productType)){
        	reqeustUrl=productOffLineUrl;
        }
        // 调用远程服务
        log.info("产品下线 调用pms请求地址"+reqeustUrl+" 请求参数：" + JSONObject.toJSONString(req));
        respContent = aesHttpClientUtils.sendPostRequest(reqeustUrl, JSONObject.toJSONString(req));
        log.info("产品下线 调用pms响应参数：：" + respContent);
        // 将json字符创转换成json对象
        if (StringUtils.isNotBlank(respContent)) {
            baseResponse = JSONObject.parseObject(respContent, BaseResponse.class);
        }
        return baseResponse;
    }

    /**
     * 产品审核调用pms  http接口
     *
     * @param
     * @throws Exception
     */
    public BaseResponse productApprovalUseHttp(ApproveProductRequest req,String productType) throws Exception {
        BaseResponse baseResponse = null;
        String respContent = null;
        String reqeustUrl=productApprovalUrl;
        if(String.valueOf(ProductTypeEnum.INTERVAL.code()).equals(productType)){
        	reqeustUrl=internalProductApprovalUrl;
        } else if(String.valueOf(ProductTypeEnum.CURRENT.code()).equals(productType)){
        	reqeustUrl=productApprovalUrl;
        }
        // 调用远程服务
        log.info("产品审核 调用pms请求地址"+reqeustUrl+" 请求参数：" + JSONObject.toJSONString(req));
		respContent = aesHttpClientUtils.sendPostRequest(reqeustUrl, JSONObject.toJSONString(req));
        log.info("产品审核 调用pms响应参数：：" + respContent);
        // 将json字符创转换成json对象
        if (StringUtils.isNotBlank(respContent)) {
            baseResponse = JSONObject.parseObject(respContent, BaseResponse.class);
        }
        return baseResponse;
    }

    /**
     * 产品审核信息列表查询调用pms  http接口
     *
     * @param
     * @throws Exception
     */
    public QueryProductApprovalInfoResponse queryProductApprovalListUseHttp(QueryProductApprovalListRequest req,String productType) throws Exception {
        QueryProductApprovalInfoResponse productApprovalInfoResponse = null;
        String respContent = null;
        String reqeustUrl=productApprovalInfoListUrl;
        if(String.valueOf(ProductTypeEnum.INTERVAL.code()).equals(productType)){
        	reqeustUrl=internalProductApprovalInfoListUrl;
        } else if(String.valueOf(ProductTypeEnum.CURRENT.code()).equals(productType)){
        	reqeustUrl=productApprovalInfoListUrl;
        }
        // 调用远程服务
        log.info("产品审核信息列表查询 调用pms请求地址"+reqeustUrl+" 请求参数：" + JSONObject.toJSONString(req));
        respContent = aesHttpClientUtils.sendPostRequest(reqeustUrl, JSONObject.toJSONString(req));
        log.info("产品审核信息列表查询 调用pms响应参数：：" + respContent);

        if (StringUtils.isNotBlank(respContent)) {
            // 将json字符创转换成json对象
            productApprovalInfoResponse = JSONObject.parseObject(respContent, QueryProductApprovalInfoResponse.class);
        }
        return productApprovalInfoResponse;
    }

    /**
     * 产品统计调用pms  http接口
     *
     * @param
     * @throws Exception
     */
    public ProductStatisticsResponse productStatistics(QueryProductListRequest req,String productType) throws Exception {
        ProductStatisticsResponse response = null;
        String respContent = null;
        String reqeustUrl=productStatisticsUrl;
        if(String.valueOf(ProductTypeEnum.INTERVAL.code()).equals(productType)){
        	reqeustUrl=internalProductStatisticsUrl;
        } else if(String.valueOf(ProductTypeEnum.CURRENT.code()).equals(productType)){
        	reqeustUrl=productStatisticsUrl;
        }
        // 调用远程服务
        log.info("产品统计查询 调用pms请求地址"+reqeustUrl+" 请求参数：" + JSONObject.toJSONString(req));
        respContent = aesHttpClientUtils.sendPostRequest(reqeustUrl, JSONObject.toJSONString(req));
        log.info("产品统计查询 调用pms响应参数：：" + respContent);
        // 将json字符创转换成json对象
        if (StringUtils.isNotBlank(respContent)) {
            response = JSONObject.parseObject(respContent, ProductStatisticsResponse.class);
        }
        return response;
    }

	@Override
	public BaseWebResponse updateCollectAmount(UpdateProductCollectAmountRequest req) {
		 log.info("ProductBizImpl.updateCollectAmount.req:" + JSON.toJSONString(req));
	        BaseWebResponse resp = null;
	        try {
	            // 调用产品上线http服务接口
	            BaseResponse baseResponse = updateProductCollectAmountUseHttp(req);

	            if (ResultCode.SUCCESS.code().equals(baseResponse.getRespCode())) {
	                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.desc());
	            } else {
	                resp = new BaseWebResponse(RespCode.SUCCESS.code(), ResultCode.FAIL.code(), baseResponse.getRespMsg());
	            }
	        } catch (Exception e) {
	            log.error(e.getMessage(), e);
	            e.printStackTrace();
	            resp = new BaseWebResponse(RespCode.FAIL.code(), RespCode.FAIL.desc());
	        }

	        log.info("ProductBizImpl.updateCollectAmount.resp:" + JSON.toJSONString(resp));
	        return resp;
	}
	
}
