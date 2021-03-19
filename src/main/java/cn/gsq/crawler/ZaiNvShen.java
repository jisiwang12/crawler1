package cn.gsq.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ZaiNvShen {
    public static void main(String[] args) throws Exception {
        URL url = new URL("https://www.nvshens.org/girl/27840/album/");
        Document document = Jsoup.parse(url, (int) TimeUnit.HOURS.toMillis(1));
        Elements el = document.select(".photo_ul");
        System.out.println(el);
        Elements img = el.select("[src]");
        System.out.println(img);
       /* for (Element element : img) {
            String text = element.attr("src");
            System.out.println(text);
        }*/
    }
}
