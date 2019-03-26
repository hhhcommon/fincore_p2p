/**
 * 
 */
package com.zb.p2p.customer.service;

import java.util.List;

import com.zb.p2p.customer.api.entity.RiskLevel;
import com.zb.p2p.customer.api.entity.RistkAssessment;

/**
 * 查询风险评测信息
 * @author laoguoliang
 *
 */
public interface RiskAssessmentService {
    /**
     * 提交用户答案获得评测结果
     * @param List<RistkAssessment>
     * @return RiskLevel
     */
    RiskLevel riskLevel(List<RistkAssessment> ristkAssessmentList, String customerId);
    /**
     * 当前会员是否已评测及结果
     * @param orgId
     * @return RiskAssessmentResult
     */
    RiskLevel selectRiskAssessment(Long customerId);
}
