package com.zb.p2p.trade.service.topic;

import com.alibaba.fastjson.JSON;
import com.zb.fincore.common.enums.ResultCodeEnum;
import com.zb.p2p.match.common.enums.AssetTypeEnum;
import com.zb.p2p.trade.common.constant.GlobalVar;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.queue.AbstractMnsMsgHandler;
import com.zb.p2p.trade.common.queue.model.MatchMqResult;
import com.zb.p2p.trade.persistence.entity.LoanRequestEntity;
import com.zb.p2p.trade.service.order.BasicDataService;
import com.zb.p2p.trade.service.process.MatchResultProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Map;


/**
 * 资产匹配完成  通知
 * @author tangqingqing
 *
 */
public class AssetMatchCompleteNotifyListener extends AbstractMnsMsgHandler {

    @Autowired
    private BasicDataService basicDataService;

    // 匹配结果处理器映射
    private Map<String, MatchResultProcessor> processorMap;

    public void setProcessorMap(Map<String, MatchResultProcessor> processorMap) {
        this.processorMap = processorMap;
    }

    @Override
    public void handleMessage(String message) throws Exception {

        logger.info("处理资产匹配完成消息开始：{}", message);
        // 转换
        MatchMqResult result = JSON.parseObject(message, MatchMqResult.class);

        //查询借款单
        final LoanRequestEntity loanRequest = basicDataService.selectByAssetCodeForLoan(result.getAssetCode());

        validate(loanRequest, result);

        // 查找还款方式（2.0到期还本付息，分期还）
        AssetTypeEnum assetType = AssetTypeEnum.getByCode(loanRequest.getAssetType());
        switch (assetType) {
            case YSZC:
                // 分期及一次性还本付息
                process(loanRequest, result, assetType);
                break;
            default:
                throw new BusinessException(ResultCodeEnum.PARAMS_VALIDATE_FAIL.code(), "暂不支持此种资产类型：" + assetType);
        }

        logger.info("处理资产匹配完成消息结束，资产类型：{}", assetType);
    }

    private void process(LoanRequestEntity loanRequest, MatchMqResult result, AssetTypeEnum assetType) throws BusinessException{
        MatchResultProcessor processor = processorMap.get(assetType.name());
        if (processor != null) {
            processor.process(loanRequest, result);
        }
    }

    /**
     * 校验
     * @param loanRequest
     */
    private void validate(LoanRequestEntity loanRequest, MatchMqResult result) {
        Assert.isTrue(GlobalVar.LOAN_SUCCESS_STATUS.contains(result.getAssetStatus()), "匹配消息的结果状态不正确");
        Assert.isTrue(result.getMatchCounts() > 0 , "匹配消息的匹配数量必须大于零");
        Assert.notNull(loanRequest, "未找到对应借款订单");
        Assert.notNull(loanRequest.getMatchedAmount(), "借款金额不能为空");
        Assert.isTrue(loanRequest.getMatchedAmount().compareTo(result.getMatchAmount()) == 0,
                "请求匹配金额和借款金额不一致");
        Assert.isTrue(result.getAssetStatus().equals(loanRequest.getLoanStatus()), "借款订单状态不正确");
    }

}
