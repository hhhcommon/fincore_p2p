package com.zb.fincore.pms.service.dal.dao;

import com.zb.fincore.pms.service.dal.model.GlobalConfig;

/**
 * 功能: 系统配置参数DAO
 * 版本: V1.0
 */
public interface GlobalConfigDao {

    GlobalConfig selectByPropertyName(String propertyName);

}