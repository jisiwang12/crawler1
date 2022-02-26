package cn.gsq.crawler;

import cn.gsq.crawler.annotation.ValueAnnotation;
import cn.gsq.crawler.thread.ThreadFactory;
import cn.gsq.crawler.utils.Meituri;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author qsefr
 * @program: sqgong
 * @description: 程序主入口
 * @date 2021-07-17 22:20:16
 */
public class Main {
    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor pool = ThreadFactory.getThreadPool();
        ValueAnnotation.run();
        Meituri.run(pool);
        pool.shutdown();
    }
}
