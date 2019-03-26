package com.zb.p2p.service.incomecalc.schedule;

import com.alibaba.fastjson.JSON;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.p2p.entity.OrderRequestEntity;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.api.resp.product.ProductDTO;
import com.zb.p2p.facade.service.internal.ProductInternalService;
import com.zb.p2p.service.common.DistributedLockService;
import com.zb.p2p.service.incomecalc.IncomeCalcService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by wangwanbin on 2017/8/31.
 * (0 14/30 * * * ?)
 */
@Component("incomeCalcTask")
public class IncomeCalcTask implements IScheduleTaskDealSingle<ProductDTO> {
    @Autowired
    private IncomeCalcService incomeCalcService;
    @Autowired
    private ProductInternalService productInternalService;
    
   

    private static Logger logger = LoggerFactory.getLogger(IScheduleTaskDealSingle.class);

    @Override
    public boolean execute(ProductDTO productDTO, String s) throws Exception { 
        incomeCalcService.incomeCalc(productDTO);
        return true;
    }

    @Override
    public List<ProductDTO> selectTasks(String s, String s1, int i, List<TaskItemDefine> list, int i1) throws Exception {
        logger.info("开始进行任务扫描 任务过滤项{},每次获取数量{}", JSON.toJSONString(list), i1);
        List<ProductDTO> resultList = productInternalService.queryProductListByDate(null);
        if (resultList == null) {
            return new ArrayList<>(0);
        }
        logger.info("本次处理任务量:{}", resultList.size());
        return resultList;
    }

    @Override
    public Comparator<ProductDTO> getComparator() {
        return null;
    }
}
