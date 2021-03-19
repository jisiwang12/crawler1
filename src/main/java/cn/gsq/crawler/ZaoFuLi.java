package cn.gsq.crawler;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ZaoFuLi extends Thread {
    public String path = null;
    public int total;

    /**
     * 网址和页数
     * @param path
     * @param total
     */
    public ZaoFuLi(String path,int total) {
        this.path = path;
        this.total = total;
    }

    @Override
    public void run() {
        int page = 0;
        URL url = null;
        try {
            url = new URL(path+".html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = Jsoup.parse(url, (int) TimeUnit.HOURS.toMillis(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements select = document.select("h1.article-title");
        String name = select.get(0).text();
        System.out.println(name);
        Elements elements = document.select("p>img");
        for (int i=0;i<elements.size();i++) {
            String src = elements.get(i).attr("src");
            try {
                FileUtils.copyURLToFile(new URL(src), new File(name+"/" + page + ".jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            page++;
            System.out.println(name+"下载成功,第"+page+"张");
        }


        for (int i = 2; i <=total; i++) {
            URL url1 = null;
            try {
                url1 = new URL(path + "_" + i + ".html");
                System.out.println(path + "_" + i + ".html");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Document document1 = null;
            try {
                document1 = Jsoup.parse(url1, (int) TimeUnit.HOURS.toMillis(1));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements elements1 = document1.select("p>img");
            for (int j=0;j<elements1.size();j++) {
                String src = elements1.get(j).attr("src");
                try {
                    FileUtils.copyURLToFile(new URL(src), new File(name+"/" + page + ".jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                page++;
                System.out.println(name+"下载成功,第"+page+"张");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
        System.out.println(name);
        System.out.println("共计"+page+"张，全部下载完毕");
    }
}
