package com.zillionfortune.boss.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper; 
import net.sf.json.JSONArray; 
import net.sf.json.JSONObject; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
 
import java.math.BigDecimal; 
import java.util.ArrayList; 
import java.util.Iterator; 
import java.util.List; 

public class JacksonUtils {

	/** 
     * 日志记录器 
     */ 
    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class); 
 
    /** 
     * 安全获取json内容 
     * 
     * @param object 
     * @param key 
     * @return 
     */ 
    public static String getString(JSONObject object, String key) { 
        try { 
            return object.getString(key); 
        } catch (Exception e) { 
            logger.error("获取String异常", e); 
            return null; 
        } 
    } 
 
    /** 
     * 安全获取json内容 
     * 
     * @param object 
     * @param key 
     * @return 
     */ 
    public static int getInt(JSONObject object, String key) { 
        try { 
            return object.getInt(key); 
        } catch (Exception e) { 
            logger.error("获取Int异常", e); 
            return 0; 
        } 
    } 
 
    /** 
     * 安全获取json内容 
     * 
     * @param object 
     * @param key 
     * @return 
     */ 
    public static long getLong(JSONObject object, String key) { 
        try { 
            return object.getLong(key); 
        } catch (Exception e) { 
            logger.error("获取Long异常", e); 
            return 0L; 
        } 
    } 
 
    /** 
     * 安全获取json内容 
     * 
     * @param object 
     * @param key 
     * @return 
     */ 
    public static BigDecimal getBigDecimal(JSONObject object, String key) { 
        try { 
            return new BigDecimal(object.getString(key)); 
        } catch (Exception e) { 
            logger.error("获取BigDecimal异常", e); 
            return null; 
        } 
    } 


 
    /** 
     * 安全转换bean 
     * 
     * @param object 
     * @param classType 
     * @param <T> 
     * @return 
     */ 
    public static <T> T toBean(JSONObject object, Class classType) { 
        try { 
            ObjectMapper mapper = new ObjectMapper(); 
            return (T) mapper.readValue(object.toString(), classType); 
        } catch (Exception e) { 
            logger.error("JsonToBean异常", e); 
            return null; 
        } 
    } 
 
    /** 
     * 获取bean列表 
     * 
     * @param array 
     * @param classType 
     * @param <T> 
     * @return 
     */ 
    public static <T> List<T> toBeanList(JSONArray array, Class classType) { 
        try { 
            Iterator<JSONObject> it = array.iterator(); 
            List<T> beanList = new ArrayList<T>(); 
            while (it.hasNext()) { 
                JSONObject object = it.next(); 
                beanList.add((T) toBean(object, classType)); 
            } 
            return beanList; 
        } catch (Exception e) { 
            logger.error("JsonToBeanList异常", e); 
            return null; 
        } 
    } 
 
    /** 
     * 安全获取JSONObject 
     * 
     * @param object 
     * @param key 
     * @return 
     */ 
    public static JSONObject getJSONObject(JSONObject object, String key) { 
        try { 
            return object.getJSONObject(key); 
        } catch (Exception e) { 
            logger.error("获取JSONObject异常", e); 
            return null; 
        } 
    } 
 
    /** 
     * 安全获取JSONArray 
     * 
     * @param object 
     * @param key 
     * @return 
     */ 
    public static JSONArray getJSONArray(JSONObject object, String key) { 
        try { 
            return object.getJSONArray(key); 
        } catch (Exception e) { 
            logger.error("获取JSONArray异常", e); 
            return null; 
        } 
    } 
}
