package com.zb.p2p.cash.schedule;

import com.alibaba.fastjson.JSON;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.p2p.cash.service.CashRecordService;
import com.zb.p2p.entity.CashRecordEntity;
import com.zb.p2p.facade.api.resp.CommonResp;
import com.zb.p2p.facade.api.resp.product.ProductDTO;
import com.zb.p2p.facade.service.MatchRecordFacade;
import com.zb.p2p.facade.service.internal.ProductInternalService;
import com.zb.p2p.facade.service.internal.dto.MatchRecordDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Function:兑付批次任务
 * 原来为持仓纬度，现在改为基于债权关系的纬度
 * Author: created by liguoliang
 * Date: 2017/9/6 0006 下午 4:57
 * Version: 1.0
 */
@Component("batchTask")
@Slf4j
public class BatchTask implements IScheduleTaskDealSingle<MatchRecordDTO> {

    @Autowired
    private CashRecordService cashRecordService;
    @Autowired
    private MatchRecordFacade matchRecordFacade;
    @Autowired
    private ProductInternalService productInternalService;

    @Override
    public boolean execute(MatchRecordDTO matchRecordEntity, String ownSign) throws Exception {
        CommonResp<ProductDTO> dtoCommonResp = productInternalService.queryProductInfoByProductCode(matchRecordEntity.getProductCode());
        ProductDTO productDTO = dtoCommonResp.getData();
        CashRecordEntity cashRecordPara = new CashRecordEntity();
        cashRecordPara.setProductCode(productDTO.getProductCode());
        cashRecordPara.setCashDate(productDTO.getProductPeriodDTO().getExpectClearTime());//产品到期日期
        cashRecordPara.setYield(productDTO.getProductProfitDTO().getMinYieldRate());//年化收益率
        cashRecordPara.setProductDays(productDTO.getProductPeriodDTO().getInvestPeriod());//产品天数
        cashRecordPara.setInterestDays(productDTO.getProductProfitDTO().getBasicInterestsPeriod());//计息天数(默认365)
        log.info("BatchTask中获取的产品信息为:{}", JSON.toJSONString(cashRecordPara));
        //基于已经生成总收益的债权生成兑付计划
        cashRecordService.saveCashRecordAndBatch(matchRecordEntity, cashRecordPara);
        return true;
    }

    @Override
    public List<MatchRecordDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
                                            List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
        log.info("batchTask配置的参数:任务过滤项{},每次获取数量{}", JSON.toJSONString(taskItemList), eachFetchDataNum);

        List<MatchRecordDTO> matchRecordEntities = matchRecordFacade.listMatchRecords(taskItemList, eachFetchDataNum);

        if (CollectionUtils.isEmpty(matchRecordEntities)) {
            return Collections.emptyList();
        }
        log.info("batchTask本次处理任务量:{}", matchRecordEntities.size());

        return matchRecordEntities;
    }

    @Override
    public Comparator<MatchRecordDTO> getComparator() {
        return null;
    }
}
