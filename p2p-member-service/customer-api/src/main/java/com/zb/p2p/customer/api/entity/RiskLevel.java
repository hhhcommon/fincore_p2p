
package com.zb.p2p.customer.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by laoguoliang
 */
@ApiModel
public class RiskLevel {
    @ApiModelProperty(value="风险等级")
    private String riskLevel;
    @ApiModelProperty(value="风险等级名")
    private String riskLevelName;
    @ApiModelProperty(value="风险等级内容")
    private String riskLevelContent;


    public RiskLevel(String riskLevel){
    	this.riskLevel = riskLevel;
    	if ("A".equals(riskLevel)) {
            riskLevelName = "保守型";
            riskLevelContent = "风险承受能力低，对收益要求不高，但追求本金安全。";
        }else if ("B".equals(riskLevel)) {
            riskLevelName = "稳健型";
            riskLevelContent = "风险承受能力较低，能容忍一定幅度的本金损失，止损意识强。";
        }else if ("C".equals(riskLevel)) {
            riskLevelName = "平衡型";
            riskLevelContent = "风险承受度适中，偏向于资产均衡配置，能够承受一定的投资风险。";
        }else if ("D".equals(riskLevel)) {
            riskLevelName = "成长型";
            riskLevelContent = "偏向于较激进的资产配置，对风险有较高的承受能力，投资收益预期相对较高，除获取资本得利之外，也寻求投资差价收益。";
        }else if ("E".equals(riskLevel)) {
            riskLevelName = "进取型";
            riskLevelContent = "对风险有非常高的承受能力，资产配置以高风险投资品种为主，投机性强。";
        }else{
        	riskLevelName = "未评分";
        	riskLevelContent = "未知";
        }
    }
    public RiskLevel() {
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getRiskLevelName() {
        return riskLevelName;
    }

    public void setRiskLevelName(String riskLevelName) {
        this.riskLevelName = riskLevelName;
    }

    public String getRiskLevelContent() {
        return riskLevelContent;
    }

    public void setRiskLevelContent(String riskLevelContent) {
        this.riskLevelContent = riskLevelContent;
    }
    
    @Override
    public String toString(){
        return "RiskLevel ["
                + "riskLevel=" + riskLevel + 
                ", riskLevelName=" + riskLevelName +
                ", riskLevelContent=" + riskLevelContent +
                "]";
    }
}
