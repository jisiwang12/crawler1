package cn.gsq.crawler;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DownImg implements Runnable {
    private String url;
    private String path;
    private Integer number;

    public DownImg(String url, String path,Integer number) {
        this.path = path;
        this.url = url;
        this.number=number;

    }


    @Override

    public void run() {

            for (int i = 1; i <= number; i++) {
                try {
                    FileUtils.copyURLToFile(new URL(url + i + ".jpg"), new File(path + i + ".jpg"));
                    System.out.println("下载了"+Thread.currentThread().getName());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("没得了");
                }

            }

    }

}
