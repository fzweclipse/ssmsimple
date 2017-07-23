package com.fuzhongwangcs.ssmsimple.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: lazyeclipse
 * @Description: 系统配置文件管理类
 * @Date: 2017/6/1 11:34
 */
public class SystemPropertiesUtil {
    static Properties prop = new Properties();

    static {
        InputStream in = SystemPropertiesUtil.class.getResourceAsStream("/system.properties");
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        String value = prop.getProperty(key).trim();
        return value;
    }
}