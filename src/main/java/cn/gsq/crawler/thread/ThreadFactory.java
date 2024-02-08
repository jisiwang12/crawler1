package cn.gsq.crawler.thread;

import org.jsoup.Jsoup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qsefr
 * @program: sqgong
 * @description: 线程池工厂类
 * @date 2021-07-15 21:21:01
 */
public class ThreadFactory implements AsyncConfigurer {


    private static final int CORE_POOL_SIZE = 30;
    private static final int MAX_POOL_SIZE = 30;
    private static final long KEEP_ALIVE_TIME = 0;
    private static final AtomicInteger COUNT = new AtomicInteger(1);
    private static final AtomicInteger URL_COUNT = new AtomicInteger(1);


    public static ThreadPoolExecutor getThreadPool() {
        return new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>()
                , (r) -> {
            Thread thread = new Thread(r);
            thread.setName("下载线程" + COUNT.getAndIncrement());
            return thread;
        });
    }

    /**
     * 图集主线程池
     *
     * @return
     */
    public static ThreadPoolExecutor getThreadPoolDownUrl() {
        return new ThreadPoolExecutor(50, 50, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>()
                , (r) -> {
            Thread thread = new Thread(r);
            thread.setName("下载图片线程" + URL_COUNT.getAndIncrement());
            return thread;
        });
    }


}
