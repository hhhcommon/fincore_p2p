package com.zillionfortune.boss.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectionsUtil {
	
	/** 
     * 数组转换为List（推荐使用） 
     * 
     * @param arr 数组 
     * @return List 
     */ 
    public static List arrayList(Object[] arr) { 
            List list = new ArrayList(); 
            if (arr == null) return list; 
            list = Arrays.asList(arr); 
            return list; 
    } 

}
