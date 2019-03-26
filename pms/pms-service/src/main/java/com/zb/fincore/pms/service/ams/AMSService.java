package com.zb.fincore.pms.service.ams;

import java.util.List;

import com.zb.fincore.ams.common.dto.BaseResponse;
import com.zb.fincore.ams.common.dto.QueryResponse;
import com.zb.fincore.ams.facade.dto.p2p.req.CancelAssetProductRelationRequest;
import com.zb.fincore.ams.facade.dto.p2p.req.CreateAssetProductRelationRequest;
import com.zb.fincore.ams.facade.dto.p2p.req.SynAssetProductRelationRequest;
import com.zb.fincore.ams.facade.dto.p2p.resp.CreateAssetProductRelationResponse;
import com.zb.fincore.ams.facade.dto.req.QueryPoolRequest;
import com.zb.fincore.ams.facade.model.PoolModel;
import com.zb.fincore.pms.common.exception.BusinessException;
import com.zb.fincore.pms.facade.product.model.ProductModel;

/**
 * 资产服务接口<br/>
 * Date: 2018年4月23日 上午11:40:53 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version
 * @since JDK 1.7
 */
public interface AMSService {

	/**
	 * 产品注册时，通知资管系统产品与借款订单关系 V2.0 (需加密) <br/>
	 *
	 * @param productCode
	 * @param productName
	 * @param loanNoList
	 * @throws Exception
	 */
	void notifyAssetCreateProductLoanRelationHttp(String productCode, String productName, List<String> loanNoList, String payChannel) throws BusinessException;

	/**
     * 产品审核时，通知资管系统通知订单匹配 V2.0 (需加密) <br/>
     *
     * @param productCode
     * @param productName
     * @param productStatus
     * @throws BusinessException
     */
	void notifyAssetProductLoanRelationHttp(String productCode, String productName, Integer productStatus) throws BusinessException;

    /**
     * 产品审核不通过 V2.0
     * @param productCode
     * @throws BusinessException
     */
    void notifyAssetCancelProductLoanRelationHttp(String productCode) throws BusinessException;

    /**
     * 设置借款信息列表（产品详情里要包含借款端信息）
     * @param productModel
     * @return
     * @throws BusinessException
     */
    ProductModel setProductLoanInfoList(ProductModel productModel) throws BusinessException;

	/**
	 * 产品注册时，通知资管系统产品与借款订单关系 V3.0 (无需加密) <br/>
	 *
	 * @param req
	 * @return
	 * @throws BusinessException
	 */
	CreateAssetProductRelationResponse createAssetProductRelationHttp(CreateAssetProductRelationRequest req) throws BusinessException;

	/**
	 * 产品审核不通过时，通知资管系统 V3.0 (无需加密) <br/>
	 *
	 * @param req
	 * @return
	 * @throws BusinessException
	 */
	BaseResponse cancelAssetProductRelationHttp(CancelAssetProductRelationRequest req) throws BusinessException;

	/**
	 * 产品审核通过时，通知资管系统通知订单匹配 V3.0 (无需加密) <br/>
	 *
	 * @param req
	 * @return
	 * @throws BusinessException
	 */
	BaseResponse synAssetProductRelation(SynAssetProductRelationRequest req) throws BusinessException;

	/**
	 * 查询资产池详情 V3.0 (无需加密) <br/>
	 *
	 * @param queryPoolRequest
	 * @return
	 * @throws BusinessException
	 */
	QueryResponse<PoolModel> queryPoolInfoHttp(QueryPoolRequest queryPoolRequest) throws BusinessException;


}
