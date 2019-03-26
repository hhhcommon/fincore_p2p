/*
 * Copyright (c) ${year}, 资邦金服（上海）网络科技有限公司. All Rights Reserved.
 *
 */
package com.zillionfortune.boss.biz.pms;

import com.zb.fincore.pms.facade.product.dto.req.ApproveProductRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductApprovalListRequest;
import com.zb.fincore.pms.facade.product.dto.req.QueryProductListRequest;
import com.zb.fincore.pms.facade.product.dto.req.RegisterProductRequest;
import com.zb.fincore.pms.facade.product.dto.req.UpdateProductSaleStatusRequest;
import com.zillionfortune.boss.common.dto.BaseWebResponse;

/**
 * ClassName: ProductBiz <br/>
 * Function: 产品管理Biz. <br/>
 * Date: 2017年5月9日 上午11:27:40 <br/>
 *
 * @author wangzinan_tech@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public interface P2PProductBiz {
	/**
	 * registerProduct:产品注册. <br/>
	 *
	 * @param req
	 * @param createBy
	 * @param userId
	 * @return
	 */
	public BaseWebResponse registerProduct(RegisterProductRequest req, String createBy, Integer userId);
	
	
    /**
     * queryProductList:产品列表查询. <br/>
     *
     * @param req
     * @return
     */
    public BaseWebResponse queryProductList(QueryProductListRequest req);

	
	/**
	 * queryProductDetail:产品详情. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse queryProductDetail(String productCode);
	
	/**
	 * putProductOnLine:产品上线. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse putProductOnLine(UpdateProductSaleStatusRequest req);
	
	/**
	 * putProductOffLine:产品下线. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse putProductOffLine(UpdateProductSaleStatusRequest req);

	/**
	 * approveProduct:产品审核. <br/>
	 *
	 * @param req
	 * @return
	 */
	public BaseWebResponse approveProduct(ApproveProductRequest req);
	
	/**
     * countUpProductStatus:产品状态分类统计. <br/>
     *
     * @return
     */
    public BaseWebResponse countUpProductStatus();    
    /**
	 * queryProductApprovalList:产品审核信息列表查询. <br/>
	 *
	 * @param req
	 * @param createBy
	 * @return
	 */
	public BaseWebResponse queryProductApprovalList(QueryProductApprovalListRequest req, String createBy);

}
