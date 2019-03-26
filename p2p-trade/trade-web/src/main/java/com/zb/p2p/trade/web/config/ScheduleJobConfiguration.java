package com.zb.p2p.trade.web.config;

import com.taobao.pamirs.schedule.strategy.TBScheduleManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Function:
 * Author: created by liguoliang
 * Date: 2018/4/21 0021 下午 3:52
 * Version: 1.0
 */
@Configuration
public class ScheduleJobConfiguration {
        @Bean(initMethod = "init")
        public TBScheduleManagerFactory tbScheduleManagerFactory(
            @Value("${tbSchedule.zkConnectString}")String zkConnectString,
            @Value("${tbSchedule.rootPath}")String rootPath,
            @Value("${tbSchedule.zkSessionTimeout}")String zkSessionTimeout,
            @Value("${tbSchedule.userName}")String userName,
            @Value("${tbSchedule.password}")String password,
            @Value("${tbSchedule.isCheckParentPath}")String isCheckParentPath) {
            TBScheduleManagerFactory tbScheduleManagerFactory = new TBScheduleManagerFactory();
            Map<String, String> zkConfig = new HashMap<String, String>();
            zkConfig.put("zkConnectString", zkConnectString);
            zkConfig.put("rootPath", rootPath);
            zkConfig.put("zkSessionTimeout", zkSessionTimeout);
            zkConfig.put("userName", userName);
            zkConfig.put("password", password);
            zkConfig.put("isCheckParentPath", isCheckParentPath);
            tbScheduleManagerFactory.setZkConfig(zkConfig);
            return tbScheduleManagerFactory;
        }
    }
