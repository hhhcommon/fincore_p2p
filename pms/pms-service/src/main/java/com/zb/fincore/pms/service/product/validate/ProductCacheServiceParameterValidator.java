package com.zb.fincore.pms.service.product.validate;

import org.springframework.stereotype.Component;

import com.zb.fincore.common.enums.product.ProductSaleStatusEnum;
import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.common.enums.ProductCollectStatusEnum;
import com.zb.fincore.pms.service.dal.model.Product;

/**
 * 功能: 产品数据库服务参数校验器
 * 日期: 2017/4/10 0030 19:25
 * 版本: V1.0
 */
@Component
public class ProductCacheServiceParameterValidator {

    public BaseResponse productStockRequestParameter(Product product, int changeType) {
        //`change_type` tinyint(2) DEFAULT NULL COMMENT '更变类型(1:冻结,2:占用,3:释放, 4:赎回, 5:取消)',
        String prefixStr = "冻结";
        switch (changeType) {
            case 1:
                prefixStr = "冻结";
                break;
            case 2:
                prefixStr = "占用";
                break;
            case 3:
                prefixStr = "释放";
                break;
            case 4:
                prefixStr = "赎回";
                break;
            case 5:
                prefixStr = "取消";
                break;
            default:
                return BaseResponse.build(Constants.FAIL_RESP_CODE, "变更类型不在设置范围内！");
        }
        String errorMsg = prefixStr + "产品库存失败";
    	if (product==null) {
    		return BaseResponse.build(Constants.FAIL_RESP_CODE, errorMsg + "，产品不存在！");
        }

        if (ProductSaleStatusEnum.PRODUCT_SALE_STATUS_ON_LINE.getCode()!=product.getSaleStatus()) {
        	return BaseResponse.build(Constants.FAIL_RESP_CODE, errorMsg + "，产品未上线！");
    	}

        if (ProductCollectStatusEnum.PRODUCT_COLLECT_STATUS_COLLECTING.getCode()!=product.getCollectStatus()) {
        	return BaseResponse.build(Constants.FAIL_RESP_CODE, errorMsg + "，产品不在募集期内！");
    	}
        return null;
    }

}
