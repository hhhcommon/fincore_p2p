package com.zb.fincore.pms.service.product.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.fincore.common.enums.ResultCodeEnum;
import com.zb.fincore.pms.common.exception.BusinessException;
import com.zb.fincore.pms.common.exception.ExceptionHandler;
import com.zb.fincore.pms.service.dal.dao.GlobalConfigDao;
import com.zb.fincore.pms.service.dal.model.GlobalConfig;
import com.zb.fincore.pms.service.product.GlobalConfigService;

/**
 * 产品系统配置业务实现类
 *
 * @author
 * @create 2017-10-12 9:55
 */
@Service
public class GlobalConfigServiceImpl implements GlobalConfigService {

    private static Logger logger = LoggerFactory.getLogger(GlobalConfigServiceImpl.class);

    @Autowired
    private GlobalConfigDao globalConfigDao;

    @Autowired
    private ExceptionHandler exceptionHandler;


    /**
     * 判断该属性名称是否存在全局配置表中. <br/>
     * return String
     *
     */
    public String doGetGlobalConfigByParam (String param) throws Exception {
        GlobalConfig globalConfig = globalConfigDao.selectByPropertyName(param);
        if (globalConfig==null) {
            throw new BusinessException(ResultCodeEnum.FAIL.code(), "在全局配置表中该属性名称不存在：" + param);
        }
        if (StringUtils.isBlank(globalConfig.getPropertyValue())) {
            throw new BusinessException(ResultCodeEnum.FAIL.code(), "在全局配置表中该属性名称所对应的值为空：" + param);
        }
        return globalConfig.getPropertyValue();
    }
}
