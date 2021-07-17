package cn.gsq.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author qsefr
 * @program: sqgong
 * @description: 读取properties配置工具类
 * @date 2021-07-17 19:01:37
 */
public class PropertiesUtils {

    private PropertiesUtils() {}

    public static String getKey(String key) {
        String value = "";
        Properties properties = new Properties();
        InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            properties.load(inputStream);
             value= properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;

    }

}
