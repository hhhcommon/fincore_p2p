package com.zb.fincore.pms.service.line.impl;

import com.zb.fincore.common.enums.product.DisplayStatusEnum;
import com.zb.fincore.common.enums.product.ProductLineStatusEnum;
import com.zb.fincore.common.utils.BeanUtils;
import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.dto.PageQueryResponse;
import com.zb.fincore.pms.common.dto.QueryResponse;
import com.zb.fincore.pms.common.model.Page;
import com.zb.fincore.pms.facade.line.dto.req.*;
import com.zb.fincore.pms.facade.line.dto.resp.RegisterProductLineResponse;
import com.zb.fincore.pms.facade.line.model.ProductLineModel;
import com.zb.fincore.pms.service.SequenceService;
import com.zb.fincore.pms.service.dal.dao.ProductDao;
import com.zb.fincore.pms.service.dal.dao.ProductLineDao;
import com.zb.fincore.pms.service.dal.model.ProductLine;
import com.zb.fincore.pms.service.line.ProductLineService;
import com.zb.fincore.pms.service.line.validate.ProductLineDbServiceParameterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

/**
 * 功能: 产品线数据库服务类
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/3/28 0028 15:41
 * 版本: V1.0
 */
@Service
public class ProductLineServiceImpl implements ProductLineService {

    /**
     * 参数校验器
     */
    @Autowired
    private ProductLineDbServiceParameterValidator validator;

    /**
     * 序列服务
     */
    @Autowired
    private SequenceService sequenceService;

    /**
     * 产品线数据访问对象
     */
    @Autowired
    private ProductLineDao productLineDao;

    /**
     * 产品数据访问对象
     */
    @Autowired
    private ProductDao productDao;

    /**
     * 产品线注册
     *
     * @param req 产品线注册请求对象
     * @return 产品线注册响应对象
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Exception.class})
    public RegisterProductLineResponse registerProductLine(@Valid RegisterProductLineRequest req) throws Exception {
        //1: 执行请求参数校验
        RegisterProductLineResponse resp = validator.checkRegisterProductLineRequestParameter(req);
        if (null != resp) {
            return resp;
        }

        //2: 按照规则生成产品线编号
        String lineCode = sequenceService.generateProductCode(Constants.SEQUENCE_NAME_PREFIX_PRODUCT_LINE, req.getPatternCode(), 3);

        //3: 入库产品线信息
        ProductLine productLine = new ProductLine();
        BeanUtils.copy(req, productLine);
        productLine.setLineCode(lineCode);
        productLine.setStatus(ProductLineStatusEnum.NORMAL.getCode());
        productLine.setDisplayStatus(DisplayStatusEnum.INVISIBLE.getCode());
        productLineDao.insertSelective(productLine);

        //4: 返回结果
        resp = BaseResponse.build(RegisterProductLineResponse.class);
        resp.setLineCode(lineCode);
        return resp;
    }

    /**
     * 产品线更新
     *
     * @param req 产品线更新请求对象
     * @return 基础响应对象
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Exception.class})
    public BaseResponse updateProductLine(@Valid UpdateProductLineRequest req) throws Exception {
        ProductLine productLine = productLineDao.selectByCode(req.getLineCode());
        if (null == productLine) {
            return BaseResponse.build(Constants.PRODUCT_LINE_UN_EXIST_RETURN_CODE, Constants.PRODUCT_LINE_UN_EXIST_RETURN_DESC);
        }

        //根据修改的展示名查询是否存在相同展示名
        int count = productLineDao.selectProductLineCountByDisplayName(req.getLineDisplayName());
        if (count > 0){
            return BaseResponse.build(Constants.PRODUCT_LINE_EXIST_RETURN_CODE, Constants.PRODUCT_LINE_EXIST_RETURN_DESC);
        }

        productLine.setLineDisplayName(req.getLineDisplayName());
        productLine.setModifyBy(req.getModifyBy());
        productLineDao.updateSelective(productLine);
        return BaseResponse.build();
    }

    /**
     * 产品线注销
     *
     * @param req 产品线注销请求对象
     * @return 基础响应对象
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Exception.class})
    public BaseResponse abandonProductLine(@Valid AbandonProductLineRequest req) throws Exception {
        ProductLine productLine = productLineDao.selectByCode(req.getLineCode());
        if (null == productLine) {
            return BaseResponse.build(Constants.PRODUCT_LINE_UN_EXIST_RETURN_CODE, Constants.PRODUCT_LINE_UN_EXIST_RETURN_DESC);
        }
        //校验产品线下是否存在未归档的产品
        int count = productDao.selectUnArchiveProductListByLineId(productLine.getId());
        if (count > 0){
            return BaseResponse.build(Constants.PRODUCT_LINE_EXIST_UN_ARCHIVE_PRODUCT_CODE, Constants.PRODUCT_LINE_EXIST_UN_ARCHIVE_PRODUCT_CODE_DESC);
        }

        //更新产品线状态
        ProductLine updateObj = new ProductLine();
        updateObj.setId(productLine.getId());
        updateObj.setStatus(ProductLineStatusEnum.CANCELED.getCode());
        updateObj.setDisplayStatus(DisplayStatusEnum.INVISIBLE.getCode());
        updateObj.setModifyBy(req.getModifyBy());
        productLineDao.updateSelective(updateObj);

        return BaseResponse.build();
    }

    /**
     * 产品线详情查询
     *
     * @param req 产品线详情查询请求对象
     * @return 产品线详情查询响应对象
     */
    public QueryResponse<ProductLineModel> queryProductLine(@Valid QueryProductLineRequest req) throws Exception {
        ProductLine productLine = productLineDao.selectByCode(req.getLineCode());
        if (null != productLine) {
            ProductLineModel productLineModel = BeanUtils.copyAs(productLine, ProductLineModel.class);
            QueryResponse<ProductLineModel> resp = BaseResponse.build(QueryResponse.class);
            resp.setData(productLineModel);
            return resp;
        } else {
            return BaseResponse.build(QueryResponse.class, Constants.PARAM_RESULTBLANK_CODE, Constants.PARAM_RESULTBLANK_DESC);
        }
    }

