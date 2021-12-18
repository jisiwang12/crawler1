package cn.gsq.crawler;

import cn.gsq.crawler.annotation.ValueAnnotation;
import cn.gsq.crawler.thread.ThreadFactory;
import cn.gsq.utils.PathUtil2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.concurrent.*;

/**
 * @author gsq
 */
public class Meituri {

    public static void main(String[] args) throws Exception {
        String pathName = "https://tjg.gzhuibei.com/a/1/";
        ValueAnnotation.run();
        Document parse = Jsoup.parse(new URL(PathUtil2.PATH), (int) TimeUnit.HOURS.toMillis(3));
        Elements as = parse.select("p.biaoti>a");
        Elements shuliangList = parse.select("span.shuliang");
        //0-20
        //20-40
        ThreadPoolExecutor pool = ThreadFactory.getThreadPool();
        for (int i = 0; i < Integer.parseInt(PathUtil2.COUNT); i++) {
            URL url;
            String href = as.get(i).attr("href");
            String html = shuliangList.get(i).html();
            String number = html.split("P")[0];
            String[] split = href.split("/");
            String[] split1 = split[4].split("[.]");
            String substring = split1[0];
            System.out.println(substring);
            String s = "https://www.tujigu.net/a/";
            url = new URL(s + substring);
            Document document = Jsoup.parse(url, (int) TimeUnit.HOURS.toMillis(3));
            Elements h1 = document.select("h1");


            Element element = h1.get(0);
            String text = element.text();
            // 替换h1标题中的/
            String s1 = text.replaceAll("/", "-");
            System.out.println(pathName + substring);
            pool.execute(new DownImg(pathName + substring + "/", s1 + "/", Integer.parseInt(number)));
        }
        pool.shutdown();


    }

}



