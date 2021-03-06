package cn.gsq.crawler;

import cn.gsq.utils.PathUtil2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Meituri {

    public static void main(String[] args) throws Exception {
//        DownImg downImg=null;
        URL url = null;
        String pathName = "https://ii.hywly.com/a/1/";
        Document parse = Jsoup.parse(new URL(PathUtil2.path), (int) TimeUnit.HOURS.toMillis(1));
        Elements as = parse.select("p.biaoti>a");
        //0-15
        //15-30
        //30-45
        //45-60
        for (int i=0;i<20;i++) {
            String href = as.get(i).attr("href");
            String[] split = href.split("/");
            String[] split1 = split[4].split("[.]");
            String substring = split1[0];
            System.out.println(substring);
            url = new URL("https://www.meituri.com/a/" + substring);
            Document document = Jsoup.parse(url, (int) TimeUnit.HOURS.toMillis(1));
            Elements h1 = document.select("h1");
            Element element = h1.get(0);
            String text = element.text();
            new DownImg(pathName + substring + "/", text + "/").start();
        }

        /*for (int i = 18576; i <= 18587; i++) {
            url = new URL("https://www.meitulu.com/item/" + i + ".html");
            Document document = Jsoup.parse(url, 100000);
            Elements h1 = document.select("h1");
            Element element = h1.get(0);
            String text = element.text();
            new DownImg(pathName + i + "/", text + "/").start();
        }*/
    }
}



