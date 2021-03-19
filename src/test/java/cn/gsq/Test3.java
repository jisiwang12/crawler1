package cn.gsq;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Test3 extends Thread {
    public int num;
    String path = null;

    /* @Test
     public void Test() throws Exception {
         String path = "http://img.itmtu.cc/xz/mslass/VOL.044";
         String fileName = "JK小女神 玥玥 [66P-537MB]";
         for (int i = 1; i <= 67; i++) {
             if (i < 10) {
                 FileUtils.copyURLToFile(new URL(path + "/000" + i + ".jpg"), new File(fileName+"/"+i + ".jpg"));
             } else {
                 FileUtils.copyURLToFile(new URL(path + "/00" + i + ".jpg"), new File(fileName+"/"+i + ".jpg"));
             }
             System.out.println("已下载...第"+i+"张");
         }

     }

     }*/
    public Test3(String path,int num) {
        this.path = path;
        this.num = num;
    }

    @Override
    public void run() {
        try {
            for (int i=1;i<=80;i++) {
                if (i < 10) {
                    FileUtils.copyURLToFile(new URL(path + "/000" + i + ".jpg"), new File("NO.0"+num+"/"+i + ".jpg"));
                } else {
                    FileUtils.copyURLToFile(new URL(path + "/00" + i + ".jpg"),new File("NO.00"+num+"/"+i + ".jpg"));
                }
                Thread.sleep(100);
                System.out.println("线程+"+Thread.currentThread().getId()+"已下载...第"+i+"张");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
  /*      String path = "http://www.itmtu.net/taotu/yaojing.html";
        Document document = Jsoup.parse(new URL(path), (int) TimeUnit.HOURS.toMillis(1));
        System.out.println(document);
        Elements elements = document.select("img.waitpic");
        for (Element element : elements) {
            System.out.println(element.attr("href"));
        }*/
        String path = "http://img.itmtu.cc/xz/yaojing/NO.001/0058.JPG";
        for (int i = 10; i < 14; i++) {
            path = path + "i";
            new Test3("http://img.itmtu.cc/xz/yaojing/NO.00"+i,i).start();
            Thread.sleep(1000);
        }
    }




}
