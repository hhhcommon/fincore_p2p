package com.zb.p2p.paychannel.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by dell on 2015/10/13.
 */
public class ReflectHelper {
    /**
     * 获取obj对象fieldName的Field
     * @param obj
     * @param fieldName
     * @return
     */
    public static Field getFieldByFieldName(Object obj, String fieldName) {
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
            }
        }
        return null;
    }

    /**
     * 获取obj对象fieldName的属性值
     * @param obj
     * @param fieldName
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Object getValueByFieldName(Object obj, String fieldName)
            throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        Field field = getFieldByFieldName(obj, fieldName);
        Object value = null;
        if(field!=null){
            if (field.isAccessible()) {
                value = field.get(obj);
            } else {
                field.setAccessible(true);
                value = field.get(obj);
                field.setAccessible(false);
            }
        }
        return value;
    }

    /**
     * 设置obj对象fieldName的属性值
     * @param obj
     * @param fieldName
     * @param value
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void setValueByFieldName(Object obj, String fieldName,
                                           Object value) throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        if (field.isAccessible()) {
            field.set(obj, value);
        } else {
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        }
    }

    /**
     * Map转换为Object
     * @param map
     * @param thisObj
     */
    @SuppressWarnings("rawtypes")
	public static void converMapToObject(Map map,Object thisObj)
    {
        Set set = map.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext())
        {
            Object obj = iterator.next();
            Object val = map.get(obj);
            setMethod(obj, val, thisObj);
        }
    }

    /**
     * 字段映射
     * @param method 字段名称
     * @param value  字段值
     * @param thisObj 对象名称
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setMethod(Object method, Object value ,Object thisObj)
    {
        Class c;
        try
        {
            c = Class.forName(thisObj.getClass().getName());
            String met = (String) method;
            met = met.trim();
            if (!met.substring(0, 1).equals(met.substring(0, 1).toUpperCase()))
            {
                met = met.substring(0, 1).toUpperCase() + met.substring(1);
            }
            if (!String.valueOf(method).startsWith("set"))
            {
                met = "set" + met;
            }
            Field[] fields = thisObj.getClass().getDeclaredFields();//获取某个类的所有字段
            Map<String,Object> fieldMap=new HashMap<String,Object>();
            for(int i=0;i<fields.length;i++){
                Field fld=fields[i];
                fieldMap.put(fld.getName(),fld.getType());
            }
            Class fieldType=(Class)fieldMap.get(method);
            Method m = c.getMethod(met, fieldType);
            m.invoke(thisObj, value);
        }
        catch (Exception e)
        {
           
            e.printStackTrace();
        }
    }
/*
    public static void main(String args[]){
        HashMap map=new HashMap();
        map.put("dictCode","000");
        map.put("dictValue","111");
        SysDict _bean=new SysDict();
        ReflectHelper.converMapToObject(map,_bean);
        System.out.println(_bean.toString());
    }*/


}
