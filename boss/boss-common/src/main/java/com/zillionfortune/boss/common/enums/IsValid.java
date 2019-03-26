package com.zillionfortune.boss.common.enums;

public enum IsValid {
	
	VALID(1,"有效"),
	
	INVALID(2,"无效");

	private int code;
	
	private String desc;

	public int code() {
		return code;
	}

	public String desc() {
		return desc;
	}

	private IsValid(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
 	public static String getDesc(int code){
    	for(IsValid item : IsValid.values()){
    		if(item.code == code){
    			return item.desc();
    		}
    	}
    	
    	return null;
    }
	
}
