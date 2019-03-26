package com.zb.fincore.pms.common.utils;

/**
 * JAVA自动适配Linux与Windows文件路径分隔符-工具类 <br/>
 * @author kaiyun@zillionfortune.com
 */
public class FilePathUtil {  
	/**
	 * 能根据系统的不同获取文件路径的分隔符
	 */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");  
  
    public static String getRealFilePath(String path) {  
        return path.replace("/", FILE_SEPARATOR).replace("\\", FILE_SEPARATOR);  
    }  
  
    public static String getHttpURLPath(String path) {  
        return path.replace("\\", "/");  
    }  
} 
