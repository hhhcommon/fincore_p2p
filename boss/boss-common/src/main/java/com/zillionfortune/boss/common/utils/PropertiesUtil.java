package com.zillionfortune.boss.common.utils;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * Description: Properties文件操作类<br />
 * </p>
 *
 * @author wangwanbin
 * @title PropertiesUtil.java
 * @package com.shtic.shared.util
 */
public class PropertiesUtil {

    private PropertiesUtil() {
    }

    private static Properties properties = new Properties();
    private String[] fileNames;

    protected Logger logger = Logger.getLogger(getClass());

    /**
     * @param @throws Exception 设定文件
     * @return void 返回类型
     * @throws
     * @Title: load
     */
    public void load() throws Exception {
        if (fileNames.length == 0) {
            return;
        }
        for (String filename : fileNames) {
            String path = StringUtils.substringAfter(filename, ":");
            InputStream in = null;
            try {
                if (filename.startsWith("classpath")) {
                    in = this.getClass().getClassLoader().getResourceAsStream(path);
                    properties.load(in);
                } else if (filename.startsWith("file")) {
                    in = new FileInputStream(path);
                    properties.load(in);
                } else {
                    logger.error("不支持的前缀类型:" + path);
                }
            } catch (IOException e) {
                logger.error("文件加载失败", e);
            } finally {
                IOUtils.closeQuietly(in);
            }
        }
    }

    /**
     * @param key
     * @return String 返回类型
     * @throws
     * @Title: getValue
     * @Description: 通过key获取属性文件的值
     */
    public static String getValue(String key) {
        if (properties.containsKey(key)) {
            String value = properties.getProperty(key);// 得到某一属性的值
            return value;
        } else {
            return "";
        }
    }

    public void setFileNames(String[] fileNames) {
        this.fileNames = fileNames;
    }
}
