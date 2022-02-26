package cn.gsq.crawler;

import cn.gsq.crawler.annotation.ValueAnnotation;
import cn.gsq.crawler.thread.ThreadFactory;
import cn.gsq.utils.PathUtil2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * @author gsq
 */
public class Meituri {

    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor pool = ThreadFactory.getThreadPool();
        ValueAnnotation.run();
        Meituri.run(pool);
        pool.shutdown();
    }

    public static void run(ThreadPoolExecutor pool) throws IOException {
        // 下载的图片路径前缀
        String pathName = "https://tjg.gzhuibei.com/a/1/";
        String cookie = "UM_distinctid=17e388968268e4-08f291879f3ba1-f791b31-1fa400-17e388968271a5e; PHPSESSID=pcedtk7uj6hinnd6ai67t33sl6; fr=tuji001_404; CNZZDATA1257039673=237528808-1641620484-https%3A%2F%2Fwww.tuji001.com%2F|1642826307; uid=313524; name=jisiwang12; leixing=0";
        Document parse = Jsoup.connect(PathUtil2.PATH).cookies(Meituri.convertCookie(cookie)).get();
        Elements as = parse.select("p.biaoti>a");
        Elements shuliangList = parse.select("span.shuliang");
        //0-20
        //20-40
        int countReal = 20;
        if (20 < Integer.parseInt(PathUtil2.COUNT)) {
            PathUtil2.COUNT = Integer.parseInt(PathUtil2.COUNT) - 20 + "";
            PathUtil2.PATH = PathUtil2.PATH + "&page=" + PathUtil2.pageCount;
            PathUtil2.pageCount += 1;
            run(pool);
        } else {
            countReal = Integer.parseInt(PathUtil2.COUNT);
        }
        for (int i = 0; i < countReal; i++) {
            URL url;
            String href = null;
            try {
                href = as.get(i).attr("href");
            } catch (Exception e) {
                break;
            }
            String html = shuliangList.get(i).html();
            String number = html.split("P")[0];
            // 获取图集编号
            String[] split = href.split("id=");
            String substring = split[1];
            System.out.println(substring);
            // 标题
            String text = as.get(i).text();
            // 替换h1标题中的/
            String s1 = text.replaceAll("/", "-");
            System.out.println(pathName + substring);
            pool.execute(new DownImg(pathName + substring + "/", s1 + "/", Integer.parseInt(number)));
        }
    }

    public static HashMap<String, String> convertCookie(String cookie) {
        HashMap<String, String> cookiesMap = new HashMap<String, String>();
        String[] items = cookie.trim().split(";");
        for (String item:items) cookiesMap.put(item.split("=")[0], item.split("=")[1]);
        return cookiesMap;
    }


}



