package cn.gsq.utils;

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
    public static final String PATH = PropertiesUtils.getKey("path");
    public static final int COUNT = Integer.parseInt(PropertiesUtils.getKey("count"));

    //https://www.tujigu.com/x/45/index_12.html


/*    public static void main(String[] args) {
        AtomicInteger count = new AtomicInteger();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 15, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(300));
        for (int i = 0; i < 300; i++) {
            threadPoolExecutor.execute(() -> {
                count.getAndIncrement();
                System.out.println(Thread.currentThread().getName());
            });

        }
        threadPoolExecutor.shutdown();
    }*/
}
