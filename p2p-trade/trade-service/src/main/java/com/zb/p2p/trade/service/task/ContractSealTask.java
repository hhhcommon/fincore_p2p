package com.zb.p2p.trade.service.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.p2p.trade.client.request.NotifyLoanStatusReq;
import com.zb.p2p.trade.common.enums.ContractEnum;
import com.zb.p2p.trade.common.enums.CreditorStatusEnum;
import com.zb.p2p.trade.common.enums.InterfaceRetryBusinessTypeEnum;
import com.zb.p2p.trade.common.enums.InterfaceRetryStatusEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.model.CommonResp;
import com.zb.p2p.trade.persistence.dao.CreditorInfoMapper;
import com.zb.p2p.trade.persistence.entity.ContractEntity;
import com.zb.p2p.trade.persistence.entity.CreditorInfoEntity;
import com.zb.p2p.trade.persistence.entity.InterfaceRetryEntity;
import com.zb.p2p.trade.service.common.AbstractScheduleTask;
import com.zb.p2p.trade.service.common.InterfaceRetryService;
import com.zb.p2p.trade.service.contract.ContractService;
import com.zb.p2p.trade.service.contract.impl.ContractServiceImpl;
import com.zb.p2p.trade.service.order.OrderAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 合同盖章 JOB
 */
@Slf4j
@Component("contractSealTask")
public class ContractSealTask extends AbstractScheduleTask<ContractEntity> {

    @Autowired
    private ContractService contractService;

    @Autowired
    private CreditorInfoMapper creditorInfoMapper;


    @Override
    public boolean process(ContractEntity contractEntity, String s) throws BusinessException {
        // 查询债权信息
        CreditorInfoEntity creditorInfoEntity = creditorInfoMapper.selectByOrderAndAsset(contractEntity.getExtInvestOrderNo(), contractEntity.getAssetCode());

        // 合同盖章
        if (creditorInfoEntity != null) {
            contractService.signContract(creditorInfoEntity.getCreditorNo() );
        }
        return true;
    }

    @Override
    protected String getTaskName() {
        return "合同盖章结果通知重试任务";
    }

    @Override
    public List<ContractEntity> selectProcessItems(String taskParameter,
                                                         List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws BusinessException {
        log.info("OrderNotifyRetryTask start running ....");

        List<ContractEntity> contractEntityList = contractService.selectByStatus(ContractEnum.INIT.getCode() );
        if (CollectionUtils.isNullOrEmpty(contractEntityList)) {
            return new ArrayList<>(0);
        }
        return contractEntityList;
    }

}
