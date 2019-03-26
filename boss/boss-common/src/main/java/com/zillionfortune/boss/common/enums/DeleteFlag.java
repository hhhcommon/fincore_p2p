package com.zillionfortune.boss.common.enums;

public enum DeleteFlag {
	
	DELETED(1,"已删除"),
	
	EXISTS(0,"未删除");

	private int code;
	
	private String desc;

	public int code() {
		return code;
	}

	public String desc() {
		return desc;
	}

	private DeleteFlag(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
 	public static String getDesc(int code){
    	for(DeleteFlag item : DeleteFlag.values()){
    		if(item.code == code){
    			return item.desc();
    		}
    	}
    	
    	return null;
    }
	
}
