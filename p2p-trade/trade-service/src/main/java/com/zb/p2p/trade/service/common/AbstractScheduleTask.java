package com.zb.p2p.trade.service.common;

import com.alibaba.fastjson.JSON;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.p2p.trade.common.constant.GlobalVar;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.util.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Comparator;
import java.util.List;

/**
 * <p> 描述 </p>
 *
 * @author Vinson
 * @version AbstractScheduleTask.java v1.0 2018/4/21 14:31 Zhengwenquan Exp $
 */
public abstract class AbstractScheduleTask<T> implements IScheduleTaskDealSingle<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 获取处理数据项
     * @param taskParameter
     * @param taskItemList
     * @param eachFetchDataNum
     * @return
     * @throws BusinessException
     */
    public abstract List<T> selectProcessItems(String taskParameter,
                                               List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception ;
    @Override
    public List<T> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
                               List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {

        logger.info("{}任务开始执行...", getTaskName());
        logger.info("配置的参数:任务过滤项{},每次获取数量{}", JSON.toJSONString(taskItemList), eachFetchDataNum);

        List<T> processItems = selectProcessItems(taskParameter, taskItemList, eachFetchDataNum);

        logger.info("本次调度处理任务数量:{}", processItems == null ? 0 : processItems.size());
        return processItems;
    }

    @Override
    public boolean execute(T t, String s) throws Exception {

        StopWatch watch = new StopWatch();
        watch.start();
        boolean processFlag = true;
        try{
            MDC.put(GlobalVar.LOG_TRACE_ID, StringUtils.getUUID());

            logger.info("{}任务开始执行...", getTaskName());

            processFlag = process(t, s);

        } catch (Exception e){
            logger.error("任务执行异常", e);
        } finally {
            watch.stop();
            logger.info("{}任务执行结束，耗时[{}]ms", getTaskName(), watch.getTime());
            MDC.clear();
        }

        return processFlag;
    }

    /**
     * 具体执行方法
     * @throws BusinessException
     */
    protected abstract boolean process(T t, String s) throws Exception;

    /**
     * 获取任务名称
     * @return
     */
    protected abstract String getTaskName();

    @Override
    public Comparator<T> getComparator() {
        return null;
    }
}
