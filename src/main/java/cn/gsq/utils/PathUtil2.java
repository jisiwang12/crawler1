package cn.gsq.utils;

import cn.gsq.crawler.owninterface.Value;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 图片下载配置类1
 *
 * @author qsefr
 */
public class PathUtil2 {
    @Value("path")
    public static  String PATH;
    @Value("count")
    public static  String COUNT;

}
