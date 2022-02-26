package cn.gsq.crawler.utils;


import cn.gsq.crawler.utils.PathUtil2;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DownImg implements Runnable {
    private String url;
    private String path;
    private Integer number;
    private String filePathStr = "J:\\美图录\\" + PathUtil2.FILEPATH + "\\";

    public DownImg(String url, String path, Integer number) {
        this.url = url;
        this.path = path;
        this.number = number;
    }


    @Override

    public void run() {

        for (int i = 1; i <= number; i++) {
            try {
                System.out.println(url + i + ".jpg");
                FileUtils.copyURLToFile(new URL(url + i + ".jpg"), new File(filePathStr + path + i + ".jpg"), 20000, 20000);
                System.out.println("下载了" + Thread.currentThread().getName());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("没得了");
            }

        }

    }

    public static void main(String[] args) throws Exception {
        int totalPage = 43;
        for (int i = 0; i < totalPage; i++) {

            Document parse = Jsoup.parse(new URL("https://www.shzx.org/a/143-7906-" + i + ".html"), 100000);
            Elements pics = parse.select(".picture");
            for (Element pic : pics) {
                for (Element img : pic.select("img")) {
                    String src = img.attr("src");
                    System.out.println(src);
                }

            }
            Thread.sleep(5);
        }


    }

}
