package com.zb.fincore.pms.service.line.validate;

import com.zb.fincore.common.enums.ChannelEnum;
import com.zb.fincore.common.enums.product.PatternCodeTypeEnum;
import com.zb.fincore.common.enums.product.ProductLineStatusEnum;
import com.zb.fincore.common.enums.product.ProductRiskLevelEnum;
import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.facade.line.dto.req.OnShelveProductLineRequest;
import com.zb.fincore.pms.facade.line.dto.req.RegisterProductLineRequest;
import com.zb.fincore.pms.facade.line.dto.resp.RegisterProductLineResponse;
import com.zb.fincore.pms.service.dal.dao.ProductLineDao;
import com.zb.fincore.pms.service.dal.model.ProductLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 功能: 产品线数据库服务参数校验器
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/3/30 0030 19:25
 * 版本: V1.0
 */
@Component
public class ProductLineDbServiceParameterValidator {

    /**
     * 产品线数据访问对象
     */
    @Autowired
    private ProductLineDao productLineDao;

    /**
     * 检测产品线注册请求参数逻辑
     *
     * @param req 产品线注册请求对象
     * @return 没有逻辑错误返回null
     */
    public RegisterProductLineResponse checkRegisterProductLineRequestParameter(RegisterProductLineRequest req) {
        if (ProductRiskLevelEnum.getEnumItem(req.getRiskLevel()) == null) {
            return BaseResponse.build(RegisterProductLineResponse.class, Constants.PRODUCT_RISK_LEVEL_NOT_IN_ENUMS_RETURN_CODE,
                    Constants.PRODUCT_RISK_LEVEL_NOT_IN_ENUMS_RETURN_DESC);
        }

        if (PatternCodeTypeEnum.getEnumItem(req.getPatternCode()) == null) {
            return BaseResponse.build(RegisterProductLineResponse.class, Constants.PRODUCT_PATTERN_CODE_NOT_IN_ENUMS_RETURN_CODE,
                    Constants.PRODUCT_PATTERN_CODE_NOT_IN_ENUMS_RETURN_DESC);
        }

        if (!ChannelEnum.validateChannel(req.getSaleChannelCode())) {
            return BaseResponse.build(RegisterProductLineResponse.class, Constants.PRODUCT_CHANNEL_CODE_NOT_IN_ENUMS_RESULT_CODE,
                    Constants.PRODUCT_CHANNEL_CODE_NOT_IN_ENUMS_RESULT_DESC);
        }

        ProductLine productLine = new ProductLine();
        productLine.setLineName(req.getLineName());
        productLine.setLineDisplayName(req.getLineDisplayName());
        int count = productLineDao.selectProductLineCountByNames(productLine);
        if (count > 0) {
            return BaseResponse.build(RegisterProductLineResponse.class, Constants.PRODUCT_LINE_EXIST_RETURN_CODE, Constants.PRODUCT_LINE_EXIST_RETURN_DESC);
        }
        return null;
    }

    /**
     * 检测产品线上架请求参数逻辑
     *
     * @param req 产品线上架请求对象
     * @return 没有逻辑错误返回null
     */
    public BaseResponse checkOnShelveProductLineRequestParameter(OnShelveProductLineRequest req, ProductLine productLine) {
        if (ProductLineStatusEnum.CANCELED.getCode() == productLine.getStatus().intValue()) {
            return BaseResponse.build(Constants.PRODUCT_LINE_ALREADY_ABANDON_CANNOT_ON_SHELVE_CODE, Constants.PRODUCT_LINE_ALREADY_ABANDON_CANNOT_ON_SHELVE_CODE_DESC);
        }
        return null;
    }
}
