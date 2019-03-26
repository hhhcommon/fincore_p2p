package com.zillionfortune.boss.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MatchUtils {
	
	private MatchUtils(){
		
	}
	
	/**
	 * 验证手机号码
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }
	
	
	
	 /**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        boolean flag = false;
        try{
                String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern regex = Pattern.compile(check);
                Matcher matcher = regex.matcher(email);
                flag = matcher.matches();
            }catch(Exception e){
                flag = false;
            }
        return flag;
    }
    
}
