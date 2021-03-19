package cn.gsq.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ZaiLanMaoMain {
    public static void main(String[] args) throws Exception {
        String path = "http://www.zhainancat.com/tag/%e7%8e%8b%e8%83%96%e8%83%96u%e5%9b%be%e5%8c%85";

        Document document = Jsoup.parse(new URL(path), (int) TimeUnit.HOURS.toMillis(1));
        Elements select = document.select("h2>a");
       /* for (int i=0;i<4;i++) {
            String href = select.get(i).attr("href");
            System.out.println(href);
            new ZaiLanMao(href).start();
            Thread.sleep(3000);
        }*/
        new ZaiLanMao("http://www.zhainancat.com/14147.html").start();
    }
}
