package cn.gsq.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * 宅福利
 */
public class ZaoFuLiMain {
    public static void main(String[] args) throws Exception {
        //页数
        int total = 23;
        //网址
        String path = "https://so.azs2019.com/serch.php?keyword=%D0%A1%B2%CC%CD%B7&page=2";

        Document document = Jsoup.parse(new URL(path), (int) TimeUnit.HOURS.toMillis(1));
        Elements a = document.select("h2>a");
        for (int i=0;i<a.size();i++) {
            String href = a.get(i).attr("href");

            if (href.contains("http")) {
                String[] split = href.split("[.]");
                new ZaoFuLi(split[0]+"."+split[1], total).start();
            } else {
                String[] split = href.split("[.]");
                new ZaoFuLi("https://qqg568.com/"+split[0],total).start();

            }
        }

    }
}
