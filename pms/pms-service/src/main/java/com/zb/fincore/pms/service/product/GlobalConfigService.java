package com.zb.fincore.pms.service.product;

import com.zb.fincore.pms.common.dto.BaseResponse;
import com.zb.fincore.pms.service.dal.model.InterfaceRetry;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 产品系统配置 服务
 * Created by kaiyun on 2017/8/31.
 */
public interface GlobalConfigService {

    /**
     * 读取配置
     *
     * @param param
     * @throws Exception
     */
    String doGetGlobalConfigByParam (String param) throws Exception;

}
