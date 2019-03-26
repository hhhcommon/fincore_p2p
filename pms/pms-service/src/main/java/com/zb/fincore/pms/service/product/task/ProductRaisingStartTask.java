package com.zb.fincore.pms.service.product.task;

import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.fincore.pms.facade.product.ProductJobServiceFacade;
import com.zb.fincore.pms.service.dal.model.Product;

/**
 * 募集期开始job. <br/>
 * Date: 2018年4月20日 下午6:31:09 <br/>
 *
 * @author kaiyun@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
@Component("productRaisingStartTask")
public class ProductRaisingStartTask implements IScheduleTaskDealSingle<Product> {
	private static Logger logger = LoggerFactory.getLogger(ProductRaisingStartTask.class);
	
	@Autowired
    private ProductJobServiceFacade productJobServiceFacade;

	/** 
	 * 获取任务的比较器,只有在NotSleep模式下需要用到 
	 * @return 
	 */
	@Override
	public Comparator<Product> getComparator() {
		return null;
	}

	/**
     * 根据条件，查询当前调度服务器可处理的任务
     * @param taskParameter 任务的自定义参数
     * @param ownSign 当前环境名称
     * @param taskQueueNum 当前任务类型的任务队列数量
     * @param taskItemList 当前调度服务器，分配到的可处理队列
     * @param eachFetchDataNum 每次获取数据的数量
     * @return
     * @throws Exception
     */
	@Override
	public List<Product> selectTasks(String taskParameter,String ownSign,int taskQueueNum,
            List<TaskItemDefine> taskItemList,int eachFetchDataNum) throws Exception {
		logger.info("募集期开始job开始了……");
		productJobServiceFacade.startProductRaising();
		return null;
	}

	/**
     * 执行单个任务，执行 selectTasks 返回的集合
     * @param orderInfo
     * @param ownSign 当前环境名称
     * @throws Exception
     */
	@Override
	public boolean execute(Product arg0, String ownSign) throws Exception {
		return true;
	}

}
