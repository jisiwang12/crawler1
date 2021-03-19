package cn.gsq.crawler;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadLearn {
    public static void main(String[] args) {
         int count=0;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                    System.out.println("lihao1");

                }
            });
        }
    }
}
