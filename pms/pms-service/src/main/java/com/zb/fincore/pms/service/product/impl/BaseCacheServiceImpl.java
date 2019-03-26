package com.zb.fincore.pms.service.product.impl;

import com.zb.fincore.common.redis.RedisManager;
import com.zb.fincore.common.utils.AesHttpClientUtils;
import com.zb.fincore.pms.common.Constants;
import com.zb.fincore.pms.facade.product.model.ProductStockModel;
import com.zb.fincore.pms.service.dal.model.ProductStock;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 功能: 公共服务实现
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/14 0014 13:41
 * 版本: V1.0
 */
public class BaseCacheServiceImpl {

    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(BaseCacheServiceImpl.class);

    @Autowired
    protected RedisManager redisManager;

    @Autowired
    protected AesHttpClientUtils aesHttpClientUtils;

    /**
     * 更新Redis库存信息
     *
     * @param productStock 库存信息
     */
    public synchronized void updateRedisProductStock(ProductStock productStock) {
        try {
            ProductStockModel stockModel = new ProductStockModel();
            PropertyUtils.copyProperties(stockModel, productStock);
            String key = Constants.PRODUCT_STOCK_PREFIX + productStock.getProductCode();
            redisManager.set(key, JSONObject.fromObject(stockModel).toString());
        } catch (Exception e) {
            logger.error("更新Redis产品库存失败", e);
        }
    }

    /**
     * 获取Redis中缓存库存信息
     *
     * @param productCode 产品编码
     * @return 产品库存信息
     */
    public ProductStockModel getRedisProductStock(String productCode) {
        ProductStockModel stockModel = null;
        try {
            String key = Constants.PRODUCT_STOCK_PREFIX + productCode;
            String jsonStr = redisManager.get(key);
            if (StringUtils.isNotBlank(jsonStr)) {
                return (ProductStockModel) JSONObject.toBean(JSONObject.fromObject(jsonStr), ProductStockModel.class);
            }
//            if (value != null) {
//                return (ProductStockModel) SerializeUtils.deserialize(value);
//            }
        } catch (Exception e) {
            logger.error("读取Redis库存信息异常:" + productCode, e);
        }
        return stockModel;
    }
}
