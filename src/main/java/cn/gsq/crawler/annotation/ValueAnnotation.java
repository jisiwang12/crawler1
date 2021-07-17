package cn.gsq.crawler.annotation;

import cn.gsq.crawler.owninterface.Value;
import cn.gsq.utils.PathUtil2;
import cn.gsq.utils.PropertiesUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author qsefr
 * @program: sqgong
 * @description: value注解反射类
 * @date 2021-07-17 21:46:38
 */
public class ValueAnnotation {
    public static void run() {
        // 获取目标内class
        Class<PathUtil2> pathUtil2Class = PathUtil2.class;
        // 所有属性
        Field[] fields = pathUtil2Class.getFields();
        for (Field field : fields) {
            // 判断属性上是否有value注解
            if (field.isAnnotationPresent(Value.class)) {
                // 获取注解上的属性值
                String value = field.getAnnotation(Value.class).value();
                String key = PropertiesUtils.getKey(value);
                try {
                    // 将配置文件中的赋值给PathUtil2中的属性
                    field.set(null,key);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
