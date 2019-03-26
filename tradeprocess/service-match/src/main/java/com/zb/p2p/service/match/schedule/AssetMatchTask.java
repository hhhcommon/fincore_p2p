/*
package com.zb.p2p.service.match.schedule;

import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.service.match.MatchRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;

*/
/**
 * 资产匹配JOB触发处理  每日晚23:00触发
 * @author
 * @create 2017-09-07 17:23
 *//*

public class AssetMatchTask implements IScheduleTaskDealSingle{
    private static Logger logger = LoggerFactory.getLogger(AssetMatchTask.class);

    @Autowired
    private MatchRecordService matchRecordService;

    @Override
    public boolean execute(Object o, String s) throws Exception {
        return true;
    }

    @Override
    public List selectTasks(String s, String s1, int i, List list, int i1) throws Exception {
        logger.info("asset match task start .");
        CommonResp<String> resp = matchRecordService.assetMatch();
        logger.info("asset match task end :", resp.getMessage());
        return null;
    }

    @Override
    public Comparator getComparator() {
        return null;
    }
}
*/
