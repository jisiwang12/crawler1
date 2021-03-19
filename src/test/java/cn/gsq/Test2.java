package cn.gsq;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Test2 extends Thread {
    private String url = null;
    private int i = 0;
    public Test2(String url,int i) {
        this.url = url;
        this.i=i;
    }

    @Override
    public void run() {
        try {
            FileUtils.copyURLToFile(new URL(url),new File("/"+i+".zip"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        String url = "https://www.ku137.net/b/76/list_76_2.html";
        Document document = Jsoup.parse(new URL(url), (int) TimeUnit.HOURS.toMillis(1));
        Elements select = document.select("ul.cl>li>a");
        for (int i=3;i<8;i++) {
            String href = select.get(i).attr("href");
            System.out.println(href);
            Document parse = Jsoup.parse(new URL(href), (int) TimeUnit.HOURS.toMillis(2));
            Elements select1 = parse.select("h1>a");
            Element down = select1.get(1);
            String urlDown = down.attr("href");
            new Test2(urlDown, i).start();
            Thread.sleep(2000);

        }
    }
}
