package com.zb.fincore.pms.facade.line.impl;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.common.dto.QueryResponse;
import com.zb.fincore.pms.common.exception.ExceptionHandler;
import com.zb.fincore.pms.facade.line.ProductLineServiceFacade;
import com.zb.fincore.pms.facade.line.dto.req.*;
import com.zb.fincore.pms.facade.line.dto.resp.RegisterProductLineResponse;
import com.zb.fincore.pms.facade.line.model.ProductLineModel;
import com.zb.fincore.pms.service.line.ProductLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能: 产品线数据库服务接口实现类
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/3/28 0028 15:36
 * 版本: V1.0
 */
@Service
public class ProductLineServiceFacadeImpl implements ProductLineServiceFacade {

    @Autowired
    private ProductLineService productLineService;

    @Autowired
    private ExceptionHandler exceptionHandler;

    /**
     * 产品线注册
     *
     * @param req 产品线注册请求对象
     * @return 产品线注册响应对象
     */
    public RegisterProductLineResponse registerProductLine(RegisterProductLineRequest req) {
        try {
            return productLineService.registerProductLine(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e, RegisterProductLineResponse.class);
        }
    }

    /**
     * 产品线更新
     *
     * @param req 产品线更新请求对象
     * @return 基础响应对象
     */
    public BaseResponse updateProductLine(UpdateProductLineRequest req) {
        try {
            return productLineService.updateProductLine(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
    }

    /**
     * 产品线注销
     *
     * @param req 产品线注销请求对象
     * @return 基础响应对象
     */
    public BaseResponse abandonProductLine(AbandonProductLineRequest req) {
        try {
            return productLineService.abandonProductLine(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
    }

    /**
     * 产品线详情查询
     *
     * @param req 产品线详情查询请求对象
     * @return 产品线查详情询响应对象
     */
    public QueryResponse<ProductLineModel> queryProductLine(QueryProductLineRequest req) {
        try {
            return productLineService.queryProductLine(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e, QueryResponse.class);
        }
    }

    /**
     * 产品线列表查询
     *
     * @param req 产品线列表查询请求对象
     * @return 产品线列表查询响应对象
     */
    public PageQueryResponse<ProductLineModel> queryProductLineList(QueryProductLineListRequest req) {
        try {
            return productLineService.queryProductLineList(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e, PageQueryResponse.class);
        }
    }

    @Override
    public BaseResponse onShelveProductLine(OnShelveProductLineRequest req) {
        try {
            return productLineService.onShelveProductLine(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
    }

    @Override
    public BaseResponse offShelveProductLine(OffShelveProductLineRequest req) {
        try {
            return productLineService.offShelveProductLine(req);
        } catch (Exception e) {
            return exceptionHandler.handleException(e);
        }
    }
}
