package com.zillionfortune.boss.common.enums;

/**
 * ClassName: DictionaryStatus <br/>
 * Function: 数据字典状态. <br/>
 * Date: 2017年2月23日 下午4:57:29 <br/>
 *
 * @author pengting
 * @version 
 * @since JDK 1.7
 */
public enum DictionaryStatus {
	
	ENABLED(1,"已启用"),
	
	NOT_ENABLED(0,"未启用");

	private int code;
	
	private String desc;

	public int code() {
		return code;
	}

	public String desc() {
		return desc;
	}

	private DictionaryStatus(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
 	public static String getDesc(int code){
    	for(DictionaryStatus item : DictionaryStatus.values()){
    		if(item.code == code){
    			return item.desc();
    		}
    	}
    	
    	return null;
    }
	
}
