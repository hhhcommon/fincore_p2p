package com.zillionfortune.boss.common.enums;

public enum ProductTypeEnum {
	
CURRENT(1,"活期"),
	
	INTERVAL(2,"定期");

	private int code;
	
	private String desc;

	public int code() {
		return code;
	}

	public String desc() {
		return desc;
	}

	private ProductTypeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
 	public static String getDesc(int code){
    	for(ProductTypeEnum item : ProductTypeEnum.values()){
    		if(item.code == code){
    			return item.desc();
    		}
    	}
    	
    	return null;
    }
	
}
