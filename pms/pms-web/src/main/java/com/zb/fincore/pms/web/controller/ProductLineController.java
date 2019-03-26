package com.zb.fincore.pms.web.controller;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.common.dto.QueryResponse;
import com.zb.fincore.pms.facade.line.ProductLineServiceFacade;
import com.zb.fincore.pms.facade.line.dto.req.*;
import com.zb.fincore.pms.facade.line.dto.resp.RegisterProductLineResponse;
import com.zb.fincore.pms.facade.line.model.ProductLineModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能: 产品线数据库服务RESTFUL接口
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/3/29 0029 13:51
 * 版本: V1.0
 */
@RestController
@RequestMapping(value = "/productLineService")
public class ProductLineController {

    @Autowired
    private ProductLineServiceFacade productLineServiceFacade;

    /**
     * 产品线注册
     *
     * @param req 产品线注册请求对象
     * @return 产品线注册响应对象
     */
    @RequestMapping(value = "/registerProductLine", method = RequestMethod.POST)
    public RegisterProductLineResponse registerProductLine(@RequestBody RegisterProductLineRequest req) {
        return productLineServiceFacade.registerProductLine(req);
    }

    /**
     * 产品线更新
     *
     * @param req 产品线更新请求对象
     * @return 基础响应对象
     */
    @RequestMapping(value = "/updateProductLine", method = RequestMethod.POST)
    BaseResponse updateProductLine(@RequestBody UpdateProductLineRequest req) {
        return productLineServiceFacade.updateProductLine(req);
    }

    /**
     * 产品线注销
     *
     * @param req 产品线注销请求对象
     * @return 基础响应对象
     */
    @RequestMapping(value = "/abandonProductLine", method = RequestMethod.POST)
    BaseResponse abandonProductLine(@RequestBody AbandonProductLineRequest req) {
        return productLineServiceFacade.abandonProductLine(req);
    }

    /**
     * 产品线详情查询
     *
     * @param req 产品线详情查询请求对象
     * @return 产品线详情查询响应对象
     */
    @RequestMapping(value = "/queryProductLine", method = RequestMethod.POST)
    QueryResponse<ProductLineModel> queryProductLine(@RequestBody QueryProductLineRequest req) {
        return productLineServiceFacade.queryProductLine(req);
    }

    /**
     * 产品线列表查询
     *
     * @param req 产品线列表查询请求对象
     * @return 产品线列表查询响应对象
     */
    @RequestMapping(value = "/queryProductLineList", method = RequestMethod.POST)
    PageQueryResponse<ProductLineModel> queryProductLineList(@RequestBody QueryProductLineListRequest req) {
        return productLineServiceFacade.queryProductLineList(req);
    }

    /**
     * 产品线上架
     *
     * @param req 产品线上架请求对象
     * @return 产品线上架响应对象
     */
    @RequestMapping(value = "/onShelveProductLine", method = RequestMethod.POST)
    BaseResponse onShelveProductLine(@RequestBody OnShelveProductLineRequest req) {
        return productLineServiceFacade.onShelveProductLine(req);
    }

    /**
     * 产品线下架
     *
     * @param req 产品线下架请求对象
     * @return 产品线下架响应对象
     */
    @RequestMapping(value = "/offShelveProductLine", method = RequestMethod.POST)
    BaseResponse offShelveProductLine(@RequestBody OffShelveProductLineRequest req) {
        return productLineServiceFacade.offShelveProductLine(req);
    }
}
