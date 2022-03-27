package cn.gsq.crawler.thread;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author qsefr
 * @program: sqgong
 * @description: 线程池工厂类
 * @date 2021-07-15 21:21:01
 */
public class ThreadFactory {

    private static final int CORE_POOL_SIZE = 30;
    private static final int MAX_POOL_SIZE = 40;
    private static final long KEEP_ALIVE_TIME = 3000;
    private static final int CAPACITY = 10000;
    private static int count = 0;

    private ThreadFactory() {
    }

    public static ThreadPoolExecutor getThreadPool() {
        return new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(CAPACITY)
                , (r) -> {
            Thread thread = new Thread(r);
            thread.setName("下载线程" + count);
            count++;
            return thread;
        });
    }



}
