/**
 * 
 */
package com.zb.p2p.customer.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zb.p2p.customer.api.entity.RiskLevel;
import com.zb.p2p.customer.api.entity.RistkAssessment;
import com.zb.p2p.customer.common.util.CustomerConstants;
import com.zb.p2p.customer.dao.CustomerBindcardMapper;
import com.zb.p2p.customer.dao.CustomerInfoMapper;
import com.zb.p2p.customer.dao.domain.CustomerInfo;
import com.zb.p2p.customer.service.RiskAssessmentService;

/**
 * 查询风险评测服务
 * @author guolitao
 *
 */
@Service
public class RiskAssessmentServiceImpl implements RiskAssessmentService {
    private static final Logger logger = LoggerFactory.getLogger(RiskAssessmentServiceImpl.class);
    @Resource
    private CustomerInfoMapper customerInfoMapper;
    
    @Resource
    private CustomerBindcardMapper customerBindcardMapper;
    
    private static final Integer[][] answers = {{-2,0,-4,10,0},{0,2,6,8,10},{2,4,8,10,0},{0,2,6,10,0},
        {0,2,6,8,10},{0,4,8,10,0},{0,4,6,10,0},{4,6,8,10,0},{2,6,10,0,0},{-5,5,10,15,20}};
    
    @Override
    public RiskLevel riskLevel(List<RistkAssessment> ristkAssessmentList, String customerId) {
        RiskLevel riskLevel = new RiskLevel();
        Integer totalScore = 0;//分数
        String level = "0";//风险承受等级
        String riskLevelName = "";//风险等级名
        String riskLevelContent = "";//风险等级内容
        for (RistkAssessment ristkAssessment : ristkAssessmentList) {
            Integer questionNo = Integer.parseInt(ristkAssessment.getQuestionNo()) -1;
            String answer = ristkAssessment.getAnswerNo();
            Integer answerNo = 0;
            if ("A".equals(answer)) {
                answerNo = 0;
            }else if ("B".equals(answer)) {
                answerNo = 1;
            }else if ("C".equals(answer)) {
                answerNo = 2;
            }else if ("D".equals(answer)) {
                answerNo = 3;
            }else if ("E".equals(answer)) {
                answerNo = 4;
            }
            totalScore = totalScore + answers[questionNo][answerNo];
        }
        if (totalScore < 20) {
            level = "A";
            riskLevelName = "保守型";
            riskLevelContent = "风险承受能力低，对收益要求不高，但追求本金安全。";
        }else if (totalScore >= 21 && totalScore <= 45) {
            level = "B";
            riskLevelName = "稳健型";
            riskLevelContent = "风险承受能力较低，能容忍一定幅度的本金损失，止损意识强。";
        }else if (totalScore >= 46 && totalScore <= 70) {
            level = "C";
            riskLevelName = "平衡型";
            riskLevelContent = "风险承受度适中，偏向于资产均衡配置，能够承受一定的投资风险。";
        }else if (totalScore >= 71 && totalScore <= 85) {
            level = "D";
            riskLevelName = "成长型";
            riskLevelContent = "偏向于较激进的资产配置，对风险有较高的承受能力，投资收益预期相对较高，除获取资本得利之外，也寻求投资差价收益。";
        }else if (totalScore >= 86) {
            level = "E";
            riskLevelName = "进取型";
            riskLevelContent = "对风险有非常高的承受能力，资产配置以高风险投资品种为主，投机性强。";
        }

        CustomerInfo info = new CustomerInfo();
        info.setCustomerId(Long.parseLong(customerId));
//        info.setRiskLevel(level);
//        info.setIsRiskRate(CustomerConstants.IS_REAL_1);//是否已实名,0-否1-是
        int count = customerInfoMapper.updateByPrimaryKeySelective(info);
        logger.info("更新风险评测信息，结果" + count);
        if (count > 0) {
            riskLevel.setRiskLevel(level);
            riskLevel.setRiskLevelName(riskLevelName);
            riskLevel.setRiskLevelContent(riskLevelContent);
        }
        return riskLevel;
    }

    @Override
    public RiskLevel selectRiskAssessment(Long customerId) {
        RiskLevel result = new RiskLevel();
        CustomerInfo info = customerInfoMapper.selectByPrimaryKey(customerId);
        if (info != null) {
            String riskLevel = "";
            String riskLevelName = "";
            String riskLevelContent = "";
            /*if ("A".equals(info.getRiskLevel())) {
                riskLevel = info.getRiskLevel();
                riskLevelName = "保守型";
                riskLevelContent = "风险承受能力低，对收益要求不高，但追求本金安全。";
            }else if ("B".equals(info.getRiskLevel())) {
                riskLevel = info.getRiskLevel();
                riskLevelName = "稳健型";
                riskLevelContent = "风险承受能力较低，能容忍一定幅度的本金损失，止损意识强。";
            }else if ("C".equals(info.getRiskLevel())) {
            	riskLevel = info.getRiskLevel();
                riskLevelName = "平衡型";
                riskLevelContent = "风险承受度适中，偏向于资产均衡配置，能够承受一定的投资风险。";
            }else if ("D".equals(info.getRiskLevel())) {
            	riskLevel = info.getRiskLevel();
                riskLevelName = "成长型";
                riskLevelContent = "偏向于较激进的资产配置，对风险有较高的承受能力，投资收益预期相对较高，除获取资本得利之外，也寻求投资差价收益。";
            }else if ("E".equals(info.getRiskLevel())) {
            	riskLevel = info.getRiskLevel();
                riskLevelName = "进取型";
                riskLevelContent = "对风险有非常高的承受能力，资产配置以高风险投资品种为主，投机性强。";
            }else{
                riskLevel = "0";
                riskLevelName = "未评分";
                riskLevelContent = "";
            }*/
            result.setRiskLevel(riskLevel);
            result.setRiskLevelName(riskLevelName);
            result.setRiskLevelContent(riskLevelContent);
        }
        return result;
    }

}
