package com.zb.p2p.customer.common.util;

import java.util.UUID;

public class UuidUtils {
	
	
	public static String getUUID(){ 
		String uuid = UUID.randomUUID().toString(); 
		//去掉“-”符号 
		return uuid.replaceAll("-", "");
		}
	
	
	public static void main(String[] args) {
		System.out.println(getUUID()+"---");
	}
}
