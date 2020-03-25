package cn.gsq.crawler;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class JsoupTest {

    public static void main(String[] args) throws IOException {
        Document document = Jsoup.parse(new URL("https://www.meitulu.com/item/19011.html"), 10000);
        Elements imgs = document.select("img");
        String src = imgs.get(2).attr("src");
        System.out.println(imgs);
        FileUtils.copyURLToFile(new URL("https://mtl.ttsqgs.com/images/img/19011/1.jpg"),new File("imgs/1.jpg"));
    }

}

class Down extends Thread {

    private String url;
    private String path;

    public Down(String url,String path){
        this.path=path;
        this.url=url;
    }


    @Override

    public void run() {
        try {
            FileUtils.copyURLToFile(new URL(url), new File(path));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
