package com.zillionfortune.boss.common.enums;

import org.apache.commons.lang3.StringUtils;


/**
 * ClassName: ResultCode <br/>
 * Function: 业务处理结果. <br/>
 * Date: 2016年12月13日 下午2:14:31 <br/>
 *
 * @author mengkai@zillionfortune.com
 * @version 
 * @since JDK 1.7
 */
public enum ResultCode {
	
	/** 0000: 业务处理成功 **/
	SUCCESS("0000","业务处理成功"),
	
	/** 9999: 业务处理失败 **/
	FAIL("9999","业务处理失败"),
	
	/** 9999: 业务处理失败 **/
	NO_DATA("0000","查询的结果为空"),
	
	ILLEGAL_PARAMETER("1000","请求参数错误"),
	
	/** NAME_OR_PASS_ERROR: 用户名或密码错误 **/
	NAME_OR_PASS_ERROR("OP9000","用户名或密码错误 "),
	
	/** USER_ISNOT_EXIST: 用户不存在  **/
	USER_ISNOT_EXIST("OP9001","用户不存在 "),
	
	/** USER_IS_EXIST: 用户已存在 **/
	USER_IS_EXIST("OP9002","用户已存在 "),
	
	/** ADD_USER_FAIL: 添加用户失败 **/
	ADD_USER_FAIL("OP9003","添加用户失败 "),
	
	/** USER_ORIGINAL_PWD_ERROR: 原始登录密码错误 **/
	USER_ORIGINAL_PWD_ERROR("OP9004","原始登录密码错误"),
	
	/** USER_HAVE_ROLE: 该用户已分配角色无法删除 **/
	USER_HAVE_ROLE("OP9005","该用户已分配角色无法删除"),
	
	/** USER_ALREADY_DEL: 该用户已删除 **/
	USER_ALREADY_DEL("OP9006","该用户已删除"),
	
	/** POWER_IS_EXIST: 权限已存在 **/
	POWER_IS_EXIST("OP2001","已经存在所分配的权限 "),
	
	/** POWER_IS_USED: 该权限已经被占用无法删除**/
	POWER_IS_USED("OP2002","该权限已经被占用无法删除 "),
	
	/** MENU_IS_EXIST: 同一级级别中，菜单已存在 **/
	MENU_IS_EXIST("OP2003","同一级别中，菜单已存在 "),
	
	/** MENU_ISNOT_EXIST: 菜单不存在 **/
	MENU_ISNOT_EXIST("OP2004","菜单不存在 "),
	
	/** ROLE_IS_USED: 该角色已经被使用无法删除 **/
	ROLE_IS_USED("OP2005","该角色已经被使用无法删除"),
	
	/** ROLE_NAME_IS_EXIST: 该角色名已存在 **/
	ROLE_NAME_IS_EXIST("OP2006","该角色名已存在"),
	
	/** SUB_MENU_IS_EXIST: 存在子菜单无法删除 **/
	SUB_MENU_IS_EXIST("OP2007","存在子菜单无法删除 "),
	
	/** RECORD_IS_NOT_EXIST: 该数据不存在或已删除无法操作 **/
	RECORD_IS_NOT_EXIST("OP2008","该数据不存在或已删除无法操作"),
	
	/** CAN_NOT_QUERY_POWER: 权限不存在 **/
	CAN_NOT_QUERY_POWER("OP2009","权限不存在"),
	
	/** DICTIONARY_CODE_IS_EXIST: 数据字典代码已存在**/
	DICTIONARY_CODE_IS_EXIST("OP2010","数据字典代码已存在"),
	
	/** DICTIONARY_KEY_IS_ENABLED: 数据字典已经启用无法删除**/
	DICTIONARY_KEY_IS_ENABLED("OP2011","数据字典已经启用无法删除"),
	
	/** DICTIONARY_KEY_IS_EXIST: 数据字典键已存在**/
	DICTIONARY_KEY_IS_EXIST("OP2012","数据字典键已存在"),
	
	/** DICTIONARY_CODE_NOT_EXIST: 数据字典代码不存在或已删除无法操作**/
	DICTIONARY_CODE_NOT_EXIST("OP2013","数据字典代码不存在或已删除，无法新增"),
	
	/** CAN_NOT_QUERY_HISTORY: 日志不存在 **/
	CAN_NOT_QUERY_HISTORY("OP2014","日志不存在"),
	
	/** OPERATOR_ISNOT_EXIST: 操作员不存在  **/
	OPERATOR_ISNOT_EXIST("OP2015","操作员不存在"),
	
	/** MENU_POWER_IS_EXIST: 菜单已分配权限无法删除 **/
	MENU_POWER_IS_EXIST("OP2016","菜单已分配权限无法删除"),
	
	/** USER_NOT_LOGIN: 用户未登录 **/
	USER_NOT_LOGIN("OP2017","用户未登录"),
	
	/** CAN_NOT_QUERY_FILE_INFO: 文件信息不存在 **/
	CAN_NOT_QUERY_FILE_INFO("OP2018","文件信息不存在"),
	
	/** DISCLOSURE_FILE_SHOW_NAME_IS_EXIST: 披露文件展示名已存在 **/
	DISCLOSURE_FILE_SHOW_NAME_IS_EXIST("OP2019","披露文件展示名已存在 "),

    /** ASSET_FILE_IS_EXIST: 文件名已存在 **/
    ASSET_FILE_IS_EXIST("OP2020","文件名已存在 "),

    /** QUERY_POWER_NOT_EXIST: 披露文件展示名已存在 **/
    QUERY_POWER_NOT_EXIST("OP2021","用户权限不存在 ");
	
	private String code;
	private String desc;

    ResultCode(String code,String desc){
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
    	for(ResultCode item : ResultCode.values()){
    		if(StringUtils.equals(item.code, code)){
    			return item.desc();
    		}
    	}
    	
    	return null;
    }
    
}
