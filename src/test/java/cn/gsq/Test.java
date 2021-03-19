package cn.gsq;

import cn.gsq.crawler.DownImg;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws Exception {
        String name = null;
        String u1 = null;
        String u2 = null;
        //Document parse1 = Jsoup.parse(new URL("https://www.nvshens.org/girl/27840/album/"), (int) TimeUnit.HOURS.toMillis(1));
        Document parse1 = Jsoup.connect("https://www.nvshens.org/girl/28036/album/").timeout(12000).followRedirects(false).execute().parse();
        Elements select1 = parse1.select(".igalleryli_link");
        for (int ij = 0; ij < 6; ij++) {
            String html = select1.get(ij).html();
            String[] split = html.split("/");
            u1 = split[4];
            u2 = split[5];
            StringBuilder stringBuilder = new StringBuilder("https://t1.onvshen.com:85/gallery/" + u1 + "/" + u2 + "/");
            URL url = new URL("https://www.nvshens.org/g/" + u2 + "/");
            Document parse = Jsoup.parse(url, (int) TimeUnit.HOURS.toMillis(1));
            Elements select = parse.select("#htilte");
            name = select.text() + "/";
            try {
                System.out.println(stringBuilder.toString() + "0.jpg");
                FileUtils.copyURLToFile(new URL(stringBuilder.toString() + "0.jpg"), new File(name + "0.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int i = 1; i < 100; i++) {

                if (i <= 9) {

                    try {
                        FileUtils.copyURLToFile(new URL(stringBuilder.toString() + "00" + i + ".jpg"), new File(name + i + ".jpg"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (i > 9) {
                    try {
                        FileUtils.copyURLToFile(new URL(stringBuilder.toString() + "0" + i + ".jpg"), new File(name + i + ".jpg"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("下载成功");
                }
            }
            System.out.println("下载完毕");
        }


    }

    @org.junit.Test
    public void test() {
        for (int i = 1; i < 120; i++) {
            try {
                FileUtils.copyURLToFile(new URL("https://lns.hywly.com/a/1/27924/" + i + ".jpg"), new File("1/" + i + ".jpg"));
                Thread.sleep(100);
                System.out.println("下载了");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("没得了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
