package com.zb.fincore.pms.common.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 读取Properties综合类
 * @author kaiyun
 *
 */
public class PropertiesUtil {

    /**
     * 得到某一个类的路径 
     *
     * @param name
     * @return
     */
    public static String getPath(Class name) {
        String strResult = null;
        if (System.getProperty("os.name").toLowerCase().indexOf("window") > -1) {
            strResult = name.getResource("/").toString().replace("file:/", "")
                    .replace("%20", " ");
        } else {
            strResult = name.getResource("/").toString().replace("file:", "")
                    .replace("%20", " ");
        }
        return strResult;
    }

    /**
     * 读取所有的property
     * @param filePath properties文件路径
     * @return 所有的property的集合(map形式)
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> getPropties(String filePath) {
        if (null == filePath) {
            return null;
        }
        Properties props = new Properties();
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(filePath));
            props.load(in);
            Map<String, String> map = new HashMap<String, String>();
            Enumeration en = props.propertyNames();
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                String Property = props.getProperty(key);
                map.put(key, Property);
            }
            return map;
            // 关闭资源
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 获取某个property的值
     * @param filePath properties文件路径
     * @param key  property的key
     * @return property的value
     */
    public static String getProp(String filePath, String key) {
        if (null == filePath || null == key) {
            return null;
        }
        Properties props = new Properties();
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(filePath));
            props.load(in);
            return props.getProperty(key);
            // 关闭资源
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取某个property的值（只用于配置分离场景，默认环境变量=ENV_PMS所对应的env.properties属性文件）
     *
     * @param key  property的key
     * @return property的value
     */
    public static String getEnvProp(String key) {
        if (null == key) {
            return null;
        }

        String filePath = new StringBuffer().append(System.getenv("ENV_PMS")).append("\\").append("env.properties").toString();
        filePath = FilePathUtil.getRealFilePath(filePath.toString());

        Properties props = new Properties();
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(filePath));
            props.load(in);
            return props.getProperty(key);
            // 关闭资源
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        //${ENV_PMS}/env.properties
        //D:\env\pms\env.properties
        System.out.println(System.getenv("ENV_PMS"));
        System.out.println(System.getProperty("os.name"));
        System.out.println(PropertiesUtil.getPath(PropertiesUtil.class));

        String filePath = new StringBuffer().append(System.getenv("ENV_PMS")).append("\\").append("env.properties").toString();
        filePath = FilePathUtil.getRealFilePath(filePath.toString());
        System.out.println(filePath);

        String mailUserName = PropertiesUtil.getEnvProp("mail.receivers.username");
        System.out.println(mailUserName);

    }
}



