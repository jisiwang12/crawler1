package cn.gsq.crawler;

import cn.gsq.utils.PathUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Mtl_RunOne {

    public static void main(String[] args) throws Exception {
        URL url = null;
        String pathName = "https://mtl.gzhuibei.com/images/img/";
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("11438");
        strings.add("10614");
        strings.add("10442");
        strings.add("10440");
        strings.add("10437");





        for (String s : strings) {
            url = new URL("https://www.meitulu.com/item/" + s + ".html");
            Document document = Jsoup.parse(url, (int) TimeUnit.HOURS.toMillis(1));
            Elements h1 = document.select("h1");
            Element element = h1.get(0);
            String text = element.text();
            new DownImg(pathName + s + "/", text + "/",1).start();
        }

    }
}



