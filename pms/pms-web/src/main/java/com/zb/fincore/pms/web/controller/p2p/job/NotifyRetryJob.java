package com.zb.fincore.pms.web.controller.p2p.job;

import com.zb.fincore.common.utils.PropertiesUtil;
import com.zb.fincore.pms.facade.product.InterfaceRetryJobServiceFacade;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by kaiyun on 2018/3/29 0029.
 */
public class NotifyRetryJob {
    private static final Logger LOG = LoggerFactory.getLogger(NotifyRetryJob.class.getName());

    @Autowired
    private InterfaceRetryJobServiceFacade interfaceRetryJobServiceFacade;

    public void doNotifyRetryJob() {
        String jobScheduleConfig = PropertiesUtil.getValue("JOB_SCHEDULE_CONFIG");
        if(StringUtils.isBlank(jobScheduleConfig) || !"Y".equals(jobScheduleConfig)){
            return;
        }

        LOG.info("--------*****-------------doNotifyRetryJob start:" + new Date());
        interfaceRetryJobServiceFacade.putNotifyRetry();
        LOG.info("--------*****-------------doNotifyRetryJob end:" + new Date());
    }
}
