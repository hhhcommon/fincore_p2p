
package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by laoguoliang
 */
@ApiModel
public class RiskAssessmentResult {
    @ApiModelProperty(value="是否评测 true:是 false:否")
    private boolean isAssessment;
    @ApiModelProperty(value="风险等级 A:保守型   B:安稳型 C:稳健性 D:成长型 E:积极型 默认为0代表未评分")
    private String riskLevel;


    public RiskAssessmentResult() {
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public boolean getIsAssessment() {
        return isAssessment;
    }

    public void setIsAssessment(boolean isAssessment) {
        this.isAssessment = isAssessment;
    }
    
}
