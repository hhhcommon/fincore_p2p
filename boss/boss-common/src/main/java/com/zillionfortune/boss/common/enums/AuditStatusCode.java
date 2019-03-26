package com.zillionfortune.boss.common.enums;

import org.apache.commons.lang3.StringUtils;


/**
 * ClassName: AuditStatusCode <br/>
 * Function: 业务处理结果. <br/>
 * Date: 2016年12月13日 下午2:14:31 <br/>
 *
 * @author fangyang
 * @version 
 * @since JDK 1.7
 */
public enum AuditStatusCode {
	
	/***企业资料审核状态**/
	AUDIT_WAIT("0","待审核"),
	
	AUDIT_SUC("1","审核通过"),
	
	AUDIT_FAIL("2","审核不通过 "),
	
	/***认证状态**/
	AUTH_WAIT("0","待认证"),
	
	AUTH_IN("1","认证中"),
	
	AUTH_FAIL("2","认证失败"),
	
	AUTH_SUC("3","已认证 "),
	
	/***企业理财-被授权人审核状态**/
	MEMBER_ATUH_PERSON_STATUS_SUC("3","审核通过"),
	MEMBER_ATUH_PERSON_STATUS_NOT_PASS("2","审核不通过"),
	MEMBER_ATUH_PERSON_STATUS_DISCARD("9","废弃");
	
	private String code;
	private String desc;

    AuditStatusCode(String code,String desc){
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
    	for(AuditStatusCode item : AuditStatusCode.values()){
    		if(StringUtils.equals(item.code, code)){
    			return item.desc();
    		}
    	}
    	
    	return null;
    }
    
}

