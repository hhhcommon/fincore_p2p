package com.zb.fincore.pms.web.controller.p2p.job;

import com.zb.fincore.common.utils.PropertiesUtil;
import com.zb.fincore.pms.facade.product.ProductJobServiceFacade;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;

/**
 * 产品募集期开始JOB
 * description:
 * @author
 * @create 2017-06-12 11:00
 */
public class ProductRaisePeriodStartJob {

    private static final Logger LOG = LoggerFactory.getLogger(ProductRaisePeriodStartJob.class.getName());

    @Autowired
    ProductJobServiceFacade productJobServiceFacade;

    public void doProductRaisePeriodStartJob() {
        String jobScheduleConfig = PropertiesUtil.getValue("JOB_SCHEDULE_CONFIG");
        if(StringUtils.isBlank(jobScheduleConfig) || !"Y".equals(jobScheduleConfig)){
            return;
        }

        LOG.info("--------*****-------------doProductRaisePeriodStartJob start:" + new Date());
        productJobServiceFacade.startProductRaising();
        LOG.info("--------*****-------------doProductRaisePeriodStartJob end:" + new Date());
    }
}
