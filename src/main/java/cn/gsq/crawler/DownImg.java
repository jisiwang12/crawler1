package cn.gsq.crawler;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DownImg extends Thread {
    private String url;
    private String path;
    private int count;

    public DownImg(String url, String path) {
        this.path = path;
        this.url = url;

    }


    @Override

    public void run() {

            for (int i = 1; i < 110; i++) {
                try {
                    FileUtils.copyURLToFile(new URL(url + i + ".jpg"), new File(path + i + ".jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("没得了");
                }
                System.out.println("下载了");

            }

    }

}
