package cn.gsq.crawler.owninterface;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author qsefr
 * @program: sqgong
 * @description: 读取配置文件注解类
 * @date 2021-07-17 21:35:44
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Value {
    String value();
}
