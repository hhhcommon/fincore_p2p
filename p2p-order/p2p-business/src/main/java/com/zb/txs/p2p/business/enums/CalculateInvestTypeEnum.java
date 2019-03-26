package com.zb.txs.p2p.business.enums;

import lombok.Getter;

/**
 * @author nibaoshan
 * @create 2017-09-26 20:34
 * @desc 投资还款类型（金核）
 **/
@Getter
public enum CalculateInvestTypeEnum {
    ONETIME("一次性还本付息",1),
    SEASON("按季付息 到期还本",2),
    MONTH("按月付息，到期还本",3),
    PROFITCAPITALEQUAL("等额本息",4),
    UNKNOW("未知方式",5);

    private  String name;
    private  Integer value;

    CalculateInvestTypeEnum(String name,Integer value){
        this.name=name;
        this.value=value;
    }


    private static final CalculateInvestTypeEnum[] calculateInvestTypes = values();

    public static String getDesc(Integer value){
        for(CalculateInvestTypeEnum calculateInvestTypeEnum:calculateInvestTypes){
            if(calculateInvestTypeEnum.getValue().equals(value)){
                return calculateInvestTypeEnum.getName();
            }
        }
        return UNKNOW.getName();
    }
}
