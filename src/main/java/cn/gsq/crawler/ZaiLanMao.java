package cn.gsq.crawler;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ZaiLanMao extends Thread  {

    public String path = "http://www.zhainancat.com/14481.html";

    public ZaiLanMao(String path) {
        this.path = path;
    }

    @Override
    public void run() {
        int page = 1;
        Document document = null;
        try {
            document = Jsoup.parse(new URL(path), (int) TimeUnit.HOURS.toMillis(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements select = document.select("p>img");
        Elements elements_h1 = document.select("h1");
        String h1 = elements_h1.text();
        System.out.println(h1);
        String src = select.get(0).attr("src");
        if (src.contains("http")) {
            try {
                FileUtils.copyURLToFile(new URL(src), new File(h1 + "/" + page + ".jpg"));
                System.out.println(h1+"已经下载第1张");
                page++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String[] split = src.split("[-]");
            try {
                FileUtils.copyURLToFile(new URL("https://p3-tt.byteimg.com/origin/pgc-image/" + split[1]), new File(h1 + "/" + page + ".jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(h1+"已经下载第1张");
            page++;
        }

        Elements as = document.select("div.fenye>a");
        for (int i = 2; i < as.size(); i++) {
            String path1 = path + "/" + i;
            Document document1 = null;
            try {
                document1 = Jsoup.parse(new URL(path1), (int) TimeUnit.HOURS.toMillis(1));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements select1 = document1.select("p>img");
            String src1 = select1.get(0).attr("src");
            if (src1.contains("http")) {
                try {
                    FileUtils.copyURLToFile(new URL(src1), new File(h1 + "/" + page + ".jpg"));
                    System.out.println(h1 + "已经下载第" + page + "张");
                    page++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                String[] split1 = src1.split("[-]");
                try {
                    FileUtils.copyURLToFile(new URL("https://p3-tt.byteimg.com/origin/pgc-image/" + split1[1]), new File(h1 + "/" + page + ".jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(h1+"已经下载第"+page+"张");
                page++;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(h1+"下载完毕,共"+(page-1)+"张");
    }


}
