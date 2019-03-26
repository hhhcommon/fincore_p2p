package com.zillionfortune.boss.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 接口响应码定义
 * @author
 * @date 2016/12/13
 */
public enum RespCode {
	/** 000000: 处理成功 **/
	SUCCESS("000000","处理成功"),
	/** 999999: 系统异常 **/
	FAIL("999999","系统异常");
	
	private String code;
	private String desc;

    RespCode(String code,String desc){
        this.code=code;
        this.desc=desc;
    }

    public String code(){
        return code;
    }

    public String desc(){
        return desc;
    }
    
    public static String getDesc(String code){
    	for(RespCode item : RespCode.values()){
    		if(StringUtils.equals(item.code, code)){
    			return item.desc();
    		}
    	}
    	
    	return null;
    }
    
}
