package cn.gsq.crawler;

import cn.gsq.utils.PathUtil2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Meituri2 {

    public static void main(String[] args) throws Exception {
//        DownImg downImg=null;

        URL url = null;
        String pathName = "https://tjg.hywly.com/a/1/";
        Document parse = Jsoup.parse(new URL(PathUtil2.PATH), (int) TimeUnit.HOURS.toMillis(3));
        Elements as = parse.select("p.biaoti>a");
        Elements shuliangList = parse.select("span.shuliang");
        //0-20
        //20-40
        for (int i = 20; i <25; i++) {
            String href = as.get(i).attr("href");
            String html = shuliangList.get(i).html();
            String number = html.split("P")[0];
            String[] split = href.split("/");
            String[] split1 = split[4].split("[.]");
            String substring = split1[0];
            System.out.println(substring);
            url = new URL("https://www.tujigu.com/a/" + substring);
            Document document = Jsoup.parse(url, (int) TimeUnit.HOURS.toMillis(3));
            Elements h1 = document.select("h1");
            Element element = h1.get(0);
            String text = element.text();
            System.out.println(pathName + substring + "/" + i + "/");
            new DownImg(pathName + substring + "/", text + "/",Integer.parseInt(number)).start();


        }
    }
}



