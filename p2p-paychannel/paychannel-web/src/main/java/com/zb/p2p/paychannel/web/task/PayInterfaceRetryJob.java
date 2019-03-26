package com.zb.p2p.paychannel.web.task;

import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.p2p.paychannel.dao.domain.InterfaceRetryEntity;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * 通知重试JOB
 */
@Slf4j
@Component("matchResultNotifyRetryJob")
public class PayInterfaceRetryJob implements IScheduleTaskDealSingle<InterfaceRetryEntity> {
    private static Logger logger = LoggerFactory.getLogger(PayInterfaceRetryJob.class);

    @Override
    public boolean execute(InterfaceRetryEntity interfaceRetryEntity, String s) throws Exception {
        return true;
    }

    @Override
    public List<InterfaceRetryEntity> selectTasks(String s, String s1, int i, List<TaskItemDefine> list, int i1) throws Exception {
        return null;
    }

    @Override
    public Comparator<InterfaceRetryEntity> getComparator() {
        return null;
    }
     
     
}
