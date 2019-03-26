package com.zb.fincore.pms.service.line;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.common.dto.QueryResponse;
import com.zb.fincore.pms.facade.line.dto.req.*;
import com.zb.fincore.pms.facade.line.dto.resp.RegisterProductLineResponse;
import com.zb.fincore.pms.facade.line.model.ProductLineModel;

import javax.validation.Valid;

/**
 * 功能: 产品线数据库服务类
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/3/28 0028 15:41
 * 版本: V1.0
 */
public interface ProductLineService {

    /**
     * 产品线注册
     *
     * @param req 产品线注册请求对象
     * @return 产品线注册响应对象
     */
    RegisterProductLineResponse registerProductLine(@Valid RegisterProductLineRequest req) throws Exception;

    /**
     * 产品线更新
     *
     * @param req 产品线更新请求对象
     * @return 基础响应对象
     */
    BaseResponse updateProductLine(@Valid UpdateProductLineRequest req) throws Exception;

    /**
     * 产品线注销
     *
     * @param req 产品线注销请求对象
     * @return 基础响应对象
     */
    BaseResponse abandonProductLine(@Valid AbandonProductLineRequest req) throws Exception;

    /**
     * 产品线详情查询
     *
     * @param req 产品线详情查询请求对象
     * @return 产品线详情查询响应对象
     */
    QueryResponse<ProductLineModel> queryProductLine(@Valid QueryProductLineRequest req) throws Exception;

    /**
     * 产品线列表查询
     *
     * @param req 产品线列表查询请求对象
     * @return 产品线列表查询响应对象
     */
    PageQueryResponse<ProductLineModel> queryProductLineList(QueryProductLineListRequest req) throws Exception;

    /**
     * 产品线上架
     *
     * @param req 产品线上架请求对象
     * @return 产品线上架响应对象
     */
    BaseResponse onShelveProductLine(@Valid OnShelveProductLineRequest req) throws Exception;

    /**
     * 产品线下架
     *
     * @param req 产品线下架请求对象
     * @return 产品线下架响应对象
     */
    BaseResponse offShelveProductLine(@Valid OffShelveProductLineRequest req) throws Exception;
}
