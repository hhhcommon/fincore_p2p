package com.zb.p2p.customer.web.config;

public class DataBaseContextHolder {
	
	public enum DataBaseType {
		MASTER("master"),SLAVE("slave");
			
		private String code;
		
		DataBaseType(String code){
			this.code = code;
		}
		
		public String getCode() {
			return code;
		}	
	}
		 
	private static final ThreadLocal<DataBaseType> contextHolder = new ThreadLocal<DataBaseType>();
 
	public static void setDataBaseType(DataBaseType dataBaseType){
		if(dataBaseType == null)throw new NullPointerException();
		contextHolder.set(dataBaseType);
	}
 
	public static DataBaseType getDataBaseType(){
		return contextHolder.get() == null ? DataBaseType.MASTER : contextHolder.get();
	}
 
	public static void clearDataBaseType(){
		contextHolder.remove();
 	}
		  
}