    /**
     * 产品线列表查询
     *
     * @param req 产品线列表查询请求对象
     * @return 产品线列表查询响应对象
     */
    public PageQueryResponse<ProductLineModel> queryProductLineList(QueryProductLineListRequest req) throws Exception {
        PageQueryResponse<ProductLineModel> resp = BaseResponse.build(PageQueryResponse.class);

        Page page = new Page();
        ProductLine productLine = new ProductLine();
        BeanUtils.copy(req, page);
        BeanUtils.copy(req, productLine);

        int totalCount = productLineDao.selectCount(productLine);
        List<ProductLineModel> productLineModels = null;
        if (totalCount > 0) {
            List<ProductLine> lineList = productLineDao.selectListByPage(productLine, page);
            productLineModels = BeanUtils.copyAs(lineList, ProductLineModel.class);
        } else {
            resp = BaseResponse.build(PageQueryResponse.class, Constants.PARAM_RESULTBLANK_CODE, Constants.PARAM_RESULTBLANK_DESC);
        }
        resp.setPageSize(page.getPageSize());
        resp.setPageNo(page.getPageNo());
        resp.setTotalCount(totalCount);
        resp.setDataList(productLineModels);

        return resp;
    }

    /**
     * 产品线上架
     *
     * @param req 产品线上架请求对象
     * @return 产品线上架响应对象
     */
    @Override
    public BaseResponse onShelveProductLine(@Valid OnShelveProductLineRequest req) throws Exception {
        ProductLine productLine = productLineDao.selectByCode(req.getLineCode());
        if (null == productLine) {
            return BaseResponse.build(Constants.PRODUCT_LINE_UN_EXIST_RETURN_CODE, Constants.PRODUCT_LINE_UN_EXIST_RETURN_DESC);
        }
        BaseResponse resp = validator.checkOnShelveProductLineRequestParameter(req, productLine);
        if (null != resp) {
            return resp;
        }

        //更新产品线状态
        ProductLine updateObj = new ProductLine();
        updateObj.setId(productLine.getId());
        updateObj.setDisplayStatus(DisplayStatusEnum.VISIBLE.getCode());
        productLineDao.updateSelective(updateObj);

        return BaseResponse.build();
    }

    /**
     * 产品线下架
     *
     * @param req 产品线下架请求对象
     * @return 产品线下架响应对象
     */
    @Override
    public BaseResponse offShelveProductLine(@Valid OffShelveProductLineRequest req) throws Exception {
        ProductLine productLine = productLineDao.selectByCode(req.getLineCode());
        if (null == productLine) {
            return BaseResponse.build(Constants.PRODUCT_LINE_UN_EXIST_RETURN_CODE, Constants.PRODUCT_LINE_UN_EXIST_RETURN_DESC);
        }
        //更新产品线状态
        ProductLine updateObj = new ProductLine();
        updateObj.setId(productLine.getId());
        updateObj.setDisplayStatus(DisplayStatusEnum.INVISIBLE.getCode());
        productLineDao.updateSelective(updateObj);
        return BaseResponse.build();
    }
}
